package com.gluon.gymplanner.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Workout {

    //TODO ADD generating ids
    private String id;

    private String name;

    private LocalDateTime date;

    boolean done = false;

    private List<ExerciseTraining> trainingList;

    public Workout(String name){
        this.name= name;
        trainingList = new ArrayList<>();
        date = LocalDateTime.now();
    }

    public Workout(){
        name = "new workout";
        trainingList = new ArrayList<>();
        date = LocalDateTime.now();
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

    public void setTrainingList(List<ExerciseTraining> trainingList) {
        this.trainingList = trainingList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
