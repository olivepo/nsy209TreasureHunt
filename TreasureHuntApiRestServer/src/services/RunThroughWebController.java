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
import treasurehunt.model.marshalling.JsonObjectMapperBuilder;

@Path("/runThroughService")
public class RunThroughWebController {

	private final static String collectionName = "runThrough";
	private final static String idKeyName = "id";

	@PUT
	@Path("putRunThrough")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RunThrough putRunThrough(RunThrough runThrough) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = buildDBObjectFromRunThrough(runThrough);
		if (dbObject == null) {
			return null;
		}
		DBObject ExistingDbObject = coll.findOne(new BasicDBObject(idKeyName, runThrough.getId()));
		if (ExistingDbObject == null) {
			coll.insert(dbObject);
		} else {
			coll.update(new BasicDBObject(idKeyName,runThrough.getId()), dbObject);
		}
		return buildRunThroughFromDBObject(dbObject);
	}

	@GET
	@Path("getRunThrough/{accountEmail}/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public RunThrough getRunThrough(@PathParam("accountEmail") String accountEmail,
			@PathParam("courseId") String courseId) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName, RunThrough.buildId(accountEmail,courseId)));
		if (dbObject == null) {
			return null;
		}
		return buildRunThroughFromDBObject(dbObject);

	}

	@DELETE
	@Path("deleteRunThrough/{accountEmail}/{courseId}")
	public Response deleteRunThrough(@PathParam("accountEmail") String accountEmail,
			@PathParam("courseId") String courseId) {

		// Retrieve the user from the database.
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName, RunThrough.buildId(accountEmail,courseId)));

		// If the user did not exist, return an error. Otherwise, remove the user.
		if (dbObject == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity(String.format("Le parcours id: %s n'existe pas.", RunThrough.buildId(accountEmail,courseId))).build();
		}

		coll.remove(new BasicDBObject(idKeyName, RunThrough.buildId(accountEmail,courseId)));
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
			ObjectMapper objectMapper = JsonObjectMapperBuilder.buildJacksonObjectMapper();
			serializedStepResolutions = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(runThrough.getStepResolutions());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return new BasicDBObject(idKeyName, runThrough.getId())
				.append("accountEmail", runThrough.getAccountEmail())
				.append("courseId", runThrough.getCourseId())
				.append("startedAt", runThrough.getStartedAt())
				.append("endedAt", runThrough.getEndedAt())
				.append("stepResolutions", serializedStepResolutions);
	}
	
	private RunThrough buildRunThroughFromDBObject(DBObject dbObject) {
		if (dbObject == null) {
			return null;
		}
		RunThrough runThrough = new RunThrough();
		runThrough.setAccountEmail((String) dbObject.get("accountEmail"));
		runThrough.setCourseId((String) dbObject.get("courseId"));
		runThrough.setStartedAt((String) dbObject.get("startedAt"));
		runThrough.setEndedAt((String) dbObject.get("endedAt"));
		try {
			ObjectMapper objectMapper = JsonObjectMapperBuilder.buildJacksonObjectMapper();
			runThrough.setStepResolutions(objectMapper
					.readValue((String) dbObject.get("stepResolutions"), new TypeReference<HashMap<String,StepResolution>>() {}));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return runThrough;
	}
}
