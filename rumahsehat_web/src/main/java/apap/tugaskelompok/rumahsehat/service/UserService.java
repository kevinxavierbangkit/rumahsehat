package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.*;

import java.util.List;

public interface UserService {
  UserModel add(UserModel user);
  public String encrypt(String password);
  UserModel getUserByUsername(String username);
  UserModel getUserById(String uuid);
  void deleteUser(UserModel user);
  List<UserModel> getAllUser();

  boolean checkOldPasss(UserModel user, String oldPass);

  boolean checkNewPass(String newPass, String ConfirmPass);

  UserModel updatePassword(UserModel user, String newPass);
}
