import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;

public class CeShiFUjian {
    public static void main(String[] args) throws UnirestException {
        String url = "http://172.17.162.27:8080/rest/api/2/issue/" + "" + "/attachments";
        System.out.println(System.getProperty("user.dir"));
        HttpResponse response = Unirest.post("http://172.17.162.27:8080/rest/api/2/issue/REL-858/attachments")
                .header("Authorization", "Basic emhlbmdqaW5zb25nOjEyMzQ1Njc4")
                .header("Accept", "application/json")
               // .header("Content-Type", "application/json")
                .header("X-Atlassian-Token", "no-check")
                .field("file", new File("D:\\file.txt"))
                .asJson();

        System.out.println(response.getBody());


// This code sample uses the  'Unirest' library:
// http://unirest.io/java.html
//        HttpResponse<JsonNode> response = Unirest.get("http://172.17.162.27:8080/rest/api/2/attachment/20429")
//                .header("Authorization", "Basic emhlbmdqaW5zb25nOjEyMzQ1Njc4")
//                .header("Accept", "application/json")
//                .asJson();
//
//        System.out.println(response.getBody());
    }


}
