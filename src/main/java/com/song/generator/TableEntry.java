
package com.song.generator;

/**
 * @author soqiho@126.com
 * @version 1.0
 */
public class TableEntry {
    

    private String tableName;
    private String sequenceName;
    private String packageName;

    private String prefixName;
    public String getPackageName() {
        return packageName;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

}
