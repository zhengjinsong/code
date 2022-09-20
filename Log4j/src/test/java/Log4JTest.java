import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;

public class Log4JTest {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void log(){
        logger.error("--------------------请求失败--------------------");

    }

}
