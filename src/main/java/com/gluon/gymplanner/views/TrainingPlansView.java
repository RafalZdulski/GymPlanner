package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.Presenter;
import com.gluon.gymplanner.presenters.QuickWorkoutPresenter;
import com.gluon.gymplanner.presenters.TrainingPlansPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class TrainingPlansView implements ViewInt{

    View view;
    TrainingPlansPresenter presenter;

    public TrainingPlansView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class.getResource("fxmls/training-plans.fxml"));
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
