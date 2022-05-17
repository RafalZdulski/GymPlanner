package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.ExercisePresenter;
import com.gluon.gymplanner.presenters.Presenter;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ExerciseView implements ViewInt{

    View view;
    ExercisePresenter presenter;

    public ExerciseView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class.getResource("fxmls/exercise-view.fxml"));
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
