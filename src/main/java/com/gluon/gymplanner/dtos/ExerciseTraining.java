package com.gluon.gymplanner.dtos;

import java.util.LinkedList;
import java.util.List;

public class ExerciseTraining {
    private final ExerciseDetails exercise;
    private List<ExerciseSeries> series;

    public ExerciseTraining(ExerciseDetails exercise) {
        this.exercise = exercise;
        series = new LinkedList<>();
    }

    public ExerciseTraining(ExerciseDetails exercise, List<ExerciseSeries> series){
        this.exercise = exercise;
        this.series = new LinkedList<>(series);
    }

    public void addSeries(double weight, int reps){
        series.add(new ExerciseSeries(weight,reps));
    }

    public void setSeries(List<ExerciseSeries> series){
        this.series = series;
    }

    //***** GETTERS *****//

    public List<ExerciseSeries> getSeriesList() {
        return series;
    }

    public ExerciseDetails getExercise() {
        return exercise;
    }

    public String getName(){ return exercise.getName(); }
}
