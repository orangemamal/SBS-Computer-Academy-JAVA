package com.checkingAccount;

import java.util.Scanner;
public class CADMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int ch;
        CAD cad = new CAD();
        try {
            while (true) {
                do {
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    System.out.println("      		                                 가계부                                                   ");
                    System.out.println("	1.가계부 소개        2.회원가입        3.로그인         4.회원탈퇴         5.종료  ");
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    System.out.print("▶");
                    ch = sc.nextInt();
                } while (ch < 1 || ch > 5);
                switch (ch) {
                    case 1:
                        cad.CAInformation();
                        break;
                    case 2:
                        cad.insertMember();
                        break;
                    case 3:
                        CADLogin.main(args);
                        break;
                    case 4:
                        cad.deleteMember();
                        break;
                    case 5:
                        //DBConn.close();
                        //Ending.main(args);
                        System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
