package com.gluon.gymplanner.presenters;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;

public class QuickWorkoutPresenter {

    @FXML
    private View secondary;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.ADD.text,
                e -> System.out.println("Add exercise"));
        fab.showOn(secondary);
        
        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        AppManager.getInstance().getDrawer().open()));

                appBar.setTitleText("Quick Workout");

                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("to implement: add this workout to your workouts")));
            }
        });
    }
}
