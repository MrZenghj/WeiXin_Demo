package org.wx.auth.controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wx.auth.util.AuthUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
@Controller
@RequestMapping("/WxAuth")
public class AuthController {
    /**
     *  授权登录
     *      入口地址
     *          第一步：用户同意授权，获取code
     */
    @RequestMapping("/wxLogin")
    public void login(HttpServletResponse response) throws IOException {
        System.out.println("login");
        //设置回调地址 （必须在公网能访问到的）
        String backUrl = "http://zhoufei.free.ngrok.cc/WxAuth/callBack";
        //用户同意授权，获取code
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ AuthUtil.APPID
                + "&redirect_uri="+ URLEncoder.encode(backUrl)
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";
        response.sendRedirect(url); //重定向到url中
    }
    /**
     *  回调地址
     *       第二步：通过code换取网页授权access_token
     * */
    @RequestMapping("/callBack")
    public String callBack(HttpServletRequest request) throws IOException {
        System.out.println("进入callBack");
        //获取code
        String code = request.getParameter("code");
        //请求返回 换取网页授权access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+AuthUtil.APPID
                + "&secret="+AuthUtil.APPSECRET
                + "&code="+code
                + "&grant_type=authorization_code";
        JSONObject jsonObject = AuthUtil.doGetJson(url);
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");
        /*
        第三步：刷新access_token（如果需要）
        String URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+AuthUtil.APPID
                + "&grant_type=refresh_token"
                + "&refresh_token=REFRESH_TOKEN";
        JSONObject jsonObjects = AuthUtil.doGetJson(URL);
        */

        //第四步：拉取用户信息(需scope为 snsapi_userinfo)
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+token
                + "&openid="+openid
                + "&lang=zh_CN";
        JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
        //打印用户信息
        System.out.println(userInfo);

        //1、直接使用用户信息进行登录
        request.setAttribute("userInfo",userInfo);
        return "userinfo";

        //2、将微信与当前的信息系统进行绑定

    }
}
