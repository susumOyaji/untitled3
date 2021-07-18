package com.example.untitled3;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import java.util.ArrayList;
//import java.lang.Object;

import android.os.Bundle;
import io.flutter.app.FlutterActivity;

//import io.flutter.plugins.GeneratedPluginRegistrant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
//import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.telecom.InCallService;
import android.widget.EditText;
import android.Manifest;
import android.app.Activity;
import android.widget.Toast;
import android.widget.TextView;
//import io.reactivex.disposables.CompositeDisposable;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.subjects.BehaviorSubject;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER;
import static android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME;



/** PluginCodelabPlugin */
public class PluginCodelabPlugin implements FlutterPlugin, MethodCallHandler{
  private MethodChannel channel;
  //private Synth synth;
  private static final String channelName = "plugin_codelab";

  private static void setup(PluginCodelabPlugin plugin, BinaryMessenger binaryMessenger) {
    plugin.channel = new MethodChannel(binaryMessenger, channelName);
    plugin.channel.setMethodCallHandler(plugin);
    //plugin.synth = new Synth();
    //plugin.synth.start();
  }


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    setup(this, flutterPluginBinding.getBinaryMessenger());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("getBatteryLevel")) {

      try {
        int batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        result.success("Battery " +batteryLevel);
      } catch (Exception ex) {
        result.error("1", ex.getMessage(), ex.getStackTrace());
      }
    } else if (call.method.equals("getTelephonyInfo")) {
      try {
        ArrayList arguments = (ArrayList) call.arguments;
        //int numKeysDown = synth.keyUp((Integer) arguments.get(0));
        //result.success(numKeysDown);
      } catch (Exception ex) {
        result.error("1", ex.getMessage(), ex.getStackTrace());
      }
    } else if (call.method.equals("androidphone")) {
      Toast.makeText(this, "Started theMethodChannel to makeCall", Toast.LENGTH_SHORT).show();

      // invokeMethodの第二引数で指定したパラメータを取得できます
      String parameters = call.arguments.toString();
      String phonestate = makeCall(parameters);
      
      if (phonestate != null) {
        result.success(phonestate);//return to Flutter
      } else {
        result.error("UNAVAILABLE", "AndroidPhone not available.", null);
      }
    }else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }




  @SuppressLint("MissingPermission")
  public String makeCall(String _phone) {
      // If permission to call is granted
      if (checkSelfPermission(CALL_PHONE) == PERMISSION_GRANTED) {
          // Create the Uri from phoneNumberInput
          Uri uri = Uri.parse("tel:"+ _phone);
          
          // Start call to the number in input
          startActivity(new Intent(Intent.ACTION_CALL, uri));
      } else {
          // Request permission to call
          ActivityCompat.requestPermissions(this, new String[]{CALL_PHONE}, REQUEST_PERMISSION);
      }
            
      String tv = CallActivity.PhoneState;
      Toast.makeText(DialerActivity.this, "makeCall  " + tv , Toast.LENGTH_SHORT).show();
      
      return tv;
  }






}