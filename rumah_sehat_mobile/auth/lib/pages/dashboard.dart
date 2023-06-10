// ignore_for_file: prefer_const_constructors

import 'package:auth/pages/login_screen.dart';
import 'package:flutter/material.dart';
import 'package:auth/domain/user.dart';
import 'package:auth/providers/auth.dart';
import 'package:provider/provider.dart';
import 'package:profil/profil.dart';
import 'package:http/http.dart' as http;
import 'package:auth/util/app_url.dart';
import 'dart:convert';
import 'package:tagihan/pages/daftar_tagihan.dart';
import 'package:tagihan/domain/tagihan.dart';
import 'package:appointment/appointment.dart';
import 'package:appointment/appointment_add.dart';

class DashBoard extends StatefulWidget {
  static const routeName = '/dashboard';
  const DashBoard({Key? key}) : super(key: key);
  @override
  _DashBoardState createState() => _DashBoardState();
}

class _DashBoardState extends State<DashBoard> {
  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<AuthProvider>(context);
    print("masuk dashboard kok");

    return Scaffold(
      appBar: AppBar(
        title: const Text("DASHBOARD PAGE"),
        elevation: 0.1,
      ),
      body: FutureBuilder<User>(
        future: provider.getUser(),
        builder: (BuildContext context, AsyncSnapshot<User> snapshot) {
          if (snapshot.hasData) {
            print("ada snapshot");
            User user = snapshot.data!;
            print("nama di dashboard" + user.nama);
            return Center(
              child: ListView(
                children: [
                  SizedBox(
                    height: 100,
                  ),
                  Center(
                    child: Text("Halo, Selamat Datang ${user.nama}",
                        style: TextStyle(
                            fontSize: 20, fontWeight: FontWeight.bold)),
                  ),
                  SizedBox(height: 40),
                  Container(
                    margin: const EdgeInsets.all(10),
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.indigo,
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(15)),
                          padding: const EdgeInsets.all(18)),
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute<void>(
                            builder: (BuildContext context) =>
                                AppointmentAdd(user: user)));
                      },
                      child: const Text('Membuat Appointment',
                          style: TextStyle(fontSize: 20)),
                    ),
                  ),
                  Container(
                    margin: const EdgeInsets.all(10),
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.indigo,
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(15)),
                          padding: const EdgeInsets.all(18)),
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute<void>(
                            builder: (BuildContext context) =>
                                Appointment(user: user)));
                      },
                      child: const Text('Daftar Appointment',
                          style: TextStyle(fontSize: 20)),
                    ),
                  ),
                  Container(
                    margin: const EdgeInsets.all(10),
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.indigo,
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(15)),
                          padding: const EdgeInsets.all(18)),
                      onPressed: () async {
                        var response = await provider.get(AppUrl.daftarTagihan+user.username);
                        print(response.statusCode);
                        if (response.statusCode == 200) {
                          List jsonRes = json.decode(response.body);
                          print(jsonRes);
                          List<Tagihan> listTagihan = jsonRes.map((data) => new Tagihan.fromJson(data)).toList();
                          print(listTagihan);
                          {Navigator.of(context).push(
                          MaterialPageRoute(builder: (context) {
                            return DaftarTagihan(user: user, listTagihan: listTagihan);
                          }),
                          );}
                        } else {print("gagal get Data");}
                        },
                      child: const Text('Daftar Tagihan',
                          style: TextStyle(fontSize: 20)),
                    ),
                  ),
                  Container(
                    margin: const EdgeInsets.all(10),
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.indigo,
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(15)),
                          padding: const EdgeInsets.all(18)),
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute<void>(
                            builder: (BuildContext context) =>
                                Profile(user: user)));
                      },
                      child:
                          const Text('Profil', style: TextStyle(fontSize: 20)),
                    ),
                  ),
                  Container(
                    margin: const EdgeInsets.all(10),
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.indigo,
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(15)),
                          padding: const EdgeInsets.all(18)),
                      onPressed: () {
                        provider.logout();
                        Navigator.pushReplacementNamed(
                            context, LoginScreen.routeName);
                      },
                      child:
                          const Text('Logout', style: TextStyle(fontSize: 20)),
                    ),
                  ),
                ],
              ),
            );
          } else if (snapshot.hasError) {
            if (snapshot.error!.toString() == "Unexpected null value.") {
              print("gagal bro di dashboard");
              print(snapshot.error);
              // Navigator.pushReplacement(context, MaterialPageRoute(builder: (BuildContext context) => LoginScreen2()));
            }
          }
          return CircularProgressIndicator();
        },
      ),
    );
  }
}
