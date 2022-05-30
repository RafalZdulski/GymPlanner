package com.gluon.gymplanner.factories;

import com.gluon.gymplanner.dtos.Plan;
import com.gluon.gymplanner.dtos.Workout;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class WorkoutListViewFactory implements Callback<ListView<Workout>, ListCell<Workout>> {
    @Override
    public ListCell<Workout> call(ListView<Workout> planListView) {
        ListCell<Workout> cell = new ListCell<>() {
            @Override
            public void updateItem(Workout item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {




                }
            }
        };
        return cell;
    }
}