package com.example.qinghua_cehsi2.unit;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RequestClientUtil {
    private static final String ENCODETYPE = "UTF-8";
    private static final String REQUEST_HEADER_ACCEPT_KEY = "Accept";
    private static final String REQUEST_HEADER_ACCEPT_VALUE = "application/json";
    private static final String REQUEST_HEADER_CONNECTION_KEY = "Connection";
    private static final String REQUEST_HEADER_CONNECTION_VALUE = "keep-alive";
    private static final String REQUEST_HTTPS = "https";

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestClientUtil.class);

    public static String get(String url) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODETYPE);
        GetMethod getMethod = new GetMethod();
        if (url.startsWith(REQUEST_HTTPS)) {
            Protocol myhttps = new Protocol(REQUEST_HTTPS,
                    new MySSLProtocolSocketFactory(), 8001);
            Protocol.registerProtocol(REQUEST_HTTPS, myhttps);
        }
        getMethod.setRequestHeader(REQUEST_HEADER_ACCEPT_KEY,
                REQUEST_HEADER_ACCEPT_VALUE);
        getMethod.setRequestHeader(REQUEST_HEADER_CONNECTION_KEY,
                REQUEST_HEADER_CONNECTION_VALUE);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(0, false));
        getMethod.setURI(new URI(url, false));
        httpClient.executeMethod(getMethod);
        byte[] responseByte = getMethod.getResponseBody();
        String responseMsg = new String(responseByte, 0,
                responseByte.length, ENCODETYPE);
        LOGGER.debug("get返回参数：{}", responseMsg);
        return responseMsg;

    }

    public static String postToWSO(String url,
                                   Map<String, String> params, Map<String, String> headers) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODETYPE);
        PostMethod postMethod = new PostMethod();
        if (url.startsWith(REQUEST_HTTPS)) {
            Protocol myhttps = new Protocol(REQUEST_HTTPS,
                    new MySSLProtocolSocketFactory(), 8001);
            Protocol.registerProtocol(REQUEST_HTTPS, myhttps);
        }
        postMethod.setURI(new URI(url, false));
        Iterator<Entry<String, String>> handerIterator = headers.entrySet().iterator();
        while (handerIterator.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>) handerIterator.next();
            String parameterName = entry.getKey();
            String parameterValue = entry.getValue();
            postMethod.setRequestHeader(parameterName,
                    parameterValue);
        }
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(0, false));
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        StringBuffer logRequestParam = new StringBuffer();
        while (iterator.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>) iterator
                    .next();
            String parameterName = entry.getKey();
            String parameterValue = entry.getValue();
            postMethod.setParameter(parameterName, parameterValue);
            logRequestParam.append(parameterName + "=" + parameterValue
                    + "&");
        }
        logRequestParam.deleteCharAt(logRequestParam.length() - 1);
        LOGGER.debug("post请求参数：{}", logRequestParam);
        httpClient.executeMethod(postMethod);
        byte[] responseByte = postMethod.getResponseBody();
        String responseMsg = new String(responseByte, 0,
                responseByte.length, ENCODETYPE);
        LOGGER.debug("post返回参数：{}", responseMsg);
        return responseMsg;
    }
}
