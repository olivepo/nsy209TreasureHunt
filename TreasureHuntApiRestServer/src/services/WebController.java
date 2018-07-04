package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

//import entities.Account;
import mongodb.MongoDBSingleton;
import treasurehunt.model.Account;
import treasurehunt.model.Course;

/**
 * @author JITHINRAJ.P
 * @author email : jithinrajktd@gmail.com
 */

@Path("/webservice")
public class WebController {

	@GET
	@Path("/echo/{message}")
	@Produces("text/plain")
	public String showMsg(@PathParam("message") String message) {
		return message;
	}

	@GET
	@Path("/insert/{name}/{by}/{likes}/{year}/{description}")
	@Produces("text/plain")
	public String insert(@PathParam("name") String name, @PathParam("description") String description,
			@PathParam("likes") Long likes, @PathParam("year") String year, @PathParam("by") String by) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getTestdb();
		DBCollection coll = db.getCollection("Books");
		BasicDBObject doc = new BasicDBObject("title", name).append("description", description).append("likes", likes)
				.append("year", year).append("by", by);
		coll.insert(doc);
		return db.isAuthenticated() + " ; " + db.getName();

	}

	@GET
	@Path("/insertUser/{email}/{login}/{password}")
	@Produces("text/plain")
	public String insertUser(@PathParam("email") String email, @PathParam("login") String login,
			@PathParam("password") String password) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getTestdb();
		DBCollection coll = db.getCollection("account");
		BasicDBObject user = new BasicDBObject("email", email).append("login", login).append("password", password);

		coll.insert(user);
		return db.isAuthenticated() + " ; " + db.getName();

	}

	@GET
	@Path("/insertCourse/{begin}/{end}/{id}/{joker}/{name}")
	@Produces("text/plain")
	public String insertCourse(@PathParam("begin") LocalDateTime begin, @PathParam("end") LocalDateTime end,
			@PathParam("id") String id, @PathParam("joker") String joker, @PathParam("name") String name) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getTestdb();
		DBCollection coll = db.getCollection("course");
		BasicDBObject course = new BasicDBObject("begin", begin).append("end", end).append("id", id).append("joker",
				joker).append("name", name);

		coll.insert(course);
		return db.isAuthenticated() + " ; " + db.getName();

	}

	@GET
	@Path("/getAccounts")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Account> getAccount() {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getTestdb();
		DBCollection coll = db.getCollection("account");
		DBCursor cursor = coll.find().sort(new BasicDBObject("by", 1));
		List<Account> list = new ArrayList<Account>();
		while (cursor.hasNext()) {
			DBObject o = cursor.next();
			Account bools = new Account();
			bools.setEmail((String) o.get("email"));
			bools.setLogin((String) o.get("login"));
			bools.setPassword((String) o.get("password"));
			list.add(bools);
		}
		return list;
	}

	@GET
	@Path("/getCourses")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getCourse() {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getTestdb();
		DBCollection coll = db.getCollection("course");
		DBCursor cursor = coll.find().sort(new BasicDBObject("by", 1));
		List<Course> list = new ArrayList<Course>();
		while (cursor.hasNext()) {
			DBObject o = cursor.next();
			Course bools = new Course();
			bools.setBegin((LocalDateTime) o.get("begin"));
			bools.setEnd((LocalDateTime) o.get("end"));
			bools.setId((String) o.get("id"));
			bools.setJokersAllowed((int) o.get("joker"));
			bools.setName((String) o.get("name"));

			list.add(bools);
		}
		return list;
	}

	// @GET
	// @Path("/getRecords")
	// @Produces(MediaType.APPLICATION_JSON)
	// public List<Books> getRecords(){
	// MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
	// DB db = dbSingleton.getTestdb();
	// DBCollection coll = db.getCollection("Books");
	// DBCursor cursor = coll.find().sort(new BasicDBObject("by", 1));
	// List<Books> list = new ArrayList<Books>();
	// while (cursor.hasNext()) {
	// DBObject o = cursor.next();
	// Books bools = new Books();
	// bools.setTitle((String) o.get("title"));
	// bools.setDescription((String) o.get("description"));
	// bools.setYear((String) o.get("year"));
	// bools.setBy((String) o.get("by"));
	// bools.setLikes((Long) o.get("likes"));
	// list.add(bools);
	// }
	// return list;
	// }

	// @GET
	// @Path("/getRecord/{title}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public List<Books> getRecordFromName(@PathParam("title") String message){
	// MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
	// DB db = dbSingleton.getTestdb();
	// DBCollection coll = db.getCollection("Books");
	// DBCursor cursor = coll.find(new BasicDBObject("title", message));
	// List<Books> list = new ArrayList<Books>();
	// while (cursor.hasNext()) {
	// DBObject o = cursor.next();
	// Books bools = new Books();
	// bools.setTitle((String) o.get("title"));
	// bools.setDescription((String) o.get("description"));
	// bools.setYear((String) o.get("year"));
	// bools.setBy((String) o.get("by"));
	// list.add(bools);
	// }
	// return list;
	// }
}