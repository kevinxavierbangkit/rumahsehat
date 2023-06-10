// ignore_for_file: depend_on_referenced_packages, library_private_types_in_public_api

import 'package:flutter/material.dart';
import 'package:auth/domain/user.dart';
import 'package:saldo/saldo.dart';

class Profile extends StatefulWidget {
  static const routeName = '/profil';

  User user;
  Profile({ Key? key, required this.user}) : super(key: key);

  @override
  _ProfileState createState() => _ProfileState();
}

class _ProfileState extends State<Profile> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          "Profil",
        ),
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        shrinkWrap: true,
        children: [
          Column(
            children: [
              // Container(
              //     margin: const EdgeInsets.only(top: 30),
              //     child: Text(
              //       "Halo ${widget.user.nama}",
              //       style: const TextStyle(
              //         fontSize: 20,
              //         fontFamily: 'Mulish',
              //         fontWeight: FontWeight.bold,
              //         color: Color(0xff131313),
              //       ),
              //     )),
              Card(
                  margin: const EdgeInsets.only(top: 20),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20),
                  ),
                  child: Padding(
                    padding: const EdgeInsets.only(
                        top: 10, left: 15, right: 15, bottom: 10),
                    child: Column(
                      children: [
                        const SizedBox(height: 20),
                        Row(
                          children: [
                            const Text(
                              "Nama",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 91,
                            ),
                            Text(
                              widget.user.nama,
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
                              "E-mail",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 91,
                            ),
                            Text(
                              widget.user.email,
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
                              "Umur",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 93,
                            ),
                            Text(
                              widget.user.umur.toString(),
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
                              "Saldo",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 93,
                            ),
                            Text(
                              widget.user.saldo.toString(),
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
                              "Username",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 60,
                            ),
                            Text(
                              widget.user.username,
                              style: const TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                          ],
                        ),
                        const SizedBox(height: 10),
                      ],
                    ),
                  )),
              const SizedBox(height: 32),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton(
                      onPressed: () {
                        {
                          Navigator.of(context).push(
                            MaterialPageRoute(builder: (context) {
                              return TopUpSaldo(user: widget.user);
                            }),
                          );
                        }
                      },
                      child: const Text("Top Up Saldo"))
                ],
              ),
            ],
          ),
        ],
      ),
    );
  }
}
