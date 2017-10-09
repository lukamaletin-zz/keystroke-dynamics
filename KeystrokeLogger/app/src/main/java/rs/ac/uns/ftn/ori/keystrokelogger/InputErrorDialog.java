package rs.ac.uns.ftn.ori.keystrokelogger;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class InputErrorDialog {

    public static void show(Context context) {
        final MainActivity activity = (MainActivity) context;

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error!");
        builder.setMessage("Repeat PIN code.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.handleWrongInput();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
