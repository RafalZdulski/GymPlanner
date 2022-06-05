package com.gluon.gymplanner.factories;

import com.gluon.gymplanner.GluonApplication;
import com.gluon.gymplanner.dtos.ExerciseDetails;
import com.gluon.gymplanner.dtos.ExerciseTraining;
import com.gluon.gymplanner.graphic.exercise.SeriesView;
import com.gluon.gymplanner.presenters.ExercisePresenter;
import com.gluon.gymplanner.presenters.QuickWorkoutPresenter;
import com.gluon.gymplanner.presenters.WorkoutPresenter;
import com.gluon.gymplanner.views.ExerciseView;
import com.gluon.gymplanner.views.QuickWorkoutView;
import com.gluon.gymplanner.views.WorkoutView;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.Arrays;

public class ExerciseDetailsListViewFactory implements Callback<ListView<ExerciseDetails>, ListCell<ExerciseDetails>> {

    @Override
    public ListCell<ExerciseDetails> call(ListView<ExerciseDetails> exerciseDetailsListView) {
        ListCell<ExerciseDetails> cell = new ListCell<>(){
            @Override
            public void updateItem(ExerciseDetails item, boolean empty){
                super.updateItem(item,empty);
                if (!empty){
                    Text name = new Text(item.getName());
                    name.setFont(Font.font("System", FontWeight.BOLD,22));
                    name.setWrappingWidth(320);

                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(126);
                    imageView.setFitHeight(84);
                    try {
                        imageView.setImage((new Image(item.getPicture(),126,84,true,true,true)));
                    } catch (IllegalArgumentException e){
                        System.err.println(item.getPicture());
                    }

                    HBox bodyPartsHBox = new HBox(Arrays.stream(item.getBodyParts()).map(Text::new).toArray(Text[]::new));
                    bodyPartsHBox.setSpacing(5);
                    Text force = new Text(item.getForce());
                    Text mechanics = new Text(item.getMechanics());

                    VBox description = new VBox(bodyPartsHBox, force, mechanics);
                    description.setSpacing(7);
                    description.setPadding(new Insets(5,0,5,5));

                    setGraphic(new VBox(name, new HBox(imageView, description)));
                }
            }
        };

        cell.setMaxWidth(330);

        cell.addEventHandler(MouseEvent.MOUSE_RELEASED, e ->{
            if(!cell.isEmpty()) {
                //System.out.println("\tClicked on:" + cell.getItem());
                ExerciseView exerciseView = new ExerciseView();
                View sidePopupContent = exerciseView.getView();
                ExercisePresenter presenter = (ExercisePresenter) exerciseView.getPresenter();
                presenter.setExercise(cell.getItem());

                SidePopupView sidePopupView = new SidePopupView(sidePopupContent);
                sidePopupContent.setBottom(getBottomNavigation(sidePopupView, cell.getItem()));

                sidePopupView.show();
            }
        });

        return cell;
    }

    /** Creating bottom button bar for given side popup view displaying exercise details */
    private Node getBottomNavigation(SidePopupView sidePopupView, ExerciseDetails exercise) {
        BottomNavigation bottomNavigation = new BottomNavigation();

        BottomNavigationButton retBtn = new BottomNavigationButton();
        retBtn.setGraphic(MaterialDesignIcon.ARROW_BACK.graphic());
        retBtn.setOnAction(e -> sidePopupView.hide());

        BottomNavigationButton favBtn = new BottomNavigationButton();
        favBtn.setGraphic(MaterialDesignIcon.FAVORITE_BORDER.graphic());
        //TODO ADD: adding exercise to favourite and removing it
        favBtn.setOnAction(e -> System.err.println("add exercise to favourites not implemented"));

        BottomNavigationButton addBtn = new BottomNavigationButton();
        addBtn.setGraphic(MaterialDesignIcon.ADD.graphic());
        addBtn.setOnAction(e -> showPopupAddToWorkout(sidePopupView, exercise));

        bottomNavigation.getActionItems().addAll(retBtn, favBtn, addBtn);
        return bottomNavigation;
    }


