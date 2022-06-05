package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.GluonApplication;
import com.gluon.gymplanner.dtos.Plan;
import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.control.Icon;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class PlansListPresenter implements Presenter {
    //TODO should be used with generating id
    private static int planCounter = 1;

    //TODO ADD plans should be acquired from database
    private final List<Plan> plans = new ArrayList<>();

    @FXML
    private View secondary;

    @FXML
    private ListView<Plan> plansListView;

    public void initialize() {
        secondary.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton floatingAddBtn = new FloatingActionButton(MaterialDesignIcon.ADD.text, e -> {
            Plan newPlan = new Plan("new plan #"+planCounter++);
            plans.add(newPlan);
            update();
//            PlanPresenter planPresenter = (PlanPresenter) GluonApplication.getView(GluonApplication.WORKOUTS_VIEW).getPresenter();
//            GluonApplication.switchView(GluonApplication.WORKOUTS_VIEW);
//            planPresenter.setPlan(newPlan);
        });
        floatingAddBtn.showOn(secondary);

        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Training Plans");
                appBar.getActionItems().add(MaterialDesignIcon.PERSON.button(e ->
                        //TODO ADD: add person panel
                        System.out.println("go to user panel")));
            }
        });
        update();
    }

    private Callback<ListView<Plan>, ListCell<Plan>> getPlanFactory() {
        return factory -> {
            ListCell<Plan> cell = new ListCell<>() {
                @Override
                public void updateItem(Plan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Icon icon = new Icon();
                        icon.setContent(MaterialDesignIcon.FITNESS_CENTER);

                        Text name = new Text(item.getName());
                        name.setFont(Font.font(22));
                        name.setWrappingWidth(210);

                        HBox hBox = new HBox(icon, name);
//                        hBox.setAlignment(Pos.CENTER);
                        hBox.setSpacing(15);
                        hBox.setPadding(new Insets(12,4,12,4));

                        setGraphic(hBox);
                    }
                }
            };

            cell.setOnMouseClicked(e -> {
                PlanPresenter planPresenter = (PlanPresenter) GluonApplication.getView(GluonApplication.PLAN_VIEW).getPresenter();
                GluonApplication.switchView(GluonApplication.PLAN_VIEW);
                planPresenter.setPlan(cell.getItem());
            });

            return cell;
        };
    }

    @Override
    public void update(){
        plansListView.setItems(FXCollections.observableList(plans));
        plansListView.setCellFactory(this.getPlanFactory());
    }
}