package com.gluon.gymplanner.dtos;

import java.sql.Timestamp;
import java.time.Instant;
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
        this.id = name + "-" + Timestamp.from(Instant.now()).hashCode();
        this.name= name;
        this.trainingList = new ArrayList<>();
        this.date = LocalDateTime.now();
    }

    public Workout(){
        this.name = "new workout";
        this.id = name + "-" + Timestamp.from(Instant.now()).hashCode();
        this.trainingList = new ArrayList<>();
        this.date = LocalDateTime.now();
    }

    public Workout(String id, List<ExerciseTraining> trainingList){
        this.id = id;
        this.name = id.substring(0,id.lastIndexOf('-'));
        this.trainingList = trainingList;
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

    public String getId(){
        return this.id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
