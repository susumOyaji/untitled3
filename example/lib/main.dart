import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:untitled3/untitled3.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _platformTelephony = 'Unknown';
  String _platformBattery = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    String platformTelephony;
    String platformBattery;

    // Platform messages may fail, so we use a try/catch PlatformException.
    /*
    try {
      platformVersion = await Untitled3.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }
    
    try {
      platformBattery = await Untitled3.platformBattery;
    } on PlatformException {
      platformVersion = 'Failed to get platform Battery.';
    }
    */
    try {
      platformTelephony = await Untitled3.platformTelephony;
    } on PlatformException {
      platformTelephony = 'Failed to get platform Telphony.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    //if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
      _platformTelephony = platformTelephony;
      _platformBattery = platformBattery;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: Column(
            children: [
              Center(child: Text('Running on Version: $_platformVersion\n')),
              Center(child: Text('Running on Battery: $_platformBattery\n')),
              Center(
                  child: Text('Running on Telephony: $_platformTelephony\n')),
            ],
          )),
    );
  }
}
