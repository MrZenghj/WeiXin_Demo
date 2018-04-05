package org.wx.oa.test;

import net.sf.json.JSONObject;
import org.wx.oa.entity.AccessToken;
import org.wx.oa.util.MessageUtil;
import org.wx.oa.util.WxOAUtil;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        //测试获取 access_token
        AccessToken accessToken = WxOAUtil.getAccessToken();
        System.out.println(accessToken);
        //获取image 的mediaId
        //String mediaId = WxOAUtil.uploadTemp("C:\\Users\\snipe\\Desktop\\tower.jpg",accessToken.getAccess_token(),"image");
        //System.out.println(mediaId);
        //获取image 的mediaId
        //String thumb_media_id = WxOAUtil.uploadTemp("C:\\Users\\snipe\\Desktop\\miao.jpg",accessToken.getAccess_token(),"thumb");
        //System.out.println(thumb_media_id);
        //菜单的初始化
        String menu = JSONObject.fromObject(WxOAUtil.initMenu()).toString();
        int result = WxOAUtil.crateMenu(accessToken.getAccess_token(), menu);
        if(result == 0) {
            System.out.println("菜单创建成功");
        }else {
            System.out.println("失败");
        }

        //菜单的查询
        /*JSONObject queryMenu = WxOAUtil.queryMenu(accessToken.getAccess_token());
        System.out.println(queryMenu);*/

        //菜单的删除
        /*int result = WxOAUtil.deleteMenu(accessToken.getAccess_token());
        if(result == 0) {
            System.out.println("菜单删除成功");
        }else {
            System.out.println("未成功");
        }*/

    }
}
