package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.CalendarPresenter;
import com.gluon.gymplanner.presenters.ExerciseExecutionPresenter;
import com.gluon.gymplanner.presenters.Presenter;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ExerciseExecutionView implements ViewInt{

    View view;
    ExerciseExecutionPresenter presenter;

    public ExerciseExecutionView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class.getResource("fxmls/exercise-execution.fxml"));
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
