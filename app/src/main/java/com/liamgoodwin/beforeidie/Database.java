package com.liamgoodwin.beforeidie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    /**
     * Keep Track of the database version
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * create the name of the database
     */
    private static final String DATABASE_NAME = "beforeidie";

    /**
     * Create the names of all the tables
     */
    private static final String TABLE_BUCKET_LIST = "bucket_list";
    private static final String TABLE_IMAGE = "image";
    private static final String TABLE_IMAGELOCATION = "image_location";
    private static final String TABLE_RECOMMENDATIONS = "recommendations";

    /**
     * Common column names
     */
    private static final String COLUMN_ID = "id";

    /**
     * Bucket List Table Column Names
     */
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_COMPLETED = "completed";

    /**
     * Image Table Column Names
     */
    private static final String COLUMN_RESOURCE = "resource";

    /**
     * Image Location Table Column Names
     */
    private static final String COLUMN_PICTURE = "id_picture";
    private static final String COLUMN_LOCATION = "id_location";

    /**
     * Create statements for all of our tables
     */

    private static final String CREATE_BUCKET_LIST_TABLE = "CREATE TABLE " + TABLE_BUCKET_LIST + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_NAME + " TEXT," + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_TIME + " BIGINT," + COLUMN_COMPLETED + " INT" + ")";

    private static final String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_RESOURCE + " TEXT" + ")";

    private static final String CREATE_IMAGE_LOCATION_TABLE = "CREATE TABLE " + TABLE_IMAGELOCATION + "("
            + COLUMN_LOCATION + " INTEGER REFERENCES " + TABLE_BUCKET_LIST + "(" + COLUMN_ID + "),"
            + COLUMN_PICTURE + " INTEGER REFERENCES " + TABLE_BUCKET_LIST + "(" + COLUMN_ID + ")" + ")";

    private static final String CREATE_RECOMMENDATIONS_TABLE = "CREATE TABLE " + TABLE_RECOMMENDATIONS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_NAME + " TEXT," + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_PICTURE + " INT" + ")";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create the tables inside of the database
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BUCKET_LIST_TABLE);
        db.execSQL(CREATE_IMAGE_TABLE);
        db.execSQL(CREATE_IMAGE_LOCATION_TABLE);
        db.execSQL(CREATE_RECOMMENDATIONS_TABLE);
    }

    /**
     * When the database upgrades delete the old tables and recreate them
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGELOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGELOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECOMMENDATIONS);
        onCreate(db);
    }

    /**
     * CRUD OPERATIONS FOR THE DATABASE AND TABLES
     */

    /**
     * CREATE new objects for the tables
     */
    public void addBucketlist(Bucketlist bucketlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, bucketlist.getName());
        values.put(COLUMN_DESCRIPTION, bucketlist.getDescription());
        values.put(COLUMN_TIME, String.valueOf(bucketlist.getTime()));
        values.put(COLUMN_COMPLETED, bucketlist.getCompleted());
        db.insert(TABLE_BUCKET_LIST, null, values);
        db.close();
    }

    //We modified addPicture to return the rowNumber it was added into
    public int addImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE, image.getResource());
        db.insert(TABLE_IMAGE, null, values);
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if (cursor.moveToFirst()) {
            int location = Integer.parseInt(cursor.getString(0));
            System.out.println("Record ID " + location);
            db.close();
            return location;
        }
        return -1;
    }

    //Added a method that will add an image location record
    public void addImageLocation(int image, int location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_PICTURE, image);
        db.insert(TABLE_IMAGELOCATION, null, values);
        db.close();
    }

    /**
     * READ objects from database
     */
    public String getTime(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BUCKET_LIST,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_TIME}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(3);
    }

    public ArrayList<Bucketlist> getAllNames() {
        ArrayList<Bucketlist> bucketList = new ArrayList<Bucketlist>();
        String selectQuery = "SELECT  * FROM " + TABLE_BUCKET_LIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Bucketlist bl = new Bucketlist();
                bl.setName(cursor.getString(1));
                bucketList.add(bl);
            } while (cursor.moveToNext());
        }
        return bucketList;
    }

    public ArrayList<Bucketlist> getAllBucketlist() {
        ArrayList<Bucketlist> bucketList = new ArrayList<Bucketlist>();
        String selectQuery = "SELECT  * FROM " + TABLE_BUCKET_LIST + " WHERE " + COLUMN_COMPLETED + " = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Bucketlist bl = new Bucketlist();
                bl.setId(Integer.parseInt(cursor.getString(0)));
                bl.setName(cursor.getString(1));
                bl.setDescription(cursor.getString(2));
                bl.setTime(cursor.getLong(3));
                bl.setCompleted(cursor.getInt(4));
                bucketList.add(bl);
            } while (cursor.moveToNext());
        }
        return bucketList;
    }

    public ArrayList<Bucketlist> getAllBucketlistCompleted() {
        ArrayList<Bucketlist> bucketList = new ArrayList<Bucketlist>();
        String selectQuery = "SELECT  * FROM " + TABLE_BUCKET_LIST + " WHERE " + COLUMN_COMPLETED + " = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Bucketlist bl = new Bucketlist();
                bl.setId(Integer.parseInt(cursor.getString(0)));
                bl.setName(cursor.getString(1));
                bl.setDescription(cursor.getString(2));
                bl.setTime(cursor.getLong(3));
                bl.setCompleted(cursor.getInt(4));
                bucketList.add(bl);
            } while (cursor.moveToNext());
        }
        return bucketList;
    }

    public Image getImage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_IMAGE, new String[]{COLUMN_ID, COLUMN_RESOURCE}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Image image = new Image(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));

        return image;
    }

    public ArrayList<Image> getAllImages() {
        ArrayList<Image> pictureList = new ArrayList<Image>();
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Image picture = new Image();
                picture.setId(Integer.parseInt(cursor.getString(0)));
                picture.setResource(cursor.getString(1));
                pictureList.add(picture);
            } while (cursor.moveToNext());
        }
        return pictureList;
    }

    public ArrayList<Recommendation> getRandomRecommendation() {
        ArrayList<Recommendation> recommendationList = new ArrayList<Recommendation>();
        String selectQuery = "SELECT * FROM " + TABLE_RECOMMENDATIONS + "ORDER BY RAND() LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Recommendation rec = new Recommendation();
                rec.setId(Integer.parseInt(cursor.getString(0)));
                rec.setName(cursor.getString(1));
                rec.setDescription(cursor.getString(2));
                rec.setImage(Integer.parseInt(cursor.getString(3)));
                recommendationList.add(rec);
            } while (cursor.moveToNext());
        }
        return recommendationList;
    }

    public void addRecommendation(Recommendation recommendation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, recommendation.getName());
        values.put(COLUMN_DESCRIPTION, recommendation.getDescription());
        values.put(COLUMN_PICTURE, String.valueOf(recommendation.getImage()));
        db.insert(TABLE_RECOMMENDATIONS, null, values);
        db.close();
    }

    /**
     * The second getAllPictures is used to grab all images associated with an location
     *
     * @param location
     * @return
     */
    public ArrayList<Image> getAllImages(int location) {
        ArrayList<Image> pictureList = new ArrayList<Image>();
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGELOCATION + " WHERE " + COLUMN_LOCATION + " = " + location;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String innerQuery = "SELECT * FROM " + TABLE_IMAGE + " WHERE " + COLUMN_ID + "=" + cursor.getInt(1);
                Cursor innerCursor = db.rawQuery(innerQuery, null);
                if (innerCursor.moveToFirst()) {
                    do {
                        Image picture = new Image();
                        picture.setId(Integer.parseInt(innerCursor.getString(0)));
                        picture.setResource(innerCursor.getString(1));
                        pictureList.add(picture);
                    } while (innerCursor.moveToNext());
                }
            } while (cursor.moveToNext());
        }
        return pictureList;
    }

    /**
     * UPDATE objects in database
     */
    public int updateBucketlist(Bucketlist bl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, bl.getName());
        values.put(COLUMN_DESCRIPTION, bl.getDescription());
        values.put(COLUMN_TIME, String.valueOf(bl.getTime()));
        values.put(COLUMN_COMPLETED, bl.getCompleted());
        return db.update(TABLE_BUCKET_LIST, values, COLUMN_ID + " = ?", new String[]{String.valueOf(bl.getId())});
    }

    public int updatePicture(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE, image.getResource());
        return db.update(TABLE_IMAGE, values, COLUMN_ID + " = ?", new String[]{String.valueOf(image.getId())});
    }

    /**
     * DELETE objects from database
     */
    public void deleteBucketlist(long location_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUCKET_LIST, COLUMN_ID + " = ?",
                new String[]{String.valueOf(location_id)});
    }

    public void deleteImage(long picture_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGE, COLUMN_ID + " = ?",
                new String[]{String.valueOf(picture_id)});
    }

    /**
     * Closing the database connection
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}

