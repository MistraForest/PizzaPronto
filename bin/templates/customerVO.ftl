
<# BINDING ELEMENTS WITH POJO CLASS>
<#assign pojo = clazzPojo >

package ${pojo.packageName};

<#if pojo.importEmpty == true>
//No import needed
<#else> 
<#list pojo.importStatments as import>
import ${import};
</#list>
</#if>

public class ${pojo.className} {

	<#list pojo.properties as propertie>
	${propertie.modifier} ${propertie.type} ${propertie.propertieName};
	</#list>
	
	<#if pojo.noConstructors == true>
	//No constructors declared
	<#else>	
	<#list pojo.constructors as constructor>
	<#if constructor.noArgs == true>
	${constructor.modifier} ${constructor.constructorName}(){
	}
	<#else>
	${constructor.modifier} ${constructor.constructorName}(<#list constructor.constructorParameters as param>${param}<#sep>, </#list>){
		
	}	
	</#if>
	</#list>
	
	</#if>
	
	<#list pojo.setters as setter>
	${setter.modifier} ${setter.returnType} ${setter.methodName}(${setter.paramType} ${setter.paramName}){
		this.${setter.fieldName} = ${setter.paramName};
	}
	</#list>
	
	<#list pojo.getters as getter>
	${getter.modifier} ${getter.returnType} ${getter.methodName}(){
		return ${getter.fieldName};
	}
	</#list>
	
	<#if pojo.noMethods == true>
	//No methods declared
	<#else>
	<#list pojo.pojoMethods as method>
	${method.modifier} ${method.returnType} ${method.methodName}(<#list method.methodParameters as param>${param}<#sep>, </#list>){
		
	}	
	</#list>
	</#if>
}