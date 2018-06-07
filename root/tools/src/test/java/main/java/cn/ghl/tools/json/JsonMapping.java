package main.java.cn.ghl.tools.json;

import groovy.lang.GroovyClassLoader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMapping {

    private static Logger LOG = LoggerFactory
        .getLogger(JsonMapping.class);

    private static GroovyClassLoader gcl = new GroovyClassLoader();

    public static String defaultDictPath = "/Json.cfg";

    public static void main(String args[]) {

        try {
            Pattern lineStart = Pattern.compile(
                "^<(.+?)\\|(.+?)>$");
            Pattern lineEnd = Pattern.compile(
                "^</>$");
            Pattern lineNode = Pattern.compile(
                "(.+?)\\{(.*)}=(.+?)\\{(.*)}");
            Pattern lineNodeList = Pattern.compile(
                "(.+?)\\[(.*)]=(.+?)\\[(.*)]");

            boolean isNode = false;
            InputStream inputStream = JsonMapping.class.getResourceAsStream(defaultDictPath);
            List<String> lines = IOUtils.readLines(inputStream);
            String objectType = null;
            String bindName = null;
            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("#")) {
                    continue;
                }
                Matcher matcher;
                if ((matcher = lineStart.matcher(line)).matches()) {
                    matcher.matches();
                    LOG.debug("节点开始<<<<: {}", line);
                } else if ((matcher = lineEnd.matcher(line)).matches()) {
                    matcher.matches();
                    LOG.debug("节点结束>>>>: {}", line);
                } else if ((matcher = lineNode.matcher(line)).matches()) {
                    matcher.matches();
                    LOG.debug("普通节点>>>>: {}", line);
                } else if ((matcher = lineNodeList.matcher(line)).matches()) {
                    matcher.matches();
                    LOG.debug("数组节点>>>>: {}", line);
                }
                /*
                Matcher mEnd = lineEnd.matcher(line);
                if (mEnd.matches()) {
                    LOG.debug("节点结束>>>>: {}", line);
                }
                Matcher mNode = lineNode.matcher(line);
                if (mNode.matches()) {
                    LOG.debug("普通节点>>>>: {}", line);
                }
                Matcher mNodeList = lineNodeList.matcher(line);
                if (mNodeList.matches()) {
                    LOG.debug("数组节点>>>>: {}", line);
                }
                */
            }
            IOUtils.closeQuietly(inputStream);

            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

}
