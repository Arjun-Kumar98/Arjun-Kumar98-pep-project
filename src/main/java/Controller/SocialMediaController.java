package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
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
  } 

