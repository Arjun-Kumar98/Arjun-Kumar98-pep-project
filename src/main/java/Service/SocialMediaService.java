package Service;
import Model.Message;
import Model.Account;
import DAO.SocialMediaDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocialMediaService {
   private static final Logger logger = LoggerFactory.getLogger(SocialMediaService.class);

SocialMediaDAO socialmediaDAO;

public SocialMediaService(){
   socialmediaDAO = new SocialMediaDAO();
} 

public SocialMediaService(SocialMediaDAO socialmediaDAO){
   this.socialmediaDAO = socialmediaDAO;
}

public Account addAccount(Account account){
String username = account.getUsername();
String password = account.getPassword();
if(username.length()>=1&&password.length()>=4){
String checker= socialmediaDAO.findUser(username);
if(checker==null){
return socialmediaDAO.registerUser(account);
}
}
return null;
}

public Account accountLogin(Account account){
   return socialmediaDAO.loginUser(account);
}
}
