package utils;

import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodNameCollector extends VoidVisitorAdapter<List<String>> {

	@Override
	public void visit(MethodDeclaration md, List<String> collector) {
		super.visit(md, collector);
		collector.add(md.getNameAsString());
		System.out.println("Method "+md.getNameAsString()+": Body Collected: " + md.getBody().get().toString());
	}
}
