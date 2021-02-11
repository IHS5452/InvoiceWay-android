package org.starboat.invoiceway;

public class peopleClass {

    String name = "";
    String address = "";
    String email = "";
    String cardNum = "";
    String CVV = "";
    String EXP = "";
    public peopleClass(String name, String add, String email, String cardNum, String CVV, String EXP ) {
        this.name =  name;
        this.address = add;
        this.email = email;
        this.cardNum = cardNum;
        this.CVV = CVV;
        this.EXP = EXP;
    }

}
