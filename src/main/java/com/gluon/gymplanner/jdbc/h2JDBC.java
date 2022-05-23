package com.gluon.gymplanner.jdbc;

import com.gluon.gymplanner.dtos.ExerciseDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class h2JDBC implements ExerciseDB{
    //TODO move it to configuration file
    final String IMAGES_DIRECTORY = "/com/gluon/gymplanner/database/exercises/images/";
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:file:./src/main/resources/com/gluon/gymplanner/database/gymplandb";
    final String DB_CREATE_SCRIPT = "./src/main/resources/com/gluon/gymplanner/database/sql/create-tables.sql";
    final String DB_POPULATE_SCRIPT = "./src/main/resources/com/gluon/gymplanner/database/sql/populate-exercises.sql";

    //  Database credentials
    final String USER = "";
    final String PASS = "";

    //only for testing
    public static void main(String[] a) {
        new h2JDBC().getAll().forEach(System.out::println);
    }

    @Override
    public List<ExerciseDetails> getAll() {
        List<ExerciseDetails> retList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM EXERCISE_DETAILS";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String pic_url = IMAGES_DIRECTORY + rs.getString("pic_url");
                String mechanics = rs.getString("mechanics");
                String force = rs.getString("force");
                String primaryBodyPart =  rs.getString("primary_body_part");
                String secondaryBodyPart = rs.getString("secondary_body_part");
                String[] bodyParts = null;
                if (secondaryBodyPart != null)
                    bodyParts = new String[]{primaryBodyPart, secondaryBodyPart};
                else
                    bodyParts = new String[]{primaryBodyPart};
                String[] targetMuscles = getTargetMuscles(id);

                retList.add(new ExerciseDetails(id, name, pic_url, bodyParts, targetMuscles, mechanics, force));
            }
            stmt.close();
            conn.close();

        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return retList;
        }
    }

    @Override
    public List<String> getAllBodyParts() {
        return null;
    }

    private String[] getTargetMuscles(String id) {
        List<String> retList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT \"muscle\" FROM TARGET_MUSCLES WHERE \"exercise_id\" = '" + id + "'";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String muscle = rs.getString(1);
                retList.add(muscle);
            }
            stmt.close();
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return retList.toArray(String[]::new);
        }
    }

    @Override
    public String[] getExerciseExecution(String id) {
        return new String[0];
    }

    @Override
    public String[] getExerciseTips(String id) {
        return new String[0];
    }

    @Override
    public List<ExerciseDetails> getByName(String name) {
        return null;
    }

    @Override
    public List<ExerciseDetails> getByMechanics(String mechanic) {
        return null;
    }

    @Override
    public List<ExerciseDetails> getByForce(String force) {
        return null;
    }

    @Override
    public List<ExerciseDetails> getByBodyParts(String[] bodyParts) {
        return null;
    }

    @Override
    public List<ExerciseDetails> getFiltered(String name, String mechanic, String force, String[] bodyParts) {
        return null;
    }
}
