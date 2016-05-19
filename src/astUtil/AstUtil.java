package astUtil;

import java.io.BufferedInputStream;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.management.openmbean.SimpleType;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.swt.events.TypedEvent;  
  
public class AstUtil {
    /** 
    * get compilation unit of source code 
    * @param javaFilePath  
    * @return CompilationUnit 
    */  
    public static CompilationUnit getCompilationUnit(String javaFilePath){  
        byte[] input = null;  
        try {  
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));  
            input = new byte[bufferedInputStream.available()];  
            bufferedInputStream.read(input);  
            bufferedInputStream.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
  
		ASTParser astParser = ASTParser.newParser(AST.JLS8);  
        astParser.setSource(new String(input).toCharArray());  
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);  
  
        CompilationUnit cu = (CompilationUnit) (astParser.createAST(null));  
          
        return cu;  
    }
    
    /**
     * get the qualified name of current node 
     * @param node
     * @return URI
     */    
    public static String getQualifiedName(ASTNode node) {
		String uri = "";		
		
		do {
			switch (node.getNodeType()) {
			case ASTNode.METHOD_DECLARATION: {
				MethodDeclaration method = (MethodDeclaration)node;
				List<SingleVariableDeclaration> parameterList = method.parameters();
				String parameters = "";
				Iterator<SingleVariableDeclaration> iter = parameterList.iterator();
				while (iter.hasNext()) {
					parameters += iter.next().getType() + ", ";
				}
				if (parameters != "") {
					parameters = parameters.substring(0, parameters.length() - 2);
				}
				parameters = "(" + parameters + ")";
				uri = method.getName() + parameters + "." + uri;
				break;
			}
			case ASTNode.TYPE_DECLARATION: {
				TypeDeclaration type = (TypeDeclaration)node;
				uri = type.getName() + "." +uri;
				break;
			}
			case ASTNode.COMPILATION_UNIT: {
				CompilationUnit compilation = (CompilationUnit)node;
				if (compilation.getPackage() != null) {
					uri = compilation.getPackage().getName() + "." + uri;
				}
				break;
			}
			case ASTNode.ANONYMOUS_CLASS_DECLARATION: {
				AnonymousClassDeclaration anony = (AnonymousClassDeclaration)node;
				ClassInstanceCreation parent = (ClassInstanceCreation)anony.getParent();
				uri = "new " + parent.getType() + "() {...}." + uri;
				break;
			}
			
			/*case ASTNode.METHOD_INVOCATION: {
				uri = "methodInvocation." + uri;
				break;
			}
			case ASTNode.IF_STATEMENT: {
				uri = "if." + uri;
				break;
			}
			case ASTNode.SWITCH_STATEMENT: {
				uri = "switch." + uri;
				break;
			}
			case ASTNode.FOR_STATEMENT: {
				uri = "for." + uri;
				break;
			}
			case ASTNode.ENHANCED_FOR_STATEMENT: {
				uri = "enhancedFor." + uri;
				break;
			}
			case ASTNode.DO_STATEMENT: {
				uri = "do." + uri;
				break;
			}
			case ASTNode.WHILE_STATEMENT: {
				uri = "while." + uri;
				break;
			}*/
			
			default: break;
			}
			
			node = node.getParent();
		} while(node != null);
		
		if( uri != "") {
			return uri.substring(0, uri.length()-1);
		}
		
		return uri;
	}
    /**
     * get the qualified name of current field declaration node 
     * @param node
     * @return
     */
    public static LinkedList<String> getQualifiedName(FieldDeclaration node) {
    	LinkedList<String> uris = new LinkedList<>();
    	String uri = "";
    	
		List<VariableDeclarationFragment> variable = node.fragments();
		Iterator<VariableDeclarationFragment> iter = variable.iterator();
		while (iter.hasNext()) {
			VariableDeclarationFragment var = iter.next();
			uri = AstUtil.getQualifiedName(var.getParent()) + "." + var.getName();
			uris.add(uri);
		}
		
		return uris;
    }
    
    public static LinkedList<String> getQualifiedName(VariableDeclarationStatement node) {
    	LinkedList<String> uris = new LinkedList<>();
    	String uri = "";
    	
		List<VariableDeclarationFragment> variable = node.fragments();
		Iterator<VariableDeclarationFragment> iter = variable.iterator();
		while (iter.hasNext()) {
			VariableDeclarationFragment var = iter.next();
			uri = AstUtil.getQualifiedName(var.getParent()) + "." + var.getName();
			uris.add(uri);
		}
		
		return uris;
    }
    
    public static String getQualifiedName(MethodInvocation node) {
    	String uri = "";
    	
		uri = AstUtil.getQualifiedName(node.getParent()) + ".methodInvocation";
    	return uri;
    }
    
    public static String getQualifiedName(SwitchStatement node) {
    	String uri = "";
    	
		uri = AstUtil.getQualifiedName(node.getParent()) + ".switch";
    	return uri;
    }
    
    public static String getQualifiedName(IfStatement node) {
    	String uri = "";
		uri = AstUtil.getQualifiedName(node.getParent()) + ".if";
    	return uri;
    }
    
    public static String getQualifiedName(WhileStatement node) {
    	String uri = "";
    	
		uri = AstUtil.getQualifiedName(node.getParent()) + ".while";
    	return uri;
    }
    
    public static String getQualifiedName(DoStatement node) {
    	String uri = "";
		uri = AstUtil.getQualifiedName(node.getParent()) + ".do";
    	return uri;
    }
    
    public static String getQualifiedName(ForStatement node) {
    	String uri = "";
		uri = AstUtil.getQualifiedName(node.getParent()) + ".for";
    	return uri;
    }
    
    public static String getQualifiedName(EnhancedForStatement node) {
    	String uri = "";
    	
		uri = AstUtil.getQualifiedName(node.getParent()) + ".enhancedFor";
    	return uri;
    }
    
}  
