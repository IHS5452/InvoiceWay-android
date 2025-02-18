package dev.ianschrauth.invoicwayv300a.ui.editIBOAccount.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class editIBOAccountViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public editIBOAccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the Edit IBO Account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}