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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import todolist.ToDoListApplication;
import todolist.dto.ListaCreateRequestDTO;
import todolist.dto.ListaResponseDTO;
import todolist.dto.ListaUpdateRequestDTO;
import todolist.dto.ListasResponseDTO;
import todolist.entity.Lista;
import todolist.entity.Usuario;
import todolist.test.utils.RetrieveUtil;

@SpringBootTest

class ListaControllerTest {

	@Autowired
	private RetrieveUtil retrieveUtil;

	@Test
	public void devuelveLista_idListaExiste_entoncesDevuelve_status200_JSON_DTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet(
				"http://localhost:8090/api/v1/listas/" + listaExistenteEnBaseDatos().getIdLista());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		ListaResponseDTO resource = RetrieveUtil.retrieveResourceFromResponse(response, ListaResponseDTO.class);
		assertThat(listaExistenteEnBaseDatos().getTituloLista(), Matchers.is(resource.getTituloLista()));
	}

	@Test
	public void devuelveListasDeUsuario_usuarioExiste_entoncesDevuelve_status200_JSON_listDTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet("http://localhost:8090/api/v1/usuarios/"
				+ listaExistenteEnBaseDatos().getUsuario().getIdUsuario() + "/listas");

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		List<ListasResponseDTO> resource = RetrieveUtil.retrieveResourceFromResponse(response, List.class);
		assertFalse(resource.isEmpty());
	}

	@Test
	public void eliminaLista_listaEliminada_entoncesDevuelve_status204() throws ClientProtocolException, IOException {

		HttpDelete request = new HttpDelete(
				"http://localhost:8090/api/v1/listas/" + listaExistenteEnBaseDatos().getIdLista());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(204, response.getStatusLine().getStatusCode());

	}

	@Test
	public void actualizaLista_listaActualizada_entoncesDevuelve_status201()
			throws ClientProtocolException, IOException {

		HttpPut request = new HttpPut(
				"http://localhost:8090/api/v1/listas/" + listaExistenteEnBaseDatos().getIdLista());
		request.setHeader("Content-type", "application/json");
		request.setEntity(retrieveUtil.retrieveStringEntity(creaListaUpdateRequestDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(201, response.getStatusLine().getStatusCode());

	}

	@Test
	public void crearLista_listaCreada_entoncesDevuelve_status201() throws ClientProtocolException, IOException {

		HttpPost request = new HttpPost("http://localhost:8090/api/v1/usuarios/listas");
		request.setHeader("Content-type", "application/json");
		request.setEntity(retrieveUtil.retrieveStringEntity(creaListaCreateRequestDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(201, response.getStatusLine().getStatusCode());

	}

	private Lista listaExistenteEnBaseDatos() {
		Lista lista = new Lista();
		lista.setIdLista(1);
		lista.setTituloLista("Trabajo");

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(1);

		lista.setUsuario(usuario);

		return lista;

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
