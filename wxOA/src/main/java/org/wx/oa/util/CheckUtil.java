package org.wx.oa.util;

import java.security.MessageDigest;
import java.util.Arrays;
/**
 *  用于服务器的验证
 * */
public class CheckUtil {

    private final static String token = "snipe"; //微信发送的Token验证

    /**
     *  验证签名是否正确
     *      1）将token、timestamp、nonce三个参数进行字典序排序
     *      2）将三个参数字符串拼接成一个字符串进行sha1加密
     *      3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     *   @param signature 签名
     *   @param timestamp 时间戳
     *   @param nonce 随机数
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce){
       //排序
        String[] arr =  new String[]{token,timestamp,nonce};
        Arrays.sort(arr);
        //拼接
        StringBuffer sb = new StringBuffer();
        for (String s: arr) {
            sb.append(s);
        }
        //加密
        String key = getSha1(sb.toString());
        return key.equals(signature);
    }

    /**
	 * sha1加密
	 */
    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
}
