import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class test {
    @Test
    public void testone() {
        //获取日志记录器对象
        Logger logger = Logger.getLogger("logManger");
        //未定义日志级别时默认使用父级的日志配置，父级为loggerRoot
        logger.setUseParentHandlers(false);
        //创建日志控制台处理器
        ConsoleHandler consoleHandler = new ConsoleHandler();
        //简单的格式转换器
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        //关联
        consoleHandler.setFormatter(simpleFormatter);
        //日志记录器添加处理器
        logger.addHandler(consoleHandler);
        //配置日志记录器输出日志级别
        logger.setLevel(Level.SEVERE);
        //配置控制台处理器的输出级别（当同时配置记录器和处理器时取交集，即范围小）
        consoleHandler.setLevel(Level.ALL);
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    @Test
    public void testtwo() throws IOException {
        //获取日志记录器对象
        Logger logger = Logger.getLogger("logManger");
        //未定义日志级别时默认使用父级的日志配置，父级为loggerRoot
        logger.setUseParentHandlers(false);
        //创建日志控制台处理器
        ConsoleHandler consoleHandler = new ConsoleHandler();
        //创建日志文件处理器
        FileHandler fileHandler=new FileHandler("E:\\log\\log.log");
        //简单的格式转换器
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        //关联
        consoleHandler.setFormatter(simpleFormatter);
        fileHandler.setFormatter(simpleFormatter);
        //日志记录器添加处理器
        logger.addHandler(consoleHandler);
        logger.addHandler(fileHandler);
        //配置日志记录器输出日志级别
        logger.setLevel(Level.SEVERE);
        //配置控制台处理器的输出级别（当同时配置记录器和处理器时取交集，即范围小）
        consoleHandler.setLevel(Level.ALL);
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    @Test
    public void  test2() throws IOException {
        System.out.println(System.getProperty("user.dir"));
        Logger logger=Logger.getLogger("song");
        logger.setUseParentHandlers(false);
        SimpleFormatter simpleFormatter=new SimpleFormatter();
        ConsoleHandler consoleHandler=new ConsoleHandler();
        FileHandler fileHandler=new FileHandler("log/log.log");
        fileHandler.setFormatter(simpleFormatter);
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.addHandler(fileHandler);
        logger.severe("最高级别");
        logger.warning("警告");
        logger.info("info");
        logger.finer("fast");
    }
}
