package dev.ianschrauth.invoicwayv300a.ui.searchOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dev.ianschrauth.invoicwayv300a.R;
import dev.ianschrauth.invoicwayv300a.databinding.FragmentSlideshowBinding;
import dev.ianschrauth.invoicwayv300a.ui.searchCustomer.MyActivityFragment;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        // Get reference to the container
        FrameLayout containerActivityFragment = view.findViewById(R.id.container_activity_fragment);

        if (containerActivityFragment != null) {
            // Create an instance of the activity fragment
            MyActivityFragment_searchOrder myActivityFragment = new MyActivityFragment_searchOrder();

            // Add the fragment to the container
            getChildFragmentManager().beginTransaction()
                    .replace(containerActivityFragment.getId(), myActivityFragment)
                    .commit();
        }

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}