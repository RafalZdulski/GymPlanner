package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.ExercisePresenter;
import com.gluon.gymplanner.presenters.ExercisesDBPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ExerciseDBView {

    View view;
    ExercisesDBPresenter presenter;

    public ExerciseDBView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class.getResource("fxmls/exercise-db.fxml"));
        try {
            view = loader.load();
            presenter = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public View getView() {
        return view;
    }

    public ExercisesDBPresenter getPresenter(){
        return presenter;
    }
}
