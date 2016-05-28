
package com.song.generator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soqiho@126.com
 * @version 1.0
 */
public class ParserInfo {
    

    // #<tableName>
    private String tableName;
    // #<tableComment>
    private String tableComment;
    // #<sequenceName>
    private String sequenceName;
    // #<daoPackage>
    private String daoPackage;
    // #<daoName>
    private String daoName;
    private String daoVarName;
    
    // #<modelPackage>
    private String modelPackage;
    // #<modelName>
    private String modelName;
    
    private String servicePackage;
    private String serviceImplPackage;

    private String keyColName;
    
    private String subPackage;
    
    private List<Field> fields;
    
    private Field primarykeyFields;

    public ParserInfo(Connection connection, String parentPackageName, String tableName, String sequenceName)
            throws Exception {
        fields = new ArrayList<Field>();
        daoPackage = parentPackageName +".dao";
        servicePackage= parentPackageName +".service";
        serviceImplPackage= parentPackageName +".service.impl";
        //modelPackage = parentPackageName;
        modelPackage = parentPackageName+ ".model";
        this.tableName = tableName;
        if (sequenceName == null) {
            sequenceName = tableName + "_S";
        }
        this.sequenceName = sequenceName;
        String javaName = Utils.getJavaName(tableName);
        daoName = javaName + "Dao";
        daoVarName=daoName.substring(0,1).toLowerCase()+daoName.substring(1);
        modelName = javaName;
        
        queryDBForTable(connection, tableName);
    }

    public ParserInfo(Connection connection, String parentPackageName, String tableName, String sequenceName,String modelPackageName)
            throws Exception {
        fields = new ArrayList<Field>();
        daoPackage = parentPackageName +".dao";
        servicePackage= parentPackageName +".service";
        serviceImplPackage= parentPackageName +".service.impl";
        //modelPackage = parentPackageName;
        modelPackage = modelPackageName+ ".model";
        this.tableName = tableName;
        if (sequenceName == null) {
            sequenceName = tableName + "_S";
        }
        this.sequenceName = sequenceName;
        String javaName = Utils.getJavaName(tableName);
        daoName = javaName + "Dao";
        daoVarName=daoName.substring(0,1).toLowerCase()+daoName.substring(1);
        modelName = javaName;
        
        queryDBForTable(connection, tableName);
    }
    /**
     * 查询数据库生成表
     * @param connection
     * @param tableName
     * @throws SQLException
     */
    private void queryDBForTable(Connection connection, String tableName)
            throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select table_comment from Information_schema.tables WHERE "
        		+ "table_name = '"+tableName.toUpperCase()+"'"+"AND TABLE_SCHEMA = '"+Generator.properties.getProperty("table_schema")+"'");

        if(rs.next())
            this.tableComment=rs.getString(1);
        statement.close();
        rs.close();
        
        
        statement = connection.createStatement();
        rs = statement.executeQuery("SELECT col.column_name FROM Information_schema.COLUMNS col "
        		+ "WHERE col.table_name = '"+tableName.toUpperCase()+"' AND col.column_key = 'PRI'"+"AND TABLE_SCHEMA = '"+Generator.properties.getProperty("table_schema")+"'");
        if(rs.next())
            this.keyColName=rs.getString(1);
        statement.close();
        rs.close();
        
        
        statement = connection.createStatement();
        statement = connection.createStatement();
        rs = statement.executeQuery("SELECT col.column_name, col.data_type, col.column_comment, col.column_key, "
        		+ "col.numeric_precision, col.numeric_scale "
        		+ "FROM Information_schema.COLUMNS col WHERE col.table_name = '"+tableName.toUpperCase()+"'"+"AND TABLE_SCHEMA = '"+Generator.properties.getProperty("table_schema")+"'");
        
        while(rs.next()){
            String columnName = rs.getString(1);
            String type =rs.getString(2).toUpperCase();
            String description =rs.getString(3);
            String key = rs.getString(4);
            int precision= rs.getInt(5);
            int scale=rs.getInt(6);
            
            if("PRI".equalsIgnoreCase(key)){
                primarykeyFields= new Field(columnName, type, Utils.getPropertyKeyByColumnName(columnName),
                        true, precision, scale);
                primarykeyFields.setFieldName(columnName);
                
                primarykeyFields.setDescription(description);
            }else{
                Field field = new Field(columnName, type, Utils.getPropertyKeyByColumnName(columnName),
                        false, precision, scale);
                field.setFieldName(columnName);
                
                field.setDescription(description);
                fields.add(field);
            }
            
            
        }
        rs.close();
        statement.close();
    }

    public String getDaoName() {
        return daoName;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public List<Field> getFields() {
        return fields;
    }

    

    public String getModelName() {
        return modelName;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    

    public String getSequenceName() {
        return sequenceName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public String getTableName() {
        return tableName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKeyColName() {
        return keyColName;
    }

    public void setKeyColName(String keyColName) {
        this.keyColName = keyColName;
    }

    public String getSubPackage() {
        return subPackage;
    }

    public void setSubPackage(String subPackage) {
        this.subPackage = subPackage;
    }

    public Field getPrimarykeyFields() {
        return primarykeyFields;
    }

    public void setPrimarykeyFields(Field primarykeyFields) {
        this.primarykeyFields = primarykeyFields;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public void setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
    }

    public String getDaoVarName() {
        return daoVarName;
    }

    public void setDaoVarName(String daoVarName) {
        this.daoVarName = daoVarName;
    }

}
