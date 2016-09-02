package com.mayaf.mayajaalfb.customviews.customalertdialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mayaf.mayajaalfb.R;
import com.mayaf.mayajaalfb.customviews.customalertdialog.interfaces.CustomAlertDialogInterface;

/**
 * Created by innoppl on 23/8/16.
 */
public class CustomAlertDialogView {

    private CustomAlertDialogInterface dataSelectionCallback;
    private int selectedView;
    private AlertDialog.Builder alertDialogBuilder = null;
    CharSequence[] charSequenceItems;

    public static AlertDialog myAlertDialog;
    Activity mActivity;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public CustomAlertDialogView(Activity activity) {
        this.mActivity = activity;
        if (activity.isDestroyed() || activity.isFinishing()) { // or call isFinishing() if min sdk version < 17
            return;
        } else if (myAlertDialog != null) {
            if (myAlertDialog.isShowing()) {
                myAlertDialog.dismiss();
                myAlertDialog = null;
            }
        }

    }

    private final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int position) {
            dialogInterface.dismiss();
            alertDialogBuilder.setSingleChoiceItems(charSequenceItems, position, listener);
            dataSelectionCallback.dataSelection(position);

        }
    };

    private void runUiThread(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!activity.isFinishing()) {
                    myAlertDialog = alertDialogBuilder.create();
                    myAlertDialog.show();
                }
            }
        });
    }


    public void displaySingleDoubleBtnAlert(final Activity activity, final CustomAlertDialogInterface dataSelectionCallback, final int statusCode, String errorMsg, String leftButtonText, String rightButtonText) {
        if (myAlertDialog != null && myAlertDialog.isShowing()) {
            myAlertDialog.dismiss();
            myAlertDialog = null;
        }

        alertDialogBuilder = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        LayoutInflater inflater = mActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_alert_layout, null);
        alertDialogBuilder.setView(dialogView);

        TextView errorText = (TextView) dialogView.findViewById(R.id.refresh_error_msg_alert_txt);
        errorText.setText(errorMsg);
        View verticalView = (View) dialogView.findViewById(R.id.verticalView);
        TextView okTextView = (TextView) dialogView.findViewById(R.id.ok_button);
        okTextView.setText(leftButtonText);
        TextView cancelTextView = (TextView) dialogView.findViewById(R.id.cancel_button);
        cancelTextView.setText(rightButtonText);

        if (TextUtils.isEmpty(rightButtonText)) {
            verticalView.setVisibility(View.GONE);
            cancelTextView.setVisibility(View.GONE);
        }

        alertDialogBuilder.setCancelable(false);
        //  selectedView = callbackPOJO.requestCode;
        this.dataSelectionCallback = dataSelectionCallback;

        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.dismiss();
                dataSelectionCallback.alertPositiveBtn(statusCode);
            }
        });
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.dismiss();
                dataSelectionCallback.alertNegativeBtn(statusCode);
            }
        });

        runUiThread(activity);
    }
}
