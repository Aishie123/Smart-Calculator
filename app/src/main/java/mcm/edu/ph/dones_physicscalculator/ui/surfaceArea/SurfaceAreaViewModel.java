package mcm.edu.ph.dones_physicscalculator.ui.surfaceArea;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SurfaceAreaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SurfaceAreaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}