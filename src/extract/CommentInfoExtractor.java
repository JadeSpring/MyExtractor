package extract;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.jdt.core.dom.*;

import astUtil.AstUtil;
import file.Content;

public class CommentInfoExtractor extends Extractor {
	private static ArrayList<ArrayList<String>>  data = null;
	
	private static LinkedList<String> projectName = new LinkedList<String>();
	private static LinkedList<String> name = new LinkedList<String>();
	private static LinkedList<String> sourceRange = new LinkedList<String>();
	private static LinkedList<String> granularity = new LinkedList<String>();
	private static LinkedList<String> parent = new LinkedList<String>();
	private static LinkedList<String> code = new LinkedList<String>();
	private static LinkedList<String> comment = new LinkedList<String>();
	private static LinkedList<String> time = new LinkedList<String>();
	private static LinkedList<String> commentType = new LinkedList<String>();
	private static LinkedList<String> annotation = new LinkedList<String>();
	
	private int lineFlag = 0;
	private int blockFlag = 0;
	private int javadocFlag = 0;
	
	public CommentInfoExtractor() {
		
	}
		
	public static ArrayList<ArrayList<String>> getData() {
		LinkedList<ArrayList<String>> temp = new LinkedList<ArrayList<String>>();
		temp.add(new ArrayList<String>(projectName));
		temp.add(new ArrayList<String>(name));
		temp.add(new ArrayList<String>(sourceRange));
		temp.add(new ArrayList<String>(granularity));
		temp.add(new ArrayList<String>(parent));
		temp.add(new ArrayList<String>(code));
		temp.add(new ArrayList<String>(comment));
		temp.add(new ArrayList<String>(commentType));
		temp.add(new ArrayList<String>(annotation));
		temp.add(new ArrayList<String>(time));
		
		projectName = new LinkedList<String>();
		name = new LinkedList<String>();
		sourceRange = new LinkedList<String>();
		granularity = new LinkedList<String>();
		parent = new LinkedList<String>();
		code = new LinkedList<String>();
		comment = new LinkedList<String>();
		commentType = new LinkedList<String>();
		annotation = new LinkedList<String>();
		time = new LinkedList<String>();
		
		data = new ArrayList<ArrayList<String>>(temp);
		return data;
	}
	
	public static void resetData() {
		data = null;
	}
	
	public static LinkedList<String> getName() {
		return name;
	}
	
	public static LinkedList<String> getGranularity() {
		return granularity;
	}
	
	public static LinkedList<String> getParent() {
		return parent;
	}
	
	public static LinkedList<String> getCode() {
		return code;
	}
	
	public static LinkedList<String> getComment() {
		return comment;
	}
	
	public static LinkedList<String> getTime() {
		return time;
	}
	
	private String getCurrentTime() {
		Date nowDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		return dateFormat.format(nowDate);
	}
	
	private String getParentName(ASTNode node) {
		if (node.getParent() instanceof CompilationUnit) {
			return "null";
		}
		
		return AstUtil.getQualifiedName(node.getParent());
	}
	
