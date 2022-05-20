package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.GluonApplication;
import com.gluon.gymplanner.dtos.ExerciseTraining;
import com.gluon.gymplanner.dtos.Workout;
import com.gluon.gymplanner.factories.ExerciseTrainingListViewFactory;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class QuickWorkoutPresenter implements Presenter{

    private Workout workout = new Workout();

    @FXML
    private View secondary;

    @FXML
    private ListView<ExerciseTraining> exerciseListView;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);

//        FloatingActionButton floatingAddBtn = new FloatingActionButton(MaterialDesignIcon.ADD.text, e -> {
//            GluonApplication.switchView(GluonApplication.EXERCISES_DB_VIEW);
//        });
//        floatingAddBtn.showOn(secondary);
        
        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Quick Workout");


                appBar.setSpacing(20);

                //TODO ADD: saving quick workout to your workouts
                //it should ask whether save it to favourite workouts or to calendar
                appBar.getActionItems().add(MaterialDesignIcon.SAVE.button(e ->
                        System.out.println("to implement: save this workout in your workouts")));

                appBar.getActionItems().add(MaterialDesignIcon.PERSON.button(e ->
                        //TODO ADD: add person panel
                        System.out.println("go to user panel")));
            }
        });

        exerciseListView.setItems(FXCollections.observableList(workout.getTrainingList()));
        exerciseListView.setCellFactory(new ExerciseTrainingListViewFactory(workout));
    }

    public void addExercise(ExerciseTraining exercise){
        workout.addExercise(exercise);
        System.out.println("Exercise: " + exercise.getName() + " added to Quick Workout");
        //TODO FIX problem: without setting items again it does not display exercises added after initializing quick workout view
        exerciseListView.setItems(FXCollections.observableList(workout.getTrainingList()));
    }


    @FXML
    void clearWorkout(ActionEvent event) {
        workout.getTrainingList().clear();
        System.out.println("Quick Workout cleared");
        //TODO FIX problem: without setting items again it does not display exercises added after initializing quick workout view
        exerciseListView.setItems(FXCollections.observableList(workout.getTrainingList()));
    }

    @FXML
    void addExercise(ActionEvent event) {
        GluonApplication.switchView(GluonApplication.EXERCISES_DB_VIEW);
    }

    @FXML
    void startWorkout(ActionEvent event) {

    }
}
