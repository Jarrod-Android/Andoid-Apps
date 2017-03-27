package com.liamgoodwin.beforeidie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.security.acl.LastOwnerException;
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

    /**
     *Image Table Column Names
     */
    private static final String COLUMN_RESOURCE = "resource";

    /**
     *Image Location Table Column Names
     */
    private static final String COLUMN_PICTURE = "id_picture";
    private static final String COLUMN_LOCATION = "id_location";

    /**
     * Create statements for all of our tables
     */

    private static final String CREATE_BUCKET_LIST_TABLE = "CREATE TABLE " + TABLE_BUCKET_LIST + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_NAME + " TEXT," + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_TIME + " TEXT," + ")";

    private static final String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_RESOURCE + " TEXT" + ")";

    private static final String CREATE_IMAGE_LOCATION_TABLE = "CREATE TABLE " + TABLE_IMAGELOCATION + "("
            + COLUMN_LOCATION + " INTEGER REFERENCES " + TABLE_BUCKET_LIST + "(" + COLUMN_ID +"),"
            + COLUMN_PICTURE + " INTEGER REFERENCES " + TABLE_BUCKET_LIST + "(" + COLUMN_ID +")" +")";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create the tables inside of the database
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BUCKET_LIST_TABLE);
        db.execSQL(CREATE_IMAGE_TABLE);
        db.execSQL(CREATE_IMAGE_LOCATION_TABLE);
    }

    /**
     * When the database upgrades delete the old tables and recreate them
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGELOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGELOCATION);
        onCreate(db);
    }

    /**
     * CRUD OPERATIONS FOR THE DATABASE AND TABLES
     */

    /**
     * CREATE new objects for the tables
     *
     */
    public void addBucketlist(Bucketlist bucketlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, bucketlist.getName());
        values.put(COLUMN_DESCRIPTION, bucketlist.getDescription());
        values.put(COLUMN_TIME, bucketlist.getTime());
        db.insert(TABLE_BUCKET_LIST, null, values);
        db.close();
    }
    //We modified addPicture to return the rowNumber it was added into
    public int addPicture(Picture picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE, picture.getResource());
        db.insert(TABLE_PICTURES, null, values);
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if(cursor.moveToFirst()) {
            int location = Integer.parseInt(cursor.getString(0));
            System.out.println("Record ID " + location);
            db.close();
            return location;
        }
        return -1;
    }
    //Added a method that will add an image location record
    public void addImageLocation(int image, int location){
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
    public Location getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOCATIONS,
                new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_COORD}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Location location = new Location(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return location;
    }

    public ArrayList<Location> getAllLocations() {
        ArrayList<Location> locationList = new ArrayList<Location>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Location location = new Location();
                location.setId(Integer.parseInt(cursor.getString(0)));
                location.setName(cursor.getString(1));
                location.setDescription(cursor.getString(2));
                location.setLocation(cursor.getString(3));
                locationList.add(location);
            } while (cursor.moveToNext());
        }
        return locationList;
    }

    public Picture getPicture(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PICTURES, new String[] {COLUMN_ID, COLUMN_RESOURCE}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Picture picture = new Picture(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));

        return picture;
    }

    public ArrayList<Picture> getAllPictures() {
        ArrayList<Picture> pictureList = new ArrayList<Picture>();
        String selectQuery = "SELECT  * FROM " + TABLE_PICTURES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Picture picture = new Picture();
                picture.setId(Integer.parseInt(cursor.getString(0)));
                picture.setResource(cursor.getString(1));
                pictureList.add(picture);
            } while (cursor.moveToNext());
        }
        return pictureList;
    }

    /**
     * The second getAllPictures is used to grab all images associated with an location
     * @param location
     * @return
     */
    public ArrayList<Picture> getAllPictures(int location) {
        ArrayList<Picture> pictureList = new ArrayList<Picture>();
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGELOCATION + " WHERE " + COLUMN_LOCATION + " = " + location;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String innerQuery = "SELECT * FROM " + TABLE_PICTURES + " WHERE " + COLUMN_ID + "=" + cursor.getInt(1);
                Cursor innerCursor = db.rawQuery(innerQuery, null);
                if (innerCursor.moveToFirst()) {
                    do {
                        Picture picture = new Picture();
                        picture.setId(Integer.parseInt(innerCursor.getString(0)));
                        picture.setResource(innerCursor.getString(1));
                        pictureList.add(picture);
                    } while (innerCursor.moveToNext());
                }
            }while (cursor.moveToNext());
        }
        return pictureList;
    }

    public Trip getTrip(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TRIPS, new String[] {COLUMN_ID, COLUMN_DATE, COLUMN_LOCATION}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Trip trip = new Trip(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        return trip;
    }

    public ArrayList<Trip> getAllTrips() {
        ArrayList<Trip> tripList = new ArrayList<Trip>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Trip trip = new Trip();
                trip.setId(Integer.parseInt(cursor.getString(0)));
                trip.setDate(cursor.getString(1));
                trip.setLocation(Integer.parseInt(cursor.getString(2)));
                tripList.add(trip);
            } while (cursor.moveToNext());
        }
        return tripList;
    }

    /**
     * UPDATE objects in database
     */
    public int updateLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, location.getName());
        values.put(COLUMN_DESCRIPTION, location.getDescription());
        values.put(COLUMN_COORD, location.getLocation());
        return db.update(TABLE_LOCATIONS, values, COLUMN_ID + " = ?", new String[] { String.valueOf(location.getId()) });
    }
    public int updatePicture(Picture picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE, picture.getResource());
        return db.update(TABLE_PICTURES, values, COLUMN_ID + " = ?", new String[] { String.valueOf(picture.getId()) });
    }
    public int updateTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, trip.getDate());
        values.put(COLUMN_LOCATION, trip.getLocation());
        return db.update(TABLE_TRIPS, values, COLUMN_ID + " = ?", new String[] { String.valueOf(trip.getId()) });
    }

    /**
     * DELETE objects from database
     */
    public void deleteLocation(long location_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATIONS, COLUMN_ID + " = ?",
                new String[] { String.valueOf(location_id) });
    }
    public void deletePicture(long picture_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PICTURES, COLUMN_ID + " = ?",
                new String[] { String.valueOf(picture_id) });
    }
    public void deleteTrip(long trip_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRIPS, COLUMN_ID + " = ?",
                new String[] { String.valueOf(trip_id) });
    }

    /**
     * Closing the database connection
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

