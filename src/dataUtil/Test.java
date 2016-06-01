package dataUtil;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import java.lang.reflect.*;

import astUtil.Visitor;
import extract.ClassInfoExtractor;
import file.FileHelper;

/*import preference.*;*/

public class Test {

	public static void main(String[] args) {
		/*String content = FileHelper.getContent("D:\\eclipse\\workspace\\MyExtractor\\src\\extract\\CommentInfoExtractor.java");
		ASTParser parser = ASTParser.newParser(AST.JLS8); // initialize

		parser.setKind(ASTParser.K_COMPILATION_UNIT); // to parse compilation unit
		
		parser.setSource(content.toCharArray()); // content is a string
											     // which stores the java
												 // source
		
		IJavaProject project = JavaCore.create(ResourcesPlugin.getWorkspace().getRoot().getProject("MyExractor"));
		parser.setProject(project);
		
		parser.setUnitName("/MyExtractor/src/extract/CommentInfoExtractor.java");
		parser.setResolveBindings(true);
		CompilationUnit root = (CompilationUnit) parser.createAST(null);
		root.accept(new ASTVisitor() {
			public boolean visit(MethodInvocation node) {
				ITypeBinding binding = node.resolveTypeBinding();
				IMethodBinding method = node.resolveMethodBinding();
				
				if (binding != null) {
					System.out.println(binding.getName());
				} else {
					System.out.println("binding is null");
				}
				
				if (method != null) {
					System.out.println(method.getName());
				} else {
					System.out.println("method binding is null");
				}
				return true;
			}
		});
		
		try {
			System.out.println(Class.forName("extract.Extractor").getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			String str = Class.forName("cn.edu.fuan.se.ASTUtils").getName();
			System.out.println(str);
		} catch (Exception e) {
			System.out.println("NotFoundClass.");
		}
	}

}
