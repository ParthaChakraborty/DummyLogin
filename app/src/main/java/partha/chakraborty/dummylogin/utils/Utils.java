package partha.chakraborty.dummylogin.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import partha.chakraborty.dummylogin.R;

public class Utils {

    private Activity mActivity;
    private Dialog newLoadingDialog = null;
    private int loadingDialogCount = 0;

    public Utils(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public Utils() {
    }

    public boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager conMan = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    public void showAlertSuccessMessage(String message, Activity activity) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity, R.style.MyAlertDialogTheme).setTitle("Success").setMessage(message)
                    .setIcon(R.drawable.ic_check_circle_black_24dp);
            dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            //Must call show() prior to fetching text view
            AlertDialog dog = dialog.create();
            dog.show();
            TextView messageView = dog.findViewById(android.R.id.message);
            //messageView.setGravity(Gravity.CENTER);
            messageView.setTextColor(Color.BLACK);

            Button btnPositive = dog.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAlertErrorMessage(String message, Activity activity) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity, R.style.MyAlertDialogTheme).setTitle("Error").setMessage(message).setIcon(R.drawable.ic_report_problem_black_24dp);
            dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            //Must call show() prior to fetching text view
            AlertDialog dog = dialog.create();
            dog.show();
            TextView messageView = dog.findViewById(android.R.id.message);
            //messageView.setGravity(Gravity.CENTER);
            messageView.setTextColor(Color.BLACK);

            Button btnPositive = dog.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoader(Activity mActivity) {
        try {
            if (newLoadingDialog == null) {
                try {
                    newLoadingDialog = new Dialog(mActivity);
                    newLoadingDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    newLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    newLoadingDialog.setContentView(R.layout.loading_dialog);
                    newLoadingDialog.setCancelable(false);
                    newLoadingDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loadingDialogCount = 1;
            } else {
                loadingDialogCount += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideLoader() {
        try {
            if (loadingDialogCount < 2) {
                if (newLoadingDialog != null) {
                    newLoadingDialog.dismiss();
                    newLoadingDialog = null;
                    loadingDialogCount = 0;
                }
            } else {
                loadingDialogCount -= 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}