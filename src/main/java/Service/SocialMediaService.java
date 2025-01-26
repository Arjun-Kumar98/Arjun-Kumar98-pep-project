package Service;
import Model.Message;
import Model.Account;
import DAO.SocialMediaDAO;
import java.util.List;


public class SocialMediaService {
SocialMediaDAO socialMediaDAO;

public SocialMediaService(){
   socialMediaDAO = new SocialMediaDAO();
} 

public SocialMediaService(SocialMediaDAO socialMediaDAO){
   this.socialMediaDAO = socialMediaDAO;
}

public int checkUserHelper(int userId){
   int userCheck = socialMediaDAO.findUserId(userId);
   return userCheck;
}

public Account addAccount(Account account){
String username = account.getUsername();
String password = account.getPassword();
if(username.length()>=1&&password.length()>=4){
String userCheck = socialMediaDAO.findUser(username);
if(userCheck==null){
return socialMediaDAO.registerUser(account);
}
}
return null;
}

public Account accountLogin(Account account){
   return socialMediaDAO.loginUser(account);
}

public Message createMessage(Message message){
   String messageTxt = message.getMessage_text();
   if(messageTxt.length()>=1&&messageTxt.length()<=255){
       int userCheck = checkUserHelper(message.getPosted_by());
       if(userCheck!=0){
         return socialMediaDAO.createMessage(message);
       }
       return null;
   }
   return null;
}



public List<Message> retrieveAllMessages(){
   return socialMediaDAO.retrieveAllMessages();
}

public Message retrieveMessageById(int messageId){
   return socialMediaDAO.retrieveMessageById(messageId);
}

public Message deleteMessageById(int messageId){
   Message message = socialMediaDAO.retrieveMessageById(messageId);
   if(message!=null){
       socialMediaDAO.deleteMessageById(messageId);
       return message;
}
return null;
}

public Message updateMessageById(int messageId,String messageTxt){
   if(messageTxt.length()>=1 && messageTxt.length()<=255){
   Message message = socialMediaDAO.retrieveMessageById(messageId);
   if(message!=null){
      int updateMsg = socialMediaDAO.updateMessageById(messageId, messageTxt);
      if(updateMsg>=1){
      message = socialMediaDAO.retrieveMessageById(messageId);
         return message;
      }else{
         return null;
      }
   }
}
   return null;
}

public List<Message> retrieveMessageByUser(int accountId){
  return socialMediaDAO.retrieveMessageByUser(accountId);

}

}
