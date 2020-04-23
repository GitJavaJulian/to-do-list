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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import todolist.ToDoListApplication;
import todolist.domain.Lista;
import todolist.domain.User;
import todolist.domain.dto.ListaCreateRequestDTO;
import todolist.domain.dto.ListaResponseDTO;
import todolist.domain.dto.ListaUpdateRequestDTO;
import todolist.domain.dto.ListasResponseDTO;
import todolist.test.utils.RetrieveUtil;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ListaControllerTest {

	@Autowired
	private RetrieveUtil retrieveUtil;

	private static Lista listaExistenteEnBaseDatos;

	@BeforeAll
	private static void creaListaExistenteEnBaseDatos() {
		listaExistenteEnBaseDatos = new Lista();
		listaExistenteEnBaseDatos.setIdLista(1);
		listaExistenteEnBaseDatos.setTituloLista("Trabajo");

		User usuario = new User();
		usuario.setIdUsuario(1);

		listaExistenteEnBaseDatos.setUsuario(usuario);

	}

	@Test
	@Order(1)
	public void devuelveLista_idListaExiste_entoncesDevuelve_status200_JSON_DTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet(
				ToDoListApplication.TEST_HOST + "api/v1/listas/" + listaExistenteEnBaseDatos.getIdLista());
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		ListaResponseDTO resource = RetrieveUtil.retrieveResourceFromResponse(response, ListaResponseDTO.class);
		assertThat(listaExistenteEnBaseDatos.getTituloLista(), Matchers.is(resource.getTituloLista()));
	}

	@Test
	@Order(2)
	public void devuelveListasDeUsuario_usuarioExiste_entoncesDevuelve_status200_JSON_listDTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet(ToDoListApplication.TEST_HOST + "api/v1/usuarios/"
				+ listaExistenteEnBaseDatos.getUsuario().getIdUsuario() + "/listas");
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		List<ListasResponseDTO> resource = RetrieveUtil.retrieveResourceFromResponse(response, List.class);
		assertFalse(resource.isEmpty());
	}

	@Test
	@Order(3)
	public void actualizaLista_listaActualizada_entoncesDevuelve_status201()
			throws ClientProtocolException, IOException {

		HttpPut request = new HttpPut(
				ToDoListApplication.TEST_HOST + "api/v1/listas/" + listaExistenteEnBaseDatos.getIdLista());
		request.setHeader("Content-type", "application/json");
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());
		request.setEntity(retrieveUtil.retrieveStringEntity(creaListaUpdateRequestDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(201, response.getStatusLine().getStatusCode());

	}

	@Test
	@Order(4)
	public void crearLista_listaCreada_entoncesDevuelve_status201() throws ClientProtocolException, IOException {

		HttpPost request = new HttpPost(ToDoListApplication.TEST_HOST + "api/v1/usuarios/listas");
		request.setHeader("Content-type", "application/json");
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());
		request.setEntity(retrieveUtil.retrieveStringEntity(creaListaCreateRequestDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(201, response.getStatusLine().getStatusCode());

	}

	@Test
	@Order(5)
	public void eliminaLista_listaEliminada_entoncesDevuelve_status204() throws ClientProtocolException, IOException {

		HttpDelete request = new HttpDelete(
				ToDoListApplication.TEST_HOST + "api/v1/listas/" + listaExistenteEnBaseDatos.getIdLista());
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(204, response.getStatusLine().getStatusCode());

	}

	private ListaUpdateRequestDTO creaListaUpdateRequestDTO() {

		ListaUpdateRequestDTO listaUpdateRequestDTO = new ListaUpdateRequestDTO();
		listaUpdateRequestDTO.setTituloLista("titulo_actualizado");

		return listaUpdateRequestDTO;
	}

	private ListaCreateRequestDTO creaListaCreateRequestDTO() {

		ListaCreateRequestDTO listaCreateRequestDTO = new ListaCreateRequestDTO();
		listaCreateRequestDTO.setTituloLista("nueva_lista");
		listaCreateRequestDTO.setIdUsuario(1);

		return listaCreateRequestDTO;
	}

}
