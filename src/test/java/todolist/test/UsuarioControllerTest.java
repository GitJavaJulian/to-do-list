package todolist.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

class UsuarioControllerTest {

	@Test
	public void idUsuarioExiste_entoncesDevuelve_status200() throws ClientProtocolException, IOException {

		int id = 1;

		HttpUriRequest request = new HttpGet("http://localhost:8080/api/v1/usuarios/" + id);

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());
	}

	@Test
	public void idUsuarioNOExiste_entoncesDevuelve_status404() throws ClientProtocolException, IOException {

		int id = 999;

		HttpUriRequest request = new HttpGet("http://localhost:8080/api/v1/usuarios/" + id);

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(404, response.getStatusLine().getStatusCode());
	}

}
