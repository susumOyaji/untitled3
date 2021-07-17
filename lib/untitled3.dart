import 'dart:async';

import 'package:flutter/services.dart';

class Untitled3 {
  static const MethodChannel _channel = const MethodChannel('untitled3');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> get platformBattery async {
    final String level = await _channel.invokeMethod('getPlatformBattery');
    return level;
  }

  static Future<String> get platformTelephony async {
    final String telephonyInfo =
        await _channel.invokeMethod('getTelephonyInfo');
    return telephonyInfo;
  }
}
