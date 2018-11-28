package pl.coderslab;

import pl.coderslab.utils.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Panel {

    public static void main(String[] args) {

        int option1 = 0;

        while (option1 < 1 || option1 > 5) {
            System.out.println("Wybierz jedną z opcji:");
            System.out.println("1 – zarządzanie użytkownikami");
            System.out.println("2 – zarządzanie zadaniami");
            System.out.println("3 – zarządzanie grupami");
            System.out.println("4 – przypisywanie zadań");
            System.out.println("5 – wyjście");

            Scanner scanner = new Scanner(System.in);
            option1 = scanner.nextInt();

            // wyjście z panelu
            if (option1 == 5) {
                return;
            }

            try (Connection conn = DbConnection.getConnection()) {

                // użytkownicy
                if (option1 == 1) {
                    User[] users = User.loadAllUsers(conn);

                    for (User userElement : users) {
                        System.out.println(userElement.getId() + ": " + userElement.getUsername() + " (Grupa " + userElement.getGroupId().getId() + ")");
                    }

                    System.out.println("Wybierz opcję: add/edit/delete/quit [a/e/d/q]:");
                    String option2 = scanner.next();

                    // wyjście z panelu
                    if (option2.equals("q")) {
                        return;
                    } else if (option2.equals("a")) { // dodawanie
                        System.out.println("Dodawanie nowego użytkownika - podaj imię:");
                        String username = scanner.next();
                        System.out.println("Podaj email:");
                        String email = scanner.next();
                        System.out.println("Podaj hasło:");
                        String password = scanner.next();
                        System.out.println("Podaj ID grupy");
                        int usergroup = scanner.nextInt();

                        User user = new User(username, email, password, UserGroup.loadUserGroupById(conn, usergroup));
                        user.saveToDB(conn);
                        System.out.println("Użytkownik dodany:");

                    } else if (option2.equals("e")) { // edycja
                        System.out.println("Edycja istniejącego użytkownika - podaj ID:");
                        int id = scanner.nextInt();
                        System.out.println("Podaj imię:");
                        String username = scanner.next();
                        System.out.println("Podaj email:");
                        String email = scanner.next();
                        System.out.println("Podaj hasło:");
                        String password = scanner.next();
                        System.out.println("Podaj ID grupy");
                        int usergroup = scanner.nextInt();

                        User userE = User.loadUserById(conn, id);
                        userE.setUsername(username).setEmail(email).setPassword(password).setUserGroup(UserGroup.loadUserGroupById(conn, usergroup));
                        userE.saveToDB(conn);
                        System.out.println("Użytkownik zmieniony:");

                    } else if (option2.equals("d")) { // kasacja
                        System.out.println("Usuwanie istniejącego użytkownika - podaj ID:");
                        int id = scanner.nextInt();

                        User userD = User.loadUserById(conn, id);
                        userD.delete(conn);
                        System.out.println("Użytkownik usunięty:");
                    }

                    User[] users2 = User.loadAllUsers(conn);

                    for (User userElement : users2) {
                        System.out.println(userElement.getId() + ": " + userElement.getUsername() + " (Grupa " + userElement.getGroupId().getId() + ")");
                    }
                } else if (option1 == 2) { // zadania
                    Exercise[] exercises = Exercise.loadAllExercises(conn);
                    for (Exercise exerciseElement : exercises) {
                        System.out.println(exerciseElement.getId() + ": " + exerciseElement.getTitle());
                    }

                    System.out.println("Wybierz opcję: add/edit/delete/quit [a/e/d/q]:");
                    String option2 = scanner.next();

                    // wyjście z panelu
                    if (option2.equals("q")) {
                        return;
                    } else if (option2.equals("a")) { // dodawanie
                        scanner.nextLine(); // konsumujemy znaki nowej linii po nextInt
                        System.out.println("Dodawanie nowego zadania - podaj tytuł:");
                        String title = scanner.nextLine();
                        System.out.println("Podaj opis:");
                        String description = scanner.nextLine();

                        Exercise exercise = new Exercise(title, description);
                        exercise.saveToDB(conn);
                        System.out.println("Zadanie dodane:");

                    } else if (option2.equals("e")) { // edycja
                        scanner.nextLine(); // konsumujemy znaki nowej linii po nextInt
                        System.out.println("Edycja istniejącego zadania - podaj ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // konsumujemy znaki nowej linii po nextInt
                        System.out.println("Podaj tytuł:");
                        String title = scanner.nextLine();
                        System.out.println("Podaj opis:");
                        String description = scanner.nextLine();

                        Exercise exercise = new Exercise(id, title, description);
                        exercise.saveToDB(conn);
                        System.out.println("Zadanie zmienione:");

                    } else if (option2.equals("d")) { // kasacja
                        System.out.println("Usuwanie istniejącego zadania - podaj ID:");
                        int id = scanner.nextInt();

                        Exercise exercise1 = Exercise.loadExerciseById(conn, id);
                        exercise1.delete(conn);
                        System.out.println("Zadanie usunięte:");
                    }

                    Exercise[] exercises2 = Exercise.loadAllExercises(conn);
                    for (Exercise exerciseElement : exercises2) {
                        System.out.println(exerciseElement.getId() + ": " + exerciseElement.getTitle());
                    }

                } else if (option1 == 3) { // grupy
                    UserGroup[] userGroups = UserGroup.loadAllGroups(conn);
                    for (UserGroup userGroupElement : userGroups) {
                        System.out.println(userGroupElement.getId() + ": " + userGroupElement.getName());
                    }

                    System.out.println("Wybierz opcję: add/edit/delete/quit [a/e/d/q]:");
                    String option2 = scanner.next();

                    // wyjście z panelu
                    if (option2.equals("q")) {
                        return;
                    } else if (option2.equals("a")) { // dodawanie
                        scanner.nextLine(); // konsumujemy znaki nowej linii po nextInt
                        System.out.println("Dodawanie nowej grupy - podaj nazwę:");
                        String name = scanner.nextLine();

                        UserGroup userGroup = new UserGroup(name);
                        userGroup.saveToDB(conn);
                        System.out.println("Grupa dodana:");

                    } else if (option2.equals("e")) { // edycja
                        scanner.nextLine(); // konsumujemy znaki nowej linii po nextInt
                        System.out.println("Edycja istniejącej grupy - podaj ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // konsumujemy znaki nowej linii po nextInt
                        System.out.println("Podaj nazwę:");
                        String name = scanner.nextLine();

                        UserGroup userGroup = UserGroup.loadUserGroupById(conn, id);
                        userGroup.setName(name);
                        userGroup.saveToDB(conn);
                        System.out.println("Grupa zmieniona:");

                    } else if (option2.equals("d")) { // kasacja
                        System.out.println("Usuwanie istniejącej grupy - podaj ID:");
                        int id = scanner.nextInt();

                        UserGroup userGroup = UserGroup.loadUserGroupById(conn, id);
                        userGroup.delete(conn);
                        System.out.println("Grupa usunięta:");
                    }

                    UserGroup[] userGroups2 = UserGroup.loadAllGroups(conn);
                    for (UserGroup userGroupElement : userGroups2) {
                        System.out.println(userGroupElement.getId() + ": " + userGroupElement.getName());
                    }

                } else if (option1 == 4) { // przypisywanie zadań

                    System.out.println("Przypisywanie zadań. Wybierz opcję: add/view/quit [a/v/q]:");
                    String option2 = scanner.next();

                    if (option2.equals("q")) {
                        return;
                    } else if (option2.equals("a")) { // dodawanie zadania
                        User[] users = User.loadAllUsers(conn);

                        for (User userElement : users) {
                            System.out.println(userElement.getId() + ": " + userElement.getUsername() + " (Grupa " + userElement.getGroupId().getId() + ")");
                        }

                        System.out.println("Podaj ID wybranego użytkownika:");
                        int userId = scanner.nextInt();

                        Exercise[] exercises = Exercise.loadAllExercises(conn);
                        for (Exercise exerciseElement : exercises) {
                            System.out.println(exerciseElement.getId() + ": " + exerciseElement.getTitle());
                        }

                        System.out.println("Podaj ID wybranego zadania:");
                        int exerciseId = scanner.nextInt();

                        // generowanie aktualnego datetime w formacie mySQL
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);

                        Solution solution = new Solution(currentTime, null, Exercise.loadExerciseById(conn, exerciseId).getDescription(), Exercise.loadExerciseById(conn, exerciseId), User.loadUserById(conn, userId));
                        solution.saveToDB(conn);
                        System.out.println("Zadanie dodane");

                        Solution[] solutions = Solution.loadAllSolutions(conn);

                        for (Solution solutionElement : solutions) {
                            System.out.println(solutionElement.getId() + " (c." + solutionElement.getCreated() + "/u." + solutionElement.getUpdated() + "): " + solutionElement.getDescription() + " [Ex." + solutionElement.getExercise().getId() + "/Us: " + solutionElement.getUser().getUsername() + "]");
                        }


                    } else if (option2.equals("v")) { // podgląd zadań per użytkownik

                        System.out.println("Podaj ID wybranego użytkownika:");
                        int userId = scanner.nextInt();

                        Solution[] solutions = Solution.loadAllByUserId(conn, userId);

                        for (Solution solutionElement : solutions) {
                            System.out.println(solutionElement.getId() + " (c." + solutionElement.getCreated() + "/u." + solutionElement.getUpdated() + "): " + solutionElement.getDescription() + " [Ex." + solutionElement.getExercise().getId() + "/Us: " + solutionElement.getUser().getUsername() + "]");
                        }

                    }

                }

                option1 = 0; // wyjście do początkowego wyboru opcji
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
