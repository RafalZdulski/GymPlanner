package com.gluon.gymplanner.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class DashboardView {

    public View getView() {
        try {
            View view = FXMLLoader.load(DashboardView.class.getResource("fxmls/dashboard.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
