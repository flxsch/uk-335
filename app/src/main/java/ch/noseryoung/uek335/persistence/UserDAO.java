package ch.noseryoung.uek335.persistence;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import ch.noseryoung.uek335.model.User;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE email = :email")
    User getOne(String email);

    @Insert
    void insertAll(List<User> users);

    @Insert
    void insertOne(User user);

    @Query("DELETE FROM user")
    void deleteAll();

}
