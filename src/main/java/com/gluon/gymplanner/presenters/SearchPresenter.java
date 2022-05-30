package com.gluon.gymplanner.presenters;

import com.gluon.gymplanner.GluonApplication;
import com.gluon.gymplanner.graphic.search.filters.FilterPillsDisplay;
import com.gluon.gymplanner.graphic.search.filters.FilterWindow;
import com.gluon.gymplanner.graphic.search.filters.FilterWrap;
import com.gluon.gymplanner.jdbc.h2JDBC;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import com.gluonhq.charm.glisten.layout.Layer;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPresenter implements Presenter{

    private List<FilterWrap> bodyParts;

    @FXML
    private View secondary;

    @FXML
    private GridPane gridPane;

    private Layer container;

    @FXML
    private ToggleButtonGroup forceToggleBtns;

    @FXML
    private ToggleButtonGroup mechToggleBtns;

    @FXML
    private TextField nameField;

    @FXML
    void addBodyPart(ActionEvent event) {
        PopupView popupView = new PopupView(secondary);
        popupView.setContent(new FilterWindow(bodyParts, popupView));
        popupView.show();
    }

    @FXML
    void clearAll(ActionEvent event) {
        clearName(event);
        clearBodyParts(event);
        forceToggleBtns.getToggles().get(2).setSelected(true);
        mechToggleBtns.getToggles().get(2).setSelected(true);
    }

    @FXML
    void clearBodyParts(ActionEvent event) {
        bodyParts.forEach(b -> b.setCheck(false));
    }

    @FXML
    void clearName(ActionEvent event) {
        nameField.setText("");
    }

    @FXML
    void hide(ActionEvent event) {
        container.hide();
    }

    @FXML
    void search(ActionEvent event) {
        //TODO REFACTOR: force and mechanics can return null and bodyParts can be empty
        ExercisesDBPresenter exercisesDBPresenter = (ExercisesDBPresenter)
                GluonApplication.getView(GluonApplication.EXERCISES_DB_VIEW).getPresenter();

//        System.out.println("search query:");
//        System.out.println("\tname: " + nameField.getText());
//        System.out.println("\tmecha: " + mechToggleBtns.getToggles().filtered(ToggleButton::isSelected).get(0).getId());
//        System.out.println("\tforce: " + forceToggleBtns.getToggles().filtered(ToggleButton::isSelected).get(0).getId());
//        System.out.println("\tparts: " + bodyParts.stream().filter(FilterWrap::getCheckValue).map(FilterWrap::getItem).collect(Collectors.joining(", ")));

        exercisesDBPresenter.setExerciseList(new h2JDBC().getFiltered(
                nameField.getText(),
                mechToggleBtns.getToggles().filtered(ToggleButton::isSelected).get(0).getId(),
                forceToggleBtns.getToggles().filtered(ToggleButton::isSelected).get(0).getId(),
                bodyParts.stream().filter(FilterWrap::getCheckValue).map(FilterWrap::getItem).toArray(String[]::new)
        ));
    }

    public void initialize() {
//        secondary.setShowTransitionFactory(BounceInRightTransition::new);
        bodyParts = new h2JDBC().getAllBodyParts().stream().map(FilterWrap::new).collect(Collectors.toList());
        FilterPillsDisplay bodyPartsPills = new FilterPillsDisplay(bodyParts);
        GridPane.setColumnSpan(bodyPartsPills,3);
        gridPane.add(bodyPartsPills, 0, 4);

        //making sure you cannot unselect all button by clicking on selected button
        forceToggleBtns.getToggles().forEach( btn -> {
            btn.setOnAction( e -> {
                if(!btn.isSelected()) btn.setSelected(true);
            });
        });
        mechToggleBtns.getToggles().forEach( btn -> {
            btn.setOnAction( e -> {
                if(!btn.isSelected()) btn.setSelected(true);
            });
        });
    }

    public void setContainer(Layer layer) {
        container = layer;
    }
}
