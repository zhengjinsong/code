import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class test2 {
    @Test
   public void testOne(){
        Map<String,Object>map=new HashMap<>();
        String str=(String)map.get("sogn");
        System.out.println(str==null);
    }
}
