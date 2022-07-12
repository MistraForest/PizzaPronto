<#assign pojo = clazzPojo >


package ${clazzPojo.packageName};
public class ${clazzPojo.className} {

	<#list clazzPojo.properties as propertie>
	${propertie.modifier} ${propertie.type} ${propertie.propertieName};
	</#list>
	
	<#list clazzPojo.constructors as constructor>
	<#if constructor.noArgs == true>
	${constructor.modifier} ${constructor.constructorName}(){
		
	}
	</#if>
	
	<#if constructor.noArgs == false>
	<#list constructor.parameters as param>
	${constructor.modifier} ${constructor.constructorName}(${param.type} ${param.paramName}){
		
	}
	</#list>
	</#if>
	
	</#list>
	
	<#list clazzPojo.setters as setter>
	${setter.modifier} ${setter.returnType} ${setter.methodName}(${setter.paramType} ${setter.paramName}){
		this.${setter.fieldName?uncap_first} = ${setter.paramName};
	}
	</#list>
	
	<#list clazzPojo.getters as getter>
	${getter.modifier} ${getter.returnType} ${getter.methodName}(){
		return ${getter.fieldName?uncap_first};
	}
	</#list>
}

<#list pojos as pojo>
	${pojo.className}
</#list>
<#--	
	${clazzPojo.setters.modifier} ${clazzPojo.setters.returnType} ${clazzPojo.setters.methodName}(${paramType} ${param}){
   		this.${field?uncap_first} = ${param};
	}
	
	${getModifier} ${get_returnType} ${getter}(){
   		return ${val?uncap_first};
	}


<#list imports as import>
import ${import};
</#list>
-->
${package};
public class ${className} {

<#assign s = object >
	${s.className} 
	
	<#--
	${s.Getters[0].returnType} ${s.Getters[0].name} (){
		return ${s.Getters.fieldName};
	}
	<#list properties as propertie>
	${propertie}
    <#assign s = propertie >
	</#list>
	
	<#list constructors as constructor>
    ${constructor}
	</#list>

	${modifier} ${return} ${methodname}(${params}){
   		${body}
   		return ${val};
	}
	-->
	
	${set_modifier} ${set_returnType} ${setter}(${paramType} ${param}){
   		this.${field?uncap_first} = ${param};
	}
	

	${getModifier} ${get_returnType} ${getter}(){
   		return ${val?uncap_first};
	}
	

	


}