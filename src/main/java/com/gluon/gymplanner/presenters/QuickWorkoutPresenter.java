package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.GluonApplication;
import com.gluon.gymplanner.dtos.ExerciseSeries;
import com.gluon.gymplanner.dtos.ExerciseTraining;
import com.gluon.gymplanner.dtos.Workout;
import com.gluon.gymplanner.factories.ExerciseTrainingListViewFactory;
import com.gluon.gymplanner.graphic.exercise.SeriesView;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class QuickWorkoutPresenter implements Presenter{

    private Workout workout = new Workout();

    @FXML
    private View secondary;

    @FXML
    private ListView<ExerciseTraining> exerciseListView;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);
        
        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> {
                    onExiting();
                    AppManager.getInstance().getDrawer().open();
                }));
                appBar.setTitleText("Quick Workout");


                appBar.setSpacing(20);

                //TODO ADD: saving quick workout to your workouts
                //it should ask whether save it to favourite workouts or to calendar
                appBar.getActionItems().add(MaterialDesignIcon.SAVE.button(e ->{
                    onExiting();
                    System.out.println("to implement: save this workout in your workouts");
                }));

                appBar.getActionItems().add(MaterialDesignIcon.PERSON.button(e ->{
                    onExiting();
                    //TODO ADD: add person panel
                    System.out.println("go to user panel");
                }));
            }
        });

        exerciseListView.setItems(FXCollections.observableList(workout.getTrainingList()));
        exerciseListView.setCellFactory(new ExerciseTrainingListViewFactory(workout));
    }

    @Override
    public void update(){
        //TODO FIX: it should not require setting items more tha once
        exerciseListView.setItems(FXCollections.observableList(workout.getTrainingList()));
    }

    @Override
    public void onExiting(){
        ExerciseTrainingListViewFactory viewFactory = (ExerciseTrainingListViewFactory) exerciseListView.getCellFactory();
        HashMap<ExerciseTraining, SeriesView> map = viewFactory.getSeriesMap();
        for (var key : map.keySet()){
            key.setSeries(map.get(key).getSeries());
        }
    }

    public void addExercise(ExerciseTraining exercise){
        workout.addExercise(exercise);
        System.out.println("Exercise: " + exercise.getName() + " added to Quick Workout");
        update();
    }


    @FXML
    void clearWorkout(ActionEvent event) {
        workout.getTrainingList().clear();
        System.out.println("Quick Workout cleared");
        update();
    }

    @FXML
    void addExercise(ActionEvent event) {
        onExiting();
        GluonApplication.switchView(GluonApplication.EXERCISES_DB_VIEW);
    }

    @FXML
    void startWorkout(ActionEvent event) {
        if (workout.getTrainingList().size() < 1)
            return;
        onExiting();

        ExerciseExecutionPresenter presenter = (ExerciseExecutionPresenter)
                GluonApplication.getView(GluonApplication.EXERCISE_EXEC_VIEW).getPresenter();
        presenter.setWorkout(workout);

        GluonApplication.switchView(GluonApplication.EXERCISE_EXEC_VIEW);
    }
}
