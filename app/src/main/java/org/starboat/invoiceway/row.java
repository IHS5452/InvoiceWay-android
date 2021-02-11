package org.starboat.invoiceway;

public class row {
    private String mText;

    public row(String itemName) {
        mText = itemName;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }
    public void clearmText() {
        this.mText = "";
    }

}
