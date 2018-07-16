package services;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.mongodb.*;

import treasurehunt.model.Account;
import treasurehunt.model.Accounts;
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
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		coll.insert(buildDBObjectFromAccount(account));
		return Response.ok().build();
	}

	@GET
	@Path("getAccount/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account getAccount(@PathParam("email") String email) {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName, email));
		return buildAccountFromDBObject(dbObject);
	}

	@DELETE
	@Path("deleteAccount/{email}")
	public Response deleteUser(@PathParam("email") String email) {

		// Retrieve the user from the database.
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBObject dbObject = coll.findOne(new BasicDBObject(idKeyName, email));

		// If the user did not exist, return an error. Otherwise, remove the user.
		if (dbObject == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity(String.format("Le compte pour l'email %s n'existe pas.", email)).build();
		}

		coll.remove(new BasicDBObject(idKeyName, email));
		return Response.ok().build();
	}

	@GET
	@Path("getAccounts")
	@Produces(MediaType.APPLICATION_JSON)
	public Accounts getAccounts() {
		MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
		DB db = dbSingleton.getDb();
		DBCollection coll = db.getCollection(collectionName);
		DBCursor cursor = coll.find().sort(new BasicDBObject(idKeyName, 1));
		Accounts accounts = new Accounts();
		Account account;
		while (cursor.hasNext()) {
			account = buildAccountFromDBObject(cursor.next());
			if (account != null) {
				accounts.list.add(account);
			}
		}
		return accounts;
	}
	
	private DBObject buildDBObjectFromAccount(Account account) {
		return new BasicDBObject(idKeyName, account.email)
				.append("email", account.email)
				.append("login", account.login)
				.append("hashedPassword", account.hashedPassword);
	}

	private Account buildAccountFromDBObject(DBObject dbObject) {
		if (dbObject == null) {
			return null;
		}
		String email = (String) dbObject.get("email");
		if (email == null) {
			return null;
		}
		Account	account = Account.getInstance(email);
		account.login = (String) dbObject.get("login");
		account.hashedPassword = (String) dbObject.get("hashedPassword");
		return account;
	}

}