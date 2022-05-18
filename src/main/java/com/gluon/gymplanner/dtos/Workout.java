package com.gluon.gymplanner.dtos;

import java.util.ArrayList;
import java.util.List;

public class Workout {

    private String name;

    private List<ExerciseTraining> trainingList;

    public Workout(){
        trainingList = new ArrayList<>();
    }

    public void addExercise(ExerciseTraining exercise){
        trainingList.add(exercise);
    }

    public List<ExerciseTraining> getTrainingList() {
        return trainingList;
    }

    public void removeExercise(ExerciseTraining exercise) {
        if (trainingList.remove(exercise))
            System.out.println("exercise " + exercise.getName() + " removed from workout");
        else
            System.err.println("could not remove " + exercise.getName());
    }
}
