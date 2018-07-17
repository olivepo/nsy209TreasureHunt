package services;

import java.io.IOException;
import java.util.HashMap;

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
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import mongodb.MongoDBSingleton;
import treasurehunt.model.RunThrough;
import treasurehunt.model.RunThroughs;
import treasurehunt.model.StepResolution;

@Path("/runThroughService")
public class RunThroughWebController {

	private final static String collectionName = "runThrough";
	private final static String idKeyName = "id";

	@PUT
	@Path("putRunThrough")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putRunThrough(RunThrough runThrough) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = buildDBObjectFromRunThrough(runThrough);
		if (dbObject == null) {
			return Response.serverError().build();
		}
		coll.insert(dbObject);
		return Response.ok().build();
	}

	@GET
	@Path("getRunThrough/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RunThrough getRunThrough(@PathParam("id") String id) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName, id));
		if (dbObject == null) {
			return null;
		}
		return buildRunThroughFromDBObject(dbObject);

	}

	@DELETE
	@Path("deleteRunThrough/{id}")
	public Response deleteRunThrough(@PathParam("id") String id) {

		// Retrieve the user from the database.
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName, id));

		// If the user did not exist, return an error. Otherwise, remove the user.
		if (dbObject == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity(String.format("Le parcours id: %s n'existe pas.", id)).build();
		}

		coll.remove(new BasicDBObject(idKeyName, id));
		return Response.ok().build();
	}
	
	@GET
	@Path("getRunThroughs/{accountEmail}")
	@Produces(MediaType.APPLICATION_JSON)
	public RunThroughs getRunThroughs(@PathParam("accountEmail") String accountEmail) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBCursor cursor = coll.find(new BasicDBObject("accountEmail", accountEmail)).sort(new BasicDBObject(idKeyName, 1));
		RunThroughs runThroughs = new RunThroughs();
		while (cursor.hasNext()) {
			runThroughs.list.add(buildRunThroughFromDBObject(cursor.next()));
		}
		return runThroughs;
	}
	
	private DBObject buildDBObjectFromRunThrough(RunThrough runThrough) {
		String serializedStepResolutions = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			serializedStepResolutions = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(runThrough.getStepResolutions());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return new BasicDBObject(idKeyName, runThrough.id)
				.append("accountEmail", runThrough.accountEmail)
				.append("courseId", runThrough.courseId)
				.append("startedAt", runThrough.getStartedAt())
				.append("endedAt", runThrough.getEndedAt())
				.append("stepResolutions", serializedStepResolutions);
	}
	
	private RunThrough buildRunThroughFromDBObject(DBObject dbObject) {
		if (dbObject == null) {
			return null;
		}
		RunThrough runThrough = new RunThrough();
		runThrough.id = (String) dbObject.get("id");
		runThrough.accountEmail = (String) dbObject.get("accountEmail");
		runThrough.courseId = (String) dbObject.get("courseId");
		runThrough.setStartedAt((String) dbObject.get("startedAt"));
		runThrough.setEndedAt((String) dbObject.get("endedAt"));
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			runThrough.setStepResolutions(objectMapper
					.readValue((String) dbObject.get("stepResolutions"), new TypeReference<HashMap<String,StepResolution>>() {}));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return runThrough;
	}
}
