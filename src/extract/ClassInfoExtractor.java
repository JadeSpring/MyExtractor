package extract;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import astUtil.AstUtil;
import file.Content;

public class ClassInfoExtractor {
	private static ArrayList<ArrayList<String>> data = null;
	private static String project = ProjectName.getProjectName();
	private static LinkedList<String> projectName = new LinkedList<String>();
	private static LinkedList<String> name = new LinkedList<String>();
	private static LinkedList<String> sourceRange = new LinkedList<String>();
	private static LinkedList<String> fileName = new LinkedList<String>();
	private static LinkedList<String> interfaces = new LinkedList<String>();
	private static LinkedList<String> superClass = new LinkedList<String>();
	private static LinkedList<String> isInterface = new LinkedList<String>();
	private static LinkedList<String> isSuperClass = new LinkedList<String>();
	private static LinkedList<String> isAnonymous = new LinkedList<String>();
	private static LinkedList<String> modifiers = new LinkedList<String>();
	private static LinkedList<String> content = new LinkedList<String>();
	
	public ClassInfoExtractor() {
		
	}
	
	public static ArrayList<ArrayList<String>> getData() {
		LinkedList<ArrayList<String>> temp = new LinkedList<ArrayList<String>>();
		temp.add(new ArrayList<String>(projectName));
		temp.add(new ArrayList<String>(name));
		temp.add(new ArrayList<String>(sourceRange));
		temp.add(new ArrayList<String>(fileName));
		temp.add(new ArrayList<String>(superClass));
		temp.add(new ArrayList<String>(interfaces));
		temp.add(new ArrayList<String>(isInterface));
		temp.add(new ArrayList<String>(isSuperClass));
		temp.add(new ArrayList<String>(isAnonymous));
		temp.add(new ArrayList<String>(modifiers));
		temp.add(new ArrayList<String>(content));
		
		projectName = new LinkedList<String>();
		name = new LinkedList<String>();
		sourceRange = new LinkedList<String>();
		fileName = new LinkedList<String>();
		interfaces = new LinkedList<String>();
		superClass = new LinkedList<String>();
		isInterface = new LinkedList<String>();
		isSuperClass = new LinkedList<String>();
		isAnonymous = new LinkedList<String>();
		modifiers = new LinkedList<String>();
		content = new LinkedList<String>();
		
		data = new ArrayList<ArrayList<String>>(temp);
		return data;
	}
	
	public static void resetData() {
		
		data = null;
	}
	
	public void extract(TypeDeclaration node) {
		projectName.add(project);
		  
		name.add(AstUtil.getQualifiedName(node));
		
		sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		fileName.add(Content.getFileName());
		
		if (node.getSuperclassType() == null) {
			superClass.add("null");
		} else {
			superClass.add(node.getSuperclassType().toString());
			
		}
		
		if (!node.superInterfaceTypes().isEmpty()) {
			List<Type> interf = node.superInterfaceTypes();
			String str = interf.toString();
			interfaces.add(str.substring(1, str.length() - 1));
		} else {
			interfaces.add("null");
		}
		
		if (node.isInterface()) {
			isInterface.add("1");
		} else {
			isInterface.add("0");
		}
		
		isSuperClass.add("0");
		
		isAnonymous.add("0");
		
		if (node.modifiers().isEmpty()) {
			modifiers.add("null");
		} else {
			@SuppressWarnings("unchecked")
			List<IExtendedModifier> modifier = node.modifiers();
			String str = modifier.toString();
			
			modifiers.add(str.substring(1, str.length() - 1));
		}
		
		content.add(node.toString());
	}
	
	public void extract(AnonymousClassDeclaration node) {
		projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		fileName.add(Content.getFileName());
		
		superClass.add("null");
		
		interfaces.add("null");
		
		isInterface.add("0");
		
		isSuperClass.add("0");
		
		isAnonymous.add("1");
		
		modifiers.add("null");
		
		content.add(node.toString());
	}
}
