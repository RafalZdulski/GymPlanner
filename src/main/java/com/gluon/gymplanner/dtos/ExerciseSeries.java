package com.gluon.gymplanner.dtos;

public class ExerciseSeries {
    private double weight;
    private int repsPlanned;
    private int repsDone;

    public ExerciseSeries(double weight, int repsPlanned) {
        this.weight = weight;
        this.repsPlanned = repsPlanned;
    }

    public ExerciseSeries(double weight, int repsPlanned, int repsDone) {
        this.weight = weight;
        this.repsPlanned = repsPlanned;
        this.repsDone = repsDone;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRepsPlanned() {
        return repsPlanned;
    }

    public void setRepsPlanned(int repsPlanned) {
        this.repsPlanned = repsPlanned;
    }

    public int getRepsDone() {
        return repsDone;
    }

    public void setRepsDone(int repsDone) {
        this.repsDone = repsDone;
    }
}
