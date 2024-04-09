package com.checkingAccount;

import java.util.Scanner;

public class CADLogin {

    public static void main(String[] args) throws AuthenException {
        Scanner sc = new Scanner(System.in);
        CAD cad = new CAD();
        int ch;
        while(true) {
            String id = cad.login();
            while(!id.equals("")){
                do{
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    System.out.println("	1.계좌/카드 등록         2.계좌/카드 삭제           3.가계부 작성           4.가계부 삭제    ");
                    System.out.println("	5.계좌이체               6.가계부 조회              7.계좌/카드 조회        8.카드대금결제   ");
                    System.out.println("	9.로그아웃");
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    System.out.print("▶");
                    ch = sc.nextInt();
                }while(ch<1 || ch>9);
                System.out.println();
                switch (ch) {
                    case 1:
                        cad.insertAset(id);
                        break;
                    case 2:
                        cad.deleteAsset(id);
                        break;
                    case 3:
                        cad.inputCAData(id);
                        break;
                    case 4:
                        cad.deleteCAData(id);
                        break;
                    case 5:
                        cad.changeAsset(id);
                        break;
                    case 6:
                        cad.selectaccount(id);
                        break;
                    case 7:
                        cad.selectAsset(id);
                        break;
                    case 8:
                        cad.cardPayment(id);
                        break;
                    case 9:
                        CADMain.main(args);
                        break;
                }
            }
            if(id.equals(""))
                System.out.println("로그인에 실패하였습니다.");
            CADMain.main(args);
        }
    }
}
