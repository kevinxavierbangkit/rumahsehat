library appointment;

import 'package:auth/pages/dashboard.dart';
import 'package:appointment/appointment_add.dart';
import 'package:appointment/appointment_detail.dart';
import 'package:appointment/models/appointment_model.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'api/appointment_api.dart';
import 'package:auth/domain/user.dart';
import 'package:intl/intl.dart';

class Appointment extends StatefulWidget {
  static const routeName = '/appointment';
  User user;
  Appointment({Key? key, required this.user}) : super(key: key);

  @override
  _AppointmentState createState() => _AppointmentState();
}

class _AppointmentState extends State<Appointment> {
  late AppointmentModel selectedAppointment;

  AppointmentApi appointmentApi = AppointmentApi();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.arrow_back),
          onPressed: () {
            // Go back to the previous page
            Navigator.of(context).push(
              MaterialPageRoute(builder: (context) {
                return const DashBoard();
              }),
            );
          },
        ),
        title: const Text('Daftar Appointment'),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Text('+'),
        onPressed: () {
          Navigator.of(context).push(
            MaterialPageRoute(builder: (context) {
              return AppointmentAdd(user: widget.user);
            }),
          );
        },
      ),
      body: FutureBuilder(
        future: appointmentApi.getListAppointment(widget.user.username),
        builder: (BuildContext context,
            AsyncSnapshot<List<AppointmentModel>> snapshot) {
          if (snapshot.hasError) {
            return Center(
              child: Text(
                  "Something wrong with message: ${snapshot.error.toString()}"),
            );
          } else if (snapshot.connectionState == ConnectionState.done) {
            List<AppointmentModel>? listAppointment = snapshot.data;
            return _buildListView(listAppointment!);
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  Widget _buildListView(List<AppointmentModel> listAppointment) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      child: ListView.builder(
        itemBuilder: (context, index) {
          AppointmentModel appointment = listAppointment[index];
          DateFormat sourceFormat = DateFormat("yyyy-MM-dd'T'HH:mm");

          // Parse the source date/time string using the source format
          DateTime dateTime = sourceFormat.parse(appointment.waktuAwal);

          // The target format
          DateFormat targetFormat = DateFormat("dd-MM-yyyy HH:mm");

          // Format the DateTime object using the target format
          String waktuAwalFormat = targetFormat.format(dateTime);

          return Padding(
            padding: const EdgeInsets.only(top: 8.0),
            child: Card(
                margin: const EdgeInsets.only(top: 10),
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
                            appointment.dokter.nama,
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
                            appointment.isDone == true
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
                      Row(
                        children: [
                          TextButton(
                            style: TextButton.styleFrom(
                              foregroundColor: Colors.white,
                              backgroundColor: Colors.blueAccent,
                              disabledForegroundColor:
                                  Colors.grey.withOpacity(0.38),
                            ),
                            child: const Text(
                              'Detail',
                              style: TextStyle(fontSize: 15),
                            ),
                            onPressed: () {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => AppointmentDetail(
                                      appointment: appointment),
                                ),
                              );
                            },
                          )
                        ],
                      ),
                    ],
                  ),
                )),
          );
        },
        itemCount: listAppointment.length,
      ),
    );
  }
}
