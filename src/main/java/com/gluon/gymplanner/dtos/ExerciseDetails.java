package com.gluon.gymplanner.dtos;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * source of data:
 * https://weighttraining.guide
 * https://exrx.net
 */
public class ExerciseDetails {
    private String id;
    private String name;
    private String picture;
    private String[] bodyParts;
    private String[] targetMuscles;
    private String mechanics;
    private String force;
    private String[] execution;
    private String[] tips;

    public ExerciseDetails(String id, String name, String picture, String[] bodyParts,
                           String[] targetMuscles, String mechanics, String force,
                           String[] execution, String[] tips) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.bodyParts = bodyParts;
        this.targetMuscles = targetMuscles;
        this.mechanics = mechanics;
        this.force = force;
        this.execution = execution;
        this.tips = tips;
    }

    public ExerciseDetails(String id, String name, String picture, String[] bodyParts,
                           String[] targetMuscles, String mechanics, String force) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.bodyParts = bodyParts;
        this.targetMuscles = targetMuscles;
        this.mechanics = mechanics;
        this.force = force;
    }

    /** @deprecated only for testing*/
    public ExerciseDetails(String name, String picture, String[] bodyParts,
                           String[] targetMuscles, String mechanics, String force) {
        this.name = name;
        this.picture = picture;
        this.bodyParts = bodyParts;
        this.targetMuscles = targetMuscles;
        this.mechanics = mechanics;
        this.force = force;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("\n")
                .append("name: ").append(name).append("\n")
                .append("pic: ").append(picture).append("\n")
                .append("body parts: ").append(Arrays.stream(bodyParts).map(s -> ", "+s )
                        .collect(Collectors.joining()).substring(Math.min(2, bodyParts.length))).append("\n")
                .append("target muscle: ").append(Arrays.stream(targetMuscles).map(s -> ", "+s)
                        .collect(Collectors.joining()).substring(Math.min(2, targetMuscles.length))).append("\n")
                .append("mechanics: ").append(mechanics).append("\n")
                .append("force: ").append(force).append("\n");
//                .append("execution: ").append(execution).append("\n")
//                .append("tips: ").append(tips).append("\n");

        return sb.toString();
    }


    //**** GETTERS ****//

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String[] getBodyParts() {
        return bodyParts;
    }

    public String[] getTargetMuscles() {
        return targetMuscles;
    }

    public String getMechanics() {
        return mechanics;
    }

    public String getForce() {
        return force;
    }

    public String[] getExecution() {
        return execution;
    }

    public String[] getTips() {
        return tips;
    }
}
