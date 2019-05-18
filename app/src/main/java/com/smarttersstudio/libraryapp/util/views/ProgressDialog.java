package com.smarttersstudio.libraryapp.util.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smarttersstudio.libraryapp.R;

public class ProgressDialog {

    private Dialog mDialog;
    private Context context;
    public ProgressDialog(Context context){
        this.context = context;
        mDialog = new Dialog(context);
    }

    public void showProgress(boolean cancelable, String msg){
        mDialog.setContentView(R.layout.pg_dialog);
        ProgressBar mProgressBar = mDialog.findViewById(R.id.progress_bar);
        TextView mTextView = mDialog.findViewById(R.id.pg_dialog_text);
        mTextView.setText(msg);
        mProgressBar.setIndeterminate(true);
        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(cancelable);
        mDialog.show();
    }
    public void showProgress(boolean cancelable,int stringId){
        showProgress(cancelable,context.getResources().getString(stringId));
    }
    public void dismissProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
