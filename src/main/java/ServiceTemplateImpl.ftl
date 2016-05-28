package ${info.serviceImplPackage};

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.liepaiwang.generic.GenericDao;
import com.liepaiwang.generic.GenericServiceImpl;

import ${info.daoPackage}.${info.daoName};
import ${info.modelPackage}.${info.modelName};
import ${info.servicePackage}.${info.modelName}Service;

@Service
public class ${info.modelName}ServiceImpl extends GenericServiceImpl<${info.modelName}, String> implements
		${info.modelName}Service {

	@Autowired
	${info.daoName} ${info.daoVarName};

	
	@Override
	public GenericDao<${info.modelName}, String> getDao() {
		return ${info.daoVarName};
	}

	
	

}
