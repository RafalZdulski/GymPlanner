package com.gluon.gymplanner.jdbc;

import com.gluon.gymplanner.dtos.ExerciseTraining;
import com.gluon.gymplanner.dtos.Plan;
import com.gluon.gymplanner.dtos.User;
import com.gluon.gymplanner.dtos.Workout;

import java.util.List;

public interface UserJDBC {

    User getUser(String login);

    List<Plan> getPlans(String userLogin);

    Plan getPlan(String planId);

    Workout getWorkout(String workoutId);

    ExerciseTraining getExerciseTraining(String workoutId, String exerciseID);

    void insertUser(User user);

    void insertPlan(String username, Plan plan);

    void insertWorkout(String planId, Workout workout);

    void insertExercise(String workoutId, int exercisePos, ExerciseTraining exercise);

    void insertSeries(String workoutId, String exerciseId, int exercisePos, int seriesPos,
                      double weight, int repsPlanned, int repsDone);

    void deleteUser(String login);

    void deletePlan(String planId);

    void deleteWorkout(String workoutId);

    void deleteExercise(String exerciseId, String workoutId, int exercisePos);

    void deleteSeries(String workoutId, String exerciseId, int exercisePos, int seriesPos);

    void update(String username, Plan plan);
}
