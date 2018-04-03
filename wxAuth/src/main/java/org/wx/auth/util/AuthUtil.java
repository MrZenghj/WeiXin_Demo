package org.wx.auth.util;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AuthUtil {

    public static final String APPID = "wxe4797497c3814e3b";
    public static final String APPSECRET = "b12ff5f80086414442d7947a8891909b";

    /**
     *      通过get方式请求并返回json
     */
    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject = null;
        //创建客户端连接
        DefaultHttpClient client = new DefaultHttpClient();
        //get方式请求
        HttpGet get = new HttpGet(url);
        //执行请求
        HttpResponse response = client.execute(get);
        //获取实体
        HttpEntity entity = response.getEntity();
        if(entity != null){
            String s = EntityUtils.toString(entity,"utf-8"); //转换成字符串
            jsonObject = JSONObject.fromObject(s);  //转换成jsonObject
        }
        return jsonObject;
    }

}
