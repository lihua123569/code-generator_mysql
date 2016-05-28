
package com.song.generator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author soqiho@126.com
 * @version 1.0
 */
public class Generator {
    
    static Properties properties = new Properties();
    
    private static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        
        properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("generator.properties"));
        String driverClass = properties.getProperty("jdbc.driver");
        String url = properties.getProperty("jdbc.url");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, properties.getProperty("username"), properties.getProperty("password"));//.getConnection(url, properties);
        if (connection == null) {
            throw new RuntimeException("connection is null, Please check the driver settings!");
        }
        return connection;
    }

    /**
     * 读取table信息
     * 
     * @author cary
     * @return
     * @throws IOException
     */
    private static List<TableEntry> readTableEntries() throws IOException {
        String tables=properties.getProperty("tableName");
        List<TableEntry> entries =new ArrayList<TableEntry>();
        for(String table:tables.split(",")){
            TableEntry te=new TableEntry();
            te.setTableName(table);
            te.setPackageName(properties.getProperty("packageName")+"."+Utils.getSubPackage(table));
            te.setPrefixName(Utils.getSubPackage(table));
            entries.add(te);
        }
        return entries;
    }

    
    
    public static void execute() throws IOException, Exception {
        Connection connection = getConnection();
        List<TableEntry> entries = readTableEntries();
        System.out.println("There is(are) " + entries.size() + " table(s) to generate!");
        for (TableEntry entry : entries) {
            String packageName = entry.getPackageName();
            String tableName = entry.getTableName();
            String sequenceName = entry.getSequenceName();
            tableName = tableName.toUpperCase();
            if (sequenceName != null) {
                sequenceName = sequenceName.toUpperCase();
            }
            
            
            if(properties.getProperty("generatorTarget").indexOf("po")!=-1){
                ParserInfo parserInfo = new ParserInfo(connection, packageName, tableName, sequenceName);
                ModelMaker modelMaker = new ModelMaker(parserInfo);
                modelMaker.makeModel();
            }
            
            ParserInfo parserInfo = new ParserInfo(connection, packageName, tableName, sequenceName,properties.getProperty("entityPackageName")+"."+entry.getPrefixName());
            
            if(properties.getProperty("generatorTarget").indexOf("xml")!=-1){
                XmlMaker xmlMaker=new XmlMaker(parserInfo);
                xmlMaker.makeDao(tableName);
            }
            
            if(properties.getProperty("generatorTarget").indexOf("dao")!=-1){
                MapperMaker daoMaker = new MapperMaker(parserInfo);
                daoMaker.makeInterface();;
            }
            if(properties.getProperty("generatorTarget").indexOf("service")!=-1){
                ServiceInterfaceMaker  serviceInterfaceMaker = new ServiceInterfaceMaker(parserInfo);
                serviceInterfaceMaker.makeModel();
                
                ServiceImplMaker serviceImplMaker = new ServiceImplMaker(parserInfo);
                serviceImplMaker.makeModel();
            }
            //ModelMaker modelMaker = new ModelMaker(parserInfo);
            //modelMaker.makeModel();
                
            
            
            
            //DaoInterfaceMaker daoInterfaceMaker = new DaoInterfaceMaker(parserInfo);
            //daoInterfaceMaker.makeInterface();

            //DaoModelMaker daoModelMaker = new DaoModelMaker(parserInfo);
            //daoModelMaker.makeModel();
            System.out.println("生成表   " + tableName + " 成功...");
        }
        connection.close();
    }

}
