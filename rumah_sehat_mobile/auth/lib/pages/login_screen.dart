// ignore_for_file: use_build_context_synchronously

import 'package:auth/providers/auth.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:auth/pages/dashboard.dart';

class LoginScreen extends StatefulWidget {
  static const routeName = '/login';
  const LoginScreen({Key? key}) : super(key: key);

  @override
  LoginScreenState createState() => LoginScreenState();
}

class LoginScreenState extends State<LoginScreen> {
  // final _loginFormKey = GlobalKey<FormState>();
  bool isPasswordVisible = false;
  void togglePasswordView() {
    setState(() {
      isPasswordVisible = !isPasswordVisible;
    });
  }

  String username = "";
  String password = "";
  String token = "";

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<AuthProvider>(context);
    // The rest of your widgets are down below
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: Stack(
        children: [
          Padding(
            padding: const EdgeInsets.only(
                top: 60.0, bottom: 20.0, left: 20.0, right: 20.0),
            child: SizedBox(
              height: double.infinity,
              width: double.infinity,
              child: SingleChildScrollView(
                physics: const AlwaysScrollableScrollPhysics(),
                child: Column(
                  children: [
                    const Text(
                      'Welcome to RUMAHSEHAT',
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        fontSize: 32,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: const [
                        Text(
                          'Sign in to your account',
                          style: TextStyle(fontSize: 20),
                        ),
                      ],
                    ),
                    const SizedBox(height: 40.0),
                    Column(
                      children: [
                        SizedBox(
                          width: 400,
                          child: TextFormField(
                            onChanged: (String? value) {
                              setState(() {
                                username = value!;
                              });
                            },
                            onSaved: (String? value) {
                              setState(() {
                                username = value!;
                              });
                            },
                            autovalidateMode:
                                AutovalidateMode.onUserInteraction,
                            validator: (value) {
                              if (value!.isEmpty) {
                                return "Please fill in your username";
                              } else {
                                return null;
                              }
                            },
                            decoration: const InputDecoration(
                              border: OutlineInputBorder(),
                              hintText: 'username',
                              labelText: 'Username',
                            ),
                          ),
                        ),
                        const SizedBox(height: 20.0),
                        SizedBox(
                          width: 400,
                          child: TextFormField(
                            obscureText: true,
                            onChanged: (String? value) {
                              setState(() {
                                password = value!;
                              });
                            },
                            onSaved: (String? value) {
                              setState(() {
                                password = value!;
                              });
                            },
                            autovalidateMode:
                                AutovalidateMode.onUserInteraction,
                            validator: (value) {
                              if (value!.isEmpty) {
                                return "Please fill in your password";
                              } else {
                                return null;
                              }
                            },
                            decoration: const InputDecoration(
                              border: OutlineInputBorder(),
                              hintText: 'password',
                              labelText: 'Password',
                            ),
                          ),
                        ),
                        const SizedBox(height: 10.0),
                      ],
                    ),
                    Container(
                      padding: const EdgeInsets.symmetric(vertical: 25),
                      width: 400,
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          backgroundColor:
                              Colors.indigo,
                          elevation: 3,
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(15)),
                          // minimumSize: const Size(120, 50),
                          padding: const EdgeInsets.all(18),
                        ),
                        child: const Text(
                          'Login',
                          style: TextStyle(fontSize: 16),
                        ),
                        onPressed: () async {
                          await provider.login(username, password);
                          String msg = provider.message;
                          if (msg == "Successful") {
                            ScaffoldMessenger.of(context).showSnackBar(SnackBar(
                              behavior: SnackBarBehavior.floating,
                              backgroundColor: Colors.transparent,
                              elevation: 0,
                              content: Container(
                                padding: const EdgeInsets.all(10),
                                height: 50,
                                decoration: const BoxDecoration(
                                  color: Colors.green,
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(20)),
                                ),
                                child: const Center(
                                  child: Text(
                                    "Login Success!",
                                    textAlign: TextAlign.center,
                                  ),
                                ),
                              ),
                            ));
                            Navigator.pushReplacementNamed(
                                context, DashBoard.routeName);
                          } else {
                            ScaffoldMessenger.of(context).showSnackBar(SnackBar(
                              behavior: SnackBarBehavior.floating,
                              backgroundColor: Colors.transparent,
                              elevation: 0,
                              content: Container(
                                padding: const EdgeInsets.all(10),
                                height: 50,
                                decoration: const BoxDecoration(
                                  color: Colors.red,
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(20)),
                                ),
                                child: const Center(
                                  child: Text(
                                    "Username or Password is wrong!",
                                    textAlign: TextAlign.center,
                                  ),
                                ),
                              ),
                            ));
                          }
                        },
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        const Text(
                          "Don't have an account?",
                          style: TextStyle(fontSize: 16),
                        ),
                        TextButton(
                          onPressed: (){
                            print("regis");
                          },
                          child: const Text(
                            ' Sign Up',
                            style: TextStyle(fontSize: 16, color: Colors.indigo),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
