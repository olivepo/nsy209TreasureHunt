package treasurehunt.client;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import treasurehunt.model.Account;

public class AccountRESTMethods {
	
	private final static String baseUrl = Configuration.baseUrl+"accountService/";
	
	public static boolean put(Account account) throws Exception {
		
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(Configuration.tomcatUser, Configuration.tomcatUserPassword));
		 
        WebResource webResource = client.resource(baseUrl+"putAccount");
 
        // Data send to web service.
        ObjectMapper mapper = new ObjectMapper();
        String input = null;
		try {
			input = mapper.writeValueAsString(account);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
 
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, input);
 
        switch (response.getStatus()) {
		
			case 200 : case 201 : case 204 :
				return true;
		
			default :
				throw new Exception("Failed : HTTP error code : "+response.getStatus());
		}
 
	}
	
	public static Account get(String email) throws Exception {
		
		Client client = Client.create();
		WebResource webResource = client.resource(baseUrl+"getAccount/"+email);
		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		switch (response.getStatus()) {
		
			case 204 :
				return null;
			
			case 200 :
				return (Account) response.getEntity(Account.class);
		
			default :
				throw new Exception("Failed : HTTP error code : "+response.getStatus());
		}

	}
	
	public static boolean delete(String email) throws Exception {
		
		Client client = Client.create();
		WebResource webResource = client.resource(baseUrl+"deleteAccount/"+email);
		ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).delete(ClientResponse.class);
		
		switch (response.getStatus())
		{
			case 200 :
				return true;

			case 400 :
				return false;
				
			default :
				throw new Exception("Failed : HTTP error code : "
						+ response.getStatus());
		}

	}
	
	public static List<Account> getAll() throws Exception {
		
		Client client = Client.create();
		 
	       WebResource webResource = client.resource(baseUrl+"getAccounts");
	 
	       Builder builder = webResource.accept(MediaType.APPLICATION_JSON)
	               .header("content-type", MediaType.APPLICATION_JSON);
	 
	       ClientResponse response = builder.get(ClientResponse.class);
	 
	       // Status 200 is successful.
	       if (response.getStatus() != 200) {
	           throw new Exception("Failed : HTTP error code : "+response.getStatus());
	       }
	 
	       GenericType<List<Account>> generic = new GenericType<List<Account>>() {};
	 
	       List<Account> list = response.getEntity(generic);
	 
	       return list;
	}
	
}
