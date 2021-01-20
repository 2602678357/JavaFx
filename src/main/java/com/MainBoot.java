package com;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.util.Version;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


@SpringBootApplication
public class MainBoot{

    public static void main(String[] args) {
       SpringApplication springBootApplication=new SpringApplication(MainBoot.class);
       springBootApplication.addListeners(new ApplicationListener<ApplicationReadyEvent>() {
           public void onApplicationEvent(ApplicationReadyEvent applicationEvent) {
               Main.applicationContext=applicationEvent.getApplicationContext();
               Application.launch(Main.class);
           }
       });
        StringBuffer param = new StringBuffer();
        param.append( "appid=" + Version.getAppid() );
        SpeechUtility.createUtility(param.toString());
        System.out.println("当前加载"+ SpeechUtility.createUtility(param.toString()));

       springBootApplication.run();

    }


}
