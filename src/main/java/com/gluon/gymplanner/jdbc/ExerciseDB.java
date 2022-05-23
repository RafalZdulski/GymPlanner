package com.gluon.gymplanner.jdbc;

import com.gluon.gymplanner.dtos.ExerciseDetails;

import java.util.List;

public interface ExerciseDB {
    List<ExerciseDetails> getAll();

    List<String> getAllBodyParts();

    String[] getExerciseExecution(String id);

    String[] getExerciseTips(String id);

    List<ExerciseDetails> getByName(String name);

    List<ExerciseDetails> getByMechanics(String mechanic);

    List<ExerciseDetails> getByForce(String force);

    List<ExerciseDetails> getByBodyParts(String[] bodyParts);

    List<ExerciseDetails> getFiltered(String name, String mechanic, String force, String[] bodyParts);
}
