package main.simpledog.mobile.app.ui.dialogs;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import main.simpledog.mobile.app.R;

public class ItemDialogs  {

 private    boolean dialogShown = false;
 protected   Context context;

    public void showLoadedItemNum(int num){
        String res = getContext().getResources().getString(R.string.items_shown,num);
        Toast toast = Toast.makeText(getContext(),res,Toast.LENGTH_SHORT);
        toast.show();
    }

   public ItemDialogs(Context _context){
        context = _context;
    }

    public void itemLoadFailure(){
        if(!dialogShown){
            dialogShown = true;
        }else {
           return;
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogShown = false;
            }
        });
        alert.setTitle(R.string.item_load_failure_title);
        alert.setMessage(R.string.item_load_failure);
        alert.create();
        alert.show();
        alert.show();
    }


    public Context getContext() {
        return context;
    }
}
