package com;

import com.config.HttpPostUtil;
import com.controller.Controller;
import com.controller.IndexController;
import com.entity.User;
import com.response.Result;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

public class IndexMain extends Application {

    public static ConfigurableApplicationContext applicationContext;

    public FXMLLoader fxmlLoader;

    @FXML
    private Label getUsernameNewText;



    @Override
    public void start(Stage stage) throws Exception {
        fxmlLoader=new FXMLLoader(getClass().getResource("/index.fxml"));
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            public Object call(Class<?> param) {
                return applicationContext.getBean(param);
            }
        });
        Parent load=fxmlLoader.load();
        getUsernameNewText=(Label) load.lookup("#getUsernameNewText");
        User newU=(User) Controller.hashedMap.get(Controller.getId);
        getUsernameNewText.setText(newU.getUsername());
        Scene s=new Scene(load, 1100, 700);
        s.getStylesheets().add("bootstrapfx.css");
        IndexController i=fxmlLoader.getController();
        ObservableList<String> strList = FXCollections.observableArrayList("红色","黄色","绿色");
        ListView<String> listView = new ListView<String>(strList);
        listView.setItems(strList);
        listView.setPrefSize(100, 200);
        AnchorPane list=(AnchorPane)load.lookup("#list");
        list.getChildren().add(listView);
        stage.setScene(s);
        stage.setTitle("综合管理系统");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
