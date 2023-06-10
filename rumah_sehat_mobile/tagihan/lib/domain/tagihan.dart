class Tagihan {
  String kode;
  String tanggal_terbuat;
  String tanggal_bayar;
  bool is_paid;
  int jumlah_tagihan;

  Tagihan({
    this.kode = "",
    this.tanggal_terbuat = "",
    this.tanggal_bayar = "",
    this.is_paid = false,
    this.jumlah_tagihan = 0
  });

  factory Tagihan.fromJson(Map<String, dynamic> responseData){
    if(responseData['tanggalBayar'] == null){
      responseData['tanggalBayar'] = "";
    }
    return Tagihan(
      kode: responseData['kode'],
      tanggal_terbuat: responseData['tanggalTerbuat'],
      is_paid: responseData['isPaid'],
      jumlah_tagihan: responseData['jumlahTagihan'],
      tanggal_bayar: responseData['tanggalBayar'],
    );
  }

  Map<String, dynamic> toJson() => {
    "kode": kode,
    "tanggalTerbuat": tanggal_terbuat,
    "tanggalBayar": tanggal_bayar,
    "isPaid": is_paid,
    "jumlahTagihan" : jumlah_tagihan
  };

  String getStatus(){
    if (this.is_paid){
      return "Lunas";
    }
    return "Belum Lunas";
  }
  
  String getTanggal(String tanggal){
    if(tanggal == null || tanggal == ""){
      return "-";
    }
    return tanggal.substring(0,10);
  }
}