
package com.example.nekogains;

import android.content.ContentValues;

import android.content.Context;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

public class DatabaseHelper extends SQLiteOpenHelper implements Serializable {

    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "Workout.db";

    private static final String EXERCISE_TABLE = "Exercises";

    private static final String WORKOUT_CALENDAR_TABLE = "Calendar";

    private static final String USER_TABLE = "Users";

    private static final String GAME_TABLE = "Games";

    private static final String PET_TABLE = "Pet";

    //Exercise Table

    private static final String EX_NAME = "NAME";

    private static final String EX_CAL = "CALORIES";

    private static final String[] workouts = {"Push-Ups", "Bench-Dips", "Chin-Ups", "Squats", "Lunges", "Calf-Raises", "Planks", "Sit-Ups", "Leg Raises", "Running", "Burpees", "Jumping Jacks"};

    private static final double[] calories = {1, 3, 1, 14, 0.9, 0.3, 3.5, 0.3, 0.7, 15, 13, 9};
    //User Table

    private static final String USER_NAME = "NAME";

    private static final String USER_SEX = "SEX";

    private static final String USER_AGE = "AGE";

    private static final String USER_HABIT = "HABITS";

    private static final String USER_WEIGHT = "WEIGHT";

    private static final String USER_HEIGHT = "HEIGHT";

    private static final String USER_INTENSITY = "INTENSITY";
    //Game Table

    private static final String GAME_MONEY = "MONEY";

    private static final String GAME_EXPERIENCE = "EXPERIENCE";
    //Calendar Table

    private static final String DAY = "DAY";

    private SQLiteDatabase db;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Creating Database\n\n");
        db.execSQL("CREATE TABLE " + EXERCISE_TABLE + " (ID INTEGER PRIMARY KEY, NAME TEXT, CALORIES INTEGER);");
        ContentValues contentValues;
        //populate whole workout table
        for (int i = 0; i < workouts.length; i++) {
            contentValues = new ContentValues();
            contentValues.put(EX_NAME, workouts[i]);
            contentValues.put(EX_CAL, calories[i]);
            db.insert(EXERCISE_TABLE, null, contentValues);
        }
        db.execSQL("CREATE TABLE " + USER_TABLE + " (ID INTEGER PRIMARY KEY, NAME TEXT, SEX TEXT, AGE TEXT, HABITS TEXT, WEIGHT TEXT, HEIGHT TEXT, INTENSITY TEXT, USERNAME TEXT, PASSWORD TEXT);");
        //db.execSQL("CREATE TRIGGER trg_bmi AFTER UPDATE ON " + USER_TABLE + " FOR EACH ROW WHEN OLD.HEIGHT > 0 BEGIN UPDATE " + USER_TABLE + " SET BMI=OLD.WEIGHT/OLD.HEIGHT*OLD.HEIGHT WHERE ID = NEW.ID; END;");
        db.execSQL("CREATE TABLE " + WORKOUT_CALENDAR_TABLE + " (ID INTEGER, DAY INTEGER, WARMUP TEXT, WREPS TEXT, CARDIO TEXT, CREPS TEXT, ARMS TEXT, AREPS TEXT, CORE TEXT, COREPS TEXT, LEGS TEXT, LREPS TEXT, WEIGHT TEXT," +
                " CONSTRAINT fk_users FOREIGN KEY (ID) REFERENCES USERS(ID));");
        db.execSQL("CREATE TABLE " + GAME_TABLE + " (ID INTEGER PRIMARY KEY, MONEY INTEGER, EXPERIENCE INTEGER," +
                " CONSTRAINT fk_users FOREIGN KEY (ID) REFERENCES USERS(ID));");
        db.execSQL("CREATE TABLE " + PET_TABLE + " (id INTEGER PRIMARY KEY, name TEXT, hunger INT, craving TEXT, level INT, pants TEXT, shirt TEXT,"+
                " CONSTRAINT fk_users FOREIGN KEY (id) REFERENCES USERS(ID));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);
    }
    //dynamic adding of exercises may not be needed

