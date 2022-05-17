package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.dtos.ExerciseDetails;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Arrays;

public class ExercisePresenter implements Presenter{

    private ExerciseDetails exercise;

    @FXML
    private View secondary;

    @FXML
    private GridPane desc;

    @FXML
    private ImageView image;

    @FXML
    private Text name;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);

    }

    public void setExercise(ExerciseDetails exercise){
        this.exercise = exercise;
        showExercise();
    }

    private void showExercise(){
        image.setImage(new Image(exercise.getPicture(),350,233,true,true,true));
        name.setText(exercise.getName());
        desc.add(new Text("Body Parts:"),0,0);
        desc.add(new VBox(Arrays.stream(exercise.getBodyParts()).map(Text::new).toArray(Text[]::new)),1,0);
        desc.add(new Text("Mechanics::"),0,1);
        desc.add(new Text(exercise.getMechanics()),1,1);
        desc.add(new Text("Force"),0,2);
        desc.add(new Text(exercise.getForce()),1,2);
    }
}
