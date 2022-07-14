
<# BINDING ELEMENTS WITH POJO CLASS>
<#assign pojo = clazzPojo >

package ${clazzPojo.packageName};


public class ${clazzPojo.className} {

	<#list clazzPojo.properties as propertie>
	${propertie.modifier} ${propertie.type} ${propertie.propertieName};
	</#list>
	
	<#if clazzPojo.noConstructors == true>
	//No constructors declared
	<#else>	
	<#list clazzPojo.constructors as constructor>
	<#if constructor.noArgs == true>
	${constructor.modifier} ${constructor.constructorName}(){
		
	}
	<#else>
	${constructor.modifier} ${constructor.constructorName}(<#list constructor.constructorParameters as param>${param}<#sep>, </#list>){
		
	}	
	</#if>
	</#list>
	
	</#if>
	
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
	
	<#if clazzPojo.noMethods == true>
	//No methods declared
	<#else>
	<#list clazzPojo.pojoMethods as method>
	${method.modifier} ${method.returnType} ${method.methodName}(<#list method.methodParameters as param>${param}<#sep>, </#list>){
		
	}	
	</#list>
	</#if>
}