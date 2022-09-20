import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;
import util.LoginG;

import java.io.PrintStream;
import java.net.URL;

public class Test1 {
    @Test
    public void test1(){
        URL resource = this.getClass().getClassLoader().getResource("shiro_jdbc.ini");
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        System.out.println(resource.getPath());
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(resource.getPath());
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("登录失败！");
            System.out.println(e.toString());
            //5、身份验证失败
        }
        System.out.println("是否认证："+subject.isAuthenticated());
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        //6、退出
        subject.logout();
    }
    @Test
    public void Allthriztion(){
        LoginG loginG =new LoginG();
        loginG.login("classpath:shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(2, principalCollection.asList().size());
    }
}
