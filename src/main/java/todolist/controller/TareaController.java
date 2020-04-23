package todolist.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import todolist.domain.dto.TareaActualizaCompletoRequestDTO;
import todolist.domain.dto.TareaCreateRequestDTO;
import todolist.domain.dto.TareaResponseDTO;
import todolist.domain.dto.TareaSegunRealizacionResponseDTO;
import todolist.domain.dto.TareaSegunRealizacionResponseV2DTO;
import todolist.domain.dto.TareasDeleteRequestDTO;
import todolist.domain.dto.TareasPorVencerResponseDTO;
import todolist.domain.dto.TareasResponseDTO;
import todolist.domain.dto.TareasSegunFechaResponseDTO;
import todolist.service.TareaService;

@RestController
@RequestMapping("/api")
public class TareaController {

	@Autowired
	private TareaService tareaService;

	@GetMapping("/v1/tareas/{id}")
	public TareaResponseDTO devuelveTarea(@PathVariable long id) {
		return tareaService.devuelveTarea(id);
	}

	@GetMapping("/v1/listas/{id}/tareas")
	public List<TareasResponseDTO> devuelveTareasDeLista(@PathVariable long id) {
		return tareaService.devuelveTareasDeLista(id);
	}

	@GetMapping("/v1/listas/{id}/tareas/activas")
	public List<TareaSegunRealizacionResponseDTO> devuelveTareasUnaListaSegunRealizacion(
			@RequestParam(name = "active", defaultValue = "false") boolean isActive, @PathVariable long id) {

		return tareaService.devuelveTareasUnaListaSegunRealizacion(isActive, id);
	}

	// Version nueva que devuelve nuevos datos
	@GetMapping("/v2/listas/{id}/tareas/activas")
	public List<TareaSegunRealizacionResponseV2DTO> devuelveTareasUnaListaSegunRealizacionV2(
			@RequestParam(name = "active", defaultValue = "false") boolean isActive, @PathVariable long id) {

		return tareaService.devuelveTareasUnaListaSegunRealizacionV2(isActive, id);
	}

	@GetMapping("/v1/usuarios/{id}/tareas/fecha")
	public List<TareasSegunFechaResponseDTO> devuelveTareasDeUsuarioSinRealizarSegunFecha(
			@RequestParam(name = "fecha") @DateTimeFormat(iso = ISO.DATE) LocalDate fecha, @PathVariable long id) {
		return tareaService.devuelveTareasDeUsuarioSinRealizarSegunFecha(fecha, id);
	}

	@GetMapping("/v1/usuarios/{id}/tareas/finaliza")
	public List<TareasPorVencerResponseDTO> devuelveTareasDeUsuarioPorVencer(@PathVariable long id) {
		return tareaService.devuelveTareasDeUsuarioPorVencer(id);
	}

	@DeleteMapping("/v1/tareas/{id}")
	public ResponseEntity<String> eliminaTarea(@PathVariable long id) {
		return tareaService.eliminaTarea(id);
	}

	@DeleteMapping("/v1/tareas")
	public ResponseEntity<String> eliminaTareas(@RequestBody List<TareasDeleteRequestDTO> tareas) {
		return tareaService.eliminaTareas(tareas);
	}

	@PutMapping("/v1/tareas/{id}")
	public ResponseEntity<Object> actualizaTarea(@RequestBody TareaActualizaCompletoRequestDTO tarea,
			@PathVariable long id) {

		return tareaService.actualizaTarea(tarea, id);
	}

	@PostMapping("/v1/listas/{id}/tareas")
	public ResponseEntity<Object> crearTarea(@RequestBody TareaCreateRequestDTO tarea, @PathVariable long id) {
		return tareaService.crearTarea(tarea, id);

	}

}
