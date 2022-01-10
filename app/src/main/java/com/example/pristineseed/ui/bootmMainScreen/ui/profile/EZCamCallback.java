package com.example.pristineseed.ui.bootmMainScreen.ui.profile;

import android.media.Image;

public interface EZCamCallback {
    void onCameraReady();
    void onPicture(Image image);
    void onError(String message);
    void onCameraDisconnected();
}
