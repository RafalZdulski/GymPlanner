package com.gluon.gymplanner.views;

import com.gluon.gymplanner.presenters.CalendarPresenter;
import com.gluon.gymplanner.presenters.Presenter;
import com.gluon.gymplanner.presenters.SearchPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class SearchView implements ViewInt{

    View view;
    SearchPresenter presenter;

    public SearchView(){
        FXMLLoader loader = new FXMLLoader(ExerciseView.class
                .getResource("fxmls/search-exercise.fxml"));
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
    public Presenter getPresenter() {
        return presenter;
    }
}
