// ignore_for_file: depend_on_referenced_packages

import 'package:auth/pages/dashboard.dart';
import 'package:flutter/material.dart';
import 'package:auth/pages/login_screen.dart';
import 'package:auth/providers/auth.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => AuthProvider(),
      child: MaterialApp(
        title: "Rumah Sehat",
        theme: ThemeData(primarySwatch: Colors.blue),
        home: const LoginScreen(),

        routes:{
          LoginScreen.routeName: (_) => const LoginScreen(),
          DashBoard.routeName: (context) => const DashBoard(),
        }
      )
    );
  } 
}
