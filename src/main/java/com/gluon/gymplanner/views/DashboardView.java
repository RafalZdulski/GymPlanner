package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.DashboardPresenter;
import com.gluon.gymplanner.presenters.Presenter;
import com.gluon.gymplanner.presenters.QuickWorkoutPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class DashboardView implements ViewInt{

    View view;
    DashboardPresenter presenter;

    public DashboardView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class.getResource("fxmls/dashboard.fxml"));
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
