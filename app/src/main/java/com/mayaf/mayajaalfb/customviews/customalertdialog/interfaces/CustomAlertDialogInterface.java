package com.mayaf.mayajaalfb.customviews.customalertdialog.interfaces;

/**
 * Created by innoppl on 23/8/16.
 */
public interface CustomAlertDialogInterface {

    public void dataSelection(int selectedPos);

    // Left button listener in alertDialog
    public void alertPositiveBtn(int statusCode);

    // Right button listener in alertDialog
    public void alertNegativeBtn(int statusCode);

    // handling refresh webservice overall
    public void onReConnect();

 /*   // handling 401 error from StarGenieErrorHandler.java
    public void onResetWebservice(Boolean isSuccess);
    */
}
