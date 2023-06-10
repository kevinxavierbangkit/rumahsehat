package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.model.AdminModel;
import apap.tugaskelompok.rumahsehat.model.UserModel;
import apap.tugaskelompok.rumahsehat.security.xml.Attributes;
import apap.tugaskelompok.rumahsehat.security.xml.ServiceResponse;
import apap.tugaskelompok.rumahsehat.service.UserService;
import apap.tugaskelompok.rumahsehat.setting.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

import static apap.tugaskelompok.rumahsehat.security.whiteListSSO.whiteListAdmin;

@Controller
public class PageController {
  private WebClient webClient = WebClient.builder().build();

  @Autowired
  ServerProperties serverProperties;

  @Autowired
  UserService userService;

  @Autowired
  Setting setting;


  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/login")
  public String login(Model model) {
    return "auth/login";
  }

  @GetMapping("/validate-ticket")
  public ModelAndView adminLoginsso(
          @RequestParam(value = "ticket", required = false) String ticket,
          HttpServletRequest request) {
    ServiceResponse serviceResponse = this.webClient.get().uri(
            String.format(
                    setting.getSERVER_VALIDATE_TICKET(),
                    ticket,
                    setting.getCLIENT_LOGIN())
    ).retrieve().bodyToMono(ServiceResponse.class).block();

    String nama = "";
    String username = "";
    if (serviceResponse != null){
      Attributes attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
      username = serviceResponse.getAuthenticationSuccess().getUser();
      nama = attributes.getNama();
    }

    UserModel user = userService.getUserByUsername(username);
    Boolean isAdmin = false;
    if (user == null) {
      for (int i=0; i < whiteListAdmin.size(); i++){
        if (username.equals(whiteListAdmin.get(i))){
          isAdmin = true;
        }
      }
    }

    if (isAdmin == true){
      AdminModel admin = new AdminModel();
      admin.setEmail(username + "@ui.ac.id");
      admin.setNama(nama);
      admin.setPassword("rumahsehat");
      admin.setUsername(username);
      admin.setRole("Admin");
      admin.setIsSSO(true);

      userService.add(admin);
    }

    Authentication authentication = new UsernamePasswordAuthenticationToken(username, "rumahsehat");
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);
    HttpSession httpSession = request.getSession(true);
    httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
    return new ModelAndView("redirect:/");
  }

  @GetMapping(value="/login-sso")
  public ModelAndView loginSSO(){
    return new ModelAndView("redirect:"+ setting.getSERVER_LOGIN() + setting.getCLIENT_LOGIN());
  }

  @GetMapping(value = "/logout-sso")
  public ModelAndView logoutSSO(Principal principal) {
    UserModel user = userService.getUserByUsername(principal.getName());
    if (user.getIsSSO() == false){
      return new ModelAndView("redirect:/logout");
    }
    return new ModelAndView( "redirect:" + setting.getSERVER_LOGOUT() + setting.getCLIENT_LOGOUT());
  }
}