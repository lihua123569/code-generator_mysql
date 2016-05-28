package ${info.daoPackage};

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.liepaiwang.generic.GenericDao;
import ${info.modelPackage}.${info.modelName};

/** ${info.tableComment}(${info.tableName}) **/
public interface ${info.daoName} extends GenericDao<${info.modelName}, ${info.primarykeyFields.javaTypeName}> {


	/** codegen **/
}