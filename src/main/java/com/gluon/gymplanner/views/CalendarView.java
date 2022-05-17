package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.CalendarPresenter;
import com.gluon.gymplanner.presenters.Presenter;
import com.gluon.gymplanner.presenters.QuickWorkoutPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class CalendarView implements ViewInt{

    View view;
    CalendarPresenter presenter;

    public CalendarView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class.getResource("fxmls/calendar.fxml"));
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
