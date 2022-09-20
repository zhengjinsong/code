import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Test_Thread {
    @Test
    public  void threadTest() throws InterruptedException {
        Thread th1=new Thread(()->{
            Logger logger=Logger.getLogger("thread");
            logger.setUseParentHandlers(false);
            ConsoleHandler consoleHandler=new ConsoleHandler();
            SimpleFormatter simpleFormatter=new SimpleFormatter();
            consoleHandler.setFormatter(simpleFormatter);
            consoleHandler.setLevel(Level.ALL);
            logger.setLevel(Level.ALL);
            logger.addHandler(consoleHandler);
            for (int i = 0; i < 100; i++) {
                logger.finest(i+"");
                if(i==100){
                    Thread.currentThread().stop();
                }
            }
        });
        th1.start();
        th1.join();
        System.out.println("主线程结束");
    }
}
