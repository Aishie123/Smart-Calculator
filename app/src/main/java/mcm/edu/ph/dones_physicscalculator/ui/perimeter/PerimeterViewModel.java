package mcm.edu.ph.dones_physicscalculator.ui.perimeter;

import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PerimeterViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PerimeterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}