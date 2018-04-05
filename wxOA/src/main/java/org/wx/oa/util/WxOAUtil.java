package org.wx.oa.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.wx.oa.entity.AccessToken;
import org.wx.oa.menu.Button;
import org.wx.oa.menu.ClickButton;
import org.wx.oa.menu.Menu;
import org.wx.oa.menu.ViewButton;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WxOAUtil {

    public static final String APPID = "wxe4797497c3814e3b";
    public static final String APPSECRET = "b12ff5f80086414442d7947a8891909b";
    public static final String ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static final String UPLOAD_URL =
            "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    public static final String CREATE_MENU_URL =
            "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public static final String QUERY_MENU_URL =
            "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    public static final String DELETE_MENU_URL =
            "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";


    /**
     *     通过get方式请求接口获取数据（json格式）
     *     @param  url 通过url获取数据
     * */
    public static JSONObject doGetJson(String url){
        JSONObject jsonObject = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        try{
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String s = EntityUtils.toString(entity,"utf-8");
                jsonObject = JSONObject.fromObject(s);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    /**
     *      通过post方式请求接口获取数据（json格式）
     *      @param  url 通过url获取数据
     *      @param  outstr 传递的参数内容
     * */
    public static JSONObject doPostJson(String url,String outstr){
        JSONObject jsonObject = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try{
            post.setEntity(new StringEntity(outstr,"utf-8")); //设置参数
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String s = EntityUtils.toString(entity,"utf-8");
                jsonObject = JSONObject.fromObject(s);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     *  获取access_token
     * */
    public static AccessToken getAccessToken(){
        AccessToken accessToken = new AccessToken();
        String url  = ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        JSONObject jsonObject = doGetJson(url);
        if(jsonObject != null){
            accessToken.setAccess_token(jsonObject.getString("access_token")); //凭证
            accessToken.setExpires_in(jsonObject.getString("expires_in")); //有效时间
        }
        return accessToken;
    }

    /**
     *     新增临时素材
     *     微信图片的上传和获取media_id
     *     @param path 文件路径
     *     @param access_token 微信凭证
     *     @param type  素材的类型
     *     @return mediaId 返回图片流的mediaId
     * */
    public static String uploadTemp(String path,String access_token,String type) throws IOException {
        File file = new File(path);
        if(!file.isFile() || !file.exists()) {
            throw new IOException();
        }
        String url = UPLOAD_URL.replace("ACCESS_TOKEN", access_token).replace("TYPE", type);
        URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        //设置请求头信息
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");

        //设置边界信息
        String BOUNDARY = "----------" + System.currentTimeMillis();;
        conn.setRequestProperty("Content-Type" ,"multipart/form-data;boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:appication/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获取输出流
        OutputStream out = new DataOutputStream(conn.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件通过流的方式推入到url
        DataInputStream input = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] buffer = new byte[1024];
        while((bytes = input.read(buffer))!= -1) {
            out.write(buffer, 0, bytes);
        }
        input.close();

        //结尾部分
        byte[] foot = ("\r\n--"+BOUNDARY+"--\r\n").getBytes("utf-8");

        out.write(foot);
        out.flush();
        out.close();

        StringBuffer buff = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader来读取url的响应
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while( (line = reader.readLine()) != null) {
                buff.append(line);
            }
            if(result == null) {
                result = buff.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(jsonObject);
        String typeName = "media_id";
        if(!"image".equals(type)) {
            typeName = "thumb_media_id"; //音乐消息的缩略图
        }
        String mediaId = jsonObject.getString(typeName);
        return mediaId;

    }

    /**
     *  组装一个微信菜单
     *  @return  返回菜单
     */
    public static Menu initMenu(){
        Menu menu = new Menu();
        //一级菜单 click类型
        ClickButton cb = new ClickButton();
        cb.setType("click");
        cb.setKey("1111");
        cb.setName("Help");
        //一级菜单 view类型
        ViewButton vb = new ViewButton();
        vb.setName("imooc视图");
        vb.setType("view");
        vb.setUrl("https://www.imooc.com/");
        //二级菜单
        ClickButton cb21 = new ClickButton();
        cb21.setType("scancode_push");
        cb21.setKey("21");
        cb21.setName("扫码回复");

        ClickButton cb22 = new ClickButton();
        cb22.setType("location_select");
        cb22.setKey("22");
        cb22.setName("地理位置");
        //一级菜单 包含两个二级菜单
        Button button = new Button();
        button.setName("菜单");
        button.setSub_button(new Button[] {cb21,cb22});
        //初始化一级菜单
        menu.setButton(new Button[] {cb,vb,button});
        return menu;
    }

    /**
     * @Title: 创建菜单
     *  @return  int 返回创建菜单是否成功码
     */
    public static int crateMenu(String token,String menu) {
        int result = -1;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject json = doPostJson(url, menu);
        if(json != null) {
            result = json.getInt("errcode");
        }
        return result;
    }

    /**
     * @Title: 菜单查询
     * @return 菜单json
     */
    public static JSONObject queryMenu(String token) {
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject json = doGetJson(url);
        return json;
    }

    /**
     * @Title: 删除菜单
     * @return  int 返回删除菜单是否成功码
     */
    public static int deleteMenu(String token) {
        String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject json = doGetJson(url);
        int result = -1;
        if(json != null) {
            result = json.getInt("errcode");
        }
        return result;
    }

}
