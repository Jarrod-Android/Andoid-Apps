package com.liamgoodwin.beforeidie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Random;

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
    private static final String TABLE_USERS = "users";

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
     * Bucket List Table Column Names
     */
    private static final String COLUMN_USERNAME = "name";
    private static final String COLUMN_PASSWORD = "description";
    private static final String COLUMN_PRIVATE = "time";

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

    private static final String CREATE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_PRIVATE + " SMALLINT," + ")";

    private static final String ADD_PARIS = "INSERT INTO " + TABLE_RECOMMENDATIONS + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PICTURE + ") VALUES ("
            + "'Paris France', " + "'Paris Frances capital is a major European city and a global center for art fashion gastronomy and culture. Its 19th-century cityscape is crisscrossed by wide boulevards and the River Seine. Beyond such landmarks as the Eiffel Tower and the 12th-century Gothic Notre-Dame cathedral the city is known for its cafe culture and designer boutiques along the Rue du Faubourg Saint-Honoré.', "
            + R.drawable.parisfrance + ")";

    private static final String ADD_ZEALAND = "INSERT INTO " + TABLE_RECOMMENDATIONS + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PICTURE + ") VALUES ("
            + "'New Zealand', " + "'New Zealand is a country in the southwestern Pacific Ocean consisting of 2 main islands both marked by volcanoes and glaciation. Capital Wellington on the North Island is home to Te Papa Tongarewa the expansive national museum. Wellingtons dramatic Mt. Victoria along with the South Islands Fiordland and Southern Lakes stood in for mythical Middle Earth in Peter Jacksons \"Lord of the Rings\" films.', "
            + R.drawable.newzealand + ")";

    private static final String ADD_NEWYORK = "INSERT INTO " + TABLE_RECOMMENDATIONS + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PICTURE + ") VALUES ("
            + "'New York City', " + "'New York City comprises 5 boroughs sitting where the Hudson River meets the Atlantic Ocean. At its core is Manhattan a densely populated borough thats among the worlds major commercial financial and cultural centers. Its iconic sites include skyscrapers such as the Empire State Building and sprawling Central Park. Broadway theater is staged in neon-lit Times Square.', "
            + R.drawable.newyork + ")";

    private static final String ADD_GRANDCANYON = "INSERT INTO " + TABLE_RECOMMENDATIONS + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PICTURE + ") VALUES ("
            + "'Grand Canyon', " + "'The Grand Canyon in Arizona is a natural formation distinguished by layered bands of red rock revealing millions of years of geological history in cross-section. Vast in scale the canyon averages 10 miles across and a mile deep along its 277-mile length. Much of the area is a national park with Colorado River white-water rapids and sweeping vistas.', "
            + R.drawable.grandcanyon + ")";

    private static final String ADD_MAUNA = "INSERT INTO " + TABLE_RECOMMENDATIONS + "(" + COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PICTURE + ") VALUES ("
            + "'Mauna Loa', " + "'Mauna Loa is one of five volcanoes that form the Island of Hawaii in the U.S. state of Hawaii in the Pacific Ocean. The largest subaerial volcano in both mass and volume Mauna Loa has historically been considered the largest volcano on Earth.', "
            + R.drawable.maunaloa + ")";

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
        db.execSQL(CREATE_USERS);
        db.execSQL(ADD_PARIS);
        db.execSQL(ADD_ZEALAND);
        db.execSQL(ADD_NEWYORK);
        db.execSQL(ADD_GRANDCANYON);
        db.execSQL(ADD_MAUNA);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
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

    public Bucketlist getSmallestTime() {
        Bucketlist bucketList = null;
        String smallestDays = "SELECT " + COLUMN_TIME + ", " + COLUMN_NAME + " FROM " + TABLE_BUCKET_LIST + " ORDER BY " + COLUMN_TIME + " ASC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(smallestDays, null);
        if (cursor.moveToFirst()) {
                bucketList = new Bucketlist();
                bucketList.setName(cursor.getString(1));
                bucketList.setTime(cursor.getLong(0));
        }
        return bucketList;
    }

    public ArrayList<Bucketlist> getAllBucketlist() {
        ArrayList<Bucketlist> bucketList = new ArrayList<Bucketlist>();
        String selectQuery = "SELECT * FROM " + TABLE_BUCKET_LIST + " WHERE " + COLUMN_COMPLETED + " = 0";

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

    public ArrayList<Picture> getAllPictures() {
        ArrayList<Picture> pictureList = new ArrayList<Picture>();
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE;

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

    public Recommendation getRandomRecommendation() {
        Random r = new Random();
        int row = r.nextInt(5);

        Recommendation recommendationList = null;
        String selectQuery = "SELECT * FROM " + TABLE_RECOMMENDATIONS + " WHERE " + COLUMN_ID + "=" + row ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            recommendationList = new Recommendation();
            recommendationList.setId(Integer.parseInt(cursor.getString(0)));
            recommendationList.setName(cursor.getString(1));
            recommendationList.setDescription(cursor.getString(2));
            recommendationList.setImage(Integer.parseInt(cursor.getString(3)));
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

    /*
    *
    * USER FUNCTIONS
    *
    * */

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_PRIVATE, user.getPrivacy());
        db.insert(TABLE_BUCKET_LIST, null, values);
        db.close();
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> usersList = new ArrayList<User>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_PRIVATE + " = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setPrivacy(cursor.getInt(3));
                usersList.add(user);
            } while (cursor.moveToNext());
        }
        return usersList;
    }

    public int updateUserPrivacy(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRIVATE, user.getPrivacy());
        return db.update(TABLE_USERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
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

