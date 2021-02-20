package com.example.nekogains;

public class Cat extends Pet{

    public Cat(DatabaseHelper dbh, String newName, int id) {
        super(dbh, newName, id);
    }

    public Cat(DatabaseHelper dbh, int id) {super(dbh, id);}
}
