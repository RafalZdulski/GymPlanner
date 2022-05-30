package com.gluon.gymplanner.jdbc;

import com.gluon.gymplanner.dtos.ExerciseDetails;

import java.util.List;

public interface ExerciseDB {
    List<ExerciseDetails> getAll();

    List<String> getAllBodyParts();

    String[] getExerciseExecution(String exercise_id);

    String[] getExerciseTips(String exercise_id);

    List<ExerciseDetails> getFiltered(String name, String mechanic, String force, String[] bodyParts);
}
