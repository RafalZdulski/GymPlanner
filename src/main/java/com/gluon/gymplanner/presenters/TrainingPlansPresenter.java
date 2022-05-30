package com.gluon.gymplanner.presenters;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;

public class TrainingPlansPresenter implements Presenter{

    @FXML
    private View secondary;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);

        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Training Plans");
                appBar.getActionItems().add(MaterialDesignIcon.PERSON.button(e ->
                        //TODO ADD: add person panel
                        System.out.println("go to user panel")));
            }
        });
    }

    @FXML
    public void addNewPlan(){
        
    }
}
