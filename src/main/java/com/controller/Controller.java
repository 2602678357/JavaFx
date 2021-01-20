package com.controller;

import com.IndexMain;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.config.HttpPostUtil;
import com.iflytek.cloud.speech.*;
import com.response.Result;
import com.response.ResultFactory;
import com.utils.BaseUrl;
import com.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.TextArea;
import java.net.URL;
import java.util.*;
import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechError;

@org.springframework.stereotype.Controller
public class Controller implements Initializable {


    /**获取用户名*/
    @FXML
    private TextField usernameText;

    /**获取密码*/
    @FXML
    private TextField passwordText;

    @Autowired
    private HttpPostUtil httpPost;

    @FXML
    private Button login;


    @FXML
    private Button get;

    public static HashMap<Integer,Object> hashedMap=new HashMap<Integer, Object>();

    public static Integer getId;

    // 语音听写对象



    public SpeechRecognizer mIat;

    @FXML
    private DialogPane dialog;

    @FXML
    private TextArea resultArea;


    public void initialize(URL location, ResourceBundle resources) {
        login.getStyleClass().add("btn-primary");
        get.getStyleClass().add("btn-danger");
        usernameText.getStyleClass().add("text-info");
    }

    private static class DefaultValue{
        public static final String ENG_TYPE = SpeechConstant.TYPE_CLOUD;
        public static final String SPEECH_TIMEOUT = "60000";
        public static final String NET_TIMEOUT = "20000";
        public static final String LANGUAGE = "zh_cn";

        public static final String ACCENT = "mandarin";
        public static final String DOMAIN = "iat";
        public static final String VAD_BOS = "5000";
        public static final String VAD_EOS = "1800";

        public static final String RATE = "16000";
        public static final String NBEST = "1";
        public static final String WBEST = "1";
        public static final String PTT = "1";

        public static final String RESULT_TYPE = "json";
        public static final String SAVE = "0";
    }

    private static final String DEF_FONT_NAME = "宋体";
    private static final int DEF_FONT_STYLE = Font.BOLD;
    private static final int DEF_FONT_SIZE = 30;
    private static final int TEXT_COUNT = 100;

    @ResponseBody
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            token.setRememberMe(true);
            try {
                subject.login(token);
                return ResultFactory.buildSuccessResult(user);
            } catch (IncorrectCredentialsException e) {
                return ResultFactory.buildFailResult("认证失败");
            } catch (UnknownAccountException e) {
                return ResultFactory.buildFailResult("账户不存在");
            }
        }else{
            return ResultFactory.buildFailResult("登录失败");
        }
    }

    @FXML
    private void getAction(ActionEvent event) throws Exception{
        mIat=SpeechRecognizer.createRecognizer();
        System.out.println(mIat.toString());
        try {
            if (!mIat.isListening())
                mIat.startListening(recognizerListener);
            else{
                mIat.stopListening();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private RecognizerListener recognizerListener = new RecognizerListener() {


        public void onBeginOfSpeech() {
        }


        public void onEndOfSpeech() {

        }

        /**
         * 获取听写结果. 获取RecognizerResult类型的识别结果，并对结果进行累加，显示到Area里
         */

        public void onResult(RecognizerResult results, boolean islast) {

            //如果要解析json结果，请考本项目示例的 com.iflytek.util.JsonParser类
//			String text = JsonParser.parseIatResult(results.getResultString());
            String text = results.getResultString();
            resultArea.append(text);
            text = resultArea.getText();
            if( null!=text ){
                int n = text.length() / TEXT_COUNT + 1;
                int fontSize = Math.max( 10, DEF_FONT_SIZE - 2*n );
                int style = n>1 ? Font.PLAIN : DEF_FONT_SIZE;
                Font newFont = new Font( DEF_FONT_NAME, style, fontSize );
                resultArea.setFont( newFont );
                resultArea.setText(results.getResultString());
            }
        }


        public void onVolumeChanged(int volume) {
            if (volume == 0)
                volume = 1;
            else if (volume >= 6)
                volume = 6;
        }


        public void onError(SpeechError error) {
        }


        public void onEvent(int eventType, int arg1, int agr2, String msg) {
            //以下代码用于调试，如果出现问题可以将sid提供给讯飞开发者，用于问题定位排查
			/*if(eventType == SpeechEvent.EVENT_SESSION_ID) {
				DebugLog.Log("sid=="+msg);
			}*/
        }
    };

    @FXML
    private void loginAction(ActionEvent event) throws Exception{
        User user=new User();
        user.setUsername(usernameText.getText().replaceAll("　| ", ""));
        user.setPassword(passwordText.getText());
        String str1 = JSON.toJSONString(user);
        JSONObject jsonObject = (JSONObject) JSON.parse(str1);
        Result rBet=httpPost.doPost(BaseUrl.base+"/login",jsonObject);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        if(rBet.getCode()==200){
            getId=user.getId();
            hashedMap.put(getId,user);
            Stage primaryStage=(Stage)login.getScene().getWindow();
            primaryStage.close();//打开新的窗口 所以要关闭当前的窗口
            IndexMain indexMain=new IndexMain();//新窗口类
            Stage stage=new Stage();
            try {
                indexMain.start(stage);//打开新窗口
            }catch (Exception e)
            {

            }
        }else{
            alert.setContentText(rBet.getMessage());
            alert.showAndWait();
        }
       System.out.println("当前为"+rBet.getCode()+rBet.getData()+rBet.getMessage());
    }
}

