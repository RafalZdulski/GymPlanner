package com.gluon.gymplanner.jdbc;

import com.gluon.gymplanner.dtos.Plan;
import com.gluon.gymplanner.dtos.Workout;

import java.util.List;

public interface UserJDBC {
    List<Plan> getPlans();

    List<Workout> getWorkouts(String planId);


}
