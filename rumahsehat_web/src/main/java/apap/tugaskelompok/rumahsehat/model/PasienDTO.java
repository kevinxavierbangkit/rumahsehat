package apap.tugaskelompok.rumahsehat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasienDTO implements Serializable {
  private String nama;
  private String role;
  private String username;
  private String email;
  private int saldo;
  private Integer umur;
}
