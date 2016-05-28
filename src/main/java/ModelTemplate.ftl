package ${info.modelPackage};

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.liepaiwang.generic.GenericEntity;;
/** ${info.tableComment}(${info.tableName}) **/
public class ${info.modelName} extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*${info.primarykeyFields.description}*/
	private ${info.primarykeyFields.javaTypeName} ${info.primarykeyFields.propertyName};
	/*${info.primarykeyFields.description}*/
	public ${info.primarykeyFields.javaTypeName} ${info.primarykeyFields.gmethodName}()\{
		return ${info.primarykeyFields.propertyName};
	}
	
	public void ${info.primarykeyFields.smethodName}(${info.primarykeyFields.javaTypeName} ${info.primarykeyFields.propertyName}){
		 this.${info.primarykeyFields.propertyName}= ${info.primarykeyFields.propertyName};
	}
	
	<#list info.fields as field>
	/* ${field.description} */
	private ${field.javaTypeName} ${field.propertyName};
	
	public ${field.javaTypeName} ${field.gmethodName}(){
		return ${field.propertyName};
	}
	
	public void ${field.smethodName}(${field.javaTypeName} ${field.propertyName}){
		this.${field.propertyName}= ${field.propertyName};
	}
	</#list>

		
}
