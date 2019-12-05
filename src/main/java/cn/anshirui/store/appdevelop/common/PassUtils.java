package cn.anshirui.store.appdevelop.common;

import java.security.MessageDigest;

/**
 * @ClassName 密码加密
 * @Description TODO
 * @Author zhangxuan
 * @Date 9:49
 * Version 1.0
 **/
public class PassUtils {

    private static String SALT_PASS = "3.14159";

    /**
    * @Author zhangxuan
    * @Description //TODO 密码加密
    * @Date 9:49 2019/12/3
    * @Param [password]
    * @return java.lang.String
    **/
    public static String getMD5Str(String password) throws Exception{
        StringBuffer buffer = new StringBuffer(SALT_PASS);
        buffer.append(password);
        String pString = buffer.toString();
        MessageDigest md = MessageDigest.getInstance("MD5");
        char[] charArray = pString.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for(int i=0;i<charArray.length;i++){
            byteArray[i] = (byte) charArray[i];
        }
        byte[] mdBytes = md.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for(int i=0;i<mdBytes.length;i++){
            int val = ((int)mdBytes[i])&0xff;
            if(val<16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
