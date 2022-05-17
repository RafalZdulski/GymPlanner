package com.gluon.gymplanner.dtos;

public class ExerciseTraining {
    final ExerciseDetails exercise;
    int repsPlanned;
    int repsDone;
    double weight; // in kilograms

    public ExerciseTraining(ExerciseDetails exercise) {
        this.exercise = exercise;
    }

    //***** SETTERS *****//

    public void setRepsPlanned(int repsPlanned) {
        this.repsPlanned = repsPlanned;
    }

    public void setRepsDone(int repsDone) {
        this.repsDone = repsDone;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    //***** GETTERS *****//

    public int getRepsPlanned() {
        return repsPlanned;
    }

    public int getRepsDone() {
        return repsDone;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return exercise.getName();
    }

    public String getPicture() {
        return exercise.getPicture();
    }

    public String[] getBodyParts() {
        return exercise.getBodyParts();
    }

    public String[] getTargetMuscles() {
        return exercise.getTargetMuscles();
    }

    public String getMechanics() {
        return exercise.getMechanics();
    }

    public String getForce() {
        return exercise.getForce();
    }

    public String[] getExecution() {
        return exercise.getExecution();
    }

    public String[] getTips() {
        return exercise.getTips();
    }


}
