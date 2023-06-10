package apap.tugaskelompok.rumahsehat.setting;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Setting {
  public String CLIENT_BASE_URL;
  public String CLIENT_LOGIN;
  public String CLIENT_LOGOUT;
  public String SERVER_BASE_URL = "https://sso.ui.ac.id/cas2";
  public String SERVER_LOGIN = SERVER_BASE_URL + "/login?service=" ;
  public String SERVER_LOGOUT = SERVER_BASE_URL + "/logout?url=";
  public String SERVER_VALIDATE_TICKET = SERVER_BASE_URL + "/serviceValidate?ticket=%s&service=%s" ;

  public Setting(@Value("${sso.url}") String ssoUrl) {
    this.CLIENT_BASE_URL = ssoUrl;
    this.CLIENT_LOGIN = CLIENT_BASE_URL + "/validate-ticket";
    this.CLIENT_LOGOUT = CLIENT_BASE_URL + "/logout";
  }

}