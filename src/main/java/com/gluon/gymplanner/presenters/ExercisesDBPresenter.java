package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.dtos.ExerciseDetails;
import com.gluon.gymplanner.factories.ExerciseDetailsListViewFactory;
import com.gluon.gymplanner.jdbc.ExerciseJDBC;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ExercisesDBPresenter {

    @FXML
    private View secondary;

    @FXML
    private ListView<ExerciseDetails> exerciseListView;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);

        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Exercise Database");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        System.out.println("add filters")));
            }
        });

        exerciseListView.setCellFactory(new ExerciseDetailsListViewFactory());
        exerciseListView.setItems(FXCollections.observableList(new ExerciseJDBC().getAllExercises()));

    }
}
