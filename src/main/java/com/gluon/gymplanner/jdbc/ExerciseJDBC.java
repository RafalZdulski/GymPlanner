package com.gluon.gymplanner.jdbc;

import com.gluon.gymplanner.dtos.ExerciseDetails;

import java.util.List;

public interface ExerciseJDBC {
    List<ExerciseDetails> getAllExercises();

    ExerciseDetails getExercise(String exerciseId);

    List<String> getAllBodyParts();

    String[] getTargetMuscles(String exercise_id);

    List<String> getExerciseExecution(String exercise_id);

    List<String> getExerciseTips(String exercise_id);

    List<ExerciseDetails> getFiltered(String name, String mechanic, String force, String[] bodyParts);
}
