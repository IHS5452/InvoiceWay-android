package org.starboat.invoiceway;

public class itemClass {

    public String SKU;
    public String catagory;
    public String itemName;
    public String price;

    public itemClass() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public itemClass(String sku, String catagory, String item, String price) {
        this.SKU = sku;
        this.catagory = catagory;
        this.itemName = item;
        this.price = price;
    }
}