package dev.ianschrauth.invoicwayv300a.ui.searchCustomer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import dev.ianschrauth.invoicwayv300a.R;

public class MyActivityFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search_customer, container, false);
    }
}
