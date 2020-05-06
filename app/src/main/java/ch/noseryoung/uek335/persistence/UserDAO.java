package ch.noseryoung.uek335.persistence;

@Dao
public interface UserDAO {

    public interface UserDao {
        @Query("SELECT * FROM user")
        List<User> getAll();

        @Query("DELETE FROM user")
        void deleteAll();

        @Insert
        void insertAll(List<User> users);
    }

}
