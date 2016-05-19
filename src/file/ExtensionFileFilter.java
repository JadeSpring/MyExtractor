package file;

import java.io.FileFilter;
import java.io.File;

public class ExtensionFileFilter implements FileFilter {
	private String extension;
	
	public ExtensionFileFilter(String extension) {
		this.extension = "." + extension;
	}
	
	public boolean accept(File pathname) {
		return pathname.getName().endsWith(extension) || pathname.isDirectory();
	}
}
