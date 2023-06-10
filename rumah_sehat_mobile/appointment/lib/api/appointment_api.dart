import 'package:appointment/models/appointment_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:auth/util/app_url.dart';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:intl/intl.dart';

class AppointmentApi extends ChangeNotifier {
  String baseURL = AppUrl.baseURL;

  List<AppointmentModel> _data = [];
  List<AppointmentModel> get listAppointment => _data;

  List<DokterModel> _dataDokter = [];
  List<DokterModel> get listDokter => _dataDokter;

  Future<List<AppointmentModel>> getListAppointment(String username) async {
    final url = '$baseURL/appointment/$username';
    final response = await http.get(Uri.parse(url));

    if (response.statusCode == 200) {
      final result = jsonDecode(response.body);
      _data = result
          .map<AppointmentModel>((json) => AppointmentModel.fromJson(json))
          .toList();
      return _data;
    } else {
      throw Exception();
    }
  }

  Future<bool> addAppointment(
      String username_pasien, String waktuAwal, String? uuid_dokter) async {
    DateTime date = DateFormat("dd-MM-yyyy HH:mm").parse(waktuAwal);
    String formattedDate = DateFormat("yyyy-MM-ddTHH:mm").format(date);

    final urlDokter = '$baseURL/appointment/dokter/$uuid_dokter';
    final responseDokter = await http.get(Uri.parse(urlDokter));

    final urlPasien = '$baseURL/appointment/pasien/$username_pasien';
    final responsePasien = await http.get(Uri.parse(urlPasien));

    Map<String, dynamic> jsonPasien = json.decode(responsePasien.body);
    Map<String, dynamic> jsonDokter = json.decode(responseDokter.body);

    Map<String, dynamic> jsonData = {
      'waktuAwal': formattedDate,
      'isDone': "false",
      'pasien': jsonPasien,
      'dokter': jsonDokter,
    };

    String jsonString = json.encode(jsonData);

    print(jsonString);

    final url = '$baseURL/appointment/add';
    final response = await http.post(
      Uri.parse(url),
      headers: {
        "Content-Type": "application/json",
      },
      body: jsonString,
    );

    print(response.statusCode);
    print(response.body);

    if (response.statusCode == 200) {
      print("berhasil");
      notifyListeners();
      return true;
    }
    print("gagal");
    return false;
  }

  Future<List<DokterModel>> getListDokter() async {
    final url = '$baseURL/appointment/dokter';
    final response = await http.get(Uri.parse(url));

    if (response.statusCode == 200) {
      final result = json.decode(response.body);
      _dataDokter = result
          .map<DokterModel>((json) => DokterModel.fromJson(json))
          .toList();

      return _dataDokter;
    } else {
      throw Exception();
    }
  }
}