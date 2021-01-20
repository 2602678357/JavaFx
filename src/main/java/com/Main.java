package com;

import com.controller.Controller;
import com.iflytek.cloud.speech.SpeechUtility;
import com.util.Version;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.context.ConfigurableApplicationContext;

public class Main extends Application {

    public static ConfigurableApplicationContext applicationContext;

    public FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        fxmlLoader=new FXMLLoader(getClass().getResource("/sample.fxml"));
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            public Object call(Class<?> param) {
                return applicationContext.getBean(param);
            }
        });
        Parent load=fxmlLoader.load();
        primaryStage.setTitle("欢迎使用");
        Scene s=new Scene(load, 463, 230);
        Controller c=fxmlLoader.getController();
        s.getStylesheets().add("bootstrapfx.css");
        primaryStage.setScene(s);
        primaryStage.show();

    }
}
