package services;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import mongodb.MongoDBSingleton;
import treasurehunt.model.Course;
import treasurehunt.model.Courses;
import treasurehunt.model.Step;
import treasurehunt.model.StepComposite;

@Path("/courseService")
public class CourseWebController {

	private final static String collectionName = "course";
	private final static String idKeyName = "id";

	@PUT
	@Path("putCourse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putCourse(Course course) {

		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getTestdb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName, course.id));
		String serializedStart = null;
		try {
			serializedStart = new ObjectMapper().writeValueAsString(course.start);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		// contrôle de l'unicité de l'email
		dbObject = new BasicDBObject(idKeyName, course.id)
				.append("accountEmail", course.accountEmail)
				.append("name", course.name)
				.append("begin", course.getBegin())
				.append("end", course.getEnd())
				.append("jokersAllowed", course.jokersAllowed)
				.append("start", serializedStart);
		coll.insert(dbObject);
		return Response.ok().build();
	}

	@GET
	@Path("getCourse/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getCourse(@PathParam("id") String id) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getTestdb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName, id));
		Course course = new Course();
		if (dbObject != null) {
			course.id = (String) dbObject.get("id");
			course.name = (String) dbObject.get("name");
			course.setBegin((String) dbObject.get("begin"));
			course.setEnd((String) dbObject.get("end"));
			course.jokersAllowed = (int) dbObject.get("jokersAllowed");
			try {
				course.start = new ObjectMapper()
						.readValue((String) dbObject.get("start"), StepComposite.class);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}

		return course;
	}

	@DELETE
	@Path("deleteCourse/{id}")
	public Response deleteCourse(@PathParam("id") String id) {

		// Retrieve the user from the database.
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getTestdb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName, id));

		// If the user did not exist, return an error. Otherwise, remove the user.
		if (dbObject == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity(String.format("La course id: %s n'existe pas.", id)).build();
		}

		coll.remove(new BasicDBObject(idKeyName, id));
		return Response.ok().build();
	}

	@GET
	@Path("getNearestCourses/{latitude}/{longitude}")
	@Produces(MediaType.APPLICATION_JSON)
	public Courses getNearestCourses(@PathParam("latitude") float latitude, @PathParam("longitude") float longitude) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getTestdb();
		DBCollection coll = db.getCollection(collectionName);
		DBCursor cursor = coll.find().sort(new BasicDBObject(idKeyName, 1));
		Courses courses = new Courses();
		Course course;
		while (cursor.hasNext()) {
			DBObject o = cursor.next();
			course = new Course();
			course.id = (String) o.get("id");
			course.name = (String) o.get("name");
			course.setBegin((String) o.get("begin"));
			course.setEnd((String) o.get("end"));
			course.jokersAllowed = (int) o.get("jokersAllowed");
			try {
				course.start = new ObjectMapper()
						.readValue((String) o.get("start"), StepComposite.class);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			courses.list.add(course);
		}
		return courses;
	}
}
