package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.GluonApplication;
import com.gluon.gymplanner.dtos.Plan;
import com.gluon.gymplanner.dtos.Workout;
import com.gluon.gymplanner.jdbc.JDBCProxy;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.*;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;

public class PlanPresenter implements Presenter{

    private int workoutCounter = 1;

    private Plan plan;

    @FXML
    private View secondary;

    @FXML
    private ListView<Workout> workoutsListView;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton floatingAddBtn = new FloatingActionButton(MaterialDesignIcon.ADD.text, e -> {
            Workout newWorkout = new Workout("New Workout #"+workoutCounter++);
            plan.addWorkout(newWorkout);
            workoutsListView.setItems(FXCollections.observableList(plan.getWorkouts()));
//            WorkoutPresenter workoutPresenter = (WorkoutPresenter) GluonApplication.getView(GluonApplication.WORKOUT_VIEW).getPresenter();
//            GluonApplication.switchView(GluonApplication.WORKOUT_VIEW);
//            workoutPresenter.setWorkout(newWorkout);
        });
        floatingAddBtn.showOn(secondary);
        
        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        AppManager.getInstance().getDrawer().open()));
//                appBar.setTitleText("your workouts");
//                appBar.setTitle(new BottomNavigationButton("edit"));
                appBar.getActionItems().add(MaterialDesignIcon.SAVE.button(e -> {
                    JDBCProxy.getInstance().getUserJDBC().update("default", plan);
                    System.out.println("plan: " + plan.getName() + " updated into db");
                }));
                appBar.getActionItems().add(MaterialDesignIcon.PERSON.button(e ->
                        //TODO ADD: add person panel
                        System.out.println("go to user panel")
                ));
            }
        });
    }

    @Override
    public void update(){
        if (plan != null) {
            plan.getWorkouts().sort(Comparator.comparing(Workout::getDate));
            workoutsListView.setItems(FXCollections.observableList(plan.getWorkouts()));
            workoutsListView.setCellFactory(this.getWorkoutCellFactory());
        }
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
        workoutCounter = plan.getWorkouts().size();
        setPlanNameInAppBar(plan.getName());
        update();
    }

    private void setPlanNameInAppBar(String name){
        AppBar appBar = AppManager.getInstance().getAppBar();
        Text nameText = new Text(name);
        nameText.setFont(Font.font("system", FontWeight.BOLD, 16));

        Icon editIcon = new Icon(MaterialDesignIcon.EDIT);
        editIcon.setOnMouseClicked(e -> {
            changePlanName(plan);
        });
        appBar.setTitle(new HBox(nameText, editIcon));
    }

    private Callback<ListView<Workout>, ListCell<Workout>> getWorkoutCellFactory() {
        return factory -> {
            ListCell<Workout> cell = new ListCell<>() {
                @Override
                public void updateItem(Workout item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Icon icon = new Icon();
                        icon.setContent(MaterialDesignIcon.FITNESS_CENTER);

                        Text name = new Text(item.getName());
                        name.setFont(Font.font(22));
                        name.setWrappingWidth(210);

                        Text date = new Text("\t" + item.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                        date.setFont(Font.font(12));
                        date.setStyle("-fx-text-fill: gray;");

                        VBox vBox = new VBox(name, date);

                        Icon doneIcon = new Icon(MaterialDesignIcon.CLOSE);
                        if (item.isDone())
                            doneIcon.setContent(MaterialDesignIcon.CHECK);

                        HBox hBox = new HBox(icon, vBox, doneIcon);
//                        hBox.setAlignment(Pos.CENTER);
                        hBox.setSpacing(15);
                        hBox.setPadding(new Insets(12,4,12,4));

                        setGraphic(hBox);
                    }
                }
            };

            cell.setOnMouseClicked(e -> {
                WorkoutPresenter workoutPresenter = (WorkoutPresenter) GluonApplication.getView(GluonApplication.WORKOUT_VIEW).getPresenter();
                GluonApplication.switchView(GluonApplication.WORKOUT_VIEW);
                workoutPresenter.setWorkout(cell.getItem());
            });

            return cell;
        };
    }

    private void changePlanName(Plan plan) {
        PopupView popupView = new PopupView(secondary);
        popupView.setLayoutX(50);
        popupView.setLayoutY(150);
        popupView.setPrefWidth(200);

        TextField nameField = new TextField(plan.getName());
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
                plan.setName(nameField.getText());
                setPlanNameInAppBar(nameField.getText());
                GluonApplication.getView(GluonApplication.PLANS_VIEW).getPresenter().update();

                popupView.hide();
            }
        });

        HBox btnBox = new HBox(returnBtn,acceptBtn);
        btnBox.setSpacing(5);

        VBox vBox = new VBox(nameField, btnBox);
        vBox.setSpacing(10);

        popupView.setContent(vBox);
        popupView.setPopupPadding(new Insets(5));
        popupView.show();
    }
}
