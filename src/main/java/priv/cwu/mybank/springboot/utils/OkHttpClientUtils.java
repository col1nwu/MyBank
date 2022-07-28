package priv.cwu.mybank.springboot.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author kunwang
 * @date 2020/1/15
 */
@Slf4j
public class OkHttpClientUtils {
    private static final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS).build();

    /**
     * 模拟请求
     *
     * @param url    资源地址
     * @param header head列表
     * @return
     * @throws java.text.ParseException
     * @throws IOException
     */
    public static String okHttpClientGet(String url, Map<String, String> header) throws java.text.ParseException, IOException {
        Headers headerBuild = Headers.of(header);

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .headers(headerBuild)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        response.close();
        return result;
    }

    /**
     * 模拟请求
     *
     * @param url 资源地址
     * @return
     * @throws java.text.ParseException
     * @throws IOException
     */
    public static String httpGET(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        response.close();
        return result;
    }

    public static String httpGETWithBasicAuth(String url, String username, String password) throws IOException {
        try {
            String credential = Credentials.basic(username, password);

            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .header("Authorization", credential)
                    .build();

            Response response = client.newCall(request).execute();
            String result = response.body().string();
            response.close();
            return result;
        } catch (Exception e) {
            log.error("httpGETWithBasicAuth happen error, url:{}, username:{}, password:{}, e:{}"
                    , url, username, password, e);
            throw e;
        }
    }

    public static String httpPost(String url, String bodyStr) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, bodyStr);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        response.close();
        return result;
    }

    /**
     * post请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String httpPost(String url, Map<String, String> param) {
        String result = null;
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        Set<String> keySet = param.keySet();
        for (String key : keySet) {
            String value = param.get(key);
            if (value != null) {
                formBodyBuilder.add(key, value);
            }
        }

        FormBody formBody = formBodyBuilder.build();
        Request request = new Request
                .Builder()
                .post(formBody)
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            result = response.body().string();
        } catch (Exception e) {
            log.error("Exception in HttpUtils.post(), url = {}, param = {}", url, param, e);
        }

        return result;
    }


    public static String httpPostUrlEncoded(String url, String bodyStr) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, bodyStr);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        response.close();
        return result;
    }

    public static String httpPut(String url, String bodyStr) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, bodyStr);
        Request request = new Request.Builder()
                .url(url)
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        response.close();
        return result;
    }

    public static String httpPostTWithBasicAuth(String url, String username, String password) throws IOException {
        String credential = Credentials.basic(username, password);

        Request request = new Request.Builder()
                .url(url)
                .method("POST", null)
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        response.close();
        return result;
    }

    public static String postTWithBasicAuth(String url, String username, String password) throws IOException {
        String credential = Credentials.basic(username, password);
        MediaType mediaType = MediaType.parse("json : application/json");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", FormBody.create(mediaType, ""))
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        response.close();
        return result;
    }
}
