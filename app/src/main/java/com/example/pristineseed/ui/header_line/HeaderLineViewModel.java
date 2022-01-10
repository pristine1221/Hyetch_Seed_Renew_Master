package com.example.pristineseed.ui.header_line;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HeaderLineViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HeaderLineViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}