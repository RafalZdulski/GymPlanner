package com.gluon.gymplanner.jdbc;

import com.gluon.gymplanner.config.PropertyManager;
import com.gluon.gymplanner.dtos.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserJDBCImpl implements UserJDBC {

    private final String JDBC_DRIVER = PropertyManager.getInstance().getProperty("JDBC_DRIVER");
    private final String DB_URL = PropertyManager.getInstance().getProperty("DB_URL");
    private final String DB_CREATE_SCRIPT = PropertyManager.getInstance().getProperty("DB_CREATE_SCRIPT");
//    final String DB_POPULATE_SCRIPT = PropertyManager.getInstance().getProperty("DB_POPULATE_EXERCISES_DETAILS_SCRIPT");

    //  Database credentials
    private final String DB_USER = "";
    private final String DB_PASS = "";

    // names of tables in database
    private final String USERS = "USERS";
    private final String PLANS = "PLANS";
    private final String WORKOUTS = "WORKOUTS";
    private final String WORKOUT_DETAILS = "WORKOUT_DETAILS";
    private final String EXERCISE_DETAILS = "EXERCISE_DETAILS";
    private final String TARGET_MUSCLES = "TARGET_MUSCLES";


    @Override
    public User getUser(String login) {
        User user = null;
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM " + USERS +
                    " WHERE \"login\" = '" + login + "';";

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String password = rs.getString(2);
                String email = rs.getString(3);
                user = new User(login, password, email);
            }

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;

    }

    @Override
    public List<Plan> getPlans(String userLogin) {
        List<Plan> ret = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

            String sql = "SELECT DISTINCT \"id\" FROM " + PLANS + " WHERE " +
                    "\"user\" = '" + userLogin + "';";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String planId = rs.getString(1);
                ret.add(this.getPlan(planId));
            }
            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public Plan getPlan(String planId) {
        List<Workout> ret = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

            String sql = "SELECT DISTINCT \"workout_id\" FROM " + WORKOUTS + " WHERE " +
                    "\"plan_id\" = '" + planId + "';";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String workoutId = rs.getString(1);
                ret.add(this.getWorkout(workoutId));
            }
            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String name = getPlanName(planId);
        return new Plan(planId, name, ret);
    }

    public String getPlanName(String planId){
        String name = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

            String sql = "SELECT  \"name\" FROM " + PLANS + " WHERE " +
                    "\"id\" = '" + planId + "';";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                name = rs.getString(1);
            }
            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    @Override
    public Workout getWorkout(String workoutId) {
        List<ExerciseTraining> list = new LinkedList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

            String sql = "SELECT DISTINCT \"exercise_id\",\"exercise_pos\"  FROM " + WORKOUT_DETAILS + " WHERE " +
                    "\"workout_id\" = '" + workoutId + "'" +
                    " ORDER BY \"exercise_pos\";";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String exerciseId = rs.getString("exercise_id");
                ExerciseTraining exerciseTraining = this.getExerciseTraining(workoutId, exerciseId);
                list.add(exerciseTraining);
            }
            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Workout workout = new Workout(workoutId, list);
        setWorkoutDateAndName(workout);
        return workout;
    }

    private void setWorkoutDateAndName(Workout workout) {
        LocalDateTime date = null;
        String name = "";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM " + WORKOUTS + " WHERE " +
                    "\"workout_id\" = '" + workout.getId() + "';";

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                date = rs.getObject("date", LocalDateTime.class);
                name = rs.getString("workout_name");
            }
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        workout.setName(name);
        workout.setDate(date);
    }

    @Override
    public ExerciseTraining getExerciseTraining(String workoutId, String exerciseId) {
        List<ExerciseSeries> series = new LinkedList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM " + WORKOUT_DETAILS + " WHERE " +
                    "\"workout_id\" = '" + workoutId + "' AND " +
                    "\"exercise_id\" = '" + exerciseId + "' ORDER BY " +
                    "\"exercise_pos\", \"series_pos\";";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                double weight = rs.getDouble("weight");
                int repsPlanned = rs.getInt("reps_planned");
                int repsDone = rs.getInt("reps_done");
                series.add(new ExerciseSeries(weight, repsPlanned, repsDone));
            }
            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ExerciseDetails details = this.getExerciseDetails(exerciseId);

        return new ExerciseTraining(details, series);
    }

    private ExerciseDetails getExerciseDetails(String exerciseId) {
        ExerciseDetails details = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM " + EXERCISE_DETAILS +
                    " WHERE \"id\" = '" + exerciseId + "';";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String pic_url = PropertyManager.getInstance().getProperty("EXERCISES_IMAGES_DIRECTORY") + rs.getString("pic_url");
                String mechanics = rs.getString("mechanics");
                String force = rs.getString("force");
                String primaryBodyPart = rs.getString("primary_body_part");
                String secondaryBodyPart = rs.getString("secondary_body_part");
                String[] bodyParts = null;
                if (secondaryBodyPart != null)
                    bodyParts = new String[]{primaryBodyPart, secondaryBodyPart};
                else
                    bodyParts = new String[]{primaryBodyPart};
                String[] targetMuscles = getTargetMuscles(id);

                details = new ExerciseDetails(id, name, pic_url, bodyParts, targetMuscles, mechanics, force);
            }
            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return details;
    }

    private String[] getTargetMuscles(String exercise_id) {
        List<String> retList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

            String sql = "SELECT \"muscle\" FROM " +
                    TARGET_MUSCLES +
                    " WHERE \"exercise_id\" = '" + exercise_id + "'";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String muscle = rs.getString(1);
                retList.add(muscle);
            }
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return retList.toArray(String[]::new);
    }

    @Override
    public void insertUser(User user) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO " + USERS +
                    "(\"login\",\"password\",\"email\") VALUES " +
                    "('" + user.getLogin() + "','" +
                    user.getPassword() + "','" + user.getEmail() + "');";

            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertPlan(String username, Plan plan) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO " + PLANS +
                    "(\"id\",\"user\",\"name\") VALUES " +
                    "('" + plan.getId() + "','" + username + "','" +
                    plan.getName() +"');";

            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (var workout : plan.getWorkouts())
            this.insertWorkout(plan.getId(), workout);
    }

    @Override
    public void insertWorkout(String planId, Workout workout) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO " + WORKOUTS +
                    "(\"workout_id\",\"date\",\"workout_name\",\"plan_id\") " +
                    "VALUES " +
                    "('" + workout.getId() + "','" +
                    workout.getDate() + "','" +
                    workout.getName() + "','" +
                    planId + "');";

            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        //inserting workout details
        int exercisesListSize = workout.getTrainingList().size();
        for (int exercisePos = 0; exercisePos < exercisesListSize; exercisePos++) {
            insertExercise(workout.getId(), exercisePos, workout.getTrainingList().get(exercisePos));
        }
    }

    @Override
    public void insertExercise(String workoutId, int exercisePos, ExerciseTraining exercise) {
        int seriesSize = exercise.getSeriesList().size();
        for (int seriesPos = 0; seriesPos < seriesSize; seriesPos++) {
            ExerciseSeries series = exercise.getSeriesList().get(seriesPos);
            this.insertSeries(
                    workoutId, exercise.getExercise().getId(),
                    exercisePos, seriesPos,
                    series.getWeight(), series.getRepsPlanned(), series.getRepsDone()
            );
        }
    }

    @Override
    public void insertSeries(String workoutId, String exerciseId, int exercisePos, int seriesPos,
                             double weight, int repsPlanned, int repsDone) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO " + WORKOUT_DETAILS +
                    "(\"workout_id\",\"exercise_id\",\"exercise_pos\",\"series_pos\"," +
                    "\"weight\",\"reps_planned\",\"reps_done\") " +
                    "VALUES ('" +
                    workoutId + "','" + exerciseId + "','" +
                    exercisePos + "','" + seriesPos + "','" +
                    weight + "','" + repsPlanned + "','" + repsDone + "');";

            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(String login) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();

            String sql = "DELETE FROM " + USERS +
                    " WHERE \"login\" = '" + login + "';";

            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePlan(String planId) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();

            String sql = "DELETE FROM " + PLANS +
                    " WHERE \"id\" = '" + planId + "';";

            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWorkout(String workoutId) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();

            String sql = "DELETE FROM " + WORKOUTS +
                    " WHERE \"workout_id\" = '" + workoutId + "';";

            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteExercise(String exerciseId, String workoutId, int exercisePos) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();

            String sql = "DELETE FROM " + WORKOUT_DETAILS + " WHERE " +
                    "\"workout_id\" = '" + workoutId + "' AND " +
                    "\"exercise_id\" = '" + exerciseId + "' AND " +
                    "\"exercise_pos\" = '" + exercisePos + "';";

            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSeries(String workoutId, String exerciseId, int exercisePos, int seriesPos) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();

            String sql = "DELETE FROM " + WORKOUT_DETAILS + " WHERE " +
                    "\"workout_id\" = '" + workoutId + "' AND " +
                    "\"exercise_id\" = '" + exerciseId + "' AND " +
                    "\"exercise_pos\" = '" + exercisePos + "' AND " +
                    "\"series_pos\" = '" + seriesPos + "';";

            stmt.execute(sql);
            stmt.close();
            conn.commit();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String username, Plan plan) {
        deletePlan(plan.getId());
        insertPlan(username, plan);
    }
}
