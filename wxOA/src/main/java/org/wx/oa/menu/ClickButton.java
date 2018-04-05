package org.wx.oa.menu;

/**
 *  Click类型菜单
 */
public class ClickButton extends Button{
    private String key; //click等点击类型必须	菜单KEY值，用于消息接口推送

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
