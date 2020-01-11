package cn.anshirui.store.appdevelop.common;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.Base64Utils;

import java.io.File;

/**
 * @ClassName 图片工具类
 * @Description TODO
 * @Author zhangxuan
 * @Date 17:39
 * Version 1.0
 **/
public class PictureUtils {

    /**
     * @Author zhangxuan
     * @Description //TODO base64转化图片
     * @Date 9:53 2019/12/6
     * @Param [base64Data]
     * @return boolean
     **/
    public static String base64ByFile(String base64Data){
        String dataPrix = "";
        String data = "";
        if(StringUtils.isEmpty(base64Data)){
            return null;
        }else{
            String [] d = base64Data.split("base64,");
            if(d != null && d.length == 2){
                dataPrix = d[0];
                data = d[1];
            }else{
                return null;
            }
        }
        String suffix = "";
        if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
            suffix = ".jpg";
        } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else{
            return null;
        }
        String tempFileName = UUID.randomUUID().toString() + suffix;
        //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
        byte[] bs = Base64Utils.decodeFromString(data);
        try{
            FileUtil.touch(KeyWord.PICTURE_URL + File.separator + tempFileName);
            FileUtil.writeBytes(bs,KeyWord.PICTURE_URL + File.separator + tempFileName);
            return KeyWord.PICTURE_URL + File.separator + tempFileName + "&&" + KeyWord.ICON_WEBURL + tempFileName;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
