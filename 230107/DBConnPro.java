package dbcommon;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
public class DBConnPro {
    private static Connection conn = null;
    public static Connection dbConnPro() {
       InputStream input = null;
       String driver = "";
       String url = "";
       String user = "";
       String password = "";
       try{
           // 외부파일인 db.properites 파일을 읽어들여서 InputStream 객체를 만듦
           input = new FileInputStream("C:\\java_project\\java_project\\src\\db.properties");
           // InputStream 객체의 값을 이용해서 db.properites 파일의 내용을 읽어들이기 위하여 프로퍼티스 객체를 생성
           Properties prop = new Properties();
           // 프로퍼티스 파일의 내용을 읽어들임
           prop.load(input);
           // 프로퍼티에서 읽은 키에 해당하는 값을 대입
           driver = prop.getProperty("db.driver");
           url = prop.getProperty("db.url");
           user = prop.getProperty("db.user");
           password = prop.getProperty("db.password");
           // JDBC 드라이버 로딩
           Class.forName(driver);
           // MySQL DB에 Connection
           conn = DriverManager.getConnection(url, user, password);

       }catch(Exception e){
           e.printStackTrace();
       }

       return conn;
    }

    public static void dbClose(ResultSet rs, PreparedStatement pstmt, Statement stmt){
        try{
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(stmt != null) stmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
