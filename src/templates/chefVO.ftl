${package}

<#list imports as import>
import ${import};
</#list>

public class ${name} {

	<#list properties as propertie>
    ${propertie};
	</#list>
	
	<#list methods as method>
    ${method}
	</#list>
	<#--
	${Properties}
	 public ${return} ${methodname}(${params}){
   		${body}
   		return ${val} 
	}-->

}