package com.gluon.gymplanner.factories;

import com.gluon.gymplanner.dtos.ExerciseDetails;
import com.gluon.gymplanner.dtos.ExerciseTraining;
import com.gluon.gymplanner.dtos.Workout;
import com.gluon.gymplanner.graphic.SeriesView;
import com.gluon.gymplanner.presenters.ExercisePresenter;
import com.gluon.gymplanner.views.ExerciseView;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.Arrays;

//TODO ADD: ability to change order of exercise by grabbing and moving list element
public class ExerciseTrainingListViewFactory implements Callback<ListView<ExerciseTraining>, ListCell<ExerciseTraining>> {

    private Workout workout;

    public ExerciseTrainingListViewFactory(Workout workout){
        this.workout = workout;
    }

    @Override
    public ListCell<ExerciseTraining> call(ListView<ExerciseTraining> exerciseTrainingListView) {
        ListCell<ExerciseTraining> cell = new ListCell<>() {
            @Override
            public void updateItem(ExerciseTraining item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty){
                    Text name = new Text(item.getName());
                    name.setFont(Font.font("System", FontWeight.BOLD,18));
                    name.setWrappingWidth(270);

                    HBox bodyPartsHBox = new HBox(Arrays.stream(item.getExercise().getBodyParts()).map(Text::new).toArray(Text[]::new));
                    bodyPartsHBox.setSpacing(5);
                    Text force = new Text(item.getExercise().getForce());
                    Text mechanics = new Text(item.getExercise().getMechanics());

                    HBox description = new HBox(bodyPartsHBox, force, mechanics);
                    description.setSpacing(7);
                    description.setPadding(new Insets(5,0,5,5));

                    //Series dropdown view
                    Button showSeriesBtn = new Button("series");
                    showSeriesBtn.setPrefWidth(300);
                    showSeriesBtn.setMaxHeight(30);

                    VBox seriesBox = new VBox(showSeriesBtn);
                    seriesBox.setAlignment(Pos.TOP_CENTER);
                    SeriesView seriesView = new SeriesView(item.getSeriesList());
                    showSeriesBtnFunc(showSeriesBtn, seriesBox, seriesView);




                    Button delBtn = new Button();
                    delBtn.setGraphic(MaterialDesignIcon.DELETE.graphic());
                    delBtn.setOnAction(e -> {
                        workout.removeExercise(item);
                        //TODO FIX problem: without setting items again it still display removed exercises
                        exerciseTrainingListView.setItems(FXCollections.observableList(workout.getTrainingList()));
                    });

                    setGraphic(new VBox(new HBox(name, delBtn), description, seriesBox));
                }
            }

            private void showSeriesBtnFunc(Button btn, VBox seriesBox, SeriesView seriesView) {
                btn.setGraphic(MaterialDesignIcon.ARROW_DROP_DOWN.graphic());
                btn.setOnAction(e ->{
                    seriesBox.getChildren().add(seriesView);
                    hideSeriesBtnFunc(btn, seriesBox, seriesView);
                });
            }

            private void hideSeriesBtnFunc(Button btn, VBox seriesBox, SeriesView seriesView) {
                btn.setGraphic(MaterialDesignIcon.ARROW_DROP_UP.graphic());
                btn.setOnAction(e -> {
                    seriesBox.getChildren().remove(seriesView);
                    showSeriesBtnFunc(btn, seriesBox, seriesView);
                });
            }
        };

        cell.setMaxWidth(AppManager.getInstance().getScreenWidth());

        cell.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            if(!cell.isEmpty()) {
                ExerciseDetails details = cell.getItem().getExercise();
                System.out.println("show details of: " + details.getName());
                ExerciseView exerciseView = new ExerciseView();
                View sidePopupContent = exerciseView.getView();
                ExercisePresenter presenter = (ExercisePresenter) exerciseView.getPresenter();
                presenter.setExercise(details);

                SidePopupView sidePopupView = new SidePopupView(sidePopupContent);
                sidePopupContent.setBottom(getBottomNavigation(sidePopupView));

                sidePopupView.show();
            }
        });

        return cell;
    }

    private Node getBottomNavigation(SidePopupView sidePopupView) {
        BottomNavigation bottomNavigation = new BottomNavigation();

        BottomNavigationButton retBtn = new BottomNavigationButton();
        retBtn.setGraphic(MaterialDesignIcon.ARROW_BACK.graphic());
        retBtn.setOnAction(e -> sidePopupView.hide());

        BottomNavigationButton favBtn = new BottomNavigationButton();
        favBtn.setGraphic(MaterialDesignIcon.FAVORITE_BORDER.graphic());
        //TODO ADD: adding exercise to favourite and removing it
        favBtn.setOnAction(e -> System.err.println("add exercise to favourites not implemented"));

        bottomNavigation.getActionItems().addAll(retBtn, favBtn);
        return bottomNavigation;
    }


}
