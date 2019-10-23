package com.ndroid.mybroadcastreceivers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MyPermissionManagerActivity extends AppCompatActivity {
    private final String TAG = "MyPermissionManagerActivity";
    private final int REQUEST_ID_PERMISSIONS_DIALOG = 1001;
    private final int REQUEST_ID_SETTINGS_ACTIVITY = 1002;
    private String[] requirePermissions /*= new String[]
            {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECEIVE_SMS
            }*/;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected abstract void onAllPermissionGranted();


    protected boolean checkAndRequestPermissions(String... requirePermissions) {
        this.requirePermissions = requirePermissions;
        return checkAndRequestPermissions();
    }


    private boolean checkAndRequestPermissions() {
        List<String> alPermReq = new ArrayList<>();

        for (String permission : requirePermissions) {
            int iPermission = ContextCompat.checkSelfPermission(this, permission);
            if (iPermission != PackageManager.PERMISSION_GRANTED) {
                alPermReq.add(permission);
            }
        }

        if (alPermReq.isEmpty()) {
            return true;
        } else {
            String[] permissions = alPermReq.toArray(new String[alPermReq.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_ID_PERMISSIONS_DIALOG);
            return false;
        }
    }


    protected boolean isAllPermissionsGranted() {
        for (String permission : requirePermissions) {
            int iPermission = ContextCompat.checkSelfPermission(this, permission);
            if (iPermission != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
            if (requestCode == REQUEST_ID_PERMISSIONS_DIALOG) {
                if (grantResults.length <= 0) {
                    return;
                }

                Map<String, Integer> permissionMap = new HashMap<>();

                for (String permission : requirePermissions) {
                    permissionMap.put(permission, PackageManager.PERMISSION_GRANTED);
                }


                for (int i = 0; i < permissions.length; i++)
                    permissionMap.put(permissions[i], grantResults[i]);


                boolean isAllPermissionGranted = true;
                for (String permission : requirePermissions) {
                    if (permissionMap.get(permission) == PackageManager.PERMISSION_DENIED) {
                        isAllPermissionGranted = false;
                        break;
                    }
                }


                if (isAllPermissionGranted) {
                    showMsg("Thank you for Granting Permissions");
                    onAllPermissionGranted();
                } else {
                    boolean shouldShowJustificationForPermission = false;
                    for (String permission : requirePermissions) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                            shouldShowJustificationForPermission = true;
                            break;
                        }
                    }

                    if (shouldShowJustificationForPermission)
                        showPermissionJustificationDialog();
                    else
                        startSettingIntentForPermissionWithDialog();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showMsg(String msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == REQUEST_ID_SETTINGS_ACTIVITY) {
                if (checkAndRequestPermissions()) {
                    showMsg("Thank you for Granting Permissions from Settings");
                    onAllPermissionGranted();
                }
            } else
                super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showPermissionJustificationDialog() {
        new AlertDialog.Builder(this)
                .setMessage("This permissions are required to function this application properly.")
                .setPositiveButton("Retry", (dialog, which) -> {
                    checkAndRequestPermissions();
                    dialog.dismiss();
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    finish();
                    dialog.dismiss();
                })
                .setCancelable(false)
                .create()
                .show();
    }


    private void startSettingIntentForPermissionWithDialog() {
        try {
            DialogInterface.OnClickListener dialogPositiveButtonClicked = (dialog, which) -> {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_ID_SETTINGS_ACTIVITY);
                dialog.cancel();
            };

            DialogInterface.OnClickListener dialogNegativeButtonClicked = (dialog, which) -> {
                dialog.dismiss();
                finish();
            };

            new AlertDialog.Builder(this)
                    .setMessage("Note : Permission Sticky Required, please enable all the permissions from setting for proceeding further.")
                    .setPositiveButton("Go to Settings", dialogPositiveButtonClicked)
                    .setNegativeButton("Exit", dialogNegativeButtonClicked)
                    .setCancelable(false)
                    .create()
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}