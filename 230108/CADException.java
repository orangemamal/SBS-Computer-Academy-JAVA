package com.checkingAccount;

import java.util.regex.Pattern;

public class CADException {

    //아이디 형식 확인
    public void idFormat(String str) throws AuthenException {

        if (str.length() < 3 || str.length() > 10) {
            throw new AuthenException("\n3~10자 이내의 아이디만 가능합니다");
        }

        int cnt1 = 0;
        int cnt2 = 0;

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
                cnt1++;
            else if (ch >= '0' && ch <= '9')
                cnt2++;
        }

        if (cnt1 == 0 || cnt2 == 0)
            throw new AuthenException("\n아이디는 영문자와 숫자를 혼용해서 만들어주세요");

    }

    //비밀번호 형식확인
    public void pwCheck(String pw1, String pw2) throws AuthenException {

        if (pw1.length() < 3 || pw1.length() > 10) {
            throw new AuthenException("\n5~20자 이내의 비밀번호만 가능합니다");
        }

        int cnt1 = 0;
        int cnt2 = 0;

        for (int i = 0; i < pw1.length(); i++) {
            char ch = pw1.charAt(i);
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
                cnt1++;
            else if (ch >= '0' && ch <= '9')
                cnt2++;
        }

        if (cnt1 == 0 || cnt2 == 0)
            throw new AuthenException("\n아이디는 영문자와 숫자를 혼용해서 만들어주세요");

        if (!pw1.equals(pw2))
            throw new AuthenException("\n비밀번호가 다릅니다");
    }

    //닉네임 형식
    public void nameCheck(String name) throws AuthenException {

        boolean check = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name);
        if (!check)
            throw new AuthenException("\n※이름은 한글로 입력해주세요");
    }

    //입출금, 예/적금만 입력 가능
    public void accdivCheck(String accdiv) throws AuthenException {
        if (!accdiv.equals("입출금") && !accdiv.equals("예/적금"))
            throw new AuthenException("\n※입력은 [입출금],[예/적금]으로만 가능합니다");
    }

    //계좌/카드 간편이름 형식
    public void accNickCheck(String accNick) throws AuthenException {

        if (accNick.length() < 1 || accNick.length() > 10) {
            throw new AuthenException("\n10자 이내의 간편이름만 가능합니다");
        }
        boolean check = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", accNick);
        if (!check)
            throw new AuthenException("\n※간편이름은 한글로만 입력가능합니다");
    }

    // 금액은 숫자만 입력 가능
    public int numberCheck1(String number) throws AuthenException{

        boolean check = number.matches("^[0-9]*$");
        if (!check)
            throw new AuthenException("\n※번호는 숫자로만 입력가능합니다");

        if (check)
            return Integer.parseInt(number);

        return 0;
    }

    //계좌/카드 번호는 숫자만 입력 가능
    public String numberCheck2(String number) throws AuthenException{

        boolean check = number.matches("^[0-9]*$");
        if (!check)
            throw new AuthenException("\n※숫자로만 입력가능합니다");

        if (check)
            return number;

        return null;
    }

    //카테고리 입력
    public void categoryCheck(String category) throws AuthenException {

        String check[] = {"자기계발", "생활비", "식대", "교육", "문화", "미용", "교통", "쇼핑", "월급"};

        int x = 0;
        for(String i: check){
            if(category.equals(i)){
                x = 1;
                break;
            }
        }

        if(x!=1)
            throw new AuthenException(
                    "\n※카테고리 : [자기계발] [생활비] [식대] [교육] [문화] [미용] [교통] [쇼핑] [월급]");
    }

    // 날짜 확인
    public void dateCheck(String date) throws AuthenException {

        boolean check = Pattern.matches(
                "(\\d{4})-(\\d{2})-(\\d{2})", date);

        if (!check)
            throw new AuthenException("※날짜 입력 형식은 [YYYY-MM-DD]입니다");
    }

    // 연도 확인
    public void yearCheck(String date) throws AuthenException {

        boolean check = Pattern.matches(
                "(\\d{4})", date);
        if (!check)
            throw new AuthenException("※연도를 정확히 입력해주세요.");
    }

    // 월 확인
    public void monthCheck(String date) throws AuthenException {

        boolean check = Pattern.matches(
                "01|02|03|04|05|06|07|08|09|10|11|12", date);
        if (!check)
            throw new AuthenException("※월을 정확히 입력해주세요. 입력 형식은 1월의 경우 [01]입니다.");
    }

}
