
package com.song.generator;

/**
 * @author soqiho@126.com
 * @version 1.0
 */
public class Field {
    
    private String fieldName;
    private String fieldType;
    private String fieldTypeXml;
    
    private String propertyName;
    private String description;
    private boolean isKey;
    private final int precision;
    private int scale;
    private String javaTypeName;
    
    private String gmethodName;
    private String smethodName;
    
    private String condition;

    
    
    public static String getJdbcType(String javaType) {
    	
    	/**
    	 * JDBC Type           Java Type  
CHAR                String  
VARCHAR             String  
LONGVARCHAR         String  
NUMERIC             java.math.BigDecimal  
DECIMAL             java.math.BigDecimal  
BIT             boolean  
BOOLEAN             boolean  
TINYINT             byte  
SMALLINT            short  
INTEGER             int  
BIGINT              long  
REAL                float  
FLOAT               double  
DOUBLE              double  
BINARY              byte[]  
VARBINARY           byte[]  
LONGVARBINARY               byte[]  
DATE                java.sql.Date  
TIME                java.sql.Time  
TIMESTAMP           java.sql.Timestamp  
CLOB                Clob  
BLOB                Blob  
ARRAY               Array  
DISTINCT            mapping of underlying type  
STRUCT              Struct  
REF                         Ref  
DATALINK            java.net.URL[color=red][/color]  
    	 */
    	
        if (javaType.equals("VARCHAR")) {
            return "VARCHAR";
        }
        if (javaType.equals("LONG")) {
            return "LONGVARCHAR";
        }
        if (javaType.equals("NUMBER")) {
            return "NUMERIC";
        }
        if (javaType.equals("DATE")) {
            return "TIMESTAMP";
        }
        if (javaType.equals("TIMESTAMP")) {
            return "TIMESTAMP";
        }
        if (javaType.equals("DATETIME")) {
            return "TIMESTAMP";
        }
        if (javaType.equals("CHAR")) {
            return "CHAR";
        }
        if (javaType.equals("CLOB")) {
            return "CLOB";
        }
        if (javaType.equals("TEXT")) {
            return "CLOB";
        }
        if (javaType.equals("DECIMAL")) {
            return "DECIMAL";
        }
        if (javaType.equals("DOUBLE")) {
            return "DOUBLE";
        }
        if (javaType.equals("INT")) {
            return "INTEGER";
        }
        if (javaType.equals("FLOAT")) {
            return "FLOAT";
        }

        if (javaType.equals("BLOB")) {
            return "BLOB";
        }
        if (javaType.equals("TINYINT")) {
        	return "TINYINT";
        }
        throw new RuntimeException("please implenents java type: " + javaType
                + "'s java type mapping!");
    }

    public Field(String fieldName, String fieldType, String propertyName,
            boolean isKey, int precision, int scale) {
        this.fieldName = fieldName.toLowerCase();
        this.fieldType = fieldType;
        this.fieldTypeXml=getJdbcType(fieldType);
        this.propertyName = propertyName;
        this.isKey = isKey;
        this.precision = precision;
        this.scale = scale;
        this.smethodName="set"+this.propertyName.substring(0,1).toUpperCase()+this.propertyName.substring(1);
        this.gmethodName="get"+this.propertyName.substring(0,1).toUpperCase()+this.propertyName.substring(1);
        
        this.javaTypeName=this.getJavaType();
        this.condition=genCon();
        System.out.println(condition);
    }

    public String getDescription() {
        return description==null?"":description;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String getJavaType() {
        if (fieldType.toUpperCase().startsWith("VARCHAR")) {
            return "String";
        } else if (fieldType.toUpperCase().startsWith("CHAR")) {
            return "String";
        } else if (fieldType.toUpperCase().startsWith("TEXT")) {
            return "String";
        } else if (fieldType.toUpperCase().startsWith("DATE")) {
            return "Date";
        } else if (fieldType.toUpperCase().startsWith("DECIMAL")) {
            return "BigDecimal";
        } else if (fieldType.toUpperCase().startsWith("DOUBLE")) {
            return "BigDecimal";
        } else if (fieldType.toUpperCase().startsWith("FLOAT")) {
            return "BigDecimal";
        } else if (fieldType.toUpperCase().startsWith("INT")) {
            return "Integer";
        }  else if (fieldType.toUpperCase().startsWith("TINYINT")) {
            return "Integer";
        } else if (fieldType.toUpperCase().startsWith("INTEGER")) {
            return "Integer";
        } else if (fieldType.toUpperCase().startsWith("NUMBER")) {
            if (scale > 0) {
                return "BigDecimal";
            }
            if (precision == 1) {
                return "Boolean";
            }
            if (precision > 10) {
                return "Long";
            }
            return "INTEGER";
        } else if (fieldType.toUpperCase().startsWith("TIMESTAMP")) {
            return "Timestamp";
        } else if (fieldType.toUpperCase().startsWith("LONG")) {
            return "String";
        } else if (fieldType.toUpperCase().startsWith("BLOB")) {
            return "String";
        } else {
            throw new RuntimeException("please implenents jdbc type: "
                    + fieldType + "'s java type mapping!");
        }
    }

    public String getPropertyName() {
        return propertyName;
    }

    public int getScale() {
        return scale;
    }

    public boolean isKey() {
        return isKey;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public void setKey(boolean isKey) {
        this.isKey = isKey;
    }


    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return fieldName + "(Key:" + isKey + ")" + "_" + fieldType + "_"
                + propertyName;
    }
    
    public String getCondition(){
        return this.condition;
    }
    
    private String genCon(){
        if(this.javaTypeName.equals("String"))
            return this.propertyName+" != null and  "+this.propertyName+" != ''  ";
        else
            return this.propertyName+" != null  ";
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }

    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }

    public String getGmethodName() {
        return gmethodName==null?"":gmethodName;
    }

    public void setGmethodName(String gmethodName) {
        this.gmethodName = gmethodName;
    }

    public String getSmethodName() {
        return smethodName;
    }

    public void setSmethodName(String smethodName) {
        this.smethodName = smethodName;
    }

    public String getFieldTypeXml() {
        return fieldTypeXml;
    }

    public void setFieldTypeXml(String fieldTypeXml) {
        this.fieldTypeXml = fieldTypeXml;
    }

    public int getPrecision() {
        return precision;
    }
    

}
