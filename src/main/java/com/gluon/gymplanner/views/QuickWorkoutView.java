package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.QuickWorkoutPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class QuickWorkoutView {

    View view;
    QuickWorkoutPresenter presenter;

    public QuickWorkoutView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class.getResource("fxmls/quick-workout.fxml"));
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

    public QuickWorkoutPresenter getPresenter(){
        return presenter;
    }
}
