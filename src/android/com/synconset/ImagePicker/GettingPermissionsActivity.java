package com.synconset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class GettingPermissionsActivity extends Activity {

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int read = checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int write = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (read != PackageManager.PERMISSION_GRANTED && write != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    new String[]{
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    0
                );
            } else {
                //dialog.dismiss();
                GettingPermissionsActivity.this.finish();
            }
        } else {
            //dialog.dismiss();
            GettingPermissionsActivity.this.finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            GettingPermissionsActivity.this.finish();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION }, 0);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GettingPermissionsActivity.this);
                    builder.show();
                    builder.setTitle("Attention");
                    builder.setMessage("Sorry, we can't help you if no Storage Permission");
                    builder.setCancelable(false);
                    builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            GettingPermissionsActivity.this.finish();
                        }
                    });
                }
            } else {
                GettingPermissionsActivity.this.finish();
            }
        }
    }
}
