${package}

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
	<#--
		
	${Properties}
	 public ${return} ${methodname}(${params}){
   		${body}
   		return ${val} 
	}-->

}