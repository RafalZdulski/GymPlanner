package com.gluon.gymplanner.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class QuickWorkoutView {
    
    public View getView() {
        try {
            View view = FXMLLoader.load(QuickWorkoutView.class.getResource("fxmls/quick-workout.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