	private void getComment(ASTNode node) {
		String content = Content.getContent();
		if (node.getRoot() instanceof CompilationUnit) {
			CompilationUnit root = (CompilationUnit)node.getRoot();
			int firstIndex = root.firstLeadingCommentIndex(node);
			int lastIndex = root.lastTrailingCommentIndex(node);
			
			if (root.getCommentList() != null) {
				List<Comment> comments = root.getCommentList();
				
				if ((firstIndex != -1) || (lastIndex != -1)) {
					StringBuffer associateComment = new StringBuffer("");
					Comment mycomment = null;
					int position;
					int length;
					
					if (firstIndex != -1) {
						
						while (firstIndex <= comments.size()-1) {
							mycomment = comments.get(firstIndex);
							position = mycomment.getStartPosition();
							length = mycomment.getLength();
							
							if (position <= node.getStartPosition()) {
								if (mycomment instanceof Javadoc) {
									Javadoc javadoc = (Javadoc)mycomment;
									associateComment.append("Javadoc : " + javadoc);
									this.setJavadocFlag();
								}
								else if (mycomment instanceof LineComment){
									associateComment.append("Lead Comment : [" + position + "+" + length + "]"
								                             + content.substring(position, position+length) + "\n");
									this.setLineFlag();
								}
								else if (mycomment instanceof BlockComment) {
									associateComment.append("Lead Comment : [" + position + "+" + length + "]"
				                             + content.substring(position, position+length) + "\n");
									this.setBlockFlag();
								}
								
								
							}
							else {
								break;
							}
							
							firstIndex++;
						}
						
					}
					
					if (lastIndex != -1) {
						StringBuffer trail = new StringBuffer("");
						
						while (lastIndex >= 0) {
							mycomment = comments.get(lastIndex);
							position = mycomment.getStartPosition();
							length = mycomment.getLength();
							
							if(position > (node.getStartPosition() + node.getLength())) {
								if (mycomment instanceof LineComment) {
									trail.insert(0, "Trail Comment : [" + position + "+" + length + "]"
						                    + content.substring(position, position+length) + "\n");
									
									this.setLineFlag();
								}
								else if (mycomment instanceof BlockComment) {
									trail.insert(0, "Trail Comment : [" + position + "+" + length + "]"
						                    + content.substring(position, position+length) + "\n");
									
									this.setBlockFlag();
								}
								else if (mycomment instanceof Javadoc) {
									trail.insert(0, "Trail Comment : [" + position + "+" + length + "]"
						                    + content.substring(position, position+length) + "\n");
									
									this.setJavadocFlag();
								}
								
							}
							else {
								break;
							}
							
							lastIndex--;							
						}
						
						associateComment.append(trail);
					}
					
					comment.add(new String(associateComment));
				}
				else {
					comment.add("null");
				}
				
				
			}
			else {
				comment.add("null");
			}
		}
		else {
			comment.add("null");
		}
		
		this.getCommentType();
	}
	
	private void setLineFlag() {
		this.lineFlag = 1;
	}
	
	private void setBlockFlag() {
		this.blockFlag = 2;
	}
	
	private void setJavadocFlag() {
		this.javadocFlag = 4;
	}
	
	private void getCommentType() {
		int flag = lineFlag + blockFlag + javadocFlag;
		
		switch (flag) {
		case 1:
			commentType.add("Line comment");
			break;
		case 2:
			commentType.add("Block comment");
		    break;
		case 3:
			commentType.add("Line and Block comment");
			break;
		case 4:
			commentType.add("Javadoc");
			break;
		case 5:
			commentType.add("Line comment and Javadoc");
			break;
		case 6:
			commentType.add("Block comment and Javadoc");
			break;
		case 7:
			commentType.add("Line, Block comment and Javadoc");
			break;
		default:
			commentType.add("No comment");
			break;
		}
	}
	
	public void extract(ASTNode node) {
		projectName.add(ProjectName.getProjectName());
		
		sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		parent.add(this.getParentName(node));
		
		this.getComment(node);
		
		time.add(this.getCurrentTime());
	}
	
	public void extract(AnnotationTypeDeclaration node) {
		
	}
	
	public void extract(AnnotationTypeMemberDeclaration node) {
		
	}
	
	public void extract(AnonymousClassDeclaration node) {
		//projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		//sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		granularity.add(Granularity.CLASS_DECLARATION);
		
		//parent.add(this.getParentName(node));
		
		code.add(node.toString());
		
		//this.getComment(node);
		
		annotation.add("null");
		
		//time.add(this.getCurrentTime());
	}
	
	public void extract(ArrayAccess node) {
		
	}
	
	public void extract(ArrayCreation node) {
		
	}
	
	public void extract(ArrayInitializer node) {
		
	}
	
