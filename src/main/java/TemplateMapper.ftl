<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${info.daoPackage}.${info.daoName}">
    <resultMap id="resultEntity" type="${info.modelPackage}.${info.modelName}">
        <id column="${info.primarykeyFields.fieldName}" property="${info.primarykeyFields.propertyName}" jdbcType="${info.primarykeyFields.fieldTypeXml}" />
        <#list info.fields as field>
		<result column="${field.fieldName}" property="${field.propertyName}" jdbcType="${field.fieldTypeXml}" />
		</#list>
    </resultMap>
    <sql id="column_List">
    	<![CDATA[
        ${info.primarykeyFields.fieldName}<#list info.fields as field>,${field.fieldName}</#list>
        ]]>
    </sql>
     <sql id="Entity_Where_Clause">
        <where>
          <trim prefix="(" suffix=")" prefixOverrides="and">
             <if test="${info.primarykeyFields.condition} ">
                and ${info.primarykeyFields.fieldName}=#\{${info.primarykeyFields.propertyName},jdbcType=${info.primarykeyFields.fieldTypeXml}}
            </if>
            <#list info.fields as field>
			<if test="${field.condition}">
               and  ${field.fieldName}=#\{${field.propertyName},jdbcType=${field.fieldTypeXml}}
           	</if>
			</#list>
          </trim>
        </where>
    </sql>
  
    <select id="selectByPrimaryKey" resultMap="resultEntity" parameterType="java.lang.String">
        select
        <include refid="column_List" />
        from ${info.tableName}
        where ${info.primarykeyFields.fieldName} = #\{${info.primarykeyFields.propertyName},jdbcType=${info.primarykeyFields.fieldTypeXml}}
    </select>
    <select id="selectByEntityWhere" resultMap="resultEntity" parameterType="${info.modelPackage}.${info.modelName}">
        select
        <include refid="column_List" />
        from ${info.tableName}
        <include refid="Entity_Where_Clause" />
    </select>
    <select id="selectByMapWhere" resultMap="resultEntity" parameterType="java.util.Map">
        select
        <include refid="column_List" />
        from ${info.tableName}
        <include refid="Entity_Where_Clause" />
        <if test="orderCondition != null and orderCondition != ''" >
	     order by $\{orderCondition}
	    </if>
	    <if test="queryCondition != null and queryCondition != ''" >
	      $\{queryCondition}
	    </if>
    </select>
    <delete id="deleteByPrimaryKey" parameterType="java.lang.String">
    	<![CDATA[
        delete from ${info.tableName}
        where ${info.primarykeyFields.fieldName} = #\{${info.primarykeyFields.propertyName},jdbcType=${info.primarykeyFields.fieldTypeXml}}
        ]]>
    </delete>
	<delete id="batchDeleteByIds" parameterType="java.lang.String">
		delete FROM ${info.tableName} where guid in
			<foreach item="idItem" collection="array" open="(" separator="," close=")">
			#\{idItem}
			</foreach>
	</delete>
    <insert id="insert" parameterType="${info.modelPackage}.${info.modelName}">
    	<!--
    	<selectKey keyProperty="${info.primarykeyFields.propertyName}" resultType="${info.primarykeyFields.javaTypeName}" order="BEFORE">  
            select replace(uuid(), '-', '')
        </selectKey>  
        -->
        <![CDATA[
        insert into ${info.tableName} (${info.primarykeyFields.fieldName}<#list info.fields as field>,${field.fieldName}</#list>)
        values (
        	#\{${info.primarykeyFields.propertyName},jdbcType=${info.primarykeyFields.fieldTypeXml}}
        	<#list info.fields as field>,#\{${field.propertyName},jdbcType=${field.fieldTypeXml}}</#list>
        	)
       ]]>
    </insert>
    <insert id="insertSelective" parameterType="${info.modelPackage}.${info.modelName}">
    	<!--
    	<selectKey keyProperty="${info.primarykeyFields.propertyName}" resultType="${info.primarykeyFields.javaTypeName}" order="BEFORE"> 
	        select replace(uuid(), '-', '')
        </selectKey>
        -->  
         insert into ${info.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="${info.primarykeyFields.condition} ">
                ${info.primarykeyFields.fieldName},
            </if>
            <#list info.fields as field>
			<if test="${field.condition}">
               ${field.fieldName},
           	</if>
			</#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        	<if test="${info.primarykeyFields.condition}">
                #\{${info.primarykeyFields.propertyName},jdbcType=${info.primarykeyFields.fieldTypeXml}},
            </if>
            <#list info.fields as field>
			<if test="${field.condition}">
                #\{${field.propertyName},jdbcType=${field.fieldTypeXml}},
           	</if>
			</#list>
        </trim>
    </insert>
   <update id="updateByIdSelective" parameterType="${info.modelPackage}.${info.modelName}">
        update ${info.tableName}
        <set>
            <#list info.fields as field>
			<if test="${field.condition}">
                ${field.fieldName}=#\{${field.propertyName},jdbcType=${field.fieldTypeXml}},
           	</if>
			</#list>
        </set>
        where  ${info.primarykeyFields.fieldName}= #\{${info.primarykeyFields.propertyName},jdbcType=${info.primarykeyFields.fieldTypeXml }}
    </update>
    <update id="updateByPrimaryKey" parameterType="${info.modelPackage}.${info.modelName}">
        update ${info.tableName}
        set 
        	<#list info.fields as field>
        		<#if field_index!=0>,</#if>${field.fieldName}=#\{${field.propertyName},jdbcType=${field.fieldTypeXml}}
			</#list>
        where  ${info.primarykeyFields.fieldName}= #\{${info.primarykeyFields.propertyName},jdbcType=${info.primarykeyFields.fieldTypeXml}}
    </update>
    <!-- 
   	 附加信息，便于自己写sql语句
    ${info.primarykeyFields.fieldName}= #\{${info.primarykeyFields.propertyName},jdbcType=${info.primarykeyFields.fieldTypeXml}}
    <#list info.fields as field>
	${field.fieldName}=#\{${field.propertyName},jdbcType=${field.fieldTypeXml}}
    </#list>
     -->
     
</mapper>