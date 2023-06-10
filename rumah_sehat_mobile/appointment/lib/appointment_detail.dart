library appointment;

import 'package:appointment/appointment.dart';
import 'package:appointment/appointment_add.dart';
import 'package:appointment/models/appointment_model.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:auth/domain/user.dart';
import 'package:intl/intl.dart';
import 'api/appointment_api.dart';

class AppointmentDetail extends StatefulWidget {
  final AppointmentModel appointment;

  static const routeName = '/appointment/detail';
  const AppointmentDetail({Key? key, required this.appointment})
      : super(key: key);

  @override
  _AppointmentDetailState createState() => _AppointmentDetailState();
}

class _AppointmentDetailState extends State<AppointmentDetail> {
  @override
  Widget build(BuildContext context) {
    DateFormat sourceFormat = DateFormat("yyyy-MM-dd'T'HH:mm");

    // Parse the source date/time string using the source format
    DateTime dateTime = sourceFormat.parse(widget.appointment.waktuAwal);

    // The target format
    DateFormat targetFormat = DateFormat("dd-MM-yyyy HH:mm");

    // Format the DateTime object using the target format
    String waktuAwalFormat = targetFormat.format(dateTime);

    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: () {
            // Go back to the previous page
            Navigator.pop(context);
          },
        ),
        title: const Text('Appointment'),
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        shrinkWrap: true,
        children: [
          Column(
            children: [
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
                              "Kode Appointment",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 72,
                            ),
                            Text(
                              widget.appointment.kode,
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
                              "Nama Dokter",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 110,
                            ),
                            Text(
                              widget.appointment.dokter.nama,
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
                              "Nama Pasien",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 109,
                            ),
                            Text(
                              widget.appointment.pasien.nama,
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
                              "Waktu Awal",
                              style: TextStyle(
                                  fontFamily: 'Mulish',
                                  fontSize: 16,
                                  color: Colors.black),
                            ),
                            const SizedBox(
                              width: 119.5,
                            ),
                            Text(
                              waktuAwalFormat,
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
                              width: 157,
                            ),
                            Text(
                              widget.appointment.isDone == true
                                  ? "Selesai"
                                  : "Belum Selesai",
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
              widget.appointment.idResep != null
                  ? Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        ElevatedButton(
                            onPressed: () {}, child: const Text("Detail Resep"))
                      ],
                    )
                  : Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: const [
                        Text(
                          'Tidak ada resep',
                          style: TextStyle(
                              fontFamily: 'Mulish',
                              fontSize: 16,
                              color: Colors.black),
                        ),
                      ],
                    )
            ],
          ),
        ],
      ),
    );
  }
}
