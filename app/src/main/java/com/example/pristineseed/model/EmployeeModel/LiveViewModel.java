package com.example.pristineseed.model.EmployeeModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiveViewModel  extends ViewModel {

    private MutableLiveData<EmployeeAttendModel> mMutableLiveData=new MutableLiveData<>();

    public MutableLiveData<EmployeeAttendModel> getLiveData() {
        return mMutableLiveData;
    }
    public void setLiveData(EmployeeAttendModel model) {
        mMutableLiveData.postValue(model);
    }
}