    private void showPopupAddToWorkout(SidePopupView sidePopupView, ExerciseDetails exerciseDetails) {
        PopupView popupView = new PopupView(sidePopupView);
        popupView.setLayoutY(150);
        popupView.setLayoutX(50);

        //popup content
        View content = new View();
        content.setPrefHeight(300);
        content.setPrefWidth(250);
        SeriesView seriesView = new SeriesView();
        ScrollPane scrollPane = new ScrollPane(seriesView);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        content.setCenter(scrollPane);

        BottomNavigationButton addBtn = new BottomNavigationButton();
        addBtn.setGraphic(MaterialDesignIcon.ADD.graphic());
        addBtn.setOnAction(e -> {
            ExerciseTraining exerciseTraining = new ExerciseTraining(exerciseDetails);
            exerciseTraining.setSeries(seriesView.getSeries());
            addToWorkout(popupView, exerciseTraining);
            //popupView.hide();
        });

        BottomNavigationButton retBtn = new BottomNavigationButton();
        retBtn.setGraphic(MaterialDesignIcon.ARROW_BACK.graphic());
        retBtn.setOnAction(e -> popupView.hide());

        BottomNavigation bottomNavigation = new BottomNavigation();
        bottomNavigation.setPrefHeight(30);
        bottomNavigation.getActionItems().addAll(retBtn,addBtn);

        content.setBottom(bottomNavigation);

        popupView.setContent(content);
        popupView.show();
    }

    private void addToWorkout(PopupView popupViewParent, ExerciseTraining exercise) {
        PopupView popupView = new PopupView(popupViewParent);
        popupView.setLayoutY(20);
        popupView.setLayoutX(20);

        //popup content
        View content = new View();

        BottomNavigationButton quickWorkoutBtn = new BottomNavigationButton("Quick Workout");
        quickWorkoutBtn.setPrefSize(150, 75);
        quickWorkoutBtn.setOnAction(e -> {
            QuickWorkoutView view = (QuickWorkoutView) GluonApplication.getView(GluonApplication.QUICK_WORKOUT_VIEW);
            QuickWorkoutPresenter presenter = (QuickWorkoutPresenter) view.getPresenter();
            presenter.addExercise(exercise);
            popupView.hide();
            popupViewParent.hide();
        });

        BottomNavigationButton lastWorkoutBtn = new BottomNavigationButton();
        lastWorkoutBtn.setPrefSize(150, 75);
        WorkoutView workoutView = (WorkoutView) GluonApplication.getView(GluonApplication.WORKOUT_VIEW);
        WorkoutPresenter workoutPresenter = (WorkoutPresenter) workoutView.getPresenter();
        if(workoutPresenter.getWorkout() != null){
            lastWorkoutBtn.setText(workoutPresenter.getWorkout().getName());
            lastWorkoutBtn.setOnAction(e -> {
                    workoutPresenter.addExercise(exercise);
                    popupView.hide();
                    popupViewParent.hide();
            });
        }else {
            lastWorkoutBtn.setText("last workout");
            lastWorkoutBtn.setDisable(true);
        }

        BottomNavigationButton returnBtn = new BottomNavigationButton();
        returnBtn.setGraphic(MaterialDesignIcon.ARROW_BACK.graphic());
        returnBtn.setPrefSize(175,50);
        returnBtn.setOnAction(e -> popupView.hide());

        Text text = new Text("Add to..");
        text.setFont(Font.font("system", FontWeight.BOLD, 24));

        VBox vBox = new VBox(text, quickWorkoutBtn, lastWorkoutBtn, returnBtn);
        vBox.setSpacing(8);
        vBox.setAlignment(Pos.CENTER);

        content.setCenter(vBox);
        popupView.setContent(content);
        popupView.show();
        //TODO ADD: popup message confirming exercise was added to quick workout
    }
}
