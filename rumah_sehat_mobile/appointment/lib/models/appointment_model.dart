class AppointmentModel {
  String kode;
  String waktuAwal;
  bool isDone;
  PasienModel pasien;
  DokterModel dokter;
  String? kodeTagihan;
  String? idResep;

  AppointmentModel(
      {required this.kode,
      required this.waktuAwal,
      required this.isDone,
      required this.pasien,
      required this.dokter,
      this.kodeTagihan,
      this.idResep});

  factory AppointmentModel.fromJson(Map<String, dynamic> json) =>
      AppointmentModel(
          kode: json['kode'],
          waktuAwal: json['waktuAwal'],
          isDone: json['isDone'],
          pasien: PasienModel.fromJson(json['pasien']),
          dokter: DokterModel.fromJson(json['dokter']),
          kodeTagihan: json['kode_tagihan'],
          idResep: json['id_resep']);
}

class DokterModel {
  String uuid;
  String nama;
  int tarif;

  DokterModel({required this.uuid, required this.nama, required this.tarif});

  factory DokterModel.fromJson(Map<String, dynamic> json) =>
      DokterModel(uuid: json['uuid'], nama: json['nama'], tarif: json['tarif']);
}

class PasienModel {
  String uuid;
  String nama;

  PasienModel({
    required this.uuid,
    required this.nama,
  });

  factory PasienModel.fromJson(Map<String, dynamic> json) =>
      PasienModel(uuid: json['uuid'], nama: json['nama']);
}
