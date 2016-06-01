package file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
	public FileHelper() {
		
	}
	
	public static String getProjectNameFromProjectPath(String projectPath)
	{
		return new File(projectPath).getName();
	}
	
	private  List<String> getSubFile(String path, FileFilter filter)
	{
		List<String> result = new ArrayList<String>();
		File file = new File(path);
		if( file.isDirectory())
		{			
			for(File subFile :file.listFiles(filter))
			{
				result.addAll(getSubFile(subFile.getAbsolutePath(), filter));
			}
		}
		else 
		{
			result.add(file.getAbsolutePath());
		}
		return result;
	
	}
	
	/**
	 * 在文件夹中，获取所有指定后缀后的文件
	 * @param path
	 * @param extension
	 * @return
	 */
	public static List<String> getSubFile(String path, String extension)
	{		 
		FileFilter fileFilter = new ExtensionFileFilter(extension);
		FileHelper fileHelper = new FileHelper();
		return fileHelper.getSubFile(path, fileFilter); 

	}
	
	public static String getContent(String file) {
		String content;
		byte[] input = null;
		
        try {  
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));  
            input = new byte[bufferedInputStream.available()];  
            bufferedInputStream.read(input);  
            bufferedInputStream.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        
        content = new String(input);
        
        return content;
	}
}
