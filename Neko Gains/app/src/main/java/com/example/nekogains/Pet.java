package com.example.nekogains;

import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Random;

public abstract class Pet {
    DatabaseHelper dbh;

    private final int SATIATION_HOURS = 12;
    //private final int SATIATION_HOURS = 1; //For testing purposes
    private int userId;
    private String name;
    private int hunger; //HUNGER range[0,100]
    private int level; //LEVEL range[0,100] (for every 1000xp pet will level up by 1
    private String pants;
    private String shirt;
    private String action;
    private long lastFed;
    private String motion;
    private String craving;


    public Pet(DatabaseHelper dbh, String newName, int id) {
        this.userId = id;
        this.dbh = dbh;
        this.name = newName;
        this.hunger = 80;
        this.level = 0;
        this.pants = "no";
        this.shirt = "no";
        this.lastFed = -1; //Timestamp of when the pet was fed
        this.craving = "none";

        if(!dbh.insertNewPet(id, newName)) {
            //Inserting new pet failed
            System.out.println("WARNING: Inserting new pet failed, try using the other constructor");
        }
    }

    public Pet(DatabaseHelper dbh, int id) {
        /*
        * In this constructor assume Pet table has already been
        * established in the database.
         */
        this.dbh = dbh;
        this.userId = id;
        this.name = dbh.getPetData(id, "name");
        this.hunger = Integer.parseInt(dbh.getPetData(id, "hunger"));
        this.level = Integer.parseInt(dbh.getPetData(id, "level"));
        this.pants = dbh.getPetData(id, "pants");
        this.shirt = dbh.getPetData(id, "shirt");
        this.craving = dbh.getPetData(id, "craving");
        setAction(false);
        this.motion = "@drawable/cat1_"+shirt+"shirt_"+pants+"pants_"+action;
        //Used to calculate hunger
        this.lastFed = MainActivity.getSettings().getLong("lastFedTime", -1);
        System.out.println("Last fed: "+this.lastFed);
    }

    //GETTING ATTRIBUTES
    public String getName() {
        return this.name;
    }

    public int getHunger() {
        /*
        * Returns the hunger that was reduced from time passing since the pet was last fed
         */
        Calendar cal = Calendar.getInstance();
        long currTime =  cal.getTimeInMillis();
        int decreaseAmount = 0;


        if(this.lastFed != -1) {
            //Number of times hunger should go down by
            decreaseAmount = (int) Math.floor((currTime - this.lastFed) / (3600000 * SATIATION_HOURS));
            //Pet reduces hunger by 10 every 12 (SATIATION_HOURS) hours

        } else {
            //Settings file has been deleted, put the new time in
            SharedPreferences.Editor editor = MainActivity.getSettings().edit();
            editor.putLong("lastFedTime", currTime);
            editor.apply();
        }
        int newHunger = this.hunger - 10 * decreaseAmount;
        if(newHunger < 0) newHunger = 0;
        return newHunger;
    }

    public int getLevel() {
        return this.level;
    }

    public String getPants() {return this.pants;}
    public String getShirt() {return this.shirt;}
    public String getMotion() {return this.motion;}
    public String getCraving() {return this.craving;}

    //CHANGING ATTRIBUTES
    public void setName(String newName) {
        this.name = newName;
        this.dbh.updatePet(this.userId, "name", newName);
    }
    public void setShirt(String newShirt) {
        this.shirt = newShirt;
        this.dbh.updatePet(this.userId, "shirt", newShirt);
        this.setMotion();
    }
    public void setPants(String newPants) {
        this.pants = newPants;
        this.dbh.updatePet(this.userId, "pants", newPants);
        this.setMotion();
    }
    public void setMotion() {
        this.motion = "@drawable/cat1_"+shirt+"shirt_"+pants+"pants_"+action;
    }

    public void setMotion(String shirt, String pants) {
        this.motion = "@drawable/cat1_"+shirt+"shirt_"+pants+"pants_"+action;
    }

    public void setLevel(int newLevel) {
        if (newLevel <= 100) {
            this.level = newLevel;
            this.dbh.updatePet(this.userId, "level", Integer.toString(this.level));
        }
    }

    public void setRandomCraving(boolean override) {
        /**
         * Sets a randomly selected craving, which will replenish double the hunger when fulfilled.
         * boolean override: Select a new craving even if one is already selected.
         */
        if(!this.craving.equals("none") && !override) return;

        Random rand = new Random();
        int choice = rand.nextInt(4);
        switch (choice){
            case 0:
                this.craving = "milk";
                break;
            case 1:
                this.craving = "blueberries";
                break;
            case 2:
                this.craving = "fish";
                break;
            case 3:
                this.craving = "catfood";
                break;
            case 4:
                this.craving = "none";
                break;
        }
        //Immediately switch the action to show craving
        setAction(false);
        setMotion();
        this.dbh.updatePet(this.userId, "craving", this.craving);
    }

    public void setAction(boolean idle) {
        if(idle || this.craving.equals("none")) {
            this.action = "idle";
        } else {
            this.action = "hunger_"+craving;
        }
    }

    public void decreaseHunger(int refillAmount) {
        /*
        * Uses a mix of stored hunger when the pet was fed and the reduced hunger after time
        * passed to calculate the current hunger, then replenishes it by the refillAmount.
         */
        this.hunger = getHunger() + refillAmount;
        if (this.getHunger() > 100) {
            this.hunger = 100;
        }
        else if (this.getHunger() < 0) {
            this.hunger = 0;
        }
        //Update database
        this.dbh.updatePet(this.userId, "hunger", Integer.toString(this.hunger));
    }


    public void feed(String food) {
        /*
        * Feeds the pet with food to increase their hunger bar
         */
        if(food.equals(this.craving)) {
            decreaseHunger(UserInventory.getFoodFill(food) * 2);
            this.craving = "none";
            this.dbh.updatePet(this.userId, "craving", this.craving);
            setAction(true);
            setMotion();
        } else {
            decreaseHunger(UserInventory.getFoodFill(food));
        }

        //Update the time in the settings file to be the current time
        SharedPreferences.Editor editor = MainActivity.getSettings().edit();
        editor.putLong("lastFedTime", Calendar.getInstance().getTimeInMillis());
        editor.apply();
    }

}
