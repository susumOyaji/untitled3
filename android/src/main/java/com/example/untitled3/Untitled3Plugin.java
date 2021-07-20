package com.example.untitled3;
/* Orgnal
import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
*/

import android.content.IntentFilter;
import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.os.BatteryManager;
import android.os.Bundle;
import android.net.Uri;
//import android.widget.Toast;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;





//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;

//import android.support.v4.app.ActivityCompat;
import androidx.core.app.ActivityCompat;

//import android.support.v4.app.FragmentActivity;
import androidx.fragment.app.FragmentActivity;

//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.telecom.TelecomManager;
import android.telecom.InCallService;
import android.widget.EditText;
import java.util.ArrayList;
import android.Manifest;
import android.app.Activity;
import android.widget.Toast;
import android.widget.TextView;
import static android.Manifest.permission.CALL_PHONE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER;
import static android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME;
import android.annotation.SuppressLint;


/** Untitled3Plugin */
public class Untitled3Plugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  String phoneNumberInput;
  String parameters;

  private static final int REQUEST_PERMISSION = 0;
  static final int REQUEST_CODE = 1;
  private static final int REQUEST_ID = 1;
  //IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
  //Intent batteryStatus = getBaseContext.registerReceiver(null, ifilter);  


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "untitled3");
    channel.setMethodCallHandler(this);
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "untitled3");
    channel.setMethodCallHandler(new Untitled3Plugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {



    
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("getPlatformBattery")) {
      //int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
      //int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
      //float batteryPct = level * 100 / (float)scale;
      result.success("BatteryLevel ");
    }else if (call.method.equals("androidphone")) {
      ///Toast.makeText(Untitled3Plugin.this, "Started theMethodChannel to makeCall", Toast.LENGTH_SHORT).show();

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
              ///Toast.makeText(Untitled3Plugin.this, "Started theMethodChannel to hangup ", Toast.LENGTH_SHORT).show();
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


    if (call.method.equals("hangup")) {
      ///Toast.makeText(Untitled3Plugin.this, "Started theMethodChannel to hangup ", Toast.LENGTH_SHORT).show();
      // invokeMethodの第二引数で指定したパラメータを取得できます
      boolean hangupparameters = (boolean)call.arguments;
      boolean hangup = hangup(hangupparameters);
      
      if (hangup != true) {
        result.success(hangup);
      } else {
        result.error("UNAVAILABLE", "Hangup not available.", null);
      }
    } else {
      result.notImplemented();// //該当するメソッドが実装されていない
    } // TODO

      //Toast.makeText(this@VscodepluginPlugin, "Started theMethodChannel to makeCall", Toast.LENGTH_SHORT).show()

      // invokeMethodの第二引数で指定したパラメータを取得できます
      //parameters = call.arguments.toString();
      //val phonestate = makeCall(parameters);
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
          ActivityCompat.requestPermissions(Untitled3Plugin.this, new String[]{CALL_PHONE}, REQUEST_PERMISSION);
      }
            
      String tv = CallActivity.PhoneState;
      ///Toast.makeText(Untitled3Plugin.this, "makeCall  " + tv , Toast.LENGTH_SHORT).show();
      
      return tv;
  }

   
  public boolean hangup(boolean hangup) {
    ///Toast.makeText(Untitled3Plugin.this, "hangup  to True ", Toast.LENGTH_SHORT).show();
    //CallActivity.onHangup();
    OngoingCall.hangup();
    return true;
  }





 

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
