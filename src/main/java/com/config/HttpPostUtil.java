package com.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.response.Result;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class HttpPostUtil {

    /**
     * 调用post方法
     * @param url
     * @param param
     * @return
     */
    public static Result doPost(String url, JSONObject param) {
        HttpPost httpPost = null;
        String result = null;
        JSONArray data=null;
        Integer code=0;
        Object o=null;
        String message=null;
        Result send=new Result();
        try {
            HttpClient client = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            if (param != null) {
                StringEntity se = new StringEntity(param.toString(), "utf-8");
                httpPost.setEntity(se); // post方法中，加入json数据
                httpPost.setHeader("Content-Type", "application/json");
            }

            HttpResponse response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result =EntityUtils.toString(resEntity,"utf-8");
                    JSONObject datas = JSONObject.parseObject(result);//转换成JSON格式
                    o=(Object) datas.get("data");//"data"是根据返回值设定
                    code=(Integer)datas.get("code");
                    message=(String)datas.get("message");
                    send.setCode(code);
                    send.setMessage(message);
                    send.setData(o);
                }
            }

        } catch (Exception ex) {
        }
        return send;
    }
}
