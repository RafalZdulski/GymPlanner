package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.GluonApplication;
import com.gluon.gymplanner.dtos.ExerciseSeries;
import com.gluon.gymplanner.dtos.ExerciseTraining;
import com.gluon.gymplanner.dtos.Workout;
import com.gluon.gymplanner.graphic.exercise.SeriesView;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public class ExerciseExecutionPresenter implements Presenter{

    private Workout workout;

    private ExerciseTraining currentExercise;

    private SeriesView currentSeriesView;

    @FXML
    private BottomNavigationButton nextExerciseBtn;

    @FXML
    private BottomNavigationButton previousExerciseBtn;

    @FXML
    private View secondary;

    @FXML
    private VBox seriesPane;

    @FXML
    private Text nameText;

    @FXML
    private Text counterText;

    @FXML
    void goToNextExercise(ActionEvent event) {
        int i = workout.getTrainingList().indexOf(currentExercise);
//        ExerciseTraining temp = workout.getTrainingList().get(i+1);
        if (i+1 < workout.getTrainingList().size()){
            currentExercise.setSeries(currentSeriesView.getSeries());
            currentExercise = workout.getTrainingList().get(i+1);
            currentSeriesView = new SeriesView(currentExercise.getSeriesList());
        }
        update();
    }

    @FXML
    void goToPreviousExercise(ActionEvent event) {
        int i = workout.getTrainingList().indexOf(currentExercise);
        if (i > 0){
            currentExercise.setSeries(currentSeriesView.getSeries());
            currentExercise = workout.getTrainingList().get(i-1);
            currentSeriesView = new SeriesView(currentExercise.getSeriesList());
        }
        update();
    }

    public void setWorkout(Workout workout){
        this.workout = workout;
        currentExercise = workout.getTrainingList().get(0);
        currentSeriesView = new SeriesView(currentExercise.getSeriesList());
        update();
    }


    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);

        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Execution");
                appBar.getActionItems().add(MaterialDesignIcon.PERSON.button(e ->
                        //TODO ADD: add person panel
                        System.out.println("go to user panel")));
            }
        });
    }

    private void update(){
        nameText.setText(currentExercise.getName());
        int i = workout.getTrainingList().indexOf(currentExercise);
        counterText.setText(i+1 + " / " + workout.getTrainingList().size());
        seriesPane.getChildren().setAll(currentSeriesView);
        setButtonsText();
    }

    private void setButtonsText(){
        int i = workout.getTrainingList().indexOf(currentExercise);
        if (i+1 < workout.getTrainingList().size()){
            nextExerciseBtn.setText(
                    workout.getTrainingList().get(i+1).getName());
            nextExerciseBtn.setOnAction(this::goToNextExercise);
        }else {
            nextExerciseBtn.setText("Finish Workout");
            nextExerciseBtn.setOnAction(e -> {
                showWorkoutEndScreen();
            });
        }
        if (i > 0){
            previousExerciseBtn.setText(
                    workout.getTrainingList().get(i-1).getName());
            previousExerciseBtn.setOnAction(this::goToPreviousExercise);
        }else {
            previousExerciseBtn.setText("Go Back");
            previousExerciseBtn.setOnAction(e -> {
                currentExercise.setSeries(currentSeriesView.getSeries());
                GluonApplication.switchView(GluonApplication.QUICK_WORKOUT_VIEW);
            });
        }
    }

    private void showWorkoutEndScreen() {
        PopupView popupView = new PopupView(secondary);
        popupView.setPopupPadding(new Insets(10));
        View endView = new View();
        endView.setPrefWidth(250);
        endView.setPrefHeight(300);

        Text congrats = new Text("Congratulations");
        congrats.setFont(Font.font(30));
        Text summaryReps = new Text("You Did Your Workout In " + String.format("%.2f", summaryReps()) + "%");
        Text summaryWeight = new Text("Totally You Lifted " + String.format("%.0f", summaryWeight()) + "kg");
        VBox summaryPane = new VBox(congrats, summaryReps, summaryWeight);
        summaryPane.setSpacing(20);
        summaryPane.setAlignment(Pos.CENTER);
        summaryPane.setPadding(new Insets(5));
        endView.setCenter(summaryPane);

        BottomNavigation bottomNavigation = new BottomNavigation();
        BottomNavigationButton resumeBtn = new BottomNavigationButton("OK");
        resumeBtn.setGraphic(MaterialDesignIcon.FITNESS_CENTER.graphic());
        resumeBtn.setOnAction( e -> {
            popupView.hide();
            //TODO ADD: adding finished workout to calendar
            GluonApplication.switchView(GluonApplication.CALENDAR_VIEW);
        });
        bottomNavigation.getActionItems().add(resumeBtn);
        endView.setBottom(bottomNavigation);

        popupView.setContent(endView);
        //TODO FIX: centering popup window
        popupView.setLayoutY(100);
        popupView.setLayoutX(40);
        popupView.show();

        System.out.println(congrats.getText());
        System.out.println(summaryReps.getText());
        System.out.println(summaryWeight.getText());
    }

    private double summaryWeight() {
        double weight = .0;

        for (var exercise : workout.getTrainingList()){
            for (var series : exercise.getSeriesList()){
                weight += (series.getRepsDone() * series.getWeight());
            }
        }
        return weight;
    }

    private double summaryReps() {
        int repsPlanned = 0;
        int repsDone = 0;

        for (var exercise : workout.getTrainingList()){
            for (var series : exercise.getSeriesList()){
                repsPlanned += series.getRepsPlanned();
                repsDone += series.getRepsDone();
            }
        }
        return repsDone / (double) repsPlanned;
    }
}
