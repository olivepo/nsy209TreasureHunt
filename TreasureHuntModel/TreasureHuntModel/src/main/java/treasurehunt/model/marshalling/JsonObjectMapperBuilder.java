package treasurehunt.model.marshalling;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.*;

public class JsonObjectMapperBuilder {
	
	public static ObjectMapper buildJacksonObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
		// Don't throw an exception when json has extra fields you are
	    // not serializing on. This is useful when you want to use a pojo
	    // for deserialization and only care about a portion of the json
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	    // Ignore null values when writing json.
	    mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
	    mapper.setSerializationInclusion(Include.NON_NULL);
		
	    // turn off auto-detection
	    mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
	    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	    
		return mapper;
	}
	
}
