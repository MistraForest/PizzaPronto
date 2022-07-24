package utils;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodBodyParser extends VoidVisitorAdapter<Void>{
	
	@Override
	public void visit(MethodDeclaration md, Void arg) {
		super.visit(md, arg);
		getBody(md);
		System.out.println("Method "+md.getName()+": Body Printed: " + md.getBody().get().toString());
	}
	
	public void getBody(MethodDeclaration methodDeclaration) {
		methodDeclaration.getBody().get().toString();
	}
}
