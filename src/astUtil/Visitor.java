package astUtil;

import org.eclipse.jdt.core.dom.*;

import extract.Extractor;
import extract.MethodInfoExtractor;
import extract.MethodInvocationInfoExtractor;
import extract.ClassInfoExtractor;
import extract.CommentInfoExtractor;

public class Visitor extends ASTVisitor {
	
	/**
	 * use MySQL database manipulate data visited
	 */
	public Visitor() {
		
	}
	
/*	public void preVisit(ASTNode node) {
		switch (node.getNodeType()) {
		case ASTNode.ANONYMOUS_CLASS_DECLARATION:
		case ASTNode.TYPE_DECLARATION:
		case ASTNode.METHOD_DECLARATION:
		case ASTNode.METHOD_INVOCATION:
		case ASTNode.IF_STATEMENT:
		case ASTNode.SWITCH_STATEMENT:
		case ASTNode.FOR_STATEMENT:
		case ASTNode.ENHANCED_FOR_STATEMENT:
		case ASTNode.WHILE_STATEMENT:
		case ASTNode.DO_STATEMENT: {
			CommentInfoExtractor extractor = new CommentInfoExtractor();
			extractor.extract(node);
			break;
		}
		default:
			break;
		}
		
		super.preVisit(node);
	}*/
	/**
	 * visit method declaration node and extract the project name, qualified name, granularity, code, javadoc and visit time from the node
	 */
/*	public boolean visit(MethodDeclaration node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);
		
		MethodInfoExtractor methodInfo = new MethodInfoExtractor();
		methodInfo.extract(node);
		
		return true;
	}*/
	
	/**
	 * visit type declaration node, class declaration or interface declaration, and extract the project name, qualified name, granularity, code, javadoc and visit time from the node
	 */
	public boolean visit(TypeDeclaration node) {
		/*Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);*/
		
		ClassInfoExtractor classInfo = new ClassInfoExtractor();
		classInfo.extract(node);
		
		return true;
	}
	
/*	public boolean visit(FieldDeclaration node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);

		return true;
	}
	
	public boolean visit(VariableDeclarationStatement node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);

		return true;
	}
	
	public boolean visit(IfStatement node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);

		return true;
	}
	
	public boolean visit(ForStatement node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);

		return true;
	}
	
	public boolean visit(DoStatement node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);

		return true;
	}
	
	@Override
	public boolean visit(WhileStatement node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);

		return true;
	}
	
	public boolean visit(SwitchStatement node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);

		return true;
	}
	
	public boolean visit(EnhancedForStatement node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);

		return true;
	}
	*/
	public boolean visit(MethodInvocation node) {
		/*Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);*/
		
		MethodInvocationInfoExtractor extractor = new MethodInvocationInfoExtractor();
		extractor.extract(node);

		return true;
	}
	
	public boolean visit(AnonymousClassDeclaration node) {
		/*Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);*/
		
		ClassInfoExtractor classInfo = new ClassInfoExtractor();
		classInfo.extract(node);

		return true;
	}
}
