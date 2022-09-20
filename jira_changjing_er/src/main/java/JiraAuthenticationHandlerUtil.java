import com.atlassian.httpclient.api.Request;
import org.apache.commons.codec.binary.Base64;

/**
 * jira与部门流程系统对接
 *
 * @author 郑金松
 * @date 2022/06/28
 */
public class JiraAuthenticationHandlerUtil {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final String username;
    private final String password;

    public JiraAuthenticationHandlerUtil(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void configure(Request.Builder builder) {
        builder.setHeader(AUTHORIZATION_HEADER, "Basic " + this.encodeCredentials());
    }

    public String encodeCredentials() {
        byte[] credentials = (this.username + ':' + this.password).getBytes();
            return new String(Base64.encodeBase64(credentials));
    }
}
