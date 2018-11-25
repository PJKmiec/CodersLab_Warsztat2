package pl.coderslab;

import pl.coderslab.utils.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        User user = new User("Artur", "email@gmail.com", "password", new UserGroup("grupa"));
        Exercise exercise = new Exercise("Cwiczenie 1", "To jest przykladowe cwiczenie testowe");

        try (Connection conn = DbConnection.getConnection()) {

//            user.saveToDB(conn);
//            User user1 = User.loadUserById(conn, 1);
//            System.out.println(user1);
//            user1.setEmail("costam@gmail.com");
//            user1.saveToDB(conn);

//            User[] users = User.loadAllUsers(conn);
//
//            for (User userElement : users) {
//                System.out.println(userElement);
//            }

//            User user4 = User.loadUserById(conn, 4);
//            user4.delete(conn);


            // exercise.saveToDB(conn);

//            Exercise[] exercises = exercise.loadAllExercises(conn);
//            for (Exercise exerciseElement : exercises) {
//                System.out.println(exerciseElement);
//            }

            Exercise exercise1 = Exercise.loadExerciseById(conn, 1);
            System.out.println(exercise1);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
