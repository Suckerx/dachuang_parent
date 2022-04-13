package com.sucker.eduservice.aliyunRTC;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class App {
    // 监听端口
    private int listen;
    // 应用ID
    private String appID;
    // 应用密钥
    private String appKey;
    // 服务地址
    private String gslb;
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

    // 请求处理类
    class LoginHandler implements HttpHandler {
        public void handle(HttpExchange he) throws IOException {
            if (he.getRequestHeaders().containsKey("Origin")) {
                // 配置响应头
                Headers headers = he.getResponseHeaders();
                headers.set("Access-Control-Allow-Origin", "*");
                headers.set("Access-Control-Allow-Methods", "GET,POST,HEAD,PUT,DELETE,OPTIONS");
                headers.set("Access-Control-Expose-Headers", "Server,Range,Content-Length,Content-Range\");\n" +
                        "                headers.set(\"Access-Control-Allow-Headers\", \"Origin,Range,Accept-Encoding,Referer,Cache-Control,X-Proxy-Authorization,X-Requested-With,Content-Type");
            }

            if (he.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                httpWrite(he, 200, "");
                return;
            }

            // 将请求参数放入map中
            Map<String, String> query = new HashMap<String, String>();
            for (String param : he.getRequestURI().getQuery().split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    query.put(entry[0], entry[1]);
                } else {
                    query.put(entry[0], "");
                }
            }

            // 频道ID
            String channelID = query.get("room");
            // 用户ID
            String user = query.get("user");

            // 处理非法参数
            if (channelID == "" || user == "") {
                httpWrite(he, 500, "invalid parameter");
                return;
            }

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
                httpWrite(he, 500, e.getMessage());
                return;
            }

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

    // 解析命令行参数
    public void run(String[] args) throws Exception {
        Options options = new Options();
        options.addOption(new Option("l", "listen", true, "listen port"));
        options.addOption(new Option("a", "appid", true, "the id of app"));
        options.addOption(new Option("k", "appkey", true, "the key of app"));
        options.addOption(new Option("g", "gslb", true, "the url of gslb"));
        CommandLine cli = new PosixParser().parse(options, args);
        if (!cli.hasOption("listen")) {
            throw new Exception("no listen");
        }
        if (!cli.hasOption("appid")) {
            throw new Exception("no appid");
        }
        if (!cli.hasOption("appkey")) {
            throw new Exception("no appkey");
        }
        if (!cli.hasOption("gslb")) {
            throw new Exception("no gslb");
        }

        // 监听端口
        listen = Integer.parseInt(cli.getOptionValue("listen"));
        // 应用ID
        appID = cli.getOptionValue("appid");
        // 应用密钥
        appKey = cli.getOptionValue("appkey");
        // 服务地址
        gslb = cli.getOptionValue("gslb");
        System.out.printf("Server listen=%d, appid=%s, appkey=%s, gslb=%s\n", listen, appID, appKey, gslb);

        // 创建httpserver
        HttpServer server = HttpServer.create(new InetSocketAddress(listen), 0);
        server.createContext("/app/v1/login", new LoginHandler());
        server.start();
    }

    public static void main(String[] args) {
        try {
            new App().run(args);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
