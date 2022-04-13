package com.sucker.eduservice.aliyunRTC;

import com.sucker.commonutils.R;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * aliyun音视频工具类
 */
public class LoginHandle {

    public static R createHttpServer(){
        //System.out.printf("Server listen=%d, appid=%s, appkey=%s, gslb=%s\n", listen, appID, appKey, gslb);

        try {
            // c
            HttpServer server = HttpServer.create(new InetSocketAddress(8002), 0);
            server.createContext("/app/v1/login", new TestHandle());
            server.start();
            return R.ok();
        } catch (IOException e) {
            e.printStackTrace();
            return R.error().message("创建httpserver出错！");
        }
    }

}
