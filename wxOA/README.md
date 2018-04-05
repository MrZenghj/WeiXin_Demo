## 关于微信公众开发的一些小简介
该项目Demo只是对微信开发做一些简单的了解，快速上手了解微信的一些简单开发。

### 关于接口的接入和微信账号的申请
1. 接口测试号申请,需要有一个微信开发账号（或者微信公众号）
2. 接入微信公众平台开发 具体参考微信开发的文档  
https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319  

### 简单实现微信一些功能
1. 接收普通文本消息
   + 文本消息
   + 图片消息  
       --->关于图片：  
        1）需要获取 access_token  
        2）通过临时素材的上传获取到对应的数据mediaId
       
   + 回复图文消息
2. 接收事件推送 
   + 关注/取消关注事件   
   ...等等 一系列操作
3. 自定义菜单的创建  
    
4. 自定义菜单事件推送
    + 需要注意的是（地理位置的推送 MsgType 并不是API文档的event事件而是location事件）