	public void extract(ArrayType node) {
		
	}
	
	public void extract(AssertStatement node) {
		
	}
	
	public void extract(Assignment node) {
		
	}
	
	public void extract(Block node) {
		
	}
	
	public void extract(BlockComment node) {
		
	}
	
	public void extract(BooleanLiteral node) {
		
	}
	
	public void extract(CastExpression node) {
		
	}
	
	public void extract(CatchClause node) {
		
	}
	
	public void extract(CharacterLiteral node) {
		
	}
	
	public void extract(ClassInstanceCreation node) {
		
	}
	
	public void extract(CompilationUnit node) {
		
	}
	
	public void extract(ConditionalExpression node) {
		
	}
	
	public void extract(ConstructorInvocation node) {
		
	}
	
	public void extract(ContinueStatement node) {
		
	}
	
	public void extract(CreationReference node) {
		
	}
	
	public void extract(Dimension node) {
		
	}
	
	public void extract(DoStatement node) {
		//projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		//sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		granularity.add(Granularity.LOOP);
		
		//parent.add(this.getParentName(node));
		
		code.add(node.toString());
		
		//this.getComment(node);
		
		annotation.add("null");
		
		//time.add(this.getCurrentTime());
	}
	
	public void extract(EmptyStatement node) {
		
	}
	
	public void extract(EnhancedForStatement node) {
		//projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		//sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		granularity.add(Granularity.LOOP);
		
		//parent.add(this.getParentName(node));
		
		code.add(node.toString());
		
		//this.getComment(node);
		
		annotation.add("null");
		
		//time.add(this.getCurrentTime());
	}
	
	public void extract(EnumConstantDeclaration node) {
		
	}
	
	public void extract(EnumDeclaration node) {
		
	}
	
	public void extract(ExpressionMethodReference node) {
		
	}
	
	public void extract(ExpressionStatement node) {
		
	}
	
	public void extract(FieldAccess node) {
		
	}
	
	public void extract(FieldDeclaration node) {
		
		LinkedList<String> uris = AstUtil.getQualifiedName(node);
		Iterator<String> uri = uris.iterator();
		while(uri.hasNext()) {
			projectName.add(ProjectName.getProjectName());
			
			name.add(uri.next());
			
			sourceRange.add(node.getStartPosition() + "+" + node.getLength());
			
			granularity.add(Granularity.ATTRIBUTE_DECLARATION);
			
			parent.add(this.getParentName(node));
			
			StringBuffer annotations = new StringBuffer("");
			if (node.modifiers().isEmpty()) {
				annotation.add("null");
			}
			else {
				List<IExtendedModifier> modifiers = node.modifiers();
				
				for (IExtendedModifier modifier : modifiers) {
					if (modifier.isAnnotation()) {
						annotations.append(modifier.toString());
					}
				}
				
				if (annotations.length() != 0) {
					annotation.add(new String(annotations));
				}
				else {
					annotation.add("null");
				}
			}
			
			if(node.getJavadoc() != null) {
				String javadoc = node.getJavadoc().toString().trim();
				code.add(node.toString().substring(javadoc.length() + 1).substring(annotations.length()).trim());
			}
			else {
				code.add(node.toString().substring(annotations.length()).trim());
			}
			
			this.getComment(node);
			
			time.add(this.getCurrentTime());
		} 
	}
	
	public void extract(ForStatement node) {
		//projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		//sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		granularity.add(Granularity.LOOP);
		
		//parent.add(this.getParentName(node));
		
		code.add(node.toString());
		
		//this.getComment(node);
		
		annotation.add("null");
		
		//time.add(this.getCurrentTime());
	}
	
	public void extract(IfStatement node) {
		//projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		//sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		granularity.add(Granularity.CONTROL_STRUCTURE);
		
		//parent.add(this.getParentName(node));
		
		code.add(node.toString());
		
		//this.getComment(node);
		
		annotation.add("null");
		
		//time.add(this.getCurrentTime());
	}
	
