package pl.coderslab;

import pl.coderslab.utils.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class UserPanel {

    public static void main(String[] args) {
        int userId = 0;
        Scanner scanner = new Scanner(System.in);

        while (userId < 1) {
            System.out.println("Podaj swoje ID użytkownika:");
            userId = scanner.nextInt();
        }

        try (Connection conn = DbConnection.getConnection()) {

            User me = User.loadUserById(conn, userId);
            System.out.println("Jesteś zalogowany jako użytkownik ID" + me.getId() + ": " + me.getUsername());
            System.out.println("Wybierz jedną z opcji: add/view/quit [a/v/q]:");
            String option2 = scanner.next();

            if (option2.equals("q")) { // wyjście z panelu
                return;
            } else if (option2.equals("a")) { // dodanie rozwiązania

                System.out.println("Zadania do których nie dodałeś rozwiązań:");

                Solution[] solutions = Solution.loadAllNotByUserId(conn, userId);
                for (Solution solutionElement : solutions) {
                    System.out.println(solutionElement.getId() + " (c." + solutionElement.getCreated() + "/u." + solutionElement.getUpdated() + "): " + solutionElement.getExercise().getTitle() + " [Ex." + solutionElement.getExercise().getId() + "/Us: " + solutionElement.getUser().getUsername() + "(" + solutionElement.getUser().getId() + ")]");
                }

                System.out.println("Podaj ID zadania do którego chcesz dostać rozwiązanie:");
                int exerciseId = scanner.nextInt();

                // sprawdzamy czy podane ID zadania znajdje się już wśród rozwiązań danego użytkownika
                Solution[] solutionsChk = Solution.loadAllByUserId(conn, userId);
                for (Solution solutionElement : solutionsChk) {
                    if (exerciseId == solutionElement.getId()) {
                        System.out.println("Dodałeś już rozwiązanie do tego zadania!");
                        return;
                    }
                }

                scanner.nextLine(); // konsumujemy znaki nowej linii po nextInt
                System.out.println("Podaj opis:");
                String exerciseDescription = scanner.nextLine();

                // generowanie aktualnego datetime w formacie mySQL
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(dt);

                Solution solution = new Solution(currentTime, null, exerciseDescription, new Exercise(exerciseId), User.loadUserById(conn, userId));
                solution.saveToDB(conn);
                System.out.println("Zadanie dodane");


            } else if (option2.equals("v")) { // przegląd swoich rozwiązań

                System.out.println("Twoje rozwiązania:");
                Solution[] solutions = Solution.loadAllByUserId(conn, userId);
                for (Solution solutionElement : solutions) {
                    System.out.println(solutionElement.getId() + " (c." + solutionElement.getCreated() + "/u." + solutionElement.getUpdated() + "): " + solutionElement.getExercise().getTitle() + " [Ex." + solutionElement.getExercise().getId() + "/Us: " + solutionElement.getUser().getUsername() + "]");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
