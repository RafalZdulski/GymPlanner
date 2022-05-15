package com.gluon.gymplanner.views;

import com.gluon.gymplanner.dtos.ExerciseDetails;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ExerciseDBView {
    
    public View getView() {
        try {
            View view = FXMLLoader.load(ExerciseDBView.class.getResource("fxmls/exercise-db.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
