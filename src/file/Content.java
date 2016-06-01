package file;

public class Content {
	private static String content;
	private static String fileName;
	
	public static void setContent(String str) {
		content = str;
	}
	
	public static String getContent() {
		return content;
	}
	
	public static void setFileName(String file) {
		fileName = file;
	}
	
	public static String getFileName() {
		return fileName;
	}
}
