package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.Presenter;
import com.gluon.gymplanner.presenters.WorkoutPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class WorkoutView implements ViewInt{

    View view;
    WorkoutPresenter presenter;

    public WorkoutView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class.getResource("fxmls/workout.fxml"));
        try {
            view = loader.load();
            presenter = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public Presenter getPresenter(){
        return presenter;
    }
}
