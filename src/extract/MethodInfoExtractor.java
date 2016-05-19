package extract;

import java.util.ArrayList;
import java.util.LinkedList;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import astUtil.AstUtil;

public class MethodInfoExtractor {
	private static ArrayList<ArrayList<String>> data = null;
	private static String project = ProjectName.getProjectName();
	private static LinkedList<String> projectName = new LinkedList<String>();
	private static LinkedList<String> name = new LinkedList<String>();
	private static LinkedList<String> className = new LinkedList<String>();
	private static LinkedList<String> returnType = new LinkedList<String>();
	private static LinkedList<String> modifies = new LinkedList<String>();
	private static LinkedList<String> throwExceptions = new LinkedList<String>();
	private static LinkedList<String> isConstructor = new LinkedList<String>();
	private static LinkedList<String> content = new LinkedList<String>();
	private static LinkedList<String> javadoc = new LinkedList<String>();

	public MethodInfoExtractor() {
		
	}
	
	public static ArrayList<ArrayList<String>> getData() {
		LinkedList<ArrayList<String>> temp = new LinkedList<ArrayList<String>>();
		temp.add(new ArrayList<String>(projectName));
		temp.add(new ArrayList<String>(name));
		temp.add(new ArrayList<String>(className));
		temp.add(new ArrayList<String>(returnType));
		temp.add(new ArrayList<String>(modifies));
		temp.add(new ArrayList<String>(throwExceptions));
		temp.add(new ArrayList<String>(isConstructor));
		temp.add(new ArrayList<String>(javadoc));
		temp.add(new ArrayList<String>(content));
		
		projectName = new LinkedList<String>();
		name = new LinkedList<String>();
		className = new LinkedList<String>();
		returnType = new LinkedList<String>();
		modifies = new LinkedList<String>();
		throwExceptions = new LinkedList<String>();
		isConstructor = new LinkedList<String>();
		content = new LinkedList<String>();
		javadoc = new LinkedList<String>();
		
		data = new ArrayList<ArrayList<String>>(temp);
		return data;
	}
	
	public static void resetData() {
		
		data = null;
	}

	
	public void extract(MethodDeclaration node) {
		projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		if(node.getJavadoc() != null) {
			
			String comment = node.getJavadoc().toString().trim();
			content.add(node.toString().substring(comment.length()).trim());
			javadoc.add(comment);
		}
		else {
			content.add(node.toString().trim());
			javadoc.add("empty");
		}
		
		className.add(AstUtil.getQualifiedName(node.getParent()));
		
		if (node.getReturnType2() != null) {
			returnType.add(node.getReturnType2().toString());
		}
		else {
			returnType.add("null");
		}
		
		
		if (node.modifiers().isEmpty()) {
			modifies.add("null");
		}
		else {
			String str = node.modifiers().toString();
			modifies.add(str.substring(1, str.length() - 1));
		}
		
		if (node.thrownExceptionTypes().isEmpty()) {
			throwExceptions.add("null");
		}
		else {
			String str = node.thrownExceptionTypes().toString();
			throwExceptions.add(str.substring(1, str.length() - 1));
		}
		
		if (node.isConstructor()) {
			isConstructor.add("1");
		}
		else {
			isConstructor.add("0");
		}
		
	}
}
