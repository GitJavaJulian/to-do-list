package todolist.test.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import todolist.ToDoListApplication;
import todolist.domain.AuthToken;
import todolist.domain.dto.LoginUserDTO;

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

	public AuthToken creaToken() throws ClientProtocolException, IOException {

		HttpPost request = new HttpPost(ToDoListApplication.TEST_HOST + "login");
		request.setHeader("Content-type", "application/json");
		request.setEntity(this.retrieveStringEntity(creaUsuariosLoginUserDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		AuthToken token = new AuthToken();
		token.setToken("Bearer " + RetrieveUtil.retrieveResourceFromResponse(response, AuthToken.class).getToken());

		return token;
	}

	private LoginUserDTO creaUsuariosLoginUserDTO() {

		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setUsername("juli123");
		loginUserDTO.setPassword("123");

		return loginUserDTO;
	}

}
