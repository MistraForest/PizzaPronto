${package};

<#list imports as import>
import ${import};
</#list>

public class ${className} {

	<#list properties as propertie>
    ${propertie};
	</#list>
	
	<#list constructors as constructor>
    ${constructor}
	</#list>
	
	<#list methods as method>
    ${method}
	</#list>
	
	<#--others methods
	${modifier} ${return} ${methodname}(${params}){
   		${body}
   		return ${val}
	}
	
	<#--getters
	${get_modifier} ${get_returnType} ${getter}(){
   		return ${val}
	}
	
	<#--setters
	${set_modifier} ${set_returnType} ${setter}(${params}){
   		this.${propertie} = ${params};
   		return ${val}
	}
	}-->

}