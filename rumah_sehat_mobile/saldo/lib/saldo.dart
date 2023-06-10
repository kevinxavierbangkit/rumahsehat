// ignore_for_file: prefer_const_constructors, depend_on_referenced_packages

import 'dart:convert';

import 'package:auth/util/app_url.dart';
import 'package:flutter/material.dart';
import 'package:auth/domain/user.dart';
import 'package:auth/providers/auth.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import 'package:profil/profil.dart';

class TopUpSaldo extends StatefulWidget {
  static const routeName = '/topUpSaldo';
  User user;
  TopUpSaldo({super.key, required this.user});
  @override
  _TopUpSaldoState createState() => _TopUpSaldoState();
}

class _TopUpSaldoState extends State<TopUpSaldo> {

  final number = TextEditingController();

  Widget buildSaldo() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        const Text(
          'Saldo',
          style: TextStyle(
              color: Colors.green,
              fontSize: 16,
              fontWeight: FontWeight.bold
          ),
        ),
        const SizedBox(height: 10),
        Container(
          alignment: Alignment.centerLeft,
          decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(10),
              boxShadow: const [
                BoxShadow(
                    color: Colors.black26,
                    blurRadius: 6,
                    offset: Offset(0,2)
                )
              ]
          ),
          height: 60,
          child: TextFormField(
            controller: number,
            keyboardType: TextInputType.number,
            inputFormatters: <TextInputFormatter>[FilteringTextInputFormatter.digitsOnly],
            style: const TextStyle (
                color: Colors.black87
            ),
            decoration: const InputDecoration(
                border: InputBorder.none,
                contentPadding: EdgeInsets.only(top: 14),
                prefixIcon: Icon(
                  Icons.money,
                  color: Color(0xff5ac18e),
                ),
                hintText: 'Masukan saldo tambahan',
                hintStyle: TextStyle(
                    color: Colors.black38
                )
            ),
            // onChanged: (String? value) {
            //   setState(() {
            //     username = value!;
            //   });
            // },
            //   onSaved: (String? value) {
            //   setState(() {
            //     username = value!;
            //   });
            // },
            // autovalidateMode: AutovalidateMode.onUserInteraction,
            validator: (value) {
              if (value == null || value.isEmpty) {
                return "Saldo tidak boleh kosong";
              }
              else {
                // saldoTambah = int.parse(value);
                return value;
              }
            },
          ),
        )
      ],
    );
  }

  @override
  Widget build(BuildContext context) {

    int saldoAwal = 0;
    int saldoTambah = 0;
    int saldoAkhir = 0;

    final provider = Provider.of<AuthProvider>(context);
    // final user = provider.getUser() as User;

    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color(0xff131313),
        title: const Text(
          "Tambah Saldo",
          textScaleFactor: 1.3,
        ),
      ),
      body: AnnotatedRegion<SystemUiOverlayStyle>(
        value: SystemUiOverlayStyle.light,
        child: GestureDetector(
          child: Stack (
            children: <Widget>[
              Container(
                height: double.infinity,
                width: double.infinity,

                child: SingleChildScrollView(
                  physics: const AlwaysScrollableScrollPhysics(),
                  padding: const EdgeInsets.symmetric(
                      horizontal: 25,
                      vertical: 120
                  ),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      const Text(
                        'Top Up Saldo',
                        style: TextStyle(
                            fontSize: 40,
                            fontWeight: FontWeight.bold
                        ),
                      ),
                      const SizedBox(height: 50),
                      buildSaldo(),
                      Container(
                        padding: EdgeInsets.symmetric(vertical:25),
                        width: double.infinity,
                        child: ElevatedButton(
                          // elevation: 5,
                          onPressed: () async {
                            print("tambah saldo onpress");
                            var res = int.parse(number.text);
                            print(res);
                            saldoTambah = res;
                            saldoAwal = widget.user.saldo;
                            saldoAkhir = saldoAwal + saldoTambah;
                            print(saldoAkhir);
                            widget.user.saldo = saldoAkhir;
                            print(widget.user.toJson());
                            provider.saveUser(widget.user);
                            provider.put(
                                AppUrl.topUpSaldo + widget.user.username,
                                jsonEncode(widget.user.toJson()));
                            if (true) {
                              ScaffoldMessenger.of(context)
                                  .showSnackBar(SnackBar(
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
                                  child: Center(
                                    child: Text(
                                     "Saldo berhasil ditambah sebanyak $saldoTambah",
                                      textAlign: TextAlign.center,
                                    ),
                                  ),
                                ),
                              ));
                              Navigator.of(context).push(
                                MaterialPageRoute(builder: (context) {
                                  return Profile(user: widget.user);
                                }),
                              );
                            }
                          },
                          style: ElevatedButton.styleFrom(
                            primary: Colors.white,
                            padding: EdgeInsets.all(15),
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15)
                            ),
                          ),
                          child: const Text(
                            'TAMBAH',
                            style: TextStyle(
                                color: Colors.amber,
                                fontSize: 18,
                                fontWeight: FontWeight.bold
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}