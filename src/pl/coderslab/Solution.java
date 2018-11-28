package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {
    private int id;
    private String created;
    private String updated;
    private String description;
    private Exercise exercise;
    private User user;

    public Solution() {
    }

    public Solution(String created, String updated, String description, Exercise exercise, User user) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise = exercise;
        this.user = user;
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution(created, updated, description, exercise_id, users_id) VALUES (?, ?, ?, ?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.created);
            preparedStatement.setString(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.exercise.getId());
            preparedStatement.setInt(5, this.user.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE solution SET created = ?, updated = ?, description = ?, exercise_id = ?, users_id = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.created);
            preparedStatement.setString(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.exercise.getId());
            preparedStatement.setInt(5, this.user.getId());
            preparedStatement.executeUpdate();
        }
    }

    static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadExerciseById(conn, resultSet.getInt("exercise_id"));
            loadedSolution.user = User.loadUserById(conn, resultSet.getInt("users_id"));
            return loadedSolution;
        }
        return null;
    }

    static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadExerciseById(conn, resultSet.getInt("exercise_id"));
            loadedSolution.user = User.loadUserById(conn, resultSet.getInt("users_id"));
            solutions.add(loadedSolution);
        }
        Solution[] eArray = new Solution[solutions.size()];
        eArray = solutions.toArray(eArray);
        return eArray;
    }

    static public Solution[] loadAllByUserId(Connection conn, int id) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution where users_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadExerciseById(conn, resultSet.getInt("exercise_id"));
            loadedSolution.user = User.loadUserById(conn, resultSet.getInt("users_id"));
            solutions.add(loadedSolution);
        }
        Solution[] eArray = new Solution[solutions.size()];
        eArray = solutions.toArray(eArray);
        return eArray;
    }

    static public Solution[] loadAllByExerciseId(Connection conn, int id) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution where exercise_id = ? order by created desc";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getString("created");
            loadedSolution.updated = resultSet.getString("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadExerciseById(conn, resultSet.getInt("exercise_id"));
            loadedSolution.user = User.loadUserById(conn, resultSet.getInt("users_id"));
            solutions.add(loadedSolution);
        }
        Solution[] eArray = new Solution[solutions.size()];
        eArray = solutions.toArray(eArray);
        return eArray;
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }


    public int getId() {
        return id;
    }
    public Exercise getExercise() {
        return exercise;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", description='" + description + '\'' +
                ", exercise=" + exercise +
                ", user=" + user +
                '}';
    }
}
