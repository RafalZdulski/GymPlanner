package com.gluon.gymplanner.dtos;

import java.util.ArrayList;
import java.util.List;

public class Plan {

    //TODO ADD generating ids
    private String id;

    private String name;

    private List<Workout> workouts;

    public Plan(String name){
        this.name = name;
        workouts = new ArrayList<>();
    }

    public void addWorkout(Workout workout){
        workouts.add(workout);
    }

    public String getName() {
        return name;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setName(String name) {
        this.name = name;
    }
}
