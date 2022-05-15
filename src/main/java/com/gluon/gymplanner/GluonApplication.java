package com.gluon.gymplanner;

import com.gluon.gymplanner.views.*;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

public class GluonApplication extends Application {

    public static final String DASHBOARD_VIEW = HOME_VIEW;
    public static final String QUICK_WORKOUT_VIEW = "Quick Workout View";
    public static final String CALENDAR_VIEW = "Calendar View";
    public static final String TRAINING_PLANS_VIEW = "Training Plans View";
    public static final String YOUR_WORKOUTS_VIEW = "Your Workouts View";
    public static final String SUMMARY_VIEW = "Summary View";

    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {
        appManager.addViewFactory(DASHBOARD_VIEW, () -> new DashboardView().getView());
        appManager.addViewFactory(QUICK_WORKOUT_VIEW, () -> new QuickWorkoutView().getView());
        appManager.addViewFactory(CALENDAR_VIEW, () -> new CalendarView().getView());
        appManager.addViewFactory(TRAINING_PLANS_VIEW, () -> new TrainingPlansView().getView());
        appManager.addViewFactory(YOUR_WORKOUTS_VIEW, () -> new YourWorkoutsView().getView());
        appManager.addViewFactory(SUMMARY_VIEW, () -> new SummaryView().getView());

        DrawerManager.buildDrawer(appManager);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        appManager.start(primaryStage);
    }

    private void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(GluonApplication.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(GluonApplication.class.getResourceAsStream("/com/gluon/gymplanner/icons/icon.png")));
    }

    public static void main(String args[]) {
        launch(args);
    }
}
