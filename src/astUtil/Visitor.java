package astUtil;

import org.eclipse.jdt.core.dom.*;

import extract.Extractor;
import extract.MethodInfoExtractor;
import extract.CommentInfoExtractor;

public class Visitor extends ASTVisitor {
	
	/**
	 * use MySQL database manipulate data visited
	 */
	public Visitor() {
		
	}
	
	/**
	 * visit method declaration node and extract the project name, qualified name, granularity, code, javadoc and visit time from the node
	 */
	public boolean visit(MethodDeclaration node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);
		MethodInfoExtractor methodInfo = new MethodInfoExtractor();
		methodInfo.extract(node);
		return true;
	}
	
	/**
	 * visit type declaration node, class declaration or interface declaration, and extract the project name, qualified name, granularity, code, javadoc and visit time from the node
	 */
	public boolean visit(TypeDeclaration node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);
		
		return true;
	}
	
	public boolean visit(FieldDeclaration node) {
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
	
	public boolean visit(MethodInvocation node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);

		return true;
	}
	
	public boolean visit(AnonymousClassDeclaration node) {
		Extractor extractor = new CommentInfoExtractor();
		extractor.extract(node);

		return true;
	}
}