	public void extract(ImportDeclaration node) {
		
	}
	
	public void extract(InfixExpression node) {
		
	}
	
	public void extract(Initializer node) {
		
	}
	
	public void extract(InstanceofExpression node) {
		
	}
	
	public void extract(IntersectionType node) {
		
	}
	
	public void extract(Javadoc node) {
		 
	}
	
	public void extract(LabeledStatement node) {
		
	}
	
	public void extract(LambdaExpression node) {
		
	}
	
	public void extract(LineComment node) {
		
	}
	
	public void extract(MarkerAnnotation node) {
		
	}
	
	public void extract(MemberRef node) {
		
	}
	
	public void extract(MemberValuePair node) {
		
	}
	
	public void extract(MethodDeclaration node) {
		//projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		//sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		granularity.add(Granularity.METHOD_DECLARATION);
		
		//parent.add(this.getParentName(node));
		
		StringBuffer annotations = new StringBuffer("");
		if (node.modifiers().isEmpty()) {
			annotation.add("null");
		}
		else {
			List<IExtendedModifier> modifiers = node.modifiers();
			
			for (IExtendedModifier modifier : modifiers) {
				if (modifier.isAnnotation()) {
					annotations.append(modifier.toString());
				}
			}
			
			if (annotations.length() != 0) {
				annotation.add(new String(annotations));
			}
			else {
				annotation.add("null");
			}
		}
		
		if(node.getJavadoc() != null) {			
			String javadoc = node.getJavadoc().toString().trim();
			code.add(node.toString().substring(javadoc.length() + 1).substring(annotations.length()).trim());
			//this.getComment(node);
		}
		else {
			code.add(node.toString().substring(annotations.length()).trim());
			//this.getComment(node);
		}
		
		//time.add(this.getCurrentTime());
	}
	
	public void extract(MethodInvocation node) {
		//projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		//sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		granularity.add(Granularity.METHOD_CALL);
		
		//parent.add(this.getParentName(node));
		
		code.add(node.toString());
		
		//this.getComment(node);
		
		annotation.add("null");
		
		//time.add(this.getCurrentTime());
	}
	
	public void extract(MethodRef node) {
		
	}
	
	public void extract(MethodRefParameter node) {
		
	}
	
	public void extract(Modifier node) {
		
	}
	
	public void extract(NameQualifiedType node) {
		
	}
	
	public void extract(NormalAnnotation node) {
		
	}
	
	public void extract(NullLiteral node) {
		
	}
	
	public void extract(NumberLiteral node) {
		
	}
	
	public void extract(PackageDeclaration node) {
		
	}
	
	public void extract(ParameterizedType node) {
		
	}
	
	public void extract(ParenthesizedExpression node) {
		
	}
	
	public void extract(PostfixExpression node) {
		
	}
	
	public void extract(PrefixExpression node) {
		
	}
	
	public void extract(PrimitiveType node) {
		
	}
	
	public void extract(QualifiedName node) {
		
	}
	
	public void extract(QualifiedType node) {
		
	}
	
	public void extract(ReturnStatement node) {
		
	}
	
	public void extract(SimpleName node) {
		
	}
	
	public void extract(SimpleType node) {
		
	}
	
	public void extract(SingleMemberAnnotation node) {
		
	}
	
	public void extract(SingleVariableDeclaration node) {
		
	}
	
	public void extract(StringLiteral node) {
		
	}
	
	public void extract(SuperConstructorInvocation node) {
		
	}
	
	public void extract(SuperFieldAccess node) {
		
	}
	
	public void extract(SuperMethodInvocation node) {
		
	}
	
	public void extract(SuperMethodReference node) {
		
	}
	
	public void extract(SwitchCase node) {
		
	}
	
