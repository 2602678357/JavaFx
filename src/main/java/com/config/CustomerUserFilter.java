package com.config;

import com.alibaba.fastjson.JSONObject;
import com.response.Result;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomerUserFilter extends FormAuthenticationFilter {

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //419 表示需要认证
//        Map<String, Object> result =new HashMap();
//        result.put("code",419);
        Result result = new Result(419);
        String entity = JSONObject.toJSONString(result);
        writerError(response, entity);
        return false;
    }



    private void writerError(ServletResponse response, String  entity) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        try {
            writer.write(entity);
            writer.flush();
        }finally {
            writer.close();
        }
    }
}

