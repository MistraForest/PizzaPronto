${package};
<#--
<#list imports as import>
import ${import};
</#list>
-->

public class ${className} {

	
	<#--
	
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