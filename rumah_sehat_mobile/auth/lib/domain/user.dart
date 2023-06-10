class User {
  String nama;
  String role;
  String username;
  String email;
  int saldo;
  int umur;

  User({ 
    this.nama = "", 
    this.role = "", 
    this.username = "", 
    this.email = "", 
    this.saldo = 0,
    this.umur = 0});

  factory User.fromJson(Map<String, dynamic> responseData) {
    return User(
        nama: responseData['userInfo']['nama'],
        role: responseData['userInfo']['role'],
        username: responseData['userInfo']['username'],
        email: responseData['userInfo']['email'],
        saldo: responseData['userInfo']['saldo'],
        umur: responseData['userInfo']['umur'],
    );
  }

  factory User.fromJson2(Map<String, dynamic> responseData) {
    return User(
        nama: responseData['nama'],
        role: responseData['role'],
        username: responseData['username'],
        email: responseData['email'],
        saldo: responseData['saldo'],
        umur: responseData['umur'],
    );
  }

  Map<String, dynamic> toJson() => {
    "nama": nama,
    "role" : role,
    "username" : username,
    "email" : email,
    "saldo" : saldo,
    "umur" : umur
  };
}
