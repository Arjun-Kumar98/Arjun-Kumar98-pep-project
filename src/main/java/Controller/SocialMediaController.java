package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.SocialMediaService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
 SocialMediaService socialMediaService;

 public SocialMediaController(){
    socialMediaService = new SocialMediaService();
 }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

       app.post("/register",this::registerAccountHandler);
       app.post("/login",this::accountLoginHandler);
       app.post("/messages",this::createMessageHandler);
       app.get("/messages",this::retrieveAllMessagesHandler);
       app.get("/messages/{message_id}",this::retrieveMessageByIdHandler);
       app.delete("/messages/{message_id}",this::deleteMessageByIdHandler);
       app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
       app.get("/accounts/{account_id}/messages",this::retrieveMessageByUserHandler);
       app.get("example-endpoint", this::exampleHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

   private void registerAccountHandler(Context ctx) throws JsonProcessingException {
     String jsonString = ctx.body();
    ObjectMapper mapper = new ObjectMapper();
    Account account = mapper.readValue(jsonString,Account.class);
   Account registeredAccount = socialMediaService.addAccount(account);
   if(registeredAccount == null){
        ctx.status(400);
    }else{
       ctx.status(200);
       ctx.json(mapper.writeValueAsString(registeredAccount)); 
  }
}

  private void accountLoginHandler(Context ctx) throws JsonProcessingException{
    String jsonString = ctx.body();
    ObjectMapper mapper = new ObjectMapper();
  
    Account account = mapper.readValue(jsonString,Account.class);
   Account loginCheck = socialMediaService.accountLogin(account);
    if(loginCheck==null){
        ctx.status(401);
       // ctx.result("Unauthorized login");
    }else{
        ctx.status(200);
        ctx.json(mapper.writeValueAsString(loginCheck));
     //   ctx.result("Successful login");
    }
  }

  private void createMessageHandler(Context ctx) throws JsonProcessingException{
    String jsonString = ctx.body();
    ObjectMapper mapper = new ObjectMapper();
    Message message = mapper.readValue(jsonString,Message.class);
    Message messageCheck = socialMediaService.createMessage(message);
    if(messageCheck==null){
       ctx.status(400);
    }else{
       ctx.status(200);
       ctx.json(mapper.writeValueAsString(messageCheck));
    }
  }

  private void retrieveAllMessagesHandler(Context ctx) throws JsonProcessingException{
    ObjectMapper mapper = new ObjectMapper();
    List<Message> messageList = socialMediaService.retrieveAllMessages();
    ctx.status(200);
    ctx.json(mapper.writeValueAsString(messageList));
  }

  private void retrieveMessageByIdHandler(Context ctx) throws JsonProcessingException{
    ObjectMapper mapper = new ObjectMapper();
    Message msg = socialMediaService.retrieveMessageById(Integer.parseInt(ctx.pathParam("message_id")));
    ctx.status(200);
    if(msg!=null){
     ctx.json(mapper.writeValueAsString(msg));
    }
  }

  private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException{
    ObjectMapper mapper = new ObjectMapper();
    Message messages = socialMediaService.deleteMessageById(Integer.parseInt(ctx.pathParam("message_id")));
    ctx.status(200);
    if(messages!=null){
      ctx.json(mapper.writeValueAsString(messages));
    }
  }

  private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException{
    String jsonString = ctx.body();
    ObjectMapper mapper = new ObjectMapper();
    Message msg = mapper.readValue(jsonString,Message.class);
    int msgId = Integer.parseInt(ctx.pathParam("message_id"));
    Message message = socialMediaService.updateMessageById(msgId, msg.getMessage_text());
    if(message==null){
      ctx.status(400);
    }else{
      ctx.status(200);
      ctx.json(mapper.writeValueAsString(message));
    }
  }

  private void retrieveMessageByUserHandler(Context ctx) throws JsonProcessingException{
    ObjectMapper mapper = new ObjectMapper();
    int accountId = Integer.parseInt(ctx.pathParam("account_id"));
    List<Message> msgList = socialMediaService.retrieveMessageByUser(accountId);
    ctx.status(200);
    if(msgList!=null){
        ctx.json(mapper.writeValueAsString(msgList));
       }
    }
  }

  

