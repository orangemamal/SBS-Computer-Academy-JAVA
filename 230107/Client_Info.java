package cms_sql;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import dbcommon.DBConnPro;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
public class Client_Info {
    // 전언변수 선언
    static Scanner scan = new Scanner(System.in);
    static int nNum = 0;
    static int rowCount = 0;
    public static void main(String[] args){
        System.setProperty("file.encoding", "UTF-8");
        try{
             Field charset = Charset.class.getDeclaredField("defaultCharset");
             charset.setAccessible(true);
             charset.set(null, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        while(true){
            String str = Client_Info_View();
            switch(str){
                case "I": //신규 고객정보 저장
                    InsertEx();
                    System.out.println("고객이 저장되었습니다..");
                    break;
                case "S": // 고객정보 검색
                    SearchEx();
                    break;
                case "P": //이전 고객 정보 검색
                    if(nNum <=0) {
                        System.out.println("찾으시는 이전 고객이 없습니다.");
                    }else{
                        prev_SearchEx("P");
                    }
                case "N": // 다음 고객 정보 검색
                    prev_SearchEx("N");
                    break;
                case "U":
                    /*
                    if(rowCount>1){
                        System.out.println("다수의 고객이 선택되었습니다. 한 고객만 선택해주세요");
                    }else{
                        if(nNum<=0) {
                            System.out.println("수정하고자 하는 고객이 없습니다.");
                        }else{
                            UpdateEx();
                            System.out.println("고객 정보를 수정하였습니다.");
                        }
                    }
                    */
                    System.out.print("수정을 원하는 고객번호를 입력하세요 : ");
                    nNum = Integer.parseInt(scan.next());
                    UpdateEx();
                    break;
                case "D":
                    /*
                    if(rowCount>1){
                        System.out.println("다수의 고객이 선택되었습니다. 한 고객만 선택해주세요");
                    }else{
                        if(nNum<=0) {
                            System.out.println("삭제하려는 고객이 없습니다.");
                        }else{
                            DeleteEx();
                        }
                    }*/
                    System.out.print(" 삭제를 원하는 고객번호를 입력하세요 : ");
                    nNum = Integer.parseInt(scan.next());
                    DeleteEx();
                    break;
                case "Q":
                    System.out.println("프로그램을 종합니다.");
                    System.exit(0);
                    break;
            }
        }
    }

    public static String Client_Info_View() {
        System.out.println("***************************************");
        System.out.println(" 고객 정보 UI 화면입니다.");
        System.out.println(" 고객정보 입력 : I ");
        System.out.println(" 고객정보 조회 : S ");
        System.out.println(" 이전고객 조회 : P ");
        System.out.println(" 이후고객 조회 : N ");
        System.out.println(" 고객정보 수정 : U ");
        System.out.println(" 고객정보 삭제 : D ");
        System.out.println(" 프로그램 종료 : Q");
        System.out.println("***************************************");
        System.out.print(" 메뉴를 선택하세요 : ");
        String str = scan.next().toUpperCase().trim();
        boolean chk = false;
        return str;
    }

    private static void InsertEx() {
        Connection conn = DBConnPro.dbConnPro();
        String tName = "";
        String tGender = "";
        String tBirthYear = "";
        String tEmail = "";
        System.out.print(" 고객 이름 입력 : ");
        tName = scan.next();
        System.out.print(" 성별 (M/F) : ");
        tGender = scan.next().toUpperCase();
        System.out.print(" 생년월일 : ");
        tBirthYear = scan.next();
        System.out.print(" E-Mail 입력 : ");
        tEmail = scan.next();

        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO CLIENT_INFO ");
        sb.append("( CL_ID, CL_NAME, CL_GENDER, CL_EMAIL, CL_BIRTHYEAR, CL_DATE) ");
        sb.append(" VALUES (nextval('member_seq'), ?, ?, ?, ?, SYSDATE() ) ");
        try{
            // query문을 mysql 데이터베이스로 전달 하면서 preparedstatement 객체를 생성하죠
            pstmt = conn.prepareStatement(sb.toString());
            // ?에 해당하는 값을 매핑한다/
            pstmt.setString(1, tName);
            pstmt.setString(2, tGender);
            pstmt.setString(3, tEmail);
            pstmt.setString(4, tBirthYear);
            // 쿼리를 실행
            // executeUpdate : INSERT문, UPDATE문, DELETE문에서 사용
            // executeQuery : SELECt문에서 사용
            pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void DeleteEx() {
        Connection conn = DBConnPro.dbConnPro();
        StringBuffer sb = new StringBuffer();
        if(nNum<=0) {
            System.out.println("삭제하려는 고객이 없습니다.");
        }else{
            prev_SearchEx("S");
           // int answer = JOptionPane.showConfirmDialog(null, "해당 고객을 정말로 삭제 하겟습니까?",
           //                                             "confirm", JOptionPane.YES_NO_OPTION);
           // if(answer == JOptionPane.YES_OPTION) {
                sb.append("DELETE FROM CLIENT_INFO ");
                sb.append(" WHERE CL_ID = ? ");
                PreparedStatement pstmt = null;
                try{
                    pstmt = conn.prepareStatement(sb.toString());
                    pstmt.setInt(1, nNum);
                    pstmt.executeUpdate();
                }catch(Exception e){
                    e.printStackTrace();
                }
            //}
        }
        nNum = 0;
    }

    private static void UpdateEx() {
        Connection conn = DBConnPro.dbConnPro();
        if(nNum==0 || nNum<=0){
            System.out.println("수정하고자 하는 고겍이 없습니다.");
        }else{
            prev_SearchEx("S");
            String tName = "";
            System.out.print(" 수정할 이름 : ");
            tName = scan.next();
            System.out.print(" 수정할 성별(M/F) : ");
            String tGender = scan.next();
            System.out.print(" 수정할 생년월일 : ");
            String tBirthYear = scan.next();
            System.out.print(" 수정할 e-Mail : ");
            String tEmail = scan.next();

            PreparedStatement pstmt = null;
            StringBuffer sb = new StringBuffer();
            sb.append(" UPDATE CLIENT_INFO SET ");
            sb.append( " CL_NAME = ?, CL_GENDER = ?, CL_EMAIL= ?, CL_BIRTHYEAR = ?, CL_ALTER_DATE = SYSDATE() ");
            sb.append(" WHERE CL_ID = ? ");
            try{
                // preparedstatement 객체가 만들어 지면서 query문을 mysql 데이터베이스 서버로 전달
                pstmt = conn.prepareStatement(sb.toString());
                // preparedstatement 객체의 ?에 해당하는 것에 값을 매핑시킴
                pstmt.setString(1, tName);
                pstmt.setString(2, tGender);
                pstmt.setString(3, tEmail);
                pstmt.setString(4, tBirthYear);
                pstmt.setInt(5, nNum);
                // 값이 매핑이 되어지면 매핑된 값을 가지고 query 실행
                pstmt.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void prev_SearchEx(String gubun) {
        Connection conn = DBConnPro.dbConnPro();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int cl_id = 0;
        String cl_name = "";
        String cl_gender = "";
        String cl_email = "";
        String cl_brithyear = "";
        String cl_Date = "";
        String cl_alter_date = "";
        if(nNum==0) { // 검색할 고객이 선택되었는지 여부 체크
            System.out.println("고객이 없습니다. ");
        }else{
            if(gubun.equals("P")) {
                nNum = nNum - 1;
            }

            if(gubun.equals("N")) {
                nNum = nNum + 1;
            }

            if(gubun.equals("S")) {
                nNum = nNum;
            }
        }

        sql = "SELECT * FROM CLIENT_INFO WHERE CL_ID = ? ";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, nNum);
            rs = pstmt.executeQuery();
            if(rs.isBeforeFirst()==false) System.out.println("찾으시는 고객이 없습니다.");
            while(rs.next()) {
                cl_id = rs.getInt("CL_ID");
                nNum = cl_id;
                cl_name = rs.getString("CL_NAME");
                cl_gender = rs.getString("CL_GENDER");
                cl_email = rs.getString("CL_EMAIL");
                cl_brithyear = rs.getString("CL_BIRTHYEAR");
                cl_Date = rs.getString("CL_DATE");
                cl_alter_date = rs.getString("CL_ALTER_DATE");

                System.out.println("고객명 : " + cl_name);
                System.out.println(" 성별 : " + cl_gender);
                System.out.println(" 이메일 : " + cl_email);
                System.out.println(" 생년월일 : " + cl_brithyear);
                System.out.println(" 등록일자 : " + cl_Date);
                System.out.println(" 고객정보 수정일자 : " + cl_alter_date);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void SearchEx() {
        Connection conn = DBConnPro.dbConnPro();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM CLIENT_INFO ORDER BY CL_ID DESC";

        try{
            // preparedstatement 객체를 생성하면서 query문을 mysql 데이터베이스 서버로 전달
            pstmt = conn.prepareStatement(sql);
            // mysql 데이터베이스는 전달받은 query문을 실행
            // 그런데 SELECT query이기 때문에 resultset 객체가 만들어 진다.
            // resultset 객체는 조회된 결과값 덩어리이다.
            // 결과값 덩어리는 레코드(행)의 모음
            // 또한 레코드를 구성하는 값은 컬럼(열)이다
            rs = pstmt.executeQuery();
            if(rs.isBeforeFirst()==false) System.out.println("찾으시는 고객 정보가 없습니다.");
            // resultset에서 커서가 가리키는 레코드의 컬럼값을 읽어온다.
            while(rs.next()) {
                int cl_id = rs.getInt("CL_ID");
                nNum = cl_id;
                rowCount += 1;
                System.out.println(" 고객번호 : " + nNum);
                System.out.println(" 고객명 : " + rs.getString("CL_NAME"));
                System.out.println(" 성별 : " + rs.getString("CL_GENDER"));
                System.out.println(" 이메일 : " + rs.getString("CL_EMAIL"));
                System.out.println(" 생년월일 : " + rs.getString("CL_BIRTHYEAR"));
                System.out.println(" 등록일 : " + rs.getString("CL_DATE"));
                System.out.println();
            }
            System.out.println("전체 [ " + rowCount + " 명]의 고객이 검색되었습니다.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
