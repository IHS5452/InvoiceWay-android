package org.starboat.invoiceway;


import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class modifyCart {
   public static void removeFromCart(int position) {

       master.itemCart.remove(position);
       master.priceCart.remove(position);
       getSumOfArrayAndChangeTotal(master.priceCart, MainActivity.total);

       MainActivity.RVA.notifyItemRemoved(position);
       MainActivity.RVA.notifyDataSetChanged();
   }

    public static double getSumOfArrayAndChangeTotal(ArrayList<Double> array, TextView total) {
        double sum = 0.00;
        if (array.size() == 0) {

            total.setText("Total: $0.00");
            master.totalBill = 0.00;

        } else {
            for(int i=-0; i<array.size(); i++){
                sum = sum + array.get(i);

                total.setText("Total: $" + sum);
            }
            master.totalBill = sum;
        }

        return sum;

    }

}
