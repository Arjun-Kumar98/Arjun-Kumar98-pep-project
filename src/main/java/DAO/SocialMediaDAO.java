package DAO;
import Model.Account;
import Model.Message;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import Util.ConnectionUtil;
public class SocialMediaDAO {

public Account registerUser(Account account){
Connection connection = ConnectionUtil.getConnection();

try{
  String sql = "insert into account(username,password) values(?,?)";
  PreparedStatement prepStmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

  prepStmt.setString(1,account.getUsername());
  prepStmt.setString(2,account.getPassword());

  prepStmt.executeUpdate();
  ResultSet pkeyResultSet = prepStmt.getGeneratedKeys();
if(pkeyResultSet.next()){
  int generated_account_id = (int) pkeyResultSet.getInt(1);
  return new Account(generated_account_id,account.getUsername(),account.getPassword());
}
}catch(SQLException e){
    System.out.println(e.getMessage());
}
return null;

}
public String findUser(String userName){
    Connection connection = ConnectionUtil.getConnection();
try{
   String sql = "select username from account where username=?";
   PreparedStatement prepStmt = connection.prepareStatement(sql);
   prepStmt.setString(1,userName);
   ResultSet rs = prepStmt.executeQuery();
 
while(rs.next()){
     String user = rs.getString("username");
     return user;
 }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}

public int findUserId(Integer userId){
    Connection connection = ConnectionUtil.getConnection();
    try{
        String sql = "select account_id from account where account_id=?";
        PreparedStatement prepStmt = connection.prepareStatement(sql);
        prepStmt.setInt(1,userId);
        ResultSet rs = prepStmt.executeQuery();
        while(rs.next()){
            int accountId = rs.getInt("account_id");
            return accountId;
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return 0;
}

public Account loginUser(Account account){
Connection connection = ConnectionUtil.getConnection();
try{
 String sql = "select account_id from account where username = ? and password = ?";
 PreparedStatement prepStmt = connection.prepareStatement(sql);
 prepStmt.setString(1,account.getUsername());
 prepStmt.setString(2,account.getPassword());
 ResultSet rs = prepStmt.executeQuery();
 while(rs.next()){
  Integer accountId = rs.getInt("account_id");
  return new Account(accountId,account.getUsername(),account.getPassword());
}}catch(SQLException e){
  System.out.println(e.getMessage());
}
return null;
}

public Message createMessage(Message message){
 Connection connection = ConnectionUtil.getConnection();
try{
     String sql = "insert into message(posted_by,message_text,time_posted_epoch) values(?,?,?)";
     PreparedStatement prepStmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
         prepStmt.setInt(1,message.getPosted_by());
         prepStmt.setString(2,message.getMessage_text());
         prepStmt.setLong(3,message.getTime_posted_epoch());
         prepStmt.executeUpdate();
         ResultSet rsKey = prepStmt.getGeneratedKeys();
         if(rsKey.next()){
            int messageId = rsKey.getInt(1);
           return new Message(messageId,message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
        }}
        catch(SQLException e){
    System.out.println(e.getMessage());
       }
    return null;
    }
    

public List<Message> retrieveAllMessages(){
Connection connection = ConnectionUtil.getConnection();
List<Message> messageList = new ArrayList<Message>();
try{
    String sql = "select * from message";
    PreparedStatement prepStmt = connection.prepareStatement(sql);
    ResultSet rs = prepStmt.executeQuery();
    while(rs.next()){
    Message messages = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
    messageList.add(messages);
    }
}catch(SQLException e){
    System.out.println(e.getMessage());
}
return messageList;
}

public Message retrieveMessageById(Integer messageId){
    Connection connection = ConnectionUtil.getConnection();
try{
    String sql = "select * from message where message_id=?";
    PreparedStatement prepStmt = connection.prepareStatement(sql);
    prepStmt.setInt(1,messageId);
    ResultSet rs = prepStmt.executeQuery();
    if(rs.next()){
        return new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
    }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}
public void deleteMessageById(Integer messageId){
Connection connection = ConnectionUtil.getConnection();
try{
    String sql = "delete from message where message_id=?";
    PreparedStatement prepStmt = connection.prepareStatement(sql);
    prepStmt.setInt(1,messageId);
    prepStmt.executeUpdate();
}catch(SQLException e){
    System.out.println(e.getMessage());
}
}

public int updateMessageById(Integer messageId, String messageTxt){
    Connection connection = ConnectionUtil.getConnection();
    try{
     String sql = "update message set message_text = ? where message_id=?";
     PreparedStatement prepStmt = connection.prepareStatement(sql);
     prepStmt.setInt(2,messageId);
     prepStmt.setString(1,messageTxt);
     int resultCheck = prepStmt.executeUpdate();
    if(resultCheck>=1){
        return resultCheck;
    }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
return 0;
}

public List<Message> retrieveMessageByUser(Integer accountId){
Connection connection = ConnectionUtil.getConnection();
List<Message> msgList = new ArrayList<Message>();
try{
    String sql = "select * from message where posted_by=?";
    PreparedStatement prepStmt = connection.prepareStatement(sql);
    prepStmt.setInt(1,accountId);
    ResultSet rs = prepStmt.executeQuery();
    while(rs.next()){
        Message messages = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
        msgList.add(messages);
    }}catch(SQLException e){
        System.out.println(e.getMessage());
    }
return msgList;
}
}