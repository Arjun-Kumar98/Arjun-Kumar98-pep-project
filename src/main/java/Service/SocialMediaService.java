package Service;
import Model.Message;
import Model.Account;
import DAO.SocialMediaDAO;
import java.util.List;
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

public Message createMessage(Message message){
   String messageTxt = message.getMessage_text();
   if(messageTxt.length()>=1&&messageTxt.length()<=255){
       int checker = socialmediaDAO.findUserId(message.getPosted_by());
       if(checker!=0){
         return socialmediaDAO.createMessage(message);
       }
       return null;
   }
   return null;
}



public List<Message> fetchAllMessages(){
   return socialmediaDAO.retrieveMessage();
}

public Message fetchMessageById(int messageId){
   return socialmediaDAO.retrieveMessageById(messageId);
}

public Message deleteMessageById(Integer messageId){
   Message message = socialmediaDAO.retrieveMessageById(messageId);
   if(message!=null){
       socialmediaDAO.deleteMessage(messageId);
       return message;
}
return null;
}


}
