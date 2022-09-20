
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class GeneralHttpClientUtil {
    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    //Get请求
    public static String doGet(Map<String, String> paras, Map<String, String> headers, String url) {
        //构建请求参数
        if (paras != null && !paras.isEmpty()) {
            url = url + "?";
            Set<Map.Entry<String, String>> entries = paras.entrySet();
            for (Map.Entry<String, String> para : entries) {
                String paraName = para.getKey();
                String paraValue = para.getValue();
                char wg = url.charAt(url.length() - 1);
                if (wg == '?') {
                    url = url + paraName + "=" + paraValue;
                } else {
                    url = url + "&" + paraName + "=" + paraValue;
                }
            }
        }
        //构建请求头
        HttpGet httpGet = new HttpGet(url);
        if (headers != null && !headers.isEmpty()) {
            Set<Map.Entry<String, String>> heads = headers.entrySet();
            for (Map.Entry<String, String> headEntry : heads) {
                httpGet.setHeader(headEntry.getKey(), headEntry.getValue());
            }
        }
        System.out.println("url:" + url);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            System.out.println(response.toString());
            String result = "";
            //响应为200为正确返回
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
                System.out.println("响应参数:" + result);
                return result;
            }

        } catch (Exception e) {
            System.out.println("HTTP获取返回结果失败：{}" + e.toString());
        }
        return null;
    }

    //Post请求
    public static String doPost(Map<String, String> paras, Map<String, String> headers, String url) {
        HttpPost httpPost = new HttpPost(url);
        //创建参数集合
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        if (paras != null && !paras.isEmpty()) {
            Set<Map.Entry<String, String>> parmSet = paras.entrySet();
            for (Map.Entry<String, String> paraEntry : parmSet) {
                list.add(new BasicNameValuePair(paraEntry.getKey(), paraEntry.getValue()));
            }
        }
        if (!list.isEmpty()) {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                System.out.println("不支持的编码形式：" + ex);
            }
        }
        //构建请求头
        if (headers != null && !headers.isEmpty()) {
            Set<Map.Entry<String, String>> heads = headers.entrySet();
            for (Map.Entry<String, String> headEntry : heads) {
                httpPost.setHeader(headEntry.getKey(), headEntry.getValue());
            }
        }
        System.out.println("url:" + url);
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            System.out.println(response.toString());
            String result = "";
            //响应为200为正确返回
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("响应参数:" + result);
                return result;
            }

        } catch (Exception e) {
            System.out.println("HTTP获取返回结果失败：{}" + e.toString());
        }
        return null;
    }
}
