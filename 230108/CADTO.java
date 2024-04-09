package com.checkingAccount;

public class CADTO {
    //Member테이블
    String id;
    String pwd;
    String name;
    String inputdate;

    //Account테이블
    String accdiv;		//구분 : 입출금/예적금
    String bank; 		//은행명
    String accname;		//예금주
    String accno;		//계좌번호
    String banknick;	//계좌 간편 이름
    int accamount;		//계좌금액


    //Card테이블
    String card;		//카드사명
    String cardname;	//명의자
    String cardno;		//카드번호
    String cardnick;	//카드 간편 이름
    int cardamount;

    //CardWithdraw테이블, AccountDisposit 테이블, AccountWithdraw테이블
    String cadate;		//사용 일자
    String category;	//사용유형(식대, 쇼핑 등)
    int caamount;		//입/출금 금액
    String canick;		//카드/계좌 간편 이름
    String nickname;    // 별칭

    String caname;
    String cano;

    String stcadate;
    String edcadate;
    int amount;


    //Member테이블
    public String getId() {       return id;    }
    public void setId(String id) {      this.id = id;    }
    public String getPwd() {      return pwd;    }
    public void setPwd(String pwd) {      this.pwd = pwd;    }
    public String getName() {      return name;    }
    public void setName(String name) {      this.name = name;    }
    public String getInputdate() {       return inputdate;    }
    public void setInputdate(String inputdate) {      this.inputdate = inputdate;   }
    //Account테이블
    public String getAccDiv() {      return accdiv;    }
    public void setAccDiv(String accDiv) {      this.accdiv = accDiv;   }
    public String getBank() {       return bank;    }
    public void setBank(String bank) {       this.bank = bank;    }
    public String getAccName() {       return accname;    }
    public void setAccName(String accName) {       this.accname = accName;    }
    public String getAccno() {       return accno;    }
    public void setAccno(String accno) {       this.accno = accno;    }
    public String getBankNick() {       return banknick;    }
    public void setBankNick(String bankNick) {       this.banknick = bankNick;    }
    public int getAccAmount() {        return accamount;    }
    public void setAccAmount(int accAmount) {       this.accamount = accAmount;    }
    //Card테이블
    public String getCard() {        return card;    }
    public void setCard(String card) {        this.card = card;    }
    public String getCardName() {        return cardname;    }
    public void setCardName(String cardName) {       this.cardname = cardName;    }
    public String getCardno() {        return cardno;    }
    public void setCardno(String cardno) {        this.cardno = cardno;    }
    public String getCardNick() {        return cardnick;    }
    public void setCardNick(String cardNick) {        this.cardnick = cardNick;    }
    public int getCardAmount() {        return cardamount;    }
    public void setCardAmount(int cardAmount) {        this.cardamount = cardAmount;    }
    //CardWithdraw테이블, AccountDisposit 테이블, AccountWithdraw테이블
    public String getCadate() { return cadate; }
    public void setCadate(String cadate) { this.cadate = cadate;}
    public String getCADate() {        return cadate;    }
    public void setCADate(String cADate) {        cadate = cADate;    }
    public String getCategory() {        return category;    }
    public void setCategory(String category) {        this.category = category;    }
    public int getCAAmount() {        return caamount;    }
    public void setCAAmount(int cAAmount) {        caamount = cAAmount;    }
    public String getCANick() {       return canick;    }
    public void setCANick(String cANick) {       canick = cANick;    }
    public void setNickname(String nickname) { this.nickname = nickname;}
    public String getNickname() { return nickname; }
    public void setCaname(String caname) { this.caname = caname; }
    public String getCaname() { return caname; }
    public void setCano(String cano) { this.cano = cano; }
    public String getCano() { return cano; }
    public void setStcadate(String stcadate) { this.stcadate = stcadate;}
    public String getStcadate(){ return stcadate; }
    public void setEdcadate(String edcadate) { this.edcadate = edcadate; }
    public String getEdcadate() { return edcadate; }
    public void setAmount(int amount) { this.amount = amount; }
    public int getAmount() { return amount; }
    public void printAcc() {
        System.out.printf("%6s통장\t%10s\t%10s\t%20s\t%10d\t%8s\n",accdiv,bank,accname,accno,accamount,nickname);
    }
    public void printCard() {
        String str = "카드";
        System.out.printf("     %6s\t%10s\t%10s\t%20s\t%10d\t%8s\n",str,card,cardname,cardno,cardamount,nickname);
    }
    public void print(){
        System.out.printf("%s %s %5s %10d %6s \n", id, cadate, nickname, amount, category);
    }
}
