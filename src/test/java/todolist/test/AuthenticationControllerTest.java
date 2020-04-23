package todolist.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import todolist.ToDoListApplication;
import todolist.domain.dto.UsuariosCreateRequestDTO;
import todolist.test.utils.RetrieveUtil;

@SpringBootTest
public class AuthenticationControllerTest {

	@Autowired
	private RetrieveUtil retrieveUtil;

	@Test
	public void register_usuarioCreado_entoncesDevuelve_status200() throws ClientProtocolException, IOException {

		HttpPost request = new HttpPost(ToDoListApplication.TEST_HOST + "register");
		request.setHeader("Content-type", "application/json");
		request.setEntity(retrieveUtil.retrieveStringEntity(creaUsuarioUsuariosCreateRequestDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

	}

	private UsuariosCreateRequestDTO creaUsuarioUsuariosCreateRequestDTO() {

		UsuariosCreateRequestDTO usuariosCreateRequestDTO = new UsuariosCreateRequestDTO();
		usuariosCreateRequestDTO.setNombreUsuario("nuevo_nombre");
		usuariosCreateRequestDTO.setMailUsuario("nuevo_mail@gmail.com");
		usuariosCreateRequestDTO.setUsername("nuevo_userName");
		usuariosCreateRequestDTO.setPassword("12345");

		return usuariosCreateRequestDTO;
	}

}
