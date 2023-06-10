package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.UserModel;
import apap.tugaskelompok.rumahsehat.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  @Autowired
  UserDb userDb;

  @Override
  @PreAuthorize("hasAuthority('Admin')")
  public UserModel add(UserModel user) {
    String pass = encrypt(user.getPassword());
    user.setPassword(pass);
    return userDb.save(user);
  }

  @Override
  public String encrypt(String password) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = passwordEncoder.encode(password);
    return hashedPassword;
  }

  @Override
  public UserModel getUserByUsername(String username){
    return userDb.findByUsername(username);
  }

  @Override
  public UserModel getUserById(String uuid){
    return userDb.findByUuid(uuid);
  }

  @Override
  public List<UserModel> getAllUser(){
    return userDb.findAll();
  }

  @Override
  @PreAuthorize("hasAuthority('Admin')")
  public void deleteUser(UserModel user){
    userDb.delete(user);
  }

  @Override
  public boolean checkOldPasss(UserModel user, String oldPass) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.matches(oldPass, user.getPassword());
  }

  @Override
  public boolean checkNewPass(String newPass, String confirmPass) {
    return newPass.equals(confirmPass);
  }

  @Override
  public UserModel updatePassword(UserModel user, String newPass){
    user.setPassword(encrypt(newPass));
    return userDb.save(user);
  }
}
