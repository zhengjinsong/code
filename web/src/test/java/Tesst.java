import mapper.CityMapper;
import mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.City;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Tesst {
    Logger logger = LoggerFactory.getLogger(Tesst.class);
    @Test
    public void test() throws IOException {
        //1. 加载mybatis的核心配置文件，获取 SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2. 获取SqlSession对象，用它来执行sql
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3. 执行sql
        //3.1 获取UserMapper接口的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<Object> objects = userMapper.selectAll();
        System.out.println(objects);
        CityMapper cityMapper=sqlSession.getMapper(CityMapper.class);
        List<City> city = cityMapper.getCity();
        logger.debug(city.toString());
    }
}
