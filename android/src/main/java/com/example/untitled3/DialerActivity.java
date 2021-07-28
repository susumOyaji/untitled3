package com.example.untitled3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.view.View;
import android.app.Activity;
import android.Manifest;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;

//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;

//import android.support.v4.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import io.flutter.embedding.android.FlutterActivity;//推奨
//import io.flutter.app.FlutterActivity;
//import io.flutter.plugins.GeneratedPluginRegistrant;
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
    private final static int REQUEST_CODE = 1000;
    private final static String[] mPermission = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);

        //ユーザがDialerユーザインターフェイスを経由せずに通話を開始し、通話を確認することをアプリケーションに許可します。
        ActivityCompat.requestPermissions(this, new String[]{
            Manifest.permission.CALL_PHONE
        }, REQUEST_CODE);
        
        
        setContentView(R.layout.activity_dialer);

        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        // get Intent data (tel number)
        if (getIntent().getData() != null)
            phoneNumberInput.setText(getIntent().getData().getSchemeSpecificPart());



        //new App();
        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
          new MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, Result result) {//TODO
                      if (call.method.equals("androidphone")) {
                        Toast.makeText(DialerActivity.this, "Started theMethodChannel to makeCall", Toast.LENGTH_SHORT).show();

                        // invokeMethodの第二引数で指定したパラメータを取得できます
                        parameters = call.arguments.toString();
                        String phonestate = makeCall(parameters);
                        
                        if (phonestate != null) {
                          result.success(phonestate);//return to Flutter
                        } else {
                          result.error("UNAVAILABLE", "AndroidPhone not available.", null);
                        }
                      } else {
                              if (call.method.equals("hangup")) {
                                Toast.makeText(DialerActivity.this, "Started theMethodChannel to hangup ", Toast.LENGTH_SHORT).show();
                                // invokeMethodの第二引数で指定したパラメータを取得できます
                                boolean hangupparameters = (boolean)call.arguments;
                                boolean hangup = hangup(hangupparameters);
                        
                                if (hangup != true) {
                                  result.success(hangup);
                                } else {
                                  result.error("UNAVAILABLE", "Hangup not available.", null);
                                }
                              } else {
                                result.notImplemented();//該当するメソッドが実装されていない
                              } // TOD
                      } // TODO


                     /*
                      if (call.method.equals("hangup")) {
                        Toast.makeText(DialerActivity.this, "Started theMethodChannel to hangup ", Toast.LENGTH_SHORT).show();
                        // invokeMethodの第二引数で指定したパラメータを取得できます
                        boolean hangupparameters = (boolean)call.arguments;
                        boolean hangup = hangup(hangupparameters);
                        
                        if (hangup != true) {
                          result.success(hangup);
                        } else {
                          result.error("UNAVAILABLE", "Hangup not available.", null);
                        }
                      } else {
                        result.notImplemented();
                      } // TODO
                      */
                     
                    }
                }
          );

    }













    @Override
    public void onStart() {
        super.onStart();
        offerReplacingDefaultDialer();
    }





    @SuppressLint("MissingPermission")
    public String makeCall(String phone) {
        System.out.println("makeCall = "+phone);

        

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
         System.out.println("makeCall 1= "+phone);
        if (ContextCompat.checkSelfPermission(this, mPermission[0]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, mPermission[1]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, mPermission, REQUEST_CODE);
        }
       
        System.out.println("makeCall 2= "+phone);
        // If permission to call is granted
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) == PERMISSION_GRANTED) {
            System.out.println("if makeCall");
            // Create the Uri from phoneNumberInput
            Uri uri = Uri.parse("tel:"+phone);
            // Start call to the number in input
            startActivity(new Intent(Intent.ACTION_CALL, uri));
        } else {
             System.out.println("else makeCall");
            // Request permission to call
            ActivityCompat.requestPermissions(this, new String[]{CALL_PHONE}, REQUEST_PERMISSION);
        }
        
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