package treasurehunt.client;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import treasurehunt.model.Course;
import treasurehunt.model.Courses;

public class CourseRESTMethods {
	private final static String baseUrl = Configuration.baseUrl+"courseService/";

	public static boolean put(Course course) throws Exception {

		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(Configuration.tomcatUser, Configuration.tomcatUserPassword));

		WebResource webResource = client.resource(baseUrl+"putCourse");

		// Data send to web service.
		ObjectMapper mapper = new ObjectMapper();
		String input = null;
		try {
			input = mapper.writeValueAsString(course);
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

	public static Course get(String id) throws Exception {

		Client client = Client.create();
		WebResource webResource = client.resource(baseUrl+"getCourse/"+id);
		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		switch (response.getStatus()) {

		case 204 :
			return null;

		case 200 :
			return (Course) response.getEntity(Course.class);

		default :
			throw new Exception("Failed : HTTP error code : "+response.getStatus());
		}

	}

	public static boolean delete(String id) throws Exception {

		Client client = Client.create();
		WebResource webResource = client.resource(baseUrl+"deleteCourse/"+id);
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

	public static List<Course> getNearestCourses(float latitude,float longitude) throws Exception {

		Client client = Client.create();

		WebResource webResource = client.resource(baseUrl+"getNearestCourses/0.0/0.0");

		Builder builder = webResource.accept(MediaType.APPLICATION_JSON)
				.header("content-type", MediaType.APPLICATION_JSON);

		ClientResponse response = builder.get(ClientResponse.class);

		// Status 200 is successful.
		if (response.getStatus() != 200) {
			throw new Exception("Failed : HTTP error code : "+response.getStatus());
		}

		Courses courses = response.getEntity(Courses.class);

		return courses.list;
	}
}
