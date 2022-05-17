package com.gluon.gymplanner.dtos;

import java.util.ArrayList;
import java.util.List;

public class Workout {
    List<ExerciseTraining> trainingList;

    public Workout(){
        trainingList = new ArrayList<>();
    }

    public void addExercise(ExerciseTraining exercise){
        trainingList.add(exercise);
    }

    public List<ExerciseTraining> getTrainingList() {
        return trainingList;
    }
}
