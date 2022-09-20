package util;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class AllRealm1 implements Realm {
    @Override
    public String getName() {
        return "allRelam1";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        String password = new String((char[])authenticationToken.getCredentials());
        if (!"zhang".equals(userName)) {
            throw new AuthenticationException("用户名错误!");
        }
        if (!"123".equals(password)) {
            throw new AuthenticationException("密码错误!");
        }
        return new SimpleAuthenticationInfo(userName, password, getName());
    }
}
