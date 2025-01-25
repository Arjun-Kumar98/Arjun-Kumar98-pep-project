package DAO;
import Model.Account;
import Model.Message;
import java.util.List;
import java.sql.*;
import Util.ConnectionUtil;
public class SocialMediaDAO {

public Account registerUser(Account account){
Connection connection = ConnectionUtil.getConnection();

try{
    String sql = "insert into account(username,password) values(?,?)";
PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

preparedStatement.setString(1,account.getUsername());
preparedStatement.setString(2,account.getPassword());

preparedStatement.executeUpdate();
ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
if(pkeyResultSet.next()){
int generated_account_id = (int) pkeyResultSet.getInt(1);
return new Account(generated_account_id,account.getUsername(),account.getPassword());
}
}catch(SQLException e){
    System.out.println(e.getMessage());
}
return null;

}
public int findUser(String userName){
    Connection connection = ConnectionUtil.getConnection();
    try{
   String sql = "select count(username) from account where username=?";
   PreparedStatement preparedStatement = connection.prepareStatement(sql);

  ResultSet rs = preparedStatement.executeQuery();
 int counter=0;
// while(rs.next()){
//     counter = rs.getInt();
// }
return counter;
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return 0;
}

public Account loginUser(Account account){

return null;
}

public Message createMessage(Message message){

return null;
}

public List<Message> retrieveMessage(){

return null;
}

public void deleteMessage(Integer messageId){

}

public Message updateMessage(Message message){
return null;
}

public List<Message> getMessages(Integer userId){

return null;
}
}