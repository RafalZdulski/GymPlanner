package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.GluonApplication;
import com.gluon.gymplanner.dtos.ExerciseTraining;
import com.gluon.gymplanner.dtos.Workout;
import com.gluon.gymplanner.factories.ExerciseTrainingListViewFactory;
import com.gluon.gymplanner.graphic.exercise.SeriesView;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.*;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class WorkoutPresenter implements Presenter{

    private Workout workout;

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
                appBar.setSpacing(20);

                appBar.getActionItems().add(MaterialDesignIcon.PERSON.button(e ->{
                    onExiting();
                    //TODO ADD: add person panel
                    System.out.println("go to user panel");
                }));
            }
        });

        exerciseListView.setCellFactory(new ExerciseTrainingListViewFactory(workout));
    }

    @Override
    public void update(){
        //TODO FIX: it should not require setting items more tha once
//        exerciseListView.setItems(FXCollections.observableList(workout.getTrainingList()));
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
        System.out.println("Exercise: " + exercise.getName() + " added to Workout: " + workout.getName());
        update();
    }


    @FXML
    void clearWorkout(ActionEvent event) {
        workout.getTrainingList().clear();
        System.out.println("Workout: " + workout.getName() + " cleared");
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

        ExerciseExecutionPresenter presenter = (ExerciseExecutionPresenter) GluonApplication.getView(GluonApplication.EXERCISE_EXEC_VIEW).getPresenter();
        presenter.setWorkout(workout);

        GluonApplication.switchView(GluonApplication.EXERCISE_EXEC_VIEW);
    }

    public void setWorkout(Workout newWorkout) {
        workout = newWorkout;
        exerciseListView.setItems(FXCollections.observableList(workout.getTrainingList()));
        setWorkoutNameInAppBar(workout.getName());
    }

    private void setWorkoutNameInAppBar(String name) {
        AppBar appBar = AppManager.getInstance().getAppBar();
        Text nameText = new Text(name);
        nameText.setFont(Font.font("system", FontWeight.BOLD, 16));

        Text dateText = new Text("\t" + workout.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        dateText.setFont(Font.font(12));

        Icon editIcon = new Icon(MaterialDesignIcon.EDIT);
        editIcon.setOnMouseClicked(e -> {
            changeWorkoutNameAndDate(workout);
        });
        appBar.setTitle(new HBox(new VBox(nameText, dateText), editIcon));
    }

    //TODO ADD changing date of workout
    void changeWorkoutNameAndDate(Workout workout) {
        PopupView popupView = new PopupView(secondary);
        popupView.setLayoutX(50);
        popupView.setLayoutY(150);
        popupView.setPrefWidth(200);

        TextField nameField = new TextField(workout.getName());
        nameField.setStyle("-fx-font-size: 22");

        BottomNavigationButton returnBtn = new BottomNavigationButton();
        returnBtn.setGraphic(MaterialDesignIcon.ARROW_BACK.graphic());
        returnBtn.setPrefSize(100,50);
        returnBtn.setOnAction(e -> popupView.hide());

        BottomNavigationButton acceptBtn = new BottomNavigationButton();
        acceptBtn.setGraphic(MaterialDesignIcon.CHECK.graphic());
        acceptBtn.setPrefSize(100,50);
        acceptBtn.setOnAction(e -> {
            if (!nameField.getText().isEmpty()){
                workout.setName(nameField.getText());
//                LocalDateTime localDateTime = datePicker.get
                setWorkoutNameInAppBar(nameField.getText());
                GluonApplication.getView(GluonApplication.PLAN_VIEW).getPresenter().update();
                popupView.hide();
            }
        });

        HBox btnBox = new HBox(returnBtn,acceptBtn);
        btnBox.setSpacing(10);


        VBox vBox = new VBox(nameField, btnBox);
        vBox.setSpacing(10);

        popupView.setContent(vBox);
        popupView.setPopupPadding(new Insets(5));
        popupView.show();
    }


    public Workout getWorkout() {
        return workout;
    }


}
