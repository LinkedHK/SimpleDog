package main.simpledog.mobile.app.ui.dialogs;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import main.simpledog.mobile.app.R;

public class ItemDialogs extends DialogFragment {


    public static void   itemLoadFailure(Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setTitle(R.string.item_load_failure_title);
        alert.setMessage(R.string.item_load_failure);
        alert.create();

    }



}
