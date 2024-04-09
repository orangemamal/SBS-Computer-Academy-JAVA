package com.checkingAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import dbcommon.mybatis.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class CADAO {
    private static SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();

    // 2.회원가입
    public int insertMember(CADTO dto) {
        int result = 0;
        try {
            SqlSession sqlSession = factory.openSession();
            result = sqlSession.insert("cadaoSQL.cadaoMemberInsert", dto);
            sqlSession.commit();
            sqlSession.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    // 3.로그인
    public String login(CADTO dto) {
        String login = null;
        try {
            SqlSession sqlSession = factory.openSession();
            dto = sqlSession.selectOne("cadaoSQL.cadaoMemberLogin", dto);
            sqlSession.close();
            if (dto != null)
                login = dto.getId();
            else
                login = "";
        } catch (Exception e) {
            System.out.println(e.toString());
            return login;
        }
        return login;
    }

    //4.회원탈퇴
    public int deleteMember(CADTO dto) {
        int result = 0;
        try {
            SqlSession sqlSession = factory.openSession();
            result = sqlSession.delete("cadaoSQL.cadaoMemberDelete", dto);
            sqlSession.commit();
            sqlSession.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }


    //5.계좌/카드 등록
    public int insertAsset(CADTO dto, int BC){
        int result=0;
        SqlSession sqlSession = factory.openSession();
        if(BC==1){
            try {
                CADTO cadto = sqlSession.selectOne("cadaoSQL.cadaoAccountNicknameSelect", dto);
                if (cadto != null) {
                    System.out.println();
                    System.out.println("존재하는 별칭입니다. 다시 입력해주세요.");
                    System.out.println();
                } else{
                    result = sqlSession.insert("cadaoSQL.cadaoAssetAccountInsert", dto);
                    sqlSession.commit();
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            sqlSession.close();
        }
        if(BC==2){
            try {
                CADTO cadto = sqlSession.selectOne("cadaoSQL.cadaoCardNicknameSelect", dto);
                if (cadto != null) {
                    System.out.println();
                    System.out.println("존재하는 별칭입니다. 다시 입력해주세요.");
                    System.out.println();
                } else{
                    result = sqlSession.insert("cadaoSQL.cadaoAssetCardInsert", dto);
                    sqlSession.commit();
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            sqlSession.close();
        }
        return result;
    }

    //6.계좌/카드 삭제
    public int deleteAsset(CADTO dto, int BC){
        int result=0;
        String sql="";
        try {
            if(BC==1)
                sql = "cadaoSQL.cadaoAssetAccountDelete";
            if(BC==2)
                sql = "cadaoSQL.cadaoAssetCardDelete";
            SqlSession sqlSession = factory.openSession();
            result = sqlSession.delete(sql, dto);
            sqlSession.commit();
            sqlSession.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    //7. 계좌/카드 조회
    public List<CADTO> selectAsset(String id, int BC) {
        List<CADTO> lists = new ArrayList<CADTO>();
        SqlSession sqlSession = factory.openSession();
        try {
            if(BC==1){
                lists = sqlSession.selectList("cadaoSQL.cadaoAssetAccountSelect", id);
            }
            if(BC==2){
                lists = sqlSession.selectList("cadaoSQL.cadaoAssetCardSelect", id);
            }
            sqlSession.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return lists;
    }

    //4.1 가계부조회 - 일별
    public void selectday(String id, String date) {
        List<CADTO> lists = null;
        SqlSession sqlSession = factory.openSession();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("cadate", date);
        int deposit = 0;
        int withdraw = 0;
        try {
            lists = sqlSession.selectList("cadaoSQL.cadaDayAllSelect", map);
            if(lists.size() == 0){
                System.out.println("입력된 입출금 내역이 존재하지 않습니다.");
                System.exit(0);
            }
            //출금총액 구하기
            withdraw = sqlSession.selectOne("cadaoSQL.cadaoDrawSumAmountDaySelect", map);
            //입금총액 구하기
            deposit = sqlSession.selectOne("cadaoSQL.cadaoAccountDepositSumAmountDaySelect", map);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if(lists != null){
            System.out.println("---------------------------------------------------");
            System.out.println("  아이디   결제일   계좌별칭    금  액    카테고리");
            System.out.println("---------------------------------------------------");
            Iterator<CADTO> it = lists.iterator();
            while (it.hasNext()) {
                CADTO dto = it.next();
                dto.print();
            }
            System.out.println("\n------------------------------------------------");
            System.out.println("\t"+date +"의 가계부 총계");
            System.out.println("지출 :" + withdraw);
            System.out.println("수입 :" + deposit);
            System.out.println("수입-지출 :" + (deposit+withdraw));
            System.out.println("------------------------------------------------\n");
        }
    }

    //4.2 가계부조회 - 기간별
    public void selectweek(String id, String date1, String date2) {
        List<CADTO> lists = null;
        SqlSession sqlSession = factory.openSession();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("stcadate", date1);
        map.put("edcadate", date2);
        int deposit = 0;
        int withdraw = 0;
        try {
            lists = sqlSession.selectList("cadaoSQL.cadaWeekAllSelect", map);
            if(lists.size() == 0){
                System.out.println("입력된 입출금 내역이 존재하지 않습니다.");
                System.exit(0);
            }
            //출금총액 구하기
            withdraw = sqlSession.selectOne("cadaoSQL.cadaoDrawSumAmountWeekSelect", map);

            //입금총액 구하기
            deposit = sqlSession.selectOne("cadaoSQL.cadaoAccountDepositSumAmountWeekSelect", map);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if(lists != null){
            System.out.println("---------------------------------------------------");
            System.out.println("  아이디   결제일   계좌별칭    금  액    카테고리");
            System.out.println("---------------------------------------------------");
            Iterator<CADTO> it = lists.iterator();
            while (it.hasNext()) {
                CADTO dto = it.next();
                dto.print();
            }
            System.out.println("\n----------------------------------------");
            System.out.println(date1+" ~ "+date2+" 의 가계부 총계");
            System.out.println("지출 :" + withdraw);
            System.out.println("수입 :" + deposit);
            System.out.println("수입-지출 :" + (deposit+withdraw));
            System.out.println("----------------------------------------\n");
        }

    }

    //4.3 가계부조회 - 월별
    public void selectmonth(String id, String year, String month) {
        List<CADTO> lists = null;
        SqlSession sqlSession = factory.openSession();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("year", year);
        map.put("month", month);
        int deposit = 0;
        int withdraw = 0;

        try {
            lists = sqlSession.selectList("cadaoSQL.cadaMonthAllSelect", map);
            if(lists.size() == 0){
                System.out.println("입력된 입출금 내역이 존재하지 않습니다.");
                System.exit(0);
            }
            //입금총액 구하기
            deposit = sqlSession.selectOne("cadaoSQL.cadaoAccountDepositSumAmountMonthSelect", map);
            //출금총액 구하기
            withdraw = sqlSession.selectOne("cadaoSQL.cadaoDrawSumAmountMonthSelect", map);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if(lists != null){
            System.out.println("---------------------------------------------------");
            System.out.println("  아이디   결제일   계좌별칭    금  액    카테고리");
            System.out.println("---------------------------------------------------");
            Iterator<CADTO> it = lists.iterator();
            while (it.hasNext()) {
                CADTO dto = it.next();
                dto.print();
            }
            System.out.println("\n----------------------------------------");
            System.out.println("\t"+year +"년 "+ month +"월의 가계부 총계");
            System.out.println("지출 :" + withdraw);
            System.out.println("수입 :" + deposit);
            System.out.println("수입-지출 :" + (deposit+withdraw));
            System.out.println("----------------------------------------\n");
        }
    }

    //4.4 가계부조회 - 카테고리별
    public void selectcategory(String id, String category) {
        List<CADTO> lists = null;
        SqlSession sqlSession = factory.openSession();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("category", "%"+category+"%");
        int deposit = 0;
        int withdraw = 0;
        try {
            lists = sqlSession.selectList("cadaoSQL.cadaCategoryAllSelect", map);
            if(lists.size() == 0){
                System.out.println("입력된 입출금 내역이 존재하지 않습니다.");
                System.exit(0);
            }
            //입금총액 구하기
            deposit = sqlSession.selectOne("cadaoSQL.cadaoAccountDepositSumAmountCategorySelect", map);
            //출금총액 구하기
            withdraw = sqlSession.selectOne("cadaoSQL.cadaoDrawSumAmountCategorySelect", map);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if(lists != null){
            System.out.println("---------------------------------------------------");
            System.out.println("  아이디   결제일   계좌별칭    금  액    카테고리");
            System.out.println("---------------------------------------------------");
            Iterator<CADTO> it = lists.iterator();
            while (it.hasNext()) {
                CADTO dto = it.next();
                dto.print();
            }
            System.out.println("\n----------------------------------------");
            System.out.println("\t"+category +"의 가계부 총계");
            System.out.println("지출 :" + withdraw);
            System.out.println("수입 :" + deposit);
            System.out.println("수입-지출 :" + (deposit+withdraw));
            System.out.println("----------------------------------------\n");
        }
    }

    //4.5.1 가계부조회 - 입금달력
    public void selectcalendar1(String id, String year, String month) {
        List<CADTO> lists = null;
        int i, y, m, w, lastday;
        y = Integer.parseInt(year);
        m = Integer.parseInt(month);
        Calendar now = Calendar.getInstance();
        int deposit[] = new int[32];
        now.set(y, m - 1, 1);
        w = now.get(Calendar.DAY_OF_WEEK);
        lastday = now.getActualMaximum(Calendar.DATE);
        SqlSession sqlSession = factory.openSession();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("year", year);
        map.put("month", month);
        try {
            //입금총액 구하기
            lists = sqlSession.selectList("cadaoSQL.cadaCalendarDepositSelect", map);
            if(lists.size() != 0) {
                Iterator<CADTO> it = lists.iterator();
                while (it.hasNext()) {
                    CADTO dto = it.next();
                    String cnt = dto.getCadate();
                    int idx = Integer.parseInt(cnt);
                    deposit[idx] = dto.getAmount();
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // 출력
        System.out.println(" ◎ "+ y + "년 " + m + "월 가계부 ◎ ");
        System.out.println("\n     일                  월                  화                  수                     목                  금                  토");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");

        for (i = 1; i < w; i++) {
            System.out.print("                     ");//1일자 칸 맞추기
        }

        for (i = 1; i <= lastday; i++) {
            System.out.printf("%4d (입금%8d원)", i, deposit[i]);
            w++;
            if (w % 7 == 1) { //토요일 줄바꿈
                System.out.println("\n");
            }
        }

        if (w % 7 != 1) {
            System.out.println("\n");//말일자 줄바꿈
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");

    }

    //4.5.1 가계부조회 - 출금달력
    public void selectcalendar2(String id, String year, String month) {
        List<CADTO> lists = null;
        int i, y, m, w, lastday;
        y = Integer.parseInt(year);
        m = Integer.parseInt(month);
        Calendar now = Calendar.getInstance();
        int withdraw[] = new int[32];
        now.set(y, m - 1, 1);
        w = now.get(Calendar.DAY_OF_WEEK);
        lastday = now.getActualMaximum(Calendar.DATE);
        SqlSession sqlSession = factory.openSession();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("year", year);
        map.put("month", month);
        try {
            //출금총액 구하기
            lists = sqlSession.selectList("cadaoSQL.cadaCalendarWithDrawSelect", map);
            if(lists.size() != 0) {
                Iterator<CADTO> it = lists.iterator();
                while (it.hasNext()) {
                    CADTO dto = it.next();
                    String cnt = dto.getCadate();
                    int idx = Integer.parseInt(cnt);
                    withdraw[idx] = dto.getAmount();
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // 출력
        System.out.println(" ◎ "+ y + "년 " + m + "월 가계부 ◎ ");
        System.out.println("\n     일                  월                  화                  수                     목                  금                  토");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");

        for (i = 1; i < w; i++) {
            System.out.print("                     ");//1일자 칸 맞추기
        }

        for (i = 1; i <= lastday; i++) {
            System.out.printf("%4d (출금%8d원)", i, withdraw[i]);
            w++;
            if (w % 7 == 1) { //토요일 줄바꿈
                System.out.println("\n");
            }
        }

        if (w % 7 != 1) {
            System.out.println("\n");//말일자 줄바꿈
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");

    }

    public int outputCardCA(CADTO dto) {
        SqlSession sqlSession = factory.openSession();
        int result = 0;
        try {
            //카드번호 조회
            String cardno = sqlSession.selectOne("cadaoSQL.cadaoCardNoSelect", dto);
            dto.setCano(cardno);
            //카드 지출
            result = sqlSession.insert("cadaoSQL.cadaoCardWithDrawInsert", dto);
            sqlSession.commit();
            //카드 지출
            result = sqlSession.update("cadaoSQL.cadaoCardUpdate", dto);
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public int outputAccountCA(CADTO dto) {
        SqlSession sqlSession = factory.openSession();
        int result = 0;
        try {
            //계좌번호 조회
            String accno = sqlSession.selectOne("cadaoSQL.cadaoAccNoSelect", dto);
            dto.setAccno(accno);
            //계좌 지출
            result = sqlSession.insert("cadaoSQL.cadaoAccountWithDrawInsert", dto);
            sqlSession.commit();
            //계좌 지출
            result = sqlSession.update("cadaoSQL.cadaoAccountUpdate", dto);
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public int inputAccountCA(CADTO dto) {
        SqlSession sqlSession = factory.openSession();
        int result = 0;
        try {
            //계좌번호 조회
            String accno = sqlSession.selectOne("cadaoSQL.cadaoAccNoSelect", dto);
            dto.setAccno(accno);
            //계좌 입금
            result = sqlSession.insert("cadaoSQL.cadaoAccountDepositInsert", dto);
            sqlSession.commit();
            result = sqlSession.update("cadaoSQL.cadaoAccountUpdate", dto);
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public int deleteOutAccountCA(CADTO dto) {
        SqlSession sqlSession = factory.openSession();
        int result = 0;
        try {
            result = sqlSession.selectOne("cadaoSQL.cadaoAccountWithDrawCountSelect", dto);
            if(result == 1){
                result = sqlSession.delete("cadaoSQL.cadaoAccountWithDrawDelete", dto);
                sqlSession.commit();
                result = sqlSession.update("cadaoSQL.cadaoAccountPlusUpdate", dto);
                sqlSession.commit();
            }//
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public int deleteInAccountCA(CADTO dto) {
        SqlSession sqlSession = factory.openSession();
        int result = 0;
        Connection conn = DBConn.getConnection();
        PreparedStatement pstmt = null;
        String sql;

        try {
            result = sqlSession.selectOne("cadaoSQL.cadaoAccountDepositCountSelect", dto);
            if(result == 1){
                result = sqlSession.delete("cadaoSQL.cadaoAccountDepositDelete", dto);
                sqlSession.commit();
                result = sqlSession.update("cadaoSQL.cadaoAccountMinusUpdate", dto);
                sqlSession.commit();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public int deleteInCardCA(CADTO dto) {
        int result = 0;
        return result;
    }

    //계좌이체
    public int changeAsset(String id, String str, String str2, int money){
        int result = 0;
        return result;
    }

    //계좌이체
    public int cardPayment(String id, String str, String str2, int money){
        int result = 0;
        return result;
    }
}
