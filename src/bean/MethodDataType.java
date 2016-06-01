package bean;

public class MethodDataType extends BaseDataType {
	
	private String className;
	private String returnType;
	private String modifiers;
	private String throwExceptions;
	private boolean isConstructor;
	private String javadoc;
	private String content;
	
	public MethodDataType() {
		
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}
	
	public String getModifiers() {
		return modifiers;
	}
	
	public void setThrowExceptions(String throwExceptions) {
		this.throwExceptions = throwExceptions;
	}
	
	public String getThrowExceptions() {
		return throwExceptions;
	}
	
	public void setIsConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
	}
	
	public boolean isConstructor() {
		return isConstructor;
	}
	
	public void setJavadoc(String javadoc) {
		this.javadoc = javadoc;
	}
	
	public String getJavadoc() {
		return javadoc;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
}
