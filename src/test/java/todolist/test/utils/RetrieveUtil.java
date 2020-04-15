package todolist.test.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class RetrieveUtil {

	public static <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz) throws IOException {

		String jsonFromResponse = EntityUtils.toString(response.getEntity());
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new JavaTimeModule());
		return mapper.readValue(jsonFromResponse, clazz);
	}

	public StringEntity retrieveStringEntity(Object o) throws JsonProcessingException, UnsupportedEncodingException {

		ObjectMapper objectMapper = new ObjectMapper();
		String JSONString = objectMapper.writeValueAsString(o);
		StringEntity stringEntity = new StringEntity(JSONString);

		return stringEntity;
	}

}
