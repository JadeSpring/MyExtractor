package main;

//import astUtil.AstUtil;
//import dataUtil.DBManipulation;
import extract.*;

//import java.sql.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		
		//ExecuteExtractor.extracts("E:\\jEdits\\jedit-4-2-final");
		ExecuteExtractor.extracts("D:\\eclipse\\workspace\\MyExtractor");
		//ExecuteExtractor.extracts("D:\\eclipse\\workspace\\HelloWorld");
		//ExecuteExtractor.extracts("E:\\jEdits\\jedit-4-3");
		//ExecuteExtractor.extracts("E:/workspace/JadeSpring");
		//ExecuteExtractor.extracts("E:/JavaSource/src/java");
		
		System.out.println("执行成功！");
		long end = System.currentTimeMillis();
		System.out.println("总用时：" + (end - start));
	}

}
