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
   logger.info("I am called here ");
String username = account.getUsername();
String password = account.getPassword();
return socialmediaDAO.registerUser(account);
// if(username.length()>=1&&password.length()>=4){
// int count = socialmediaDAO.findUser(username);
// logger.info("I am called here ");
// if(count==0){
// return socialmediaDAO.registerUser(account);
// }
// }
// return null;
}
}