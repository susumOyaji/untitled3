package com.example.untitled3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.view.View;
import android.app.Activity;
import android.Manifest;
import androidx.core.content.ContextCompat;

//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;

//import android.support.v4.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import io.flutter.embedding.android.FlutterActivity;//推奨
import androidx.core.app.ActivityCompat;

import android.telecom.TelecomManager;
import android.widget.EditText;
import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER;
import static android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME;


public class DialerActivity extends FlutterActivity {
    EditText phoneNumberInput;
    private static final int REQUEST_PERMISSION = 0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        // get Intent data (tel number)
        if (getIntent().getData() != null)
            phoneNumberInput.setText(getIntent().getData().getSchemeSpecificPart());
    }

    @Override
    public void onStart() {
        super.onStart();
        offerReplacingDefaultDialer();

        phoneNumberInput.setOnEditorActionListener((v, actionId, event) -> {
            makeCall("1234");
            return true;
        });
    }

    @SuppressLint("MissingPermission")
    public String makeCall(String phone) {
        System.out.println(phone);

        /*
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // Android 6.0 のみ、該当パーミッションが許可されていない場合
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // パーミッションが必要であることを明示するアプリケーション独自のUIを表示
            }

        } else {
            // 許可済みの場合、もしくはAndroid 6.0以前
            // パーミッションが必要な処理
        }
        */
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        
        /*
        // If permission to call is granted
        if (checkSelfPermission(this,Manifest.permission.CALL_PHONE) == PERMISSION_GRANTED) {
            // Create the Uri from phoneNumberInput
            Uri uri = Uri.parse("tel:"+phone);
            // Start call to the number in input
            startActivity(new Intent(Intent.ACTION_CALL, uri));
        } else {
            // Request permission to call
            ActivityCompat.requestPermissions(this, new String[]{CALL_PHONE}, REQUEST_PERMISSION);
        }
        */
        return "";
        
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ArrayList<Integer> grantRes = new ArrayList<>();
        // Add every result to the array
        for (Integer grantResult: grantResults) grantRes.add(grantResult);

        if (requestCode == REQUEST_PERMISSION && grantRes.contains(PERMISSION_GRANTED)) {
            makeCall("1234");
        }
    }

    private void offerReplacingDefaultDialer() {
        TelecomManager systemService = this.getSystemService(TelecomManager.class);
        if (systemService != null && !systemService.getDefaultDialerPackage().equals(this.getPackageName())) {
        startActivity((new Intent(ACTION_CHANGE_DEFAULT_DIALER)).putExtra(EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, this.getPackageName()));
        }
    }
}