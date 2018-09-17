package com.example.noticeboard;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ServiceManager extends ContextWrapper {

public ServiceManager(Context base) {
    super(base);
}

public boolean isNetworkAvailable() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
        return true;
    }
    return false;
}

}
