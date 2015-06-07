package data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Hello world!
 * 
 */
public class DataBaseLink // 数据库连接
{
	public static String ip = "172.26.48.141";
//	public static String ip = "114.212.42.143";
	public static String url = "jdbc:mysql://" + ip
			+ ":3306/nba?useUnicode=true&characterEncoding=utf-8";
	public static String driver = "com.mysql.jdbc.Driver";

	public DataBaseLink() {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			System.out.println("无法加载驱动");
		}

	}
//--------no use
	public void operation(String sql) {
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed()){
				
			}

				//System.out.println("success");

			Statement st = con.createStatement();
			st.executeUpdate(sql);

			con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
