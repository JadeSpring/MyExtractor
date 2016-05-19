package dataUtil;

import java.sql.*;
import java.util.ArrayList;

//import com.mysql.jdbc.Statement;
/**
 * 
 * @author syc
 *
 */
public class DBManipulation {
	private static String url = "jdbc:mysql://localhost:3306/ast_db?useSSL=false&useServerPrepStmts=false&rewriteBatchedStatements=true";
	private static String user = "root";
	private static String password = "jade";
	private static String driver = "com.mysql.jdbc.Driver";
	
	int x, y, z;
	/**
	 * use MySQL database by default 
	 */
	public DBManipulation() {
		
	}
	
	/*
	public DBManipulation(String dbdriver, String dburl, String dbuser, String dbpassword) {
		driver = dbdriver;
		url = dburl;
		user = dbuser;
		password = dbpassword;
	}*/
	
	/**
	 * set database which will be used
	 * @param database
	 */
	public static void setDatabase(String database) {
		if (database == "MySQL") {
			setDriver("com.mysql.jdbc.Driver");
			setUrl("jdbc:mysql://localhost:3306/ast_db?useSSL=false");
		}
		
		if (database == "SQLite") {
			setDriver("");
			setUrl("hello sqlite");
		}
		
		if (database == "SQL Server") {
			setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
			setUrl("hello sql server");
		}
	}
	
	/**
	 * get current database driver
	 * @return driver
	 */
	public String getDriver() {
		return driver;
	}
	
	/**
	 * set the driver of current database
	 * @param dbdriver
	 */
	public static void setDriver(String dbdriver) {
		driver = dbdriver;
	}
	
	/**
	 * get current URL
	 * @return URL
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * set URL of current database
	 * @param dburl
	 */
	public static void setUrl(String dburl) {
		url = dburl;
	}
	
	/**
	 * get current user
	 * @return user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * set user of current database
	 * @param dbuser
	 */
	public static void setUser(String dbuser) {
		user = dbuser;
	}
	
	/**
	 * get current password
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * set password of current database
	 * @param dbpassword
	 */
	public static void setPassword(String dbpassword) {
		password = dbpassword;
	}
	
	/**
	 * connect database
	 * @return Connection
	 */
	public static Connection getDbConnection() {
		Connection con = null;
		
		try {
			Class.forName(driver);
		} catch(ClassNotFoundException e) {
			System.out.println("Not found suitable driver.");
			System.out.println(e);
		}
		
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Connection failed.");
			System.out.println("Input url, user and password correctly, please!");
			System.out.println(e);
		}
		
		return con;
	}
	
	/**
	 * insert data into the table
	 * @param table
	 * @param data
	 */
	public static void DoStore(String table, ArrayList<ArrayList<String>> data) {
		Connection con = null;
		PreparedStatement sql;
		int columns = data.size();        //column number of table
		int rows = data.get(0).size();    //the row number of data which will be inserted into the table
		StringBuffer insertStatement = new StringBuffer("insert into " + table + " values(");
		
		try {
			con = getDbConnection();
			con.setAutoCommit(false);			
			
			for (int i=0; i<columns; i++) {
				insertStatement.append("?,");
			}
			
			insertStatement.replace(insertStatement.length() - 1, insertStatement.length(), ")");

			sql = con.prepareStatement(new String(insertStatement));
			
			for (int j=0; j<rows; j++) {
				for (int i=0; i<columns; i++) {
					sql.setString(i+1, data.get(i).get(j));
				}
				
				sql.addBatch();
			}
			
			sql.executeBatch();
			con.commit();
			
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
	}
}