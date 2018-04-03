## 微信授权demo
### 接口测试号申请
https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421137522  
获取到：appID 、 appsecret  

### 公众号和服务号的网页授权登陆:  
微信开发文档: 
https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842  
具体而言，网页授权流程分为四步:  
1. 引导用户进入授权页面同意授权，获取code
2. 通过code换取网页授权access_token（与基础支持中的access_token不同）
3. 如果需要，开发者可以刷新网页授权access_token，避免过期
4. 通过网页授权access_token和openid获取用户基本信息（支持UnionID机制）  

## 小结
1. 组装授权地址时，需要注意参数的顺序跟API保持一致
2. 回调地址必须在公网中能访问到的
3. 回调地址域名的设置（网页帐号	网页授权获取用户基本信息 修改 填写回调域名）

+ 外网映射工具（sun ngrok）  
 -- 参照网址自行搭建