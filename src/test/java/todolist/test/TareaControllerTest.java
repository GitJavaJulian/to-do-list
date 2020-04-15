package todolist.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import todolist.ToDoListApplication;
import todolist.dto.TareaActualizaCompletoRequestDTO;
import todolist.dto.TareaCreateRequestDTO;
import todolist.dto.TareaResponseDTO;
import todolist.dto.TareaSegunRealizacionResponseV2DTO;
import todolist.dto.TareasSegunFechaResponseDTO;
import todolist.entity.Lista;
import todolist.entity.Tarea;
import todolist.entity.Usuario;
import todolist.test.utils.RetrieveUtil;

@SpringBootTest
class TareaControllerTest {

	@Autowired
	private RetrieveUtil retrieveUtil;
	private static Tarea tareatareaExistenteEnBaseDatos;

	@BeforeAll
	private static void creaTareaExistenteEnBaseDatos() {

		tareatareaExistenteEnBaseDatos = new Tarea();
		tareatareaExistenteEnBaseDatos.setIdTarea(1);
		tareatareaExistenteEnBaseDatos.setTituloTarea("Tarea 1");
		tareatareaExistenteEnBaseDatos.setDetalleTarea("Detalle 1");

		Lista lista = new Lista();
		lista.setIdLista(1);

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(1);

		lista.setUsuario(usuario);
		tareatareaExistenteEnBaseDatos.setLista(lista);

	}

	@Test
	public void devuelveTarea_idTareaExiste_entoncesDevuelve_status200_JSON_DTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet(
				"http://localhost:8090/api/v1/tareas/" + tareatareaExistenteEnBaseDatos.getIdTarea());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		TareaResponseDTO resource = RetrieveUtil.retrieveResourceFromResponse(response, TareaResponseDTO.class);
		assertThat(tareatareaExistenteEnBaseDatos.getTituloTarea(), Matchers.is(resource.getTituloTarea()));
		assertThat(tareatareaExistenteEnBaseDatos.getDetalleTarea(), Matchers.is(resource.getDetalleTarea()));
	}

	@Test
	public void devuelveTareasUnaListaSegunRealizacionV2_idListaExisteYTiene2Realizadas_entoncesDevuelve_status200_JSON_DTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet("http://localhost:8090/api/v2/listas/"
				+ tareatareaExistenteEnBaseDatos.getLista().getIdLista() + "/tareas/activas?active=true");

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		List<TareaSegunRealizacionResponseV2DTO> resource = RetrieveUtil.retrieveResourceFromResponse(response,
				List.class);
		assertTrue(resource.size() == 2);
	}

	@Test
	public void devuelveTareasUnaListaSegunRealizacionV2_idListaExisteYTiene1SinRealizar_entoncesDevuelve_status200_JSON_DTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet("http://localhost:8090/api/v2/listas/"
				+ tareatareaExistenteEnBaseDatos.getLista().getIdLista() + "/tareas/activas?active=false");

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		List<TareaSegunRealizacionResponseV2DTO> resource = RetrieveUtil.retrieveResourceFromResponse(response,
				List.class);
		assertTrue(resource.size() == 1);
	}

	@Test
	public void devuelveTareasDeUsuarioSinRealizarSegunFecha_idUsuarioExisteYTiene2SinRealizarEnFecha_entoncesDevuelve_status200_JSON_DTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet("http://localhost:8090/api/v1/usuarios/"
				+ tareatareaExistenteEnBaseDatos.getLista().getUsuario().getIdUsuario()
				+ "/tareas/fecha?fecha=2020-04-07");

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		List<TareasSegunFechaResponseDTO> resource = RetrieveUtil.retrieveResourceFromResponse(response, List.class);
		assertTrue(resource.size() == 2);
	}

	@Test
	public void eliminaTarea_tareaEliminada_entoncesDevuelve_status204() throws ClientProtocolException, IOException {

		HttpDelete request = new HttpDelete(
				"http://localhost:8090/api/v1/tareas/" + tareatareaExistenteEnBaseDatos.getIdTarea());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(204, response.getStatusLine().getStatusCode());

	}

	@Test
	public void actualizaTarea_tareaActualizada_entoncesDevuelve_status201()
			throws ClientProtocolException, IOException {

		HttpPut request = new HttpPut(
				"http://localhost:8090/api/v1/tareas/" + tareatareaExistenteEnBaseDatos.getIdTarea());
		request.setHeader("Content-type", "application/json");
		request.setEntity(retrieveUtil.retrieveStringEntity(creaTareaActualizaCompletoRequestDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(201, response.getStatusLine().getStatusCode());

	}

	@Test
	public void crearTarea_tareaCreadaEnLista1_entoncesDevuelve_status201()
			throws ClientProtocolException, IOException {

		HttpPost request = new HttpPost("http://localhost:8090/api/v1/listas/" + 1 + "/tareas");
		request.setHeader("Content-type", "application/json");
		request.setEntity(retrieveUtil.retrieveStringEntity(creaTareaCreateRequestDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(201, response.getStatusLine().getStatusCode());

	}

	private TareaActualizaCompletoRequestDTO creaTareaActualizaCompletoRequestDTO() {

		TareaActualizaCompletoRequestDTO tareaActualizaCompletoRequestDTO = new TareaActualizaCompletoRequestDTO();
		tareaActualizaCompletoRequestDTO.setTituloTarea("titulo_nuevo");

		return tareaActualizaCompletoRequestDTO;
	}

	private TareaCreateRequestDTO creaTareaCreateRequestDTO() {

		TareaCreateRequestDTO tareaCreateRequestDTO = new TareaCreateRequestDTO();
		tareaCreateRequestDTO.setTituloTarea("tarea_nueva");
		tareaCreateRequestDTO.setDetalleTarea("detalle_nuevo");

		return tareaCreateRequestDTO;
	}
}
