package bean;

public class ClassdDataType extends BaseDataType {
	
	private String fileName;
	private String superClass;
	private String interfaces;
	private boolean isInterface = false;
	private boolean isSuperClass = false;
	private boolean isAnonymous = false;
	private String modifiers;
	private String content;
	
	public ClassdDataType() {
		super();
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public String getSuperClass() {
		return superClass;
	}
	
	public void setInterfaces(String interfaces) {
		this.interfaces = interfaces;
	}
	
	public String getInterfaces() {
		return interfaces;
	}
	public void setIsInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}
	
	public boolean isInterface() {
		return isInterface;
	}
	
	public void setIsSuperClass(boolean isSuperClass) {
		this.isSuperClass = isSuperClass;
	}
	
	public boolean isSuperClass() {
		return isSuperClass;
	}
	
	public void setIsAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	
	public boolean isAnonymous() {
		return isAnonymous;
	}
	
	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}
	
	public String getModifiers() {
		return modifiers;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
}
