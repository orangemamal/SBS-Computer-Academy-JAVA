package com.checkingAccount;

import java.util.Scanner;
public class Money {
    public int inputMoney(String id, int i){
        Scanner sc = new Scanner(System.in);
        CADException cad = new CADException();
        boolean date=true;
        boolean won = true;
        boolean category=true;
        int x;
        int result=0;
        try {
            CADAO dao = new CADAO();
            CADTO dto = new CADTO();
            dto.setId(id);
            do{
                try {
                    System.out.print("날짜: ");
                    dto.setCADate(sc.next());
                    cad.dateCheck(dto.getCADate());
                    date = false;
                } catch (AuthenException e) {
                    System.out.println(e.toString());
                }
            } while(date);

            do{
                try{
                    System.out.print("금액: ");
                    x = cad.numberCheck1(sc.next());

                    dto.setCAAmount(x);
                    dto.setAmount(x);
                    won = false;

                } catch (AuthenException e) {
                    System.out.println(e.toString());
                }
            } while(won);

            System.out.println("※카테고리 : [자기계발] [생활비] [식대] [교육] [문화] [미용] [교통] [쇼핑] [월급]");
            do{
                try {
                    System.out.print("카테고리: ");
                    dto.setCategory(sc.next());
                    cad.categoryCheck(dto.getCategory());
                    category = false;
                } catch (AuthenException e) {
                    System.out.println(e.toString());
                }
            } while(category);

            System.out.print("카드/계좌 별칭: ");
            dto.setCANick(sc.next());
            dto.setNickname(dto.getCANick());
            if(i==1)
                result = dao.deleteInCardCA(dto);
            else if(i==2)
                result = dao.deleteInAccountCA(dto);
            else if(i==3)
                result = dao.deleteOutAccountCA(dto);
            else if(i==4)
                result = dao.outputCardCA(dto);
            else if(i==5)
                result = dao.outputAccountCA(dto);
            else
                result = dao.inputAccountCA(dto);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }
}
