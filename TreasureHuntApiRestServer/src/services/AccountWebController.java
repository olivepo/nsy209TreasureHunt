package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.mongodb.*;

import treasurehunt.model.Account;
import mongodb.MongoDBSingleton;  
  
@Path("/accountService")  
public class AccountWebController {  
	
	private final static String collectionName = "account";
	private final static String idKeyName = "email";
	
     @PUT
     @Path("putAccount")
     @Consumes(MediaType.APPLICATION_JSON)
     public Response putAccount(Account account) {
    	 
	     MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
	     DB db = dbSingleton.getTestdb();
	     DBCollection coll = db.getCollection(collectionName);
	     BasicDBObject dbObject = new BasicDBObject(idKeyName, account.email)
	    		 .append("email", account.email)
	    		 .append("login", account.login)
	    		 .append("hashedPassword", account.hashedPassword);
	     coll.insert(dbObject);
	     
	     return Response.ok().build();

     }
     
     @GET 
     @Path("getAccount/{email}")  
     @Produces(MediaType.APPLICATION_JSON)  
     public Account getAccount(@PathParam("email") String email) {
    	 MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
    	 DB db = dbSingleton.getTestdb();
    	 DBCollection coll = db.getCollection(collectionName);
    	 DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName,email));
    	 Account account = null;
    	 if (dbObject != null) {
    		 account = Account.getInstance(email);
             account.login = (String) dbObject.get("login");
	         account.hashedPassword = (String) dbObject.get("hashedPassword"); 
    	 }
    	 return account;
     }
     
     @DELETE
     @Path("deleteAccount/{email}")
     public Response deleteUser(@PathParam("email") String email) {

       // Retrieve the user from the database.
    	 MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
         DB db = dbSingleton.getTestdb();
         DBCollection coll = db.getCollection(collectionName);
         DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName,email));

	     // If the user did not exist, return an error.  Otherwise, remove the user.
	     if (dbObject == null) {
	       return Response.status(Status.BAD_REQUEST).entity(String.format("Le compte pour l'email %s n'existe pas.",email)).build();
	     }
	     
	     coll.remove(new BasicDBObject(idKeyName,email));
	     return Response.ok().build();
     }
     
     @GET 
     @Path("getAccounts")  
     @Produces(MediaType.APPLICATION_JSON)  
     public List<Account> getAccounts() {
    	 MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
    	 DB db = dbSingleton.getTestdb();
    	 DBCollection coll = db.getCollection(collectionName);	
    	 DBCursor cursor = coll.find().sort(new BasicDBObject(idKeyName, 1));
    	 List<Account> list = new ArrayList<Account>();
    	 Account account;
    	 String email;
    	 while (cursor.hasNext()) { 
             DBObject o = cursor.next();
             email = (String) o.get("email");
             if (email != null) {
	             account = Account.getInstance(email);
	             account.login = (String) o.get("login");
	             account.hashedPassword = (String) o.get("hashedpassword"); 
	             list.add(account);
             }
         }
    	 return list;
     }
     
}  