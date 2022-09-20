import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class JiraRequestJsonNodeBulid {
    private ObjectNode payload = JsonNodeFactory.instance.objectNode();
    //构建gira请求字段fields
    ObjectNode fields = payload.putObject("fields");

    //请求参数为key-value类型
    public void setKeyValue(String field, Object value) {
        fields.put(field, (String) value);
    }

    //请求参数为key - id -value格式
    public void setKeyIdValue(String field, Object value) {
        ObjectNode issuetype = fields.putObject(field);
        issuetype.put("id", (String) value);
    }

    //请求参数为数组的格式
    public void setArry(String field, Object value) {
        ArrayNode arry = fields.putArray(field);
        ObjectNode child = arry.addObject();
        child.put("id", (String) value);
    }

    //请求参数为联机下拉框
    public void setChildValue(String field, Object pValue, Object cValue) {
        ObjectNode issuetype = fields.putObject(field);
        issuetype.put("id", (String) pValue);
        ObjectNode child = issuetype.putObject("child");
        child.put("id", (String) cValue);
    }

    public ObjectNode getPayload() {
        return this.payload;
    }
}
