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
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import todolist.ToDoListApplication;
import todolist.domain.Lista;
import todolist.domain.Tarea;
import todolist.domain.User;
import todolist.domain.dto.TareaActualizaCompletoRequestDTO;
import todolist.domain.dto.TareaCreateRequestDTO;
import todolist.domain.dto.TareaResponseDTO;
import todolist.domain.dto.TareaSegunRealizacionResponseV2DTO;
import todolist.test.utils.RetrieveUtil;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class TareaControllerTest {

	@Autowired
	private RetrieveUtil retrieveUtil;

	private static Tarea tareaExistenteEnBaseDatos;

	@BeforeAll
	private static void creaTareaExistenteEnBaseDatos() {

		tareaExistenteEnBaseDatos = new Tarea();
		tareaExistenteEnBaseDatos.setIdTarea(1);
		tareaExistenteEnBaseDatos.setTituloTarea("Tarea 1");
		tareaExistenteEnBaseDatos.setDetalleTarea("Detalle 1");

		Lista lista = new Lista();
		lista.setIdLista(1);

		User usuario = new User();
		usuario.setIdUsuario(1);

		lista.setUsuario(usuario);
		tareaExistenteEnBaseDatos.setLista(lista);

	}

	@Test
	@Order(1)
	public void devuelveTarea_idTareaExiste_entoncesDevuelve_status200_JSON_DTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet(
				ToDoListApplication.TEST_HOST + "api/v1/tareas/" + tareaExistenteEnBaseDatos.getIdTarea());
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		TareaResponseDTO resource = RetrieveUtil.retrieveResourceFromResponse(response, TareaResponseDTO.class);
		assertThat(tareaExistenteEnBaseDatos.getTituloTarea(), Matchers.is(resource.getTituloTarea()));
		assertThat(tareaExistenteEnBaseDatos.getDetalleTarea(), Matchers.is(resource.getDetalleTarea()));
	}

	@Test
	@Order(2)
	public void devuelveTareasUnaListaSegunRealizacionV2_idListaExisteYTiene2Realizadas_entoncesDevuelve_status200_JSON_DTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet(ToDoListApplication.TEST_HOST + "api/v2/listas/"
				+ tareaExistenteEnBaseDatos.getLista().getIdLista() + "/tareas/activas?active=true");
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		List<TareaSegunRealizacionResponseV2DTO> resource = RetrieveUtil.retrieveResourceFromResponse(response,
				List.class);
		assertTrue(resource.size() == 2);
	}

	@Test
	@Order(3)
	public void devuelveTareasUnaListaSegunRealizacionV2_idListaExisteYTiene1SinRealizar_entoncesDevuelve_status200_JSON_DTO()
			throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet(ToDoListApplication.TEST_HOST + "api/v2/listas/"
				+ tareaExistenteEnBaseDatos.getLista().getIdLista() + "/tareas/activas?active=false");
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(200, response.getStatusLine().getStatusCode());

		assertEquals(ToDoListApplication.TIPO_DATO_DEVUELTO,
				ContentType.getOrDefault(response.getEntity()).getMimeType());

		List<TareaSegunRealizacionResponseV2DTO> resource = RetrieveUtil.retrieveResourceFromResponse(response,
				List.class);
		assertTrue(resource.size() == 1);
	}

	@Test
	@Order(4)
	public void actualizaTarea_tareaActualizada_entoncesDevuelve_status201()
			throws ClientProtocolException, IOException {

		HttpPut request = new HttpPut(
				ToDoListApplication.TEST_HOST + "api/v1/tareas/" + tareaExistenteEnBaseDatos.getIdTarea());
		request.setHeader("Content-type", "application/json");
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());
		request.setEntity(retrieveUtil.retrieveStringEntity(creaTareaActualizaCompletoRequestDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(201, response.getStatusLine().getStatusCode());

	}

	@Test
	@Order(5)
	public void crearTarea_tareaCreadaEnLista1_entoncesDevuelve_status201()
			throws ClientProtocolException, IOException {

		HttpPost request = new HttpPost(ToDoListApplication.TEST_HOST + "api/v1/listas/" + 1 + "/tareas");
		request.setHeader("Content-type", "application/json");
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());
		request.setEntity(retrieveUtil.retrieveStringEntity(creaTareaCreateRequestDTO()));

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(201, response.getStatusLine().getStatusCode());

	}

	@Test
	@Order(6)
	public void eliminaTarea_tareaEliminada_entoncesDevuelve_status204() throws ClientProtocolException, IOException {

		HttpDelete request = new HttpDelete(
				ToDoListApplication.TEST_HOST + "api/v1/tareas/" + tareaExistenteEnBaseDatos.getIdTarea());
		request.setHeader("Authorization", retrieveUtil.creaToken().getToken());

		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assertEquals(204, response.getStatusLine().getStatusCode());

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
