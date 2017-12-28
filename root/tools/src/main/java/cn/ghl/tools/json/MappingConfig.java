package cn.ghl.tools.json;

import groovy.lang.GroovyClassLoader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MappingConfig {

    private static Logger LOG = LoggerFactory
            .getLogger(MappingConfig.class);

    private static GroovyClassLoader gcl = new GroovyClassLoader();

    public static String defaultDictPath = "/ObjectToJsonMapping";

    public static Map<String, List<ObjectBind>> getObjectBindMap (String dictPath) {

        try {

            Map<String, List<ObjectBind>> objectBindMap = new HashMap<>();

            List<ObjectBind> objectBindList = new ArrayList<>();

            Pattern patternHeader = Pattern.compile(
                    "(.+?)\\|(.+?)<");
            Pattern patternNode = Pattern.compile(
                    "(.+?)\\{(.*)\\}=(.+?)\\{(.*)\\}");
            Pattern patternListNode = Pattern.compile(
                    "(.+?)\\[(.*)\\]=(.+?)\\[(.*)\\]");
            boolean isNode = false;
            dictPath = StringUtils.isBlank(dictPath) ? defaultDictPath : dictPath;
            InputStream inputStream = MappingConfig.class.getResourceAsStream(dictPath);
            List<String> lines = IOUtils.readLines(inputStream);
            String objectType = null;
            String bindName = null;
            for (String line : lines) {
                if (isNode == false && line.contains("<")) {
                    //LOG.debug("节点开始<<<<");
                    objectBindList = new ArrayList<>();
                    isNode = true;
                    Matcher m = patternHeader.matcher(line);
                    if (m.matches()) {
                        objectType = m.group(1);
                        bindName = m.group(2);
                    }
                } else if (isNode == true && line.contains(">")) {
                    //LOG.debug("节点结束>>>>");
                    objectBindMap.put(objectType, objectBindList);
                    isNode = false;
                    objectType = null;
                    bindName = null;
                } else if (isNode == true && line.contains("[")) {
                    Matcher m = patternListNode.matcher(line);
                    if (m.matches()) {
                        ObjectBind objectBind = new ObjectBind();
                        objectBind.setList(true);
                        objectBind.setListObjectType(m.group(2));
                        objectBind.setBindName(bindName);
                        objectBind.setObjectType(objectType);
                        objectBind.setObjectPath(m.group(1));
                        //objectBind.setWrite(createObjectTranslate(m.group(2)));
                        objectBind.setJsonPath(m.group(3));
                        //objectBind.setRead(createObjectTranslate(m.group(4)));
                        objectBindList.add(objectBind);
                    }
                } else if (isNode == true && line.contains("{")) {
                    Matcher m = patternNode.matcher(line);
                    if (m.matches()) {
                        ObjectBind objectBind = new ObjectBind();
                        objectBind.setBindName(bindName);
                        objectBind.setObjectType(objectType);
                        objectBind.setObjectPath(m.group(1));
                        objectBind.setWrite(createObjectTranslate(m.group(2)));
                        objectBind.setJsonPath(m.group(3));
                        objectBind.setRead(createObjectTranslate(m.group(4)));
                        objectBindList.add(objectBind);
                    }
                }
            }
            IOUtils.closeQuietly(inputStream);

            return objectBindMap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ObjectTranslate createObjectTranslate(String context) {
        try {
            Class clazz = gcl.parseClass("" +
                    "class Incredible implements cn.ghl.tools.json.ObjectTranslate {" +
                    "def toInt(x){ return x==null?null:new Integer(x as int);} \n" +
                    "def toDouble(x){ return x==null?null:new Double(x as double);} \n" +
                    "def toDate(x){ return x==null?null:new Date(x as Long);} \n" +
                    "  public Object translate(Object x) {" +
                    (StringUtils.isEmpty(context) ? "return x;" : context) +
                    "  }" +
                    "};");
            ObjectTranslate objectTranslate = (ObjectTranslate) clazz.newInstance();
            return objectTranslate;
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }
}