	public void extract(SwitchStatement node) {
		//projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		//sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		granularity.add(Granularity.CONTROL_STRUCTURE);
		
		//parent.add(this.getParentName(node));
		
		code.add(node.toString());
		
		//this.getComment(node);
		
		annotation.add("null");
		
		//time.add(this.getCurrentTime());
	}
	
	public void extract(SynchronizedStatement node) {
		
	}
	
	public void extract(TagElement node) {
		
	}
	
	public void extract(TextElement node) {
		
	}
	
	public void extract(ThisExpression node) {
		
	}
	
	public void extract(ThrowStatement node) {
		
	}
	
	public void extract(TryStatement node) {
		
	}
	
	public void extract(TypeDeclaration node) {
		//projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		//sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		granularity.add(Granularity.CLASS_DECLARATION);
		
		//parent.add(this.getParentName(node));
		
		StringBuffer annotations = new StringBuffer("");
		if (node.modifiers().isEmpty()) {
			annotation.add("null");
		}
		else {
			@SuppressWarnings("unchecked")
			List<IExtendedModifier> modifiers = node.modifiers();
						
			for (IExtendedModifier modifier : modifiers) {
				if (modifier.isAnnotation()) {
					annotations.append(modifier.toString());
				}
			}
			
			if (annotations.length() != 0) {
				annotation.add(new String(annotations));
			}
			else {
				annotation.add("null");
			}
		}
		
		if(node.getJavadoc() != null) {			
			String javadoc = node.getJavadoc().toString().trim();
			code.add(node.toString().substring(javadoc.length() + 1).substring(annotations.length()).trim());
			//this.getComment(node);
		}
		else {
			code.add(node.toString().substring(annotations.length()).trim());
			//this.getComment(node);
		}
		
		//time.add(this.getCurrentTime());
	}
	
	public void extract(TypeDeclarationStatement node) {
		
	}
	
	public void extract(TypeLiteral node) {
		
	}
	
	public void extract(TypeMethodReference node) {
		
	}
	
	public void extract(TypeParameter node) {
		
	}
	
	public void extract(UnionType node) {
		
	}
	
	public void extract(VariableDeclarationExpression node) {
		
	}
	
	public void extract(VariableDeclarationFragment node) {
		
	}
	
	public void extract(VariableDeclarationStatement node) {
		LinkedList<String> uris = AstUtil.getQualifiedName(node);
		Iterator<String> uri = uris.iterator();
		while(uri.hasNext()) {
			projectName.add(ProjectName.getProjectName());
			
			name.add(uri.next());
			
			sourceRange.add(node.getStartPosition() + "+" + node.getLength());
			
			granularity.add(Granularity.VARIABLE_DECLARATION);
			
			parent.add(this.getParentName(node));
			
			this.getComment(node);
			
			StringBuffer annotations = new StringBuffer("");
			if (node.modifiers().isEmpty()) {
				annotation.add("null");
			}
			else {
				List<IExtendedModifier> modifiers = node.modifiers();
				
				for (IExtendedModifier modifier : modifiers) {
					if (modifier.isAnnotation()) {
						annotations.append(modifier.toString());
					}
				}
				
				if (annotations.length() != 0) {
					annotation.add(new String(annotations));
				}
				else {
					annotation.add("null");
				}
			}
			
			code.add(node.toString().substring(annotations.length()).trim());
			
			time.add(this.getCurrentTime());
		}
	}
	
	public void extract(WhileStatement node) {
		//projectName.add(project);
		
		name.add(AstUtil.getQualifiedName(node));
		
		//sourceRange.add(node.getStartPosition() + "+" + node.getLength());
		
		granularity.add(Granularity.LOOP);
		
		//parent.add(this.getParentName(node));
		
		code.add(node.toString());
		
		//this.getComment(node);
		
		annotation.add("null");
		
		//time.add(this.getCurrentTime());
	}
	
	public void extract(WildcardType node) {
		
	}
}
