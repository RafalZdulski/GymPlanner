package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.dtos.ExerciseTraining;
import com.gluon.gymplanner.factories.ExerciseTrainingListViewFactory;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class QuickWorkoutPresenter {

    private List<ExerciseTraining> exerciseList = new ArrayList<>();

    @FXML
    private View secondary;

    @FXML
    private ListView<ExerciseTraining> exerciseListView;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton floatingAddBtn = new FloatingActionButton(MaterialDesignIcon.ADD.text, e -> {
            System.out.println("Add exercise ----");
        });
        floatingAddBtn.showOn(secondary);
        
        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        AppManager.getInstance().getDrawer().open()));

                appBar.setTitleText("Quick Workout");

                appBar.getActionItems().add(MaterialDesignIcon.SAVE.button(e ->
                        System.out.println("to implement: save this workout in your workouts")));
            }
        });

        exerciseListView.setItems(FXCollections.observableList(exerciseList));
        exerciseListView.setCellFactory(new ExerciseTrainingListViewFactory());
    }
}
