package bean;

public class BaseDataType {
	
	private String projectName;
	private String name;
	private String sourceRange;
	
	public BaseDataType() {
		
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSourceRange(String sourceRange) {
		this.sourceRange = sourceRange;
	}
	
	public String getSourceRange() {
		return sourceRange;
	}
}
