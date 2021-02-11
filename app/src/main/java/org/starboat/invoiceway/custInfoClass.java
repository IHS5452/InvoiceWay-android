package org.starboat.invoiceway;



    public class custInfoClass {

        public String address;
        public String email;

        public custInfoClass() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public custInfoClass(String address, String uid) {
            this.address = uid;
            this.email = address;

        }
    }

