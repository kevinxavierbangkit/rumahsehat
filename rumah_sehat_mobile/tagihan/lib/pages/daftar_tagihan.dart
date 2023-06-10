// // ignore_for_file: prefer_const_constructors
//
import 'dart:convert';

import 'package:auth/pages/login_screen.dart';
import 'package:auth/util/app_url.dart';
import 'package:flutter/material.dart';
import 'package:auth/domain/user.dart';
import 'package:auth/providers/auth.dart';
import 'package:flutter/services.dart';
import 'package:auth/pages/dashboard.dart';
import 'package:provider/provider.dart';
import 'package:tagihan/domain/tagihan.dart';
import 'package:tagihan/pages/detail_tagihan.dart';

class DaftarTagihan extends StatefulWidget {
  static const routeName = '/dafarTagihan';
  User user;
  List<Tagihan> listTagihan;
  DaftarTagihan({super.key, required this.user, required this.listTagihan});
  @override
  _DaftarTagihanState createState() => _DaftarTagihanState();
}

class _DaftarTagihanState extends State<DaftarTagihan> {

  Widget buildBack() {
    return ElevatedButton(
        onPressed: () async {
          Navigator.pushReplacementNamed(context, DashBoard.routeName);
        },
        child: const Text(
            'Kembali ke Home'));
  }

  @override
  Widget build(BuildContext context) {

    List<Tagihan> data = widget.listTagihan;
    final provider = Provider.of<AuthProvider>(context);

    // await provider.get(AppUrl.daftarTagihan+widget.user.username);
    // print(res);
    // List lst = res as List;
    // List<Tagihan> listTagihan = lst.map((data) => new Tagihan.fromJson(data)).toList();

    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color(0xff131313),
        title: const Text(
          "Daftar Tagihan",
          textScaleFactor: 1.3,
        ),
      ),
      body: ListView.builder(
        padding: const EdgeInsets.all(16),
        shrinkWrap: true,
        itemCount: widget.listTagihan.length,
        itemBuilder: (BuildContext context, int index) {
          return Container(
            margin: const EdgeInsets.only(top: 20),
            child: Column(
              children: <Widget>[
                Column(
                  children: [
                    Card(
                        margin: const EdgeInsets.only(top: 10),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(20),
                        ),
                        child: Padding(
                          padding: const EdgeInsets.only(
                              top: 20, left: 15, right: 15, bottom: 10),
                          child: Column(
                            children: [
                              Row(
                                children: [
                                  const Text(
                                    "Kode Tagihan",
                                    style: TextStyle(
                                        fontFamily: 'Mulish',
                                        fontSize: 16,
                                        color: Colors.black),
                                  ),
                                  const SizedBox(
                                    width: 50,
                                  ),
                                  Text(
                                    widget.listTagihan![index].kode,
                                    style: const TextStyle(
                                        fontFamily: 'Mulish',
                                        fontSize: 16,
                                        color: Colors.black),
                                  ),
                                ],
                              ),
                              const SizedBox(height: 10),
                              Row(
                                children: [
                                  const Text(
                                    "Tanggal terbuat",
                                    style: TextStyle(
                                        fontFamily: 'Mulish',
                                        fontSize: 16,
                                        color: Colors.black),
                                  ),
                                  const SizedBox(
                                    width: 35,
                                  ),
                                  Text(
                                    widget.listTagihan![index].getTanggal(widget.listTagihan![index].tanggal_terbuat),
                                    style: const TextStyle(
                                        fontFamily: 'Mulish',
                                        fontSize: 16,
                                        color: Colors.black),
                                  ),
                                ],
                              ),
                              const SizedBox(height: 10),
                              Row(
                                children: [
                                  const Text(
                                    "Status",
                                    style: TextStyle(
                                        fontFamily: 'Mulish',
                                        fontSize: 16,
                                        color: Colors.black),
                                  ),
                                  const SizedBox(
                                    width: 100,
                                  ),
                                  Text(
                                    widget.listTagihan![index].getStatus(),
                                    style: const TextStyle(
                                        fontFamily: 'Mulish',
                                        fontSize: 16,
                                        color: Colors.black),
                                  ),
                                ],
                              ),
                              const SizedBox(height: 10),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  ElevatedButton(
                                      onPressed: () async {
                                        var response = await provider.get(AppUrl.detailTagihan+widget.listTagihan![index].kode);
                                        print(response.statusCode);
                                        if (response.statusCode == 200) {
                                          final Map<String, dynamic> res = json.decode(response.body);
                                          final Tagihan tagihanNew = Tagihan.fromJson(res);
                                          {Navigator.of(context).push(
                                            MaterialPageRoute(builder: (context) {
                                              return DetailTagihan(user: widget.user, tagihanDetail: tagihanNew);
                                            }),
                                          );
                                          }
                                        } else {
                                          print("gagal get Data");
                                        }
                                      },
                                      child: const Text("Detail")),
                                ],
                              ),
                            ],
                          ),
                        ),
                    ),
                  ],
                ),
                const SizedBox(height: 30),
                index == widget.listTagihan.length-1? buildBack() : Row(),
              ],
            ),
          );
        },
      ),
    );
  }
}
