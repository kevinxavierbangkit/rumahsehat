library appointment;

import 'package:appointment/appointment.dart';
import 'package:appointment/models/appointment_model.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:intl/intl.dart';
import 'api/appointment_api.dart';
import 'package:auth/domain/user.dart';

class AppointmentAdd extends StatefulWidget {
  static const routeName = '/appointment/add';
  User user;
  AppointmentAdd({Key? key, required this.user}) : super(key: key);

  @override
  _AppointmentAddState createState() => _AppointmentAddState();
}

class _AppointmentAddState extends State<AppointmentAdd> {
  final TextEditingController _waktuAwal = TextEditingController();

  AppointmentApi appointmentApi = AppointmentApi();
  String? _selectedDoctorId;

  void submit(BuildContext context) {
    print("submit");
    appointmentApi
        .addAppointment(
            widget.user.username, _waktuAwal.text, _selectedDoctorId)
        .then((res) {
      if (res) {
        Navigator.of(context).push(
          MaterialPageRoute(builder: (context) {
            return Appointment(user: widget.user);
          }),
        );
      } else {}
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
            icon: Icon(Icons.arrow_back),
            onPressed: () {
              // Go back to the previous page
              Navigator.pop(context);
            }),
        title: const Text('Add Appointment'),
        actions: <Widget>[
          TextButton(
            style: TextButton.styleFrom(
              foregroundColor: Colors.white,
              backgroundColor: Colors.greenAccent,
              disabledForegroundColor: Colors.grey.withOpacity(0.38),
            ),
            child: const Text(
              'Save',
              style: TextStyle(fontSize: 15),
            ),
            onPressed: () => submit(context),
          )
        ],
      ),
      body: FutureBuilder(
        future: appointmentApi.getListDokter(),
        builder:
            (BuildContext context, AsyncSnapshot<List<DokterModel>> snapshot) {
          if (snapshot.hasError) {
            return Center(
              child: Text(
                  "Something wrong with message: ${snapshot.error.toString()}"),
            );
          } else if (snapshot.connectionState == ConnectionState.done) {
            List<DokterModel>? listDokter = snapshot.data;
            return _buildListView(listDokter!);
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  Widget _buildListView(List<DokterModel> listDokter) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      child: ListView(children: <Widget>[
        TextField(
          controller: _waktuAwal, //editing controller of this TextField
          decoration: const InputDecoration(
              icon: Icon(Icons.calendar_today), //icon of text field
              labelText: "Waktu Awal" //label text of field
              ),

          readOnly: true, //set it true, so that user will not able to edit text
          onTap: () async {
            final date = await showDatePicker(
                context: context,
                initialDate: DateTime.now(),
                firstDate: DateTime(2000),
                lastDate: DateTime(2101));

            if (date != null) {
              final time = await showTimePicker(
                  context: context,
                  initialTime: TimeOfDay.fromDateTime(DateTime.now()));

              final dateTime = DateTime(
                  date.year, date.month, date.day, time!.hour, time.minute);

              String formattedDate =
                  DateFormat('dd-MM-yyyy HH:mm').format(dateTime);
              setState(() {
                _waktuAwal.text =
                    formattedDate; //set output date to TextField value.
              });
            }
          },
        ),
        DropdownButton(
          hint: _selectedDoctorId == null
              ? Text('Pilih Dokter')
              : Text(
                  _selectedDoctorId!,
                ),
          value: _selectedDoctorId,
          onChanged: (newValue) {
            setState(() {
              _selectedDoctorId = newValue!;
            });
          },
          items: listDokter.map((dokter) {
            return DropdownMenuItem(
              value: dokter.uuid,
              child: Text("${dokter.nama} - ${dokter.tarif}"),
            );
          }).toList(),
        )
      ]),
    );
  }
}
