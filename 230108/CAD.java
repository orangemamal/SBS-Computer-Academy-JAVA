package com.checkingAccount;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CAD {

    Scanner sc = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    CADAO dao = new CADAO();
    CADException cad = new CADException();
    Money money = new Money();
    Picture pic = new Picture();

    // 1. 가계부 소개
    public void CAInformation() {
        System.out.println("가계부");
    }

    // 2.회원가입
    public void insertMember() throws AuthenException {
        String pw2 = null;
        String str = null;
        boolean id = true;
        boolean pw = true;
        boolean name = true;
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("             				        회원가입");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        try {
            CADTO dto = new CADTO();
            do {
                try {
                    System.out.print("아이디:");
                    str = (sc.next());
                    cad.idFormat(str);
                    id = false;
                    dto.setId(str);
                } catch (AuthenException e) {
                    System.out.println(e.toString());
                }

            } while (id);
            do {
                try {
                    System.out.print("비밀번호:");
                    dto.setPwd(sc.next());
                    System.out.print("비밀번호 확인:");
                    pw2 = sc.next();
                    cad.pwCheck(dto.getPwd(), pw2);
                    pw = false;
                } catch (AuthenException e) {
                    System.out.println(e.toString());
                }
            } while (pw);
            do {
                try {
                    System.out.print("이름:");
                    str = sc.next();
                    cad.nameCheck(str);
                    name = false;
                    dto.setName(str);
                } catch (AuthenException e) {
                    System.out.println(e.toString());
                }
            } while (name);
            int result = dao.insertMember(dto);
            if (result == 1) {
                System.out.println();
                System.out.println("****성공적으로 가입이 되었습니다!****");
                System.out.println();
            } else
                System.out.println("회원가입에 실패했습니다");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // 3.로그인
    public String login() {
        String checkid = null;
        CADTO dto = new CADTO();
        try {
            System.out.print("아이디:");
            dto.setId(sc.next());
            System.out.print("비밀번호:");
            dto.setPwd(sc.next());
            checkid = dao.login(dto);
        } catch (Exception e) {
            System.out.println(e.toString());
            return checkid;
        }
        return checkid;
    }

    // 4.회원탈퇴
    public void deleteMember() {
        CADTO dto = new CADTO();
        try {
            System.out.println("삭제할 정보를 입력하세요.");
            System.out.print("id: ");
            dto.setId(sc.next());
            System.out.print("pwd: ");
            dto.setPwd(sc.next());
            int result = dao.deleteMember(dto);
            if (result == 1) {
                System.out.println("회원탈퇴가 성공적으로 진행되었습니다!");
            } else {
                System.out.println("회원탈퇴가 진행되지 못하였습니다! 회원정보를 확인해주세요.");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // 5.계좌/카드 등록
    public void insertAset(String id) throws AuthenException {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("                                        ◈카드/계좌 등록◈");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        try {
            int result = 0;
            int BC = 0;
            int y = 0;
            String str = null;
            boolean accDiv = true;
            boolean accno = true;
            boolean cardno = true;
            boolean accAmount = true;
            boolean nickname = true;
            CADTO dto = new CADTO();
            // 계좌등록 / 카드등록 확인
            dto.setId(id);
            do {
                System.out
                        .println("계좌 등록을 원하시면 [1]을, 카드 등록을 원하시면 [2]를 입력해주세요.");
                System.out.print("▶");
                BC = sc.nextInt();
            } while (BC < 1 || BC > 2);
            System.out.println();
            switch (BC) {
                case 1:
                    System.out.println("등록하시는 계좌의 유형을 입력해주세요.");
                    System.out.println("유형은 [입출금],[예/적금] 두가지가 가능합니다.");
                    do {
                        try {
                            System.out.print("▶");
                            dto.setAccDiv(sc.next());
                            cad.accdivCheck(dto.getAccDiv());
                            accDiv = false;
                            dto.setAccDiv(dto.getAccDiv());
                        } catch (AuthenException e) {
                            System.out.println(e.toString());
                        }
                    } while (accDiv);

                    System.out.println();
                    System.out.println("은행을 입력해주세요");
                    System.out.print("▶");
                    dto.setBank(sc.next());
                    System.out.println();
                    System.out.println("예금주를 입력해주세요");
                    System.out.print("▶");
                    dto.setAccName(sc.next());
                    System.out.println();
                    System.out.println("계좌번호를 입력해주세요.");
                    System.out.println("계좌번호는 - 없이 숫자로만 입력 가능합니다.");
                    do {
                        try {
                            System.out.print("▶");
                            str = cad.numberCheck2(sc.next());
                            dto.setAccno(str);
                            accno = false;
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    } while (accno);

                    System.out.println();
                    System.out.println("현재 계좌에 있는 금액을 입력해주세요.");
                    System.out.println("금액은 숫자로만 입력 가능합니다.");
                    do {
                        try {
                            System.out.print("▶");
                            y = cad.numberCheck1(sc.next());
                            dto.setAccAmount(y);
                            accAmount = false;
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    } while (accAmount);

                    System.out.println();
                    do {
                        try {
                            System.out.println("계좌 간편이름을 작성해주세요");
                            System.out.print("▶");
                            dto.setNickname(sc.next());
                            cad.accNickCheck(dto.getNickname());
                            nickname = false;
                            dto.setBankNick(dto.getNickname());
                        } catch (AuthenException e) {
                            System.out.println(e.toString());
                        }
                    } while (nickname);

                    result = dao.insertAsset(dto, BC);
                    if (result == 1) {
                        System.out.println();
                        System.out.println("****성공적으로 계좌등록이 되었습니다!****");
                        System.out.println();
                    } else
                        System.out.println("계좌등록에 실패했습니다");
                    break;
                case 2:
                    System.out.println("카드번호를 입력해주세요.");
                    System.out.println("카드번호는 - 없이 숫자로만 입력 가능합니다.");
                    do {
                        try {
                            System.out.print("▶");
                            str = cad.numberCheck2(sc.next());
                            dto.setCardno(str);
                            cardno = false;
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    } while (cardno);

                    System.out.println();
                    System.out.println("카드 은행을 입력해주세요");
                    System.out.print("▶");
                    dto.setCard(sc.next());
                    System.out.println();
                    System.out.println("명의주를 입력해주세요");
                    System.out.print("▶");
                    dto.setCardName(sc.next());
                    System.out.println();
                    do {
                        try {
                            System.out.println("카드 간편이름을 작성해주세요");
                            System.out.print("▶");
                            dto.setNickname(sc.next());
                            cad.accNickCheck(dto.getNickname());
                            nickname = false;
                            dto.setCardNick(dto.getNickname());
                        } catch (AuthenException e) {
                            System.out.println(e.toString());
                        }
                    } while (nickname);

                    result = dao.insertAsset(dto, BC);
                    if (result == 1) {
                        System.out.println();
                        System.out.println("****성공적으로 카드등록이 되었습니다!****");
                        System.out.println();
                    } else
                        System.out.println("카드등록에 실패했습니다");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // 6.계좌/카드 삭제
    public void deleteAsset(String id) {
        try {
            String CAname;
            String CAno;
            CADTO dto = new CADTO();
            dto.setId(id);
            int BC = 0;
            int result = 0;
            do {
                System.out
                        .println("계좌 삭제를 원하시면 [1]을, 카드 삭제를 원하시면 [2]를 입력해주세요.");
                System.out.print("▶");
                BC = sc.nextInt();
            } while (BC < 1 || BC > 2);
            System.out.println("삭제할 정보를 입력하세요.");
            System.out.print("사용하는 카드/계좌 은행: ");
            dto.setCaname(sc.next());
            System.out.print("사용하는 카드/계좌 번호: ");
            dto.setCano(sc.next());
            result = dao.deleteAsset(dto, BC);
            if (result == 1) {
                System.out.println("삭제으로 진행되었습니다!");
            } else {
                System.out.println("삭제가 진행되지 못하였습니다! 계좌/카드 정보를 확인해주세요.");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
    // 8.계좌/카드 조회
    public void selectAsset(String id) {
        System.out.println("   카드/계좌            은행           예금주           계좌/카드번호        금액           간편이름");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        // 계좌정보 출력
        List<CADTO> lists = dao.selectAsset(id, 1);
        Iterator<CADTO> it = lists.iterator();
        while (it.hasNext()) {
            CADTO dto = it.next();
            dto.printAcc();
        }
        // 카드정보 출력
        lists = dao.selectAsset(id, 2);
        it = lists.iterator();
        while (it.hasNext()) {
            CADTO dto = it.next();
            dto.printCard();
        }
        System.out.println();
    }

    // 가계부 조회
    public void selectaccount(String id) {
        int ch = 0;
        int dal = 0;
        String date1 = "";
        String date2 = "";
        String category = "";
        boolean categoryCheck = true;
        boolean flag=true;
        try {
            do {
                System.out.println("가계부 조회 유형을 선택하세요.");
                System.out.println("1.일별 2.기간별 3.월별 4.유형별 5.달력 ");
                System.out.print("입력 ▶ ");
                ch = sc.nextInt();
                switch (ch) {
                    case 1:
                        // 4.1 일별
                        do{
                            try {
                                System.out.print("조회할 일자를 입력하세요: ");
                                date1 = sc.next();
                                cad.dateCheck(date1);
                                dao.selectday(id, date1);
                                flag=false;
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }while(flag);
                        break;
                    case 2:
                        // 4.2 기간별
                        do{
                            try {
                                System.out.println("조회할 기간을 입력하세요. ");
                                System.out.print("시작일: ");
                                date1 = sc.next();
                                cad.dateCheck(date1);
                                System.out.print("종료일: ");
                                date2 = sc.next();
                                cad.dateCheck(date2);
                                dao.selectweek(id, date1, date2);
                                flag=false;
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }while(flag);
                        break;
                    case 3:
                        // 4.3 월별
                        do{
                            try {
                                System.out.println("조회할 년/월을 입력하세요.");
                                System.out.print("연도: ");
                                date1 = sc.next();
                                cad.yearCheck(date1);
                                System.out.print("월: ");
                                date2 = sc.next();
                                cad.monthCheck(date2);
                                flag=false;
                                dao.selectmonth(id, date1, date2);
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }while(flag);
                        break;
                    case 4:
                        // 4.4 카테고리별
                        System.out.println("조회할 카테고리를 입력하세요.");
                        System.out.println("※카테고리 : [자기계발] [생활비] [식대] [교육] [문화] [미용] [교통] [쇼핑] [월급]");
                        do{
                            try {
                                System.out.print("카테고리: ");
                                category = sc.next();
                                cad.categoryCheck(category);
                                categoryCheck = false;
                            } catch (AuthenException e) {
                                System.out.println(e.toString());
                            }
                        } while(categoryCheck);
                        dao.selectcategory(id, category);
                        break;
                    case 5:
                        // 4.5 달력
                        do{
                            System.out.println("달력을 조회할 년도와 월을 입력하세요.");
                            System.out.print("연도: ");
                            date1 = sc.next();
                            cad.yearCheck(date1);
                            System.out.print("월: ");
                            date2 = sc.next();
                            cad.monthCheck(date2);
                            System.out.println("입출금 달력 중 무엇을 조회하시겠습니까? [1:입금], [2:출금], [3:메인화면으로 돌아가기] ");
                            dal = sc.nextInt();
                            if(1 == dal){
                                dao.selectcalendar1(id, date1, date2); break;
                            }else if(2 == dal){
                                dao.selectcalendar2(id, date1, date2); break;
                            }else if(3 == dal){
                                break;
                            }else{
                                System.out.println("다시 입력하여 주세요. ");
                            }
                        }while(true);
                }
            } while (ch > 5 || ch < 1);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void deleteCAData(String id) { // 지훈 가계부 삭제
        int se1, se2;
        int result;
        do {
            System.out
                    .println("카드사용 가계부 삭제를 원하시면 [1]을, 계좌사용 가계부 삭제를 원하시면 [2]을,");
            System.out.println("가계부 삭제를 취소하시려면 [3]을 입력해주세요.");
            System.out.print("▶");
            se1 = sc.nextInt();
        } while (se1 < 1 || se1 > 3);
        System.out.println();
        switch (se1) {
            case 1:
                System.out.println("카드사용 가계부 삭제를 진행합니다.");
                System.out.println("정보를 입력해주세요.");
                System.out.print("▶");
                result = money.inputMoney(id,1);
                if (result != 0) {
                    System.out.println("삭제 완료!!");
                } else {
                    System.out.println("해당 내용으로 작성된 가계부가 존재하지 않습니다.");
                }
                break; // 카드 사용 삭제
            case 2:
                do {
                    System.out.println("계좌입금 내역 가계부 삭제를 원하시면 [1]을, 계좌출금 가계부 삭제를 원하시면 [2]을,");
                    System.out.println("가계부 삭제를 취소하시려면 [3]을 입력해주세요.");
                    System.out.print("▶");
                    se2 = sc.nextInt();
                } while (se2 < 1 || se2 > 3);
                switch (se2) {
                    case 1:
                        result = money.inputMoney(id,2);
                        if (result != 0) {
                            System.out.println("삭제 완료!!");
                        } else {
                            System.out.println("해당 내용으로 작성된 가계부가 존재하지 않습니다.");
                        }
                        break;
                    case 2:
                        result = money.inputMoney(id,3);
                        if (result != 0) {
                            System.out.println("삭제 완료!!");
                        } else {
                            System.out.println("해당 내용으로 작성된 가계부가 존재하지 않습니다.");
                        }
                        break;
                    case 3:
                        break;
                }
                break; // 계좌 사용 삭제
            case 3:
                break;// 삭제 나가기
        }
    }

    public void inputCAData(String id) {
        int se1, se2;
        int result;
        do {
            System.out.println("지출 가계부를 원하시면 [1]을, 수입 가계부를 원하시면 [2]을,");
            System.out.println("가계부 입력을 취소하시려면 [3]을 입력해주세요.");
            System.out.print("▶");
            se1 = sc.nextInt();
        } while (se1 < 1 || se1 > 3);
        System.out.println();
        switch (se1) {// 지출 수입 선택
            case 1:// 지출 가계부 입력
                do {
                    System.out.println("카드 지출을 원하시면 [1]을, 계좌 지출을 원하시면 [2]을,");
                    System.out.println("가계부 입력을 취소하시려면 [3]을 입력해주세요.");
                    System.out.print("▶");
                    se2 = sc.nextInt();
                } while (se2 < 1 || se2 > 3);
                switch (se2) {// 카드지출
                    case 1:
                        result = money.inputMoney(id,4);
                        if (result != 0) {
                            System.out.println("작성 완료!!");
                        } else {
                            System.out.println("작성 실패!!");
                        }
                        break;
                    case 2: // 계좌 지출
                        result = money.inputMoney(id,5);

                        if (result != 0) {
                            System.out.println("작성 완료!!");
                        } else {
                            System.out.println("작성 실패!!");
                        }
                        break;
                    case 3:
                        break; // 지출 가계부 끝
                }
                break;
            case 2:// 수입입력
                result = money.inputMoney(id,6);
                if (result != 0) {
                    System.out.println("작성 완료!!");
                } else {
                    System.out.println("작성 실패!!");
                }
            case 3:
                break;
        }
    }// 가계부 작성 끝

    //계좌이체
    public void changeAsset(String id) {
        try{
            String str;
            String str2;
            int money;
            System.out.print("출금통장 : ");
            str = sc.next();
            System.out.print("입금통장 : ");
            str2 = sc.next();
            System.out.print("금액 : ");
            money = sc.nextInt();
            int result = dao.changeAsset(id, str, str2, money);
            if(result==4)
                System.out.println("계좌이체에 성공하셨습니다.");
            else
                System.out.println("계좌이체에 실패하셨습니다. 통장정보를 확인해주세요");
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    //카드결제
    public void cardPayment(String id) {
        try {
            String str;
            String str2;
            int money;
            System.out.print("결제 카드[간편이름] : ");
            str = sc.next();
            System.out.print("결제 통장[간편이름] : ");
            str2 =sc.next();
            System.out.print("결제금액 : ");
            money = sc.nextInt();
            int result = dao.cardPayment(id, str, str2, money);
            if(result==4)
                System.out.println("카드결제에 성공하셨습니다.");
            else
                System.out.println("카드결제에 실패하셨습니다. 통장정보를 확인해주세요");
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
