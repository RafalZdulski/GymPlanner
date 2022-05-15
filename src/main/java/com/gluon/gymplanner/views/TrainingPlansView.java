package com.gluon.gymplanner.views;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class TrainingPlansView {
    
    public View getView() {
        try {
            View view = FXMLLoader.load(TrainingPlansView.class.getResource("fxmls/training-plans.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
