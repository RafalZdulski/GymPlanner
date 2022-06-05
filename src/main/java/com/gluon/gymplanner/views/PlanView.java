package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.Presenter;
import com.gluon.gymplanner.presenters.PlanPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class PlanView implements ViewInt{

    View view;
    PlanPresenter presenter;

    public PlanView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class.getResource("fxmls/workouts-list.fxml"));
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
