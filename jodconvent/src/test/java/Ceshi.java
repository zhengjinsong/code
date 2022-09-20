import org.jodconverter.JodConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.junit.Test;

import java.io.File;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class Ceshi {
    @Test
    public void ceshi() {
        Map<String, Object> innerMap=new HashMap<>();
        Double value=new Double(874788900);
        Object object=(Object)value;
//        Double value2=new Double(874115511);
//        System.out.println(value.toString()+"");
//        System.out.println(value);
//
////        System.out.println(value2.toString());
//         innerMap.put("name",value);
//         innerMap.put("name2",value2);
//         Object bo1=innerMap.get("name");
//         Object bo2=innerMap.get("name2");
        NumberFormat nf=NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        System.out.println(nf.format(object));
    }
}
