package com.gluon.gymplanner.jdbc;

import com.gluon.gymplanner.dtos.ExerciseDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        //new h2JDBC().getFiltered("a","isolation","pull",new String[]{"back"}).forEach(System.out::println);
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
        List<String> retList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT DISTINCT \"primary_body_part\" from exercise_details order by \"primary_body_part\";";

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
            return retList;
        }
    }

    private String[] getTargetMuscles(String exercise_id) {
        List<String> retList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT \"muscle\" FROM TARGET_MUSCLES WHERE \"exercise_id\" = '" + exercise_id + "'";

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
    public List<String> getExerciseExecution(String exercise_id) {
        List<String> retList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT \"text\" FROM EXECUTION WHERE \"exercise_id\" = '" + exercise_id + "' ORDER BY \"position\"";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String text = rs.getString(1);
                retList.add(text);
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
    public List<String> getExerciseTips(String exercise_id) {
        List<String> retList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT \"tip\" FROM TIPS WHERE \"exercise_id\" = '" + exercise_id + "' ORDER BY \"id\"";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String tip = rs.getString(1);
                retList.add(tip);
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
    public List<ExerciseDetails> getFiltered(String name, String mechanics, String force, String[] bodyParts) {
        List<ExerciseDetails> retList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            StringBuilder sql = new StringBuilder("SELECT * FROM exercise_details ");
            boolean isFirstFilter = true;
            if (name != null && !name.isBlank() ) {
                isFirstFilter = false;
                sql.append("WHERE ");
                String searchName = name.replace(' ','%').toLowerCase();
                sql.append("LOWER(\"name\") LIKE '%").append(searchName).append("%' ");
            }
            if (mechanics != null) {
                if (isFirstFilter) sql.append("WHERE ");
                else sql.append("AND ");
                isFirstFilter = false;
                sql.append("\"mechanics\" = '").append(mechanics).append("' ");
            }
            if (force != null) {
                if (isFirstFilter) sql.append("WHERE ");
                else sql.append("AND ");
                isFirstFilter = false;
                sql.append("\"force\" = '").append(force).append("' ");
            }
            if (bodyParts != null && bodyParts.length > 0){
                if (isFirstFilter) sql.append("WHERE ");
                else sql.append("AND ");
                String searchArray = Arrays.stream(bodyParts).map(s -> "'"+s+"'").collect(Collectors.joining(","));
                sql.append("\"primary_body_part\" IN (").append(searchArray).append(") OR ");
                sql.append("\"secondary_body_part\" IN (").append(searchArray).append(") ");
            }

            ResultSet rs = stmt.executeQuery(sql.append(";").toString());
            while (rs.next()) {
                String id = rs.getString("id");
                String nameVal = rs.getString("name");
                String pic_url = IMAGES_DIRECTORY + rs.getString("pic_url");
                String mechanicsVal = rs.getString("mechanics");
                String forceVal = rs.getString("force");
                String primaryBodyPart =  rs.getString("primary_body_part");
                String secondaryBodyPart = rs.getString("secondary_body_part");
                String[] bodyPartsVal = null;
                if (secondaryBodyPart != null)
                    bodyPartsVal = new String[]{primaryBodyPart, secondaryBodyPart};
                else
                    bodyPartsVal = new String[]{primaryBodyPart};
                String[] targetMuscles = getTargetMuscles(id);

                retList.add(new ExerciseDetails(id, nameVal, pic_url, bodyPartsVal, targetMuscles, mechanicsVal, forceVal));
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
}
