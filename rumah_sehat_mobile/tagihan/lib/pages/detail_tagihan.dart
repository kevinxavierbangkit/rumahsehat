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
import 'package:tagihan/pages/daftar_tagihan.dart';

class DetailTagihan extends StatefulWidget {
  static const routeName = '/detailTagihan';
  User user;
  Tagihan tagihanDetail;
  DetailTagihan({super.key, required this.user, required this.tagihanDetail});
  @override
  _DetailTagihanState createState() => _DetailTagihanState();
}

class _DetailTagihanState extends State<DetailTagihan> {

  @override
  Widget build(BuildContext context) {

    final provider = Provider.of<AuthProvider>(context);
    bool lunas = widget.tagihanDetail.is_paid;
    // final user = provider.getUser() as User;
    // final args = ModalRoute.of(context)!.settings.arguments as User;

    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color(0xff131313),
        title: const Text(
          "Detail Tagihan",
          textScaleFactor: 1.3,
        ),
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        shrinkWrap: true,
        children: [
          Column(
            children: [
              Container(
                  margin: const EdgeInsets.only(top: 30),
                  child: const Text(
                    "Tagihan",
                    style: TextStyle(
                      fontSize: 30,
                      fontFamily: 'Mulish',
                      fontWeight: FontWeight.bold,
                      color: const Color(0xff131313),
                    ),
                  )),
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
                        Row(
                          children: [
                            const Text(
                              "Kode",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 110,
                            ),
                            Text(
                              widget.tagihanDetail.kode,
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
                              "Jumlah Tagihan",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 30,
                            ),
                            Text(
                              (widget.tagihanDetail.jumlah_tagihan).toString(),
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
                              widget.tagihanDetail.getStatus(),
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
                              "Tanggal Terbuat",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 30,
                            ),
                            Text(
                              widget.tagihanDetail.getTanggal(widget.tagihanDetail.tanggal_terbuat),
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
                              "Tanggal Bayar",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 30,
                            ),
                            Text(
                              widget.tagihanDetail.getTanggal(widget.tagihanDetail.tanggal_bayar),
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
              Container(
                child: (!lunas)?
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      ElevatedButton(
                          onPressed: () {
                            _confirmationAlert(widget.user, widget.tagihanDetail, provider);
                          },
                          child: const Text("Bayar Tagihan"))
                    ],
                ):
                    Row(),
              ),
              Container(
                child: ElevatedButton(
                    onPressed: () async {
                      var response = await provider.get(AppUrl.daftarTagihan+widget.user.username);
                      print(response.statusCode);
                      if (response.statusCode == 200) {
                        List jsonRes = json.decode(response.body);
                        print(jsonRes);
                        List<Tagihan> listTagihan = jsonRes.map((data) => new Tagihan.fromJson(data)).toList();
                        print(listTagihan);
                        {Navigator.of(context).push(
                          MaterialPageRoute(builder: (context) {
                            return DaftarTagihan(user: widget.user, listTagihan: listTagihan);
                          }),
                        );
                        }
                      } else {
                        print("gagal get Data");
                      }
                    },
                    child: const Text("Kembali ke Daftar Tagihan")),
              )
            ],
          ),
        ],
      ),
    );
  }

  Future<void> _confirmationAlert(User user, Tagihan detailTagihan, AuthProvider provider) async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Konfirmasi Pembayaran'),
          content: SingleChildScrollView(
            child: Column(
              children: <Widget>[
                Text('Apakah Anda yakin melanjutkan pembayaran tagihan ini?'),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Ya'),
              onPressed: () {
                print('Confirmed');

                int saldo = user.saldo;
                int jumlahTagihan = detailTagihan.jumlah_tagihan;

                if(jumlahTagihan > saldo){
                  _messageGagal();
                }
                else {
                  int saldoAkhir = saldo - jumlahTagihan;
                  print(saldoAkhir);
                  user.saldo = saldoAkhir;
                  print(user.toJson());
                  provider.saveUser(user);
                  
                  provider.get(AppUrl.baseURL+"/tagihan/"+user.username+"/bayar/"+detailTagihan.kode);
                  _messageSukses(user, widget.tagihanDetail, provider);
                }
                // Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('Batal'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  Future<void> _messageSukses(User user, Tagihan tagihanDetail, AuthProvider provider) async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Pembayaran Berhasil'),
          content: SingleChildScrollView(
            child: Column(
              children: <Widget>[
                Text('Tagihan dengan kode '+widget.tagihanDetail.kode+' telah lunas.'),
                Text('Saldo berkurang sebanyak '+ (widget.tagihanDetail.jumlah_tagihan.toString())),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('OK'),
              onPressed: () async {
                var response = await provider.get(AppUrl.detailTagihan+tagihanDetail.kode);
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
            ),
          ],
        );
      },
    );
  }

  Future<void> _messageGagal() async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Pembayaran Dibatalkan'),
          content: SingleChildScrollView(
            child: Column(
              children: <Widget>[
                Text('Saldo yang dimiliki tidak mencukup untuk melakukan pembayaran ini.'),
                Text('Harap lakukan top up saldo terlebih dahulu.'),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Ok'),
              onPressed: () {
                print('Confirmed gagal');
                // Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}