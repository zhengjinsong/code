import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Jira_test {
    private static final String ITS_DIRECTORY_CONTRAST = "its_directory_contrast.json";
    private static final String ITS_LEXIANG_CONFIG = "its_lexiang_config.json";

    private final static String CONTENT_TYPE = "application/json";
    private static final Logger LOGGER = LoggerFactory.getLogger(JiraHttpsRequestBuildUtil.class);

    @Test
    public void main() throws UnirestException {
//        HttpResponse<JsonNode> response = Unirest.get("http://172.17.162.27:8080/rest/api/2/issue/NEWITOM-22704")
//                .header("Authorization", "Basic emhlbmdqaW5zb25nOjEyMzQ1Njc4")
//                .header("Accept", "application/json")
//                .asJson();
//        System.out.println(response.getBody());
        String uri = "/relax/jira/35189/NEWITOM-22747";
        String issKey = uri.substring(uri.lastIndexOf("/") + 1);
        boolean status = checkStatusYiHuiGui(issKey);
//        if (!status){
//            return;
//        }
        System.out.println(status);
        syncRelax(issKey);
    }

    private boolean checkStatusYiHuiGui(String issKey) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", CONTENT_TYPE);
        JiraHttpsRequestBuildUtil request = new JiraHttpsRequestBuildUtil("http://172.17.162.27:8080/rest/api/2/issue/" + issKey + "?fields=status", "zhengjinsong", "12345678");
        HttpResponse<JsonNode> response = request.get(headers);
        JSONObject jsonObjects = JSONObject.parseObject(response.getBody().toString());
        JSONObject fields = jsonObjects.getJSONObject("fields");
        if (fields == null) {
            return false;
        }
        String statusName = fields.getJSONObject("status").getString("name");
        if ("已回归".equals(statusName)) {
            return true;
        }
        return false;
    }

    private void syncRelax(String issKey){
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", CONTENT_TYPE);
        JiraHttpsRequestBuildUtil request = new JiraHttpsRequestBuildUtil("http://172.17.162.27:8080/rest/api/2/issue/" + issKey, "zhengjinsong", "12345678");
        HttpResponse<JsonNode> response = request.get(headers);
        JSONObject jsonObjects = JSONObject.parseObject(response.getBody().toString());
        JSONObject fields = jsonObjects.getJSONObject("fields");
        if (fields == null) {
            return;
        }
        String qxyyText = fields.getJSONObject("customfield_10500").getString("value");
        System.out.println("缺陷原因：" + qxyyText);
        //获取relax工单id
        String workId = fields.getString("customfield_11054");
        System.out.println("工单id：" + workId);
        //影响版本
        String yxbb = fields.getString("customfield_11119");
        System.out.println("影响版本：" + yxbb);
        //原因描述
        String yyms = fields.getString("customfield_11008");
        System.out.println("原因描述:" + yyms);
        //解决方案的影响范围
        String yxfw = fields.getString("customfield_11120");
        System.out.println("影响范围:" + yxfw);
        //hotfixbanbe
        JSONObject hotfixJson = fields.getJSONObject("customfield_11002");
        if (hotfixJson != null) {
            String hotfix = hotfixJson.getString("value");
            System.out.println("hotfix版本：" + hotfix);
        }
        //备注
        JSONObject comment = fields.getJSONObject("comment");
        JSONArray comments = comment.getJSONArray("comments");
        String beizhu = "";
        for (int i = 0; i < comments.size(); i++) {
            JSONObject pl = comments.getJSONObject(i);
            beizhu += pl.getJSONObject("author").getString("displayName") +
                    "添加评论：" + pl.getString("body")+"\n";
        }
        System.out.println("描述备注：" + beizhu);
    }

}
