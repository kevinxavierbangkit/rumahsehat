class AppUrl {
  static const String liveBaseURL = "";
  static const String localBaseURL = "http://localhost:8080/api/v1";

  static const String baseURL = localBaseURL;
  static const String login = "$baseURL/auth/login";
  static const String register = "$baseURL/registration";
  static const String topUpSaldo = "$baseURL/pasien/top-up/";
  static const String daftarTagihan = "$baseURL/tagihan/";
  static const String detailTagihan = "$baseURL/tagihan/detail/";
}
