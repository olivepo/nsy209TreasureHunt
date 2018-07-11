package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import mongodb.MongoDBSingleton;
import treasurehunt.model.Account;
import treasurehunt.model.Course;

@Path("/courseService") 
public class CourseWebController {
	
	/*@GET
    @Path("/putCourse")
    @Produces("text/plain")
    public String putAccount(@QueryParam("email") String email,
   		 @QueryParam("login") String login,
   		 @QueryParam("password") String password)
   				 throws JsonGenerationException, JsonMappingException, IOException {
   	 
    MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
    DB db = dbSingleton.getTestdb();
    DBCollection coll = db.getCollection("account");
    ObjectMapper mapper = new ObjectMapper();
    Account account = Account.getInstance(email);
    account.login = login;
    account.setPassword(password);
    String jsonString = mapper.writeValueAsString(account);
    // BasicDBObject user = new BasicDBObject("email", email).append("login", login).append("password", password);
    BasicDBObject user = new BasicDBObject("_id", email).append("compte",jsonString);
    coll.insert(user);
    return db.isAuthenticated() + " ; " + db.getName();

    }
	
	@GET 
    @Path("/getCourse")  
    @Produces(MediaType.APPLICATION_JSON)  
    public Account getAccount(@QueryParam("email") String email) {
   	 MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
   	 DB db = dbSingleton.getTestdb();
   	 DBCollection coll = db.getCollection("account");	
   	 DBObject dbObject = coll.findOne(new BasicDBObject("_id",email));
   	 Account account = null;
   	 if (dbObject != null) {
   		 account = Account.getInstance(email);
            account.login = (String) dbObject.get("login");
	         account.hashedPassword = (String) dbObject.get("password"); 
         }
   	 return account;
    }
	
	@GET 
    @Path("/getCourses")  
    @Produces(MediaType.APPLICATION_JSON)  
    public List<Course> getCourses(@PathParam("accountEmail") String accountEmail){
   	 MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
   	 DB db = dbSingleton.getTestdb();
   	 DBCollection coll = db.getCollection("course");	
   	 DBCursor cursor = coll.find().sort(new BasicDBObject("accountEmail", accountEmail));
   	 List<Course> list = new ArrayList<Course>();
   	 Course course;
   	 while (cursor.hasNext()) { 
            DBObject o = cursor.next();
            course = (Course) o.get("course");
            list.add(course);
         }
   	 return list;
    }
	
	@GET 
    @Path("/getAccounts")  
    @Produces(MediaType.APPLICATION_JSON)  
    public List<Account> getAccounts() {
   	 MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
   	 DB db = dbSingleton.getTestdb();
   	 DBCollection coll = db.getCollection("account");	
   	 DBCursor cursor = coll.find().sort(new BasicDBObject("email", 1));
   	 List<Account> list = new ArrayList<Account>();
   	 Account account;
   	 ObjectMapper mapper = new ObjectMapper();
   	 TypeReference<Account> typeRef = new TypeReference<Account>() {};
   	 while (cursor.hasNext()) { 
            DBObject o = cursor.next();
            try {
	             String serializedAccount = (String) o.get("compte");
	             if (serializedAccount != null) {
		             account = mapper.readValue(serializedAccount, typeRef);
		             list.add(account);
	             }
            } catch (Exception e) {
           	 
            }
         }
   	 return list;
    }*/
	
}
