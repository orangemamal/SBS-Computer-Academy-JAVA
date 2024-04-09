import java.util.Scanner;
import java.util.Vector;
import java.lang.StringBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class StatementInsertTest {
    public void dbInsert(Vector<BoardVO> vo) {
        int count = 0;
        try{
             Class.forName("com.mysql.cj.jdbc.Driver");
             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb",
                                                            "root", "zaq12wsx");
             Statement stmt = conn.createStatement();
             for(int i=0; i<vo.size(); i++) {
                 BoardVO v = (BoardVO)vo.get(i);
                 StringBuffer sql = new StringBuffer();
                 sql.append("INSERT INTO TB_BOARD VALUES (");
                 sql.append(" nextval('boardnumber_seq'),  '");
                 sql.append(v.getName() + "','");
                 sql.append(v.getTitle() + "','");
                 sql.append(v.getContent() + "',");
                 sql.append(" SYSDATE() ,");
                 sql.append(" 0 )");
                 System.out.println("Query 문 : " + sql.toString());
                 int result = stmt.executeUpdate(sql.toString());
                 count += result;
             }
             if(count > 0) System.out.println("총 " + count + "건 입력 성공");
             else System.out.println("입력실패");
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class BoardVO {
    private int boardnumber;
    private String name;
    private String title;
    private String content;
    private String write_date;
    private int hit;

    public BoardVO() {}
    public BoardVO(int boardnumber, String name, String title, String content, String write_date, int hit) {
        this.boardnumber = boardnumber;
        this.name = name;
        this.title = title;
        this.content = content;
        this.write_date = write_date;
        this.hit = hit;
    }
    public void setBoardnumber(int boardnumber) { this.boardnumber = boardnumber; }
    public int getBoardnumber() { return boardnumber; }
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }
    public void setContent(String content) { this.content = content; }
    public String getContent(){ return content; }
    public void setWrite_date(String write_date) {this.write_date = write_date;}
    public String getWrite_date() { return write_date;}
    public void setHit(int hit) {this.hit = hit;}
    public int getHit() { return hit;}
}

class DataValue {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Vector<BoardVO> v = new Vector<BoardVO>();
        while(true){
            System.out.print("이름을 입력하세요 : ");
            String name = scanner.nextLine();
            System.out.print("제목을 입력하세요 : ");
            String title = scanner.nextLine();
            System.out.print("내용을 입력하세요 : ");
            String content = scanner.nextLine();
            BoardVO vo = new BoardVO();
            vo.setName(name);
            vo.setTitle(title);
            vo.setContent(content);
            v.add(vo);
            System.out.println("계속 입력 하시겠습니까[Y/N] ? ");
            String yn = scanner.nextLine();
            if(yn.toUpperCase().equals("N")) {
                StatementInsertTest s = new StatementInsertTest();
                s.dbInsert(v);
                break;
            }else{
                continue;
            }
        }
    }
}