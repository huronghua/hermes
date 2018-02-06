package com.banma.hermes.consumer.Utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.banma.hermes.consumer.domain.MessageReceive;
import com.banma.hermes.consumer.domain.WebMessage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by banma on 2017/12/5.
 */
public class TransUtil {

    public static MessageReceive messageExt2Java(MessageExt temp){
        String wholeMes = new String(temp.getBody());
        JSONObject jasonObject = JSONObject.parseObject(wholeMes);
        MessageReceive messageReceive = JSONObject.toJavaObject(jasonObject,MessageReceive.class);
        return messageReceive;
    }

    public static WebMessage messageExt2webMessage(MessageExt temp){
        String wholeMes = new String(temp.getBody());
        JSONObject jasonObject = JSONObject.parseObject(wholeMes);
        WebMessage webMessage = JSONObject.toJavaObject(jasonObject,WebMessage.class);
        return webMessage;
    }

    public static MessageReceive StringToJava(String temp){
        JSONObject jasonObject = JSONObject.parseObject(temp);
        MessageReceive messageReceive = JSONObject.toJavaObject(jasonObject,MessageReceive.class);
        return messageReceive;
    }

    public static void byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
