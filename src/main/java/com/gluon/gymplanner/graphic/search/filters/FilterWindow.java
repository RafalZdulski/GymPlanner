package com.gluon.gymplanner.graphic.search.filters;

import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class FilterWindow extends View{
    private final int WIDTH = 270;
    private final int HEIGHT = 400;
    PopupView popupView;

    private final List<FilterWrap> newFilterList;
    private final List<FilterWrap> oldFilterList;

    public FilterWindow(List<FilterWrap> items, PopupView popupView){
        this.oldFilterList = items;
        this.newFilterList = items.stream().map(FilterWrap::copyOf).collect(Collectors.toList());
        this.setCenter(getList());
        this.setBottom(getButtons());
        this.popupView = popupView;

    }

    private BottomNavigation getButtons() {
        BottomNavigation navigation = new BottomNavigation();
        BottomNavigationButton addBtn = new BottomNavigationButton("add");

        addBtn.setOnAction( e -> {
            for (int i=0; i< oldFilterList.size(); i++){
                oldFilterList.get(i).setCheck(newFilterList.get(i).getCheckValue());
            }
            popupView.hide();
        });

        BottomNavigationButton retBtn = new BottomNavigationButton("cancel");
        retBtn.setOnAction( e ->  popupView.hide());

        BottomNavigationButton clearBtn = new BottomNavigationButton("clear");
        clearBtn.setOnAction(e -> newFilterList.forEach(filter -> filter.setCheck(false)));

        navigation.getActionItems().addAll(clearBtn, retBtn, addBtn);
        return navigation;
    }

    private ListView<FilterWrap> getList(){
        ListView<FilterWrap> listView = new ListView<>();
        listView.setItems(FXCollections.observableList(newFilterList));

        listView.setCellFactory(c -> {
            ListCell<FilterWrap> cell = new ListCell<>(){
                @Override
                protected void updateItem(FilterWrap item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        final CheckBox cb = new CheckBox(item.toString());
                        cb.selectedProperty().bind(item.getCheck());
                        //disabling so it EventHandlers would not overlap with cell event defined below
                        cb.setDisable(true);
                        cb.setOpacity(1);

                        setGraphic(cb);
                    }
                }
            };

            cell.setFont(Font.font(24));
            cell.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
                if (!cell.isEmpty())
                    cell.getItem().reverseCheck();
            });
            return cell;
        });

        return listView;
    }
}
