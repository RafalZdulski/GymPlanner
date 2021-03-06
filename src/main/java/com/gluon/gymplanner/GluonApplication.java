package com.gluon.gymplanner;

import com.gluon.gymplanner.views.*;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;
import static com.gluonhq.charm.glisten.application.AppManager.getInstance;

public class GluonApplication extends Application {

    public static final String DASHBOARD_VIEW = HOME_VIEW;
    public static final String QUICK_WORKOUT_VIEW = "Quick Workout View";
    public static final String CALENDAR_VIEW = "Calendar View";
    public static final String PLANS_VIEW = "Plans View";
    public static final String PLAN_VIEW = "Workouts View";
    public static final String SUMMARY_VIEW = "Summary View";
    public static final String EXERCISES_DB_VIEW = "Exercise DB View";
    public static final String SEARCH_VIEW = "Search View";
    public static final String EXERCISE_EXEC_VIEW = "Exercise Exec View";
    public static final String WORKOUT_VIEW = "Workout View";

    private static final Map<String, ViewInt> viewMap = new HashMap<>();

    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {
        initViewMap();
        appManager.addViewFactory(DASHBOARD_VIEW, () -> getView(DASHBOARD_VIEW).getView());
        appManager.addViewFactory(QUICK_WORKOUT_VIEW, () -> getView(QUICK_WORKOUT_VIEW).getView());
        appManager.addViewFactory(CALENDAR_VIEW, () -> getView(CALENDAR_VIEW).getView());
        appManager.addViewFactory(PLANS_VIEW, () -> getView(PLANS_VIEW).getView());
        appManager.addViewFactory(PLAN_VIEW, () -> getView(PLAN_VIEW).getView());
        appManager.addViewFactory(SUMMARY_VIEW, () -> getView(SUMMARY_VIEW).getView());
        appManager.addViewFactory(EXERCISES_DB_VIEW, () -> getView(EXERCISES_DB_VIEW).getView());
        appManager.addViewFactory(EXERCISE_EXEC_VIEW, () -> getView(EXERCISE_EXEC_VIEW).getView());
        appManager.addViewFactory(WORKOUT_VIEW, () -> getView(WORKOUT_VIEW).getView());


        DrawerManager.buildDrawer(appManager);
    }

    private void initViewMap() {
        viewMap.put(DASHBOARD_VIEW, new DashboardView());
        viewMap.put(QUICK_WORKOUT_VIEW, new QuickWorkoutView());
        viewMap.put(CALENDAR_VIEW, new CalendarView());
        viewMap.put(PLANS_VIEW, new PlansView());
        viewMap.put(PLAN_VIEW, new PlanView());
        viewMap.put(SUMMARY_VIEW, new SummaryView());
        viewMap.put(EXERCISES_DB_VIEW, new ExerciseDBView());
        viewMap.put(SEARCH_VIEW, new SearchView());
        viewMap.put(EXERCISE_EXEC_VIEW, new ExerciseExecutionView());
        viewMap.put(WORKOUT_VIEW, new WorkoutView());
        //TODO REFACTOR: move exercise view initialization from exercisesDBView to here
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        appManager.start(primaryStage);
        primaryStage.setWidth(350);
       //primaryStage.setResizable(false);
    }

    private void postInit(Scene scene) {
        Swatch.GREY.assignTo(scene);

        scene.getStylesheets().add(GluonApplication.class.getResource("/com/gluon/gymplanner/views/css/style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Objects.requireNonNull(GluonApplication.class.getResourceAsStream("/com/gluon/gymplanner/icons/dumbbell-logo.png"))));
    }

    public static void main(String args[]) {
        launch(args);
    }


    public static void switchView(String viewName){
        getView(viewName).getPresenter().update();
        getInstance().switchView(viewName);
    }

    public static ViewInt getView(String viewName){
        return viewMap.get(viewName);
    }
}
