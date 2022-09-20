import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * jira与部门流程系统对接
 *
 * @author 郑金松
 * @date 2022/06/28
 */
public class JiraHttpsRequestBuildUtil {
    private final static String AUTHORIZATION = "Authorization";
    private String uri;
    private String userName;
    private String password;

    private Map<String, String> headersMap = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(JiraHttpsRequestBuildUtil.class);

    public JiraHttpsRequestBuildUtil(String uri, String userName, String password) {
        this.uri = uri;
        this.userName = userName;
        this.password = password;
        buildPrometer();
    }

    public void buildPrometer() {
        JiraAuthenticationHandlerUtil auth = new JiraAuthenticationHandlerUtil(userName, password);
        this.headersMap.put(AUTHORIZATION, "Basic " + auth.encodeCredentials());
    }

    public HttpResponse<JsonNode> post(ObjectNode payload, Map<String, String> headers) {
        try {
            objectMapper();
            if (headers != null && !headers.isEmpty()) {
                this.headersMap.putAll(headers);
            }
            LOGGER.debug("调用jira接口参数{}", this.headersMap);
            return Unirest.post(uri).headers(headersMap).body(payload).asJson();

        } catch (UnirestException e) {
            LOGGER.debug("调用jira接口出错:{}", e.getMessage());
            LOGGER.debug(e.getMessage());
        }
        return null;
    }

    public HttpResponse<JsonNode> get(Map<String, String> headers) {
        try {
            objectMapper();
            if (headers != null && !headers.isEmpty()) {
                this.headersMap.putAll(headers);
            }
            System.out.println("调用jira接口参数{}"+ this.headersMap);
            return Unirest.get(uri).headers(headersMap).asJson();

        } catch (UnirestException e) {
            System.out.println("调用jira接口出错:{}"+ e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    public HttpResponse<JsonNode> uploadFilie(String url, Map<String, String> headers) {
        try {
            objectMapper();
            if (headers != null && !headers.isEmpty()) {
                this.headersMap.putAll(headers);
            }
            LOGGER.debug("调用jira接口参数{}", this.headersMap);
            LOGGER.debug("文件的路径为：{}", url);
            return Unirest.post(uri).headers(headersMap).field("file", new File(url)).asJson();
        } catch (UnirestException e) {
            LOGGER.debug("调用jira接口出错:{}", e);
            return null;
        }
    }

    public void objectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    LOGGER.debug("映射字段异常：{}", e);
                    return null;
                }

            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    LOGGER.debug("映射字段异常：{}", e);
                    return null;
                }
            }
        });
    }

}
