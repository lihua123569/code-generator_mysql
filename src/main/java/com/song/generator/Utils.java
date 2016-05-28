
package com.song.generator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author soqiho@126.com
 * @version 1.0
 */
public class Utils {
    
    public static String getSubPackage(String tableName){
        return tableName.split("_")[0].toLowerCase();
    }
    public static String getPropertyKeyByColumnName(String columnName) {
        String[] split = columnName.split("_");
        StringBuffer buffer = new StringBuffer();
        buffer.append(split[0].toLowerCase());
        for (int i = 1; i < split.length; i++) {
            String lowerCaseSplit = split[i].toLowerCase();
            String firstUpperChar = (lowerCaseSplit.charAt(0) + "").toUpperCase();
            buffer.append(firstUpperChar + lowerCaseSplit.substring(1));
        }
        return buffer.toString();
    }

    public static String getSegment(String segment) {
        return Character.toUpperCase(segment.charAt(0)) + segment.substring(1).toLowerCase();
    }
    public static String getJavaName(String tableName) {
        String[] segments = tableName.split("_");
        if (segments.length == 1) {
            return getSegment(segments[0]);
        }
        StringBuilder javaName = new StringBuilder();
        for (int i = 0; i < segments.length; i++) {
            String segment = segments[i];
            javaName.append(getSegment(segment));
        }
        return javaName.toString();
    }
    
    // TODO:
        public static File createFile(String packagee, String fileName,
                String postFix) {
            String currrntPath = new File("").getAbsolutePath();
            System.out.println(currrntPath);
            String pkgPath = packagee.replace(".", File.separator);
            String filePath = currrntPath + File.separator + "src/main/java" + File.separator
                    + pkgPath + File.separator + fileName + postFix;
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            return file;
        }

        public static String getTemplateFileContent(String fileName)
                throws IOException {
            URL template = ClassLoader.getSystemResource(fileName);
            InputStream is = template.openStream();
            StringBuffer buffer = new StringBuffer();
            Reader reader = new InputStreamReader(new BufferedInputStream(template.openStream())); 
            int c; 
            while ((c = reader.read()) != -1) { 
                buffer.append((char) c); 
            } 
            is.close();
            return buffer.toString().trim();
        }

        public static void writeContentToFile(File file, String content)
                throws Exception {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(content.getBytes("UTF-8"));
                fos.flush();
            } catch (Exception e) {
                throw e;
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
    public static String generateDaoContent(ParserInfo parserInfo,String templName) throws IOException {
        String templateFileContent = getTemplateFileContent(templName);

        // 初始化FreeMarker配置
        // 创建一个Configuration实例
        Configuration cfg = new Configuration(
                Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        // 设置FreeMarker的模版文件位置
        cfg.setDirectoryForTemplateLoading(new File("."));

        Map root = new HashMap();
        root.put("info", parserInfo);

        StringTemplateLoader loader = new StringTemplateLoader(); // 模版加载器
        loader.putTemplate("chain", templateFileContent);
        Configuration config = new freemarker.template.Configuration(
                Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        config.setTemplateLoader(loader);
        Template template = config.getTemplate("chain", "UTF-8");
        StringWriter out = new StringWriter();

        try {
            template.process(root, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return out.toString().replace("\\{", "{").replaceAll("\\}", "}");
    }
    
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
    }

}
