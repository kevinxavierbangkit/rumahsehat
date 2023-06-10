// ignore_for_file: depend_on_referenced_packages

import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '/domain/user.dart';
import '/util/app_url.dart';
import 'package:jwt_decode/jwt_decode.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AuthProvider with ChangeNotifier { 

  Map<String, String> headers = {};
  User user = User();
  String message = "";
  bool _loggedInStatus = false;
  String token = "";

  bool get loggedInStatus => _loggedInStatus;

  Future<User> getUser() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    print("get User");
    print(prefs.getString("user"));
    User userData = User.fromJson2(json.decode(prefs.getString("user")!));

    token = (await getToken())!;
    headers['Authorization'] = 'Bearer $token';
    headers['Connection'] = 'keep-alive';
    headers['Content-Type'] = 'application/json';
    headers['Accept'] = 'application/json';

    print("di provider");
    print(userData.nama);
    return userData;
  }

  Future<void> saveUser(User userData) async{
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    user = userData;
    headers['Authorization'] = 'Bearer $token';
    headers['Connection'] = 'keep-alive';
    headers['Content-Type'] = 'application/json';
    headers['Accept'] = 'application/json';

    prefs.setString("user", json.encode(user.toJson()));
    notifyListeners();
  }
  
  Future<void> removeUser() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.remove("token");
    await prefs.remove("user");
    token = "";
    _loggedInStatus = false;
    headers = {};
  }

  String getJwtToken(){
    return token;
  }

  Future<bool> setToken(String token) async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.setString('token', token);
  }

  Future<String?> getToken() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString('token');
  }

  Future<void> login(String username, String password) async {
    final Map<String, dynamic> loginData = {
      'username': username,
      'password': password
    };
    try {
    
    http.Response response = await http.post(
      Uri.parse(AppUrl.login),
      headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          },
      body: json.encode(loginData),
    );

    if (response.statusCode == 200) {
      final Map<String, dynamic> responseData = json.decode(response.body);

      token = responseData['jwtToken'];

      setToken(token);

      User authUser = User.fromJson(responseData);
      message = "Successful";
      saveUser(authUser);

      _loggedInStatus = true;
      print('Bearer $token');
      notifyListeners();

    } else {
      _loggedInStatus = false;
      message = "Error terjadi, harap login kembali!";
      notifyListeners();
    }
    } catch (e){
      print(e.toString());
    }
  }

  Future<void> logout() async{
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    // await removeUser();
    await prefs.remove("token");
    await prefs.remove("user");
    token = ""; 
    _loggedInStatus = false;
    headers= {};
    notifyListeners();

  }
  

  Future<dynamic> get(String url) async {
    autoLogout(await getToken());
    http.Response response =
        await http.get(
          Uri.parse(url),
          headers: headers
        );

    return response; // Expects and returns JSON request body
  }

  Future<dynamic> post(String url, dynamic data) async {
    autoLogout(await getToken());
    http.Response response =
        await http.post(
          Uri.parse(url),
          body: data,
          headers:headers
        );
    return response; // Expects and returns JSON request body
  }

  Future<dynamic> put(String url, dynamic data) async {
    autoLogout(await getToken());
    print("masuk put");
    print(headers);
    http.Response response =
    await http.put(
        Uri.parse(url),
        body: data,
        headers:headers,
    );
    print(json.encode(response.body));
    return response; // Expects and returns JSON request body
  }

  autoLogout(token){
    if (Jwt.isExpired(token)){
      print(" NIH JWT EXPIRED");
      logout();
    }
  }
}