import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Test2 {
    private final static String CONTENT_TYPE = "application/json";
    @Test
    public void teste2(){
        JiraHttpsRequestBuildUtil request = new JiraHttpsRequestBuildUtil("http://172.17.162.27:8080/rest/api/2/issue/NEWITOM-22832/transitions",
                "songkai", "123456");
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", CONTENT_TYPE);
        headers.put("Content-Type", CONTENT_TYPE);
        ObjectNode payload = JsonNodeFactory.instance.objectNode();
        ObjectNode transition = payload.putObject("transition");
        ObjectNode idValue=transition.put("id",191);
        //191为现场验证通过
        HttpResponse<JsonNode> response = request.post(payload,headers);
        System.out.println(response.getBody());
    }
}
