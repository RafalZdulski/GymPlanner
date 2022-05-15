package com.gluon.gymplanner;

import com.gluonhq.attach.lifecycle.LifecycleService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.attach.util.Services;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.image.Image;

import static com.gluon.gymplanner.GluonApplication.*;

public class DrawerManager {

    public static void buildDrawer(AppManager app) {
        NavigationDrawer drawer = app.getDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Gym Planner",
                "Plan Your Silhouette",
                new Avatar(30, new Image(DrawerManager.class.getResourceAsStream("/com/gluon/gymplanner/icons/dumbbell-logo.png"))));
        drawer.setHeader(header);

        final Item dashboardItem = new ViewItem("dashboard",
                MaterialDesignIcon.DASHBOARD.graphic(), DASHBOARD_VIEW, ViewStackPolicy.SKIP);

        final Item quickWorkoutItem = new ViewItem("Quick Workout",
                MaterialDesignIcon.FITNESS_CENTER.graphic(), QUICK_WORKOUT_VIEW);

        final Item calendarItem = new ViewItem("Calendar",
                MaterialDesignIcon.DATE_RANGE.graphic(), CALENDAR_VIEW);

        final Item trainingPlansItem = new ViewItem("Training Plans",
                MaterialDesignIcon.APPS.graphic(), TRAINING_PLANS_VIEW);

        final Item workoutsItem = new ViewItem("Your Workouts",
                MaterialDesignIcon.STYLE.graphic(), YOUR_WORKOUTS_VIEW);

        final Item summaryItem = new ViewItem("Summary",
                MaterialDesignIcon.ADD_BOX.graphic(), SUMMARY_VIEW);

        final Item exercisesDBItem = new ViewItem("Exercise Database",
                MaterialDesignIcon.DNS.graphic(), EXERCISES_DB_VIEW);


        drawer.getItems().addAll(
                dashboardItem, quickWorkoutItem, calendarItem,
                trainingPlansItem, workoutsItem, summaryItem,
                exercisesDBItem
        );
        
        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
    }
}