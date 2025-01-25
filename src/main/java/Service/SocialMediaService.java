package Service;
import Model.Message;
import Model.Account;
import DAO.SocialMediaDAO;
public class SocialMediaService {
    
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
int count = socialmediaDAO.findUser(username);
if(count==0){
return socialmediaDAO.registerUser(account);
}
}
return null;
}
}