package services;

import java.io.IOException;
import java.util.List;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
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
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = buildDBObjectFromCourse(course);
		if (dbObject == null) {
			return Response.serverError().build();
		}
		coll.insert(dbObject);
		return Response.ok().build();
	}

	@GET
	@Path("getCourse/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getCourse(@PathParam("id") String id) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName, id));
		if (dbObject == null) {
			return null;
		}
		return buildCourseFromDBObject(dbObject);

	}

	@DELETE
	@Path("deleteCourse/{id}")
	public Response deleteCourse(@PathParam("id") String id) {

		// Retrieve the user from the database.
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
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
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBCursor cursor = coll.find().sort(new BasicDBObject(idKeyName, 1));
		Courses courses = new Courses();
		while (cursor.hasNext()) {
			courses.list.add(buildCourseFromDBObject(cursor.next()));
		}
		return courses;
	}
	
	@GET
	@Path("getAccountCourses/{accountEmail}")
	@Produces(MediaType.APPLICATION_JSON)
	public Courses getNearestCourses(@PathParam("accountEmail") String accountEmail) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBCursor cursor = coll.find(new BasicDBObject("accountEmail", accountEmail)).sort(new BasicDBObject(idKeyName, 1));
		Courses courses = new Courses();
		while (cursor.hasNext()) {
			courses.list.add(buildCourseFromDBObject(cursor.next()));
		}
		return courses;
	}
	
	private DBObject buildDBObjectFromCourse(Course course) {
		String serializedStart = null;
		String serializedSteps = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			serializedStart = objectMapper.writeValueAsString(course.start);
			List<Step> steps = course.getSteps();
			CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(steps.getClass(), Step.class);
			serializedSteps = objectMapper.writerFor(listType).writeValueAsString(steps);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return new BasicDBObject(idKeyName, course.id)
				.append("accountEmail", course.accountEmail)
				.append("name", course.name)
				.append("begin", course.getBegin())
				.append("end", course.getEnd())
				.append("jokersAllowed", course.jokersAllowed)
				.append("start", serializedStart)
				.append("steps", serializedSteps);
	}
	
	private Course buildCourseFromDBObject(DBObject dbObject) {
		if (dbObject == null) {
			return null;
		}
		Course course = new Course();
		course.id = (String) dbObject.get("id");
		course.name = (String) dbObject.get("name");
		course.setBegin((String) dbObject.get("begin"));
		course.setEnd((String) dbObject.get("end"));
		course.jokersAllowed = (int) dbObject.get("jokersAllowed");
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			course.start = objectMapper
					.readValue((String) dbObject.get("start"), StepComposite.class);
			course.setSteps(objectMapper
					.readValue((String) dbObject.get("steps"), new TypeReference<List<Step>>() {}));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return course;
	}
}
