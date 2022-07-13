
<# BINDING ELEMENTS WITH POJO CLASS>
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
	<#list constructor.constParameters as param>
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

<#-- <#list pojos as pojo>
	${pojo.className}
</#list> -->


<#--<#--

<# BINDING ELEMENTS WITH OBJECT NODE (String)>
<#assign s = object >
	 
	
package ${s.package?replace("\"", "")};
public class ${s.className?replace("\"", "")} {
	
	${s.Getters[14..19]} ${s.Getters[36..40]} ${s.Getters[51..63]}(){
		return ${s.Getters[79..88]?uncap_first};
	}
	
	<#--  
	${set_modifier} ${set_returnType} ${setter}(${paramType} ${param}){
   		this.${field?uncap_first} = ${param};
	}
	
	${getModifier} ${get_returnType} ${getter}(){
   		return ${val?uncap_first};
	}
	
}


<# FIRST BINDING METHODS>
${package};

<#list someImports as import>
import ${import};
</#list>

public class ${className} {

	<#list props as propertie>
    ${propertie};
	</#list>
	
	<#list constrs as constructor>
    ${constructor}
	</#list>
	
	<#list meths as method>
    ${method}
	</#list>
	
}
-->
	
	