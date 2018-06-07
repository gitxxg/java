package cn.ghl.tester.xml;

import java.util.List;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/23/2018
 */
public class XMLConfigDemo {

    public static void readXMLFile() throws Exception {
        XMLConfiguration config = new XMLConfiguration("tables.xml");
        String name1 = config.getString("tables.table(0).name");
        System.out.println(name1);
        String tableType1 = config.getString("tables.table(0)[@tableType]");
        System.out.println(tableType1);
        String fieldName1 = config.getString("tables.table(1).fields.field.name");
        System.out.println(fieldName1);
        String fieldType1 = config.getString("tables.table.fields.field(0).type");
        System.out.println(fieldType1);

        HierarchicalConfiguration sub = config.configurationAt("tables.table(0)");
        String tableName = sub.getString("name");
        System.out.println("tableName = " + tableName);
        List<Object> fieldNames = sub.getList("fields.field.name");
        System.out.println("size = " + fieldNames.size());
        for (Object obj : fieldNames) {
            System.out.println(obj);
        }

        List<Object> fieldTypes = config.getList("tables.table(0).fields.field.type");
        for (Object obj : fieldTypes) {
            System.out.println(obj);
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        readXMLFile();
    }
}
