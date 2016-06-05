package extract;

import java.util.ArrayList;
import java.util.LinkedList;

import org.eclipse.jdt.core.dom.*;
//import java.util.*;

//import dataUtil.*;
import astUtil.*;
import dataUtil.DBManipulation;
import file.Content;
import file.FileHelper;

public class ExecuteExtractor {
	/**
	 * extract method and comment information from java file
	 * @param javaFilePath
	 */
	public static void extracts(String projectPath) {
		String projectName = FileHelper.getProjectNameFromProjectPath(projectPath);
		ProjectName.setProjectName(projectName);
		
		for (String file : FileHelper.getSubFile(projectPath, "java")) {
			String content = FileHelper.getContent(file);
			
			Content.setContent(content);
			Content.setFileName(file);
			
			ASTParser parser = ASTParser.newParser(AST.JLS8); // initialize

			parser.setKind(ASTParser.K_COMPILATION_UNIT); // to parse compilation unit
			
			parser.setSource(content.toCharArray()); 
			
			parser.setResolveBindings(true);
			CompilationUnit root = (CompilationUnit) parser.createAST(null);
			Visitor micrASTVisitor = new Visitor();
			root.accept(micrASTVisitor);
		}
		
		long start = System.currentTimeMillis();
		
		ArrayList<ArrayList<String>> data /*= CommentInfoExtractor.getData()*/;
		long t = System.currentTimeMillis();
		/*if (!data.isEmpty()) {
			DBManipulation.DoStore("commentinfo", data);
		}
		else {
			System.out.println("failed !");
		}
		CommentInfoExtractor.resetData();*/
		
		/*data = MethodInfoExtractor.getData();
		
		
		if (!data.isEmpty()) {
			DBManipulation.DoStore("methodinfo", data);
		}
		else {
			System.out.println("failed !");
		}
		MethodInfoExtractor.resetData();*/
		
		data = ClassInfoExtractor.getData();
		ClassInfoExtractor.resetData();
		if (!data.isEmpty()) {
			DBManipulation.DoStore("classinfo", data);
		}
		else {
			System.out.println("failed !");
		}
		
		long end = System.currentTimeMillis();
		System.out.println("getData用时：" + (t - start));
		System.out.println("插入数据库用时：" + (end - t));
	}
	
}
