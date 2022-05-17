package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.Presenter;
import com.gluon.gymplanner.presenters.QuickWorkoutPresenter;
import com.gluon.gymplanner.presenters.SummaryPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class SummaryView implements ViewInt{

    View view;
    SummaryPresenter presenter;

    public SummaryView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class.getResource("fxmls/summary.fxml"));
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
