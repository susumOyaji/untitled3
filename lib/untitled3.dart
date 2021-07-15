import 'dart:async';

import 'package:flutter/services.dart';

class Untitled3 {
  static const MethodChannel _channel =
      const MethodChannel('untitled3');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