    public boolean insertNewExercise(String name, String calories) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EX_NAME, name);
        contentValues.put(EX_CAL, calories);
        long result = db.insert(EXERCISE_TABLE, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    //Adds new user to table with null stats

    public int insertEmptyUser() {
        ContentValues cv = new ContentValues();
        cv.putNull(USER_NAME);
        cv.putNull(USER_SEX);
        cv.putNull(USER_AGE);
        cv.putNull(USER_HABIT);
        cv.putNull(USER_WEIGHT);
        cv.putNull(USER_HEIGHT);
        cv.putNull(USER_INTENSITY);
        long result = db.insert(USER_TABLE, null, cv);
        if (result == -1) {
            return -1;
        } else {
            return (int) result;
        }
    }
    //Adds new user to table with given stats

    public int insertNewUser(String name, String sex, String age, String habits, String weight, String height, String intensity) {
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, name);
        cv.put(USER_SEX, sex);
        cv.put(USER_AGE, age);
        cv.put(USER_HABIT, habits);
        cv.put(USER_WEIGHT, weight);
        cv.put(USER_HEIGHT, height);
        cv.put(USER_INTENSITY, intensity);
        long result = db.insert(USER_TABLE, null, cv);
        if (result == -1) {
            return -1;
        } else {
            return (int) result;
        }
    }
    //Update a row for the user at the given id

    public boolean updateUserData(int id, String row, String contents) {
        System.out.println(row);
        System.out.println(contents);
        ContentValues cv = new ContentValues();
        cv.put(row, contents);
        long result = db.update(USER_TABLE, cv, "ID=" + id, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    //Get a row for the user at the given id

    public String getUserData(int id, String row) {
        String data = "";
        Cursor c = null;
        try {
            c = db.rawQuery("SELECT " + row + " FROM " + USER_TABLE + " WHERE ID =?", new String[]{id + ""});
            if (c.getCount() > 0) {
                c.moveToFirst();
                data = c.getString(c.getColumnIndex(row));
            }
        } finally {
            //c.close();
            return data;
        }
    }
    //Insert new game with it's ID tied to the user

    public boolean insertNewGame(String money, String experience) {
        ContentValues cv = new ContentValues();
        cv.put(GAME_MONEY, money);
        cv.put(GAME_EXPERIENCE, experience);
        long result = db.insert(GAME_TABLE, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    //Update a row for the game at the given id

    public boolean updateGame(int id, String row, int contents) {
        ContentValues cv = new ContentValues();
        cv.put(row, contents);
        long result = db.update(GAME_TABLE, cv, "ID=" + id, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    //Get a row for the game at the given id

    public String getGameData(int id, String row) {
        String data = "";
        Cursor c = null;
        try {
            c = db.rawQuery("SELECT " + row + " FROM " + GAME_TABLE + " WHERE ID =?", new String[]{id + ""});
            if (c.getCount() > 0) {
                c.moveToFirst();
                data = c.getString(c.getColumnIndex(row));
            }
            return data;
        } finally {
            //c.close();
        }
    }

    public int insertNewDay(int day, int id) {
        ContentValues cv = new ContentValues();
        cv.put(DAY, day);
        cv.put("ID", id);
        cv.putNull("WARMUP");
        cv.put("WREPS", 0);
        cv.putNull("CARDIO");
        cv.put("CREPS", 0);
        cv.putNull("ARMS");
        cv.put("AREPS", 0);
        cv.putNull("CORE");
        cv.put("COREPS", 0);
        cv.putNull("LEGS");
        cv.put("LEGS", 0);
        long result = db.insert(WORKOUT_CALENDAR_TABLE, null, cv);
        if (result == -1) {
            return -1;
        } else {
            return (int) result;
        }
    }

    public String getLastDay(int id) {
        String data = "";
        Cursor c = null;
        try {
            c = db.rawQuery("SELECT DAY FROM " + WORKOUT_CALENDAR_TABLE + " WHERE DAY = (SELECT MAX(DAY) FROM " + WORKOUT_CALENDAR_TABLE + ") AND ID =?", new String[]{id + ""});
            if (c.getCount() > 0) {
                c.moveToFirst();
                data = c.getString(c.getColumnIndex(DAY));
            }
            if (data.isEmpty()) {
                data = "0";
            }
        } finally {
            //c.close();
            return data;
        }
    }
    //Update a row for the game at the given id

    public boolean updateDay(int day, int id, String row, String contents) {
        ContentValues cv = new ContentValues();
        cv.put(row, contents);
        long result = db.update(WORKOUT_CALENDAR_TABLE, cv, "DAY=" + day + " AND ID=" + id, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getUserCalendarData(int day, int id, String row) {
        String data = "";
        Cursor c = null;
        try {
            c = db.rawQuery("SELECT " + row + " FROM " + WORKOUT_CALENDAR_TABLE + " WHERE DAY =? AND ID =?", new String[]{day + "", id + ""});
            if (c.getCount() > 0) {
                c.moveToFirst();
                data = c.getString(c.getColumnIndex(row));
            }
        } finally {
            //c.close();
            return data;
        }
    }

    public boolean insertNewPet(int id, String name) {
        ContentValues cv = new ContentValues();

        cv.put("id", id);
        cv.put("name", name);
        cv.put("level", 0); //Default starting level is 0
        cv.put("hunger", 80); //Default starting hunger is 80
        cv.put("craving", "none");
        cv.put("pants", "no"); //No clothes by default
        cv.put("shirt", "no");

        long result = db.insert(PET_TABLE, null, cv);

        if (result != -1) return true;
        System.out.println("Database failed to insert new Pet");
        return false;
    }

    public boolean updatePet(int id, String row, String contents) {
        ContentValues cv = new ContentValues();
        cv.put(row, contents);

        long result = db.update(PET_TABLE, cv, "id="+id, null);

        if (result != -1) return true;
        System.out.println("Database failed to update Pet table");
        return false;
    }

    public String getPetData(int id, String row) {
        /*
        * Method written by Kana
        * I don't know how the code works, I'm just copying it from Roy's code
        */
        String data = "";
        Cursor c = null;

        try {
            c = db.rawQuery("SELECT "+row+" FROM "+PET_TABLE+" WHERE ID=?",
                    new String[] {id + ""});

            if(c.getCount() > 0) {
                c.moveToFirst();
                data = c.getString(c.getColumnIndex(row));
            }
        } catch (Exception e) {
            System.out.println("Exception caused at getPetData() in DatabaseHelper");
            System.out.println(e.getMessage());
        }
        return data;
    }

}