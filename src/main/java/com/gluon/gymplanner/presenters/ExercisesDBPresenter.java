package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.GluonApplication;
import com.gluon.gymplanner.dtos.ExerciseDetails;
import com.gluon.gymplanner.factories.ExerciseDetailsListViewFactory;
import com.gluon.gymplanner.jdbc.h2JDBC;
import com.gluon.gymplanner.views.SearchView;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ListView;

import java.util.List;

public class ExercisesDBPresenter implements Presenter{



    @FXML
    private View secondary;

    @FXML
    private ListView<ExerciseDetails> exerciseListView;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton floatingAddBtn = new FloatingActionButton(MaterialDesignIcon.SEARCH.text, e -> {
            SearchView searchView = (SearchView) GluonApplication.getView(GluonApplication.SEARCH_VIEW);
            SearchPresenter searchPresenter = (SearchPresenter) searchView.getPresenter();
            SidePopupView popupView = new SidePopupView(searchView.getView());
            searchPresenter.setContainer(popupView);

            popupView.setSide(Side.RIGHT);
            popupView.show();
        });
        floatingAddBtn.showOn(secondary);

        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Exercise Database");
                appBar.getActionItems().add(MaterialDesignIcon.PERSON.button(e ->
                        //TODO ADD: add person panel
                        System.out.println("go to user panel")));

            }
        });

        exerciseListView.setCellFactory(new ExerciseDetailsListViewFactory());
        exerciseListView.setItems(FXCollections.observableList(new h2JDBC().getAll()));

    }

    public void setExerciseList(List<ExerciseDetails> exerciseDetailsList){
        exerciseListView.setItems(FXCollections.observableList(exerciseDetailsList));
        exerciseListView.setCellFactory(new ExerciseDetailsListViewFactory());
    }


}
