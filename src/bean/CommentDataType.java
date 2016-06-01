package bean;

public class CommentDataType extends BaseDataType {
	
	private String granularity;
	private String parent;
	private String code;
	private String comment;
	private String commentType;
	private String annotation;
	private String time;
	
	public CommentDataType() {
		super();
	}
	
	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}
	
	public String getGranularity() {
		return granularity;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String getParent() {
		return parent;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	
	public String getCommentType() {
		return commentType;
	}
	
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	public String getAnnotation() {
		return annotation;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTime() {
		return time;
	}
}
