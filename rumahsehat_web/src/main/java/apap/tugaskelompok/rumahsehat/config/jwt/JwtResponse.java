package apap.tugaskelompok.rumahsehat.config.jwt;

import apap.tugaskelompok.rumahsehat.model.PasienDTO;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {
  private static final long serialVersionUID = -8091879091924046844L;
  private PasienDTO userInfo;
  private String jwtToken;
}