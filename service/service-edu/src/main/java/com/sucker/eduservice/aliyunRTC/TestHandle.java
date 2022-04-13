package com.sucker.eduservice.aliyunRTC;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestHandle implements HttpHandler {

    // 监听端口
    private final int listen = 8002;
    // 应用ID
    private final String appID = "wtpdqsx9";
    // 应用密钥
    private final String appKey = "efc7ffbee986c58392c33c665f12bd80";
    // 服务地址
    private final String gslb = "https://rgslb.rtc.aliyuncs.com";
    // 频道随机码
    private String nonce;
    // 频道时间戳
    private Long timestamp;
    // 用户唯一标识
    private String userID;
    // 加入频道token
    private String token;


    // 生成token
    public static String createToken(
            String appId, String appKey, String channelId, String userId,
            String nonce, Long timestamp
    ) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(appId.getBytes());
        digest.update(appKey.getBytes());
        digest.update(channelId.getBytes());
        digest.update(userId.getBytes());
        digest.update(nonce.getBytes());
        digest.update(Long.toString(timestamp).getBytes());

        String token = DatatypeConverter.printHexBinary(digest.digest()).toLowerCase();
        return token;
    }

    // 生成userID
    public static String createUserID(String channelID, String user) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(channelID.getBytes());
        digest.update("/".getBytes());
        digest.update(user.getBytes());

        String uid = DatatypeConverter.printHexBinary(digest.digest()).toLowerCase();
        return uid.substring(0, 16);
    }

    // 写响应报文工具方法
    private static void httpWrite(HttpExchange he, int code, String response) throws IOException {
        OutputStream os = he.getResponseBody();
        he.sendResponseHeaders(code, response.length());
        os.write(response.getBytes());
        os.close();
    }



    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestHeaders().containsKey("Origin")) {
            // 配置响应头
            Headers headers = he.getResponseHeaders();
            headers.set("Access-Control-Allow-Origin", "*");
            headers.set("Access-Control-Allow-Methods", "GET,POST,HEAD,PUT,DELETE,OPTIONS");
            headers.set("Access-Control-Expose-Headers", "Server,Range,Content-Length,Content-Range\");\n" +
                    "                headers.set(\"Access-Control-Allow-Headers\", \"Origin,Range,Accept-Encoding,Referer,Cache-Control,X-Proxy-Authorization,X-Requested-With,Content-Type");
        }

        System.out.println("1");

        if (he.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            httpWrite(he, 200, "");
            return;
        }

        // 将请求参数放入map中
        Map<String, String> query = new HashMap<String, String>();
        String[] split = he.getRequestURI().getQuery().split("&");
        for (String param : split) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                query.put(entry[0], entry[1]);
            } else {
                query.put(entry[0], "");
            }
        }

        System.out.println("--------------------------------");

        // 频道ID
        String channelID = query.get("room");
        // 用户ID
        String user = query.get("user");

        // 处理非法参数
        if (channelID == "" || user == "") {
            httpWrite(he, 500, "invalid parameter");
            return;
        }

        System.out.println("2222222222");

        try {
            userID = createUserID(channelID, user);

            //令牌随机码，这里使用AK-+UUID
            nonce = String.format("AK-%s", UUID.randomUUID().toString());

            Calendar nowTime = Calendar.getInstance();
            // 令牌过期时间，48小时
            nowTime.add(Calendar.HOUR_OF_DAY, 48);
            timestamp = nowTime.getTimeInMillis() / 1000;

            token = createToken(appID, appKey, channelID, userID, nonce, timestamp);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("error");
            httpWrite(he, 500, e.getMessage());
            return;
        }

        System.out.println("3333333");

        // 生成随机用户名
        String username = String.format("%s?appid=%s&channel=%s&nonce=%s&timestamp=%d",
                userID, appID, channelID, nonce, timestamp);

        System.out.printf("Login: appID=%s, appKey=%s, channelID=%s, userID=%s, nonce=%s, " +
                        "timestamp=%d, user=%s, userName=%s, token=%s\n",
                appID, appKey, channelID, userID, nonce, timestamp, user, username, token);

        // 封装响应报文，json格式
        JSONObject response = new JSONObject()
                .put("code", 0)
                .put("data", new JSONObject()
                        .put("appid", appID)
                        .put("userid", userID)
                        .put("gslb", new JSONArray().put(gslb))
                        .put("token", token)
                        .put("nonce", nonce)
                        .put("timestamp", timestamp)
                        .put("turn", new JSONObject()
                                .put("username", username)
                                .put("password", token)
                        ));
        he.getResponseHeaders().set("Content-Type", "application/json");
        httpWrite(he, 200, response.toString());
    }

}
