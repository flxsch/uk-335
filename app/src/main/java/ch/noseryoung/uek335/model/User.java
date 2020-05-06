package ch.noseryoung.uek335.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="first_name")
    private String firstName;

    @ColumnInfo(name="last_name")
    private String lastName;

    @ColumnInfo(name="email")
    private String email;

    @ColumnInfo(name="password")
    private String password;

}
