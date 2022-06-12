package com.gluon.gymplanner.dtos;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Plan {

    //TODO ADD generating ids
    private String id;

    private String name;

    private List<Workout> workouts;

    public Plan(String name){
        this.id = name + "-" + Timestamp.from(Instant.now()).hashCode();
        this.name = name;
        this.workouts = new ArrayList<>();
    }

    public Plan(String id, String name, List<Workout> workouts){
        this.id = id;
        this.name = name;
        this.workouts = workouts;
    }

    public void addWorkout(Workout workout){
        workouts.add(workout);
    }


    /* GETTERS AND SETTERS */

    public String getName() {
        return name;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
