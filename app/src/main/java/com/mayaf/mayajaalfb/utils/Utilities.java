package com.mayaf.mayajaalfb.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mayaf.mayajaalfb.customviews.customalertdialog.CustomAlertDialogView;
import com.mayaf.mayajaalfb.customviews.customalertdialog.interfaces.CustomAlertDialogInterface;

/**
 * Created by innoppl on 11/8/16.
 */
public class Utilities {

    private static Utilities mInstance = null;

    public static Utilities getInstance() {
        if (mInstance == null) {
            mInstance = new Utilities();
        }
        return mInstance;
    }

    /**
     * This method is used for checking internet reachable
     *
     * @param context application context
     * @return if internet is available it return true else false.
     */
    public boolean isInternetAvail(Context context) {
        if (context != null) {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            try {
                if (connectivity == null) {
                    return false;
                } else {
                    NetworkInfo[] info = connectivity.getAllNetworkInfo();
                    if (info != null) {
                        for (NetworkInfo anInfo : info) {
                            if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                                return true;
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                return false;

            }
        }
        return false;
    }

  /*  public void alertMessage(String msg, Context context) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(msg);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }*/

    /*
  It shows alert with single button
   */
    public void showSingleButtonAlert(CustomAlertDialogView customAlertDialogView, Activity activity, CustomAlertDialogInterface customAlertDialogInterface, String message, String buttonName) {
        try {
            customAlertDialogView.displaySingleDoubleBtnAlert(activity, customAlertDialogInterface, -1,
                    message,
                    buttonName, "");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
