package todolist.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import todolist.ToDoListApplication;
import todolist.domain.dto.UsuarioResponseDTO;
import todolist.domain.dto.UsuarioUpdateRequestDTO;
import todolist.domain.dto.UsuariosCreateRequestDTO;
import todolist.domain.dto.UsuariosResponseDTO;
import todolist.test.utils.RetrieveUtil;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class UsuarioControllerTest {

	@Autowired
	private RetrieveUtil retrieveUtil;

	private long idUsuarioExisteEnBase = 1;
	private String mailUsuarioExisteEnBase = "lapunzinajulian@gmail.com";

	@Test
	@Order(1)
	public void devuelveUsuario_idUsuarioExiste_entoncesDevuelve_status200_JSON_DTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet(
				ToDoListApplication.TEST_HOST + "api/v1/usuarios/" + idUsuarioExisteEnBase);
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		UsuarioResponseDTO resource = RetrieveUtil.retrieveResourceFromResponse(response, UsuarioResponseDTO.class);
		assertThat(mailUsuarioExisteEnBase, Matchers.is(resource.getMailUsuario()));
	}

	@Test
	@Order(2)
	public void devuelveUsuarios_usuariosExisten_entoncesDevuelve_status200_JSON_listDTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet(ToDoListApplication.TEST_HOST + "api/v1/usuarios");
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		List<UsuariosResponseDTO> resource = RetrieveUtil.retrieveResourceFromResponse(response, List.class);
		assertFalse(resource.isEmpty());
	}

	@Test
	@Order(4)
	public void actualizaUsuario_usuarioActualizado_entoncesDevuelve_status201()
			throws ClientProtocolException, IOException {

		HttpPut request = new HttpPut(ToDoListApplication.TEST_HOST + "api/v1/usuarios/" + idUsuarioExisteEnBase);
		request.setHeader("Content-type", "application/json");
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());
		request.setEntity(retrieveUtil.retrieveStringEntity(creaUsuarioUpdateRequestDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(201, response.getStatusLine().getStatusCode());

	}

	@Test
	@Order(5)
	public void eliminaUsuario_usuariosEliminado_entoncesDevuelve_status204()
			throws ClientProtocolException, IOException {

		HttpDelete request = new HttpDelete(ToDoListApplication.TEST_HOST + "api/v1/usuarios/" + idUsuarioExisteEnBase);
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(204, response.getStatusLine().getStatusCode());

	}

	private UsuarioUpdateRequestDTO creaUsuarioUpdateRequestDTO() {

		UsuarioUpdateRequestDTO usuarioUpdateRequestDTO = new UsuarioUpdateRequestDTO();
		usuarioUpdateRequestDTO.setNombreUsuario("nombre_actualizado");
		usuarioUpdateRequestDTO.setMailUsuario("mail_actualizado");

		return usuarioUpdateRequestDTO;
	}

	private UsuariosCreateRequestDTO creaUsuariosCreateRequestDTO() {

		UsuariosCreateRequestDTO usuariosCreateRequestDTO = new UsuariosCreateRequestDTO();
		usuariosCreateRequestDTO.setNombreUsuario("nombre_nuevo");
		usuariosCreateRequestDTO.setMailUsuario("mail_nuevo");
		usuariosCreateRequestDTO.setUsername("username_nuevo");
		usuariosCreateRequestDTO.setPassword("pass_nueva");

		return usuariosCreateRequestDTO;
	}

}
