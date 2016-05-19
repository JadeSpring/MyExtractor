package dataUtil;

import java.util.*;
import org.eclipse.jdt.core.dom.*;

import file.FileHelper;

/*import preference.*;*/

public class Test {

	public static void main(String[] args) {
		String str = "@ConstructorProperties({\"startPoint\",\"endPoint\",\"fractions\",\"colors\",\"cycleMethod\",\"colorSpace\",\"transform\"})";
		int len = str.getBytes().length;
		System.out.println(len);
	}

}
