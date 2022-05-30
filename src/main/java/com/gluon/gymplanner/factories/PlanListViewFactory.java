package com.gluon.gymplanner.factories;


import com.gluon.gymplanner.dtos.ExerciseTraining;
import com.gluon.gymplanner.dtos.Plan;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PlanListViewFactory implements Callback<ListView<Plan>, ListCell<Plan>> {
    @Override
    public ListCell<Plan> call(ListView<Plan> planListView) {
        ListCell<Plan> cell = new ListCell<>() {
            @Override
            public void updateItem(Plan item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {




                }
            }
        };
        return cell;
    }
}
