package com.gluon.gymplanner.factories;

import com.gluon.gymplanner.GluonApplication;
import com.gluon.gymplanner.dtos.ExerciseDetails;
import com.gluon.gymplanner.dtos.ExerciseTraining;
import com.gluon.gymplanner.views.ExerciseView;
import com.gluon.gymplanner.views.QuickWorkoutView;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
                    imageView.setImage((new Image(item.getPicture(),126,84,true,true,true)));

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

        cell.addEventHandler(MouseEvent.MOUSE_RELEASED, e ->{
            //System.out.println("\tClicked on:" + cell.getItem());
            ExerciseView  exerciseView = new ExerciseView();
            View sidePopupContent = exerciseView.getView();
            exerciseView.getPresenter().setExercise(cell.getItem());

            SidePopupView sidePopupView = new SidePopupView(sidePopupContent);
            sidePopupContent.setBottom(getBottomNavigation(sidePopupView,cell.getItem()));

            sidePopupView.show();
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
        favBtn.setGraphic(MaterialDesignIcon.FAVORITE.graphic());
        //TODO implement adding exercise to favourite and removing it
        favBtn.setOnAction(e -> System.err.println("add exercise to favourites not implemented"));

        BottomNavigationButton addBtn = new BottomNavigationButton();
        addBtn.setGraphic(MaterialDesignIcon.ADD.graphic());
        addBtn.setOnAction(e -> showPopupAddToQuickWorkout(sidePopupView, exercise));

        bottomNavigation.getActionItems().addAll(retBtn, favBtn, addBtn);
        return bottomNavigation;
    }


    private void showPopupAddToQuickWorkout(SidePopupView sidePopupView, ExerciseDetails exerciseDetails) {
        PopupView popupView = new PopupView(sidePopupView);
        popupView.setLayoutY(200);
        popupView.setLayoutX(100);

        //popup content
        View content = new View();
        content.setPrefHeight(200);
        content.setPrefWidth(150);

        TextField weightTxtField = new TextField();
        weightTxtField.setPromptText("weight (kg)");
        TextField repsPlannedTxtField = new TextField();
        repsPlannedTxtField.setPromptText("reps planned");
        TextField repsDoneTxtField = new TextField();
        repsDoneTxtField.setPromptText("reps done");

        VBox vBox = new VBox(weightTxtField, repsPlannedTxtField, repsDoneTxtField);
        vBox.setPadding(new Insets(15));
        vBox.setSpacing(20);

        content.setCenter(vBox);

        BottomNavigationButton addBtn = new BottomNavigationButton();
        addBtn.setGraphic(MaterialDesignIcon.ADD.graphic());
        addBtn.setOnAction(e -> {
            ExerciseTraining exerciseTraining = getExerciseTraining(
                    exerciseDetails, weightTxtField, repsPlannedTxtField, repsDoneTxtField);
            addToQuickWorkout(popupView, exerciseTraining);
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

    private ExerciseTraining getExerciseTraining(ExerciseDetails details, TextField weightTxtField, TextField repsPlannedTxtField, TextField repsDoneTxtField){
        ExerciseTraining exercise = new ExerciseTraining(details);
        double weightVal = 0;
        if (!weightTxtField.getText().isEmpty())
            try{
                weightVal = Double.parseDouble(weightTxtField.getText());
            }catch (NumberFormatException e ){
                //TODO add validation of input data
            }


        int repsPlannedVal = 0;
        if (!repsPlannedTxtField.getText().isEmpty())
            try{
                repsPlannedVal = Integer.parseInt(repsPlannedTxtField.getText());
            }catch (NumberFormatException e ){
                //TODO add validation of input data
            }

        int repsDoneVal = 0;
        if (!repsDoneTxtField.getText().isEmpty())
            try{
                repsDoneVal = Integer.parseInt(repsDoneTxtField.getText());
            }catch (NumberFormatException e ){
                //TODO add validation of input data
            }

        exercise.setWeight(weightVal);
        exercise.setRepsPlanned(repsPlannedVal);
        exercise.setRepsDone(repsDoneVal);

//        System.out.println(exercise.getName());
//        System.out.println(weightVal);
//        System.out.println(repsDoneVal);
//        System.out.println(repsPlannedVal);

        return exercise;
    }

    private void addToQuickWorkout(PopupView popupView, ExerciseTraining exercise) {
        QuickWorkoutView view = GluonApplication.getQuickWorkoutView();
        view.getPresenter().addExercise(exercise);
        popupView.hide();
    }
}
