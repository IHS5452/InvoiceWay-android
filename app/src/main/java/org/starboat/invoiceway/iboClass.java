package org.starboat.invoiceway;

public class iboClass {

    public String IBONum;
    public String email;
    public String fullName;
    public String pin;

    public iboClass() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public iboClass(String uid, String author, String title, String body) {
        this.IBONum = uid;
        this.email = author;
        this.fullName = title;
        this.pin = body;
    }
}