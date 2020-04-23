package todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todolist.domain.dto.ListaCreateRequestDTO;
import todolist.domain.dto.ListaResponseDTO;
import todolist.domain.dto.ListaUpdateRequestDTO;
import todolist.domain.dto.ListasResponseDTO;
import todolist.service.ListaService;

@RestController
@RequestMapping("/api")
public class ListaController {

	@Autowired
	private ListaService listaService;

	@GetMapping("/v1/listas/{id}")
	public ListaResponseDTO devuelveLista(@PathVariable long id) {
		return listaService.devuelveLista(id);
	}

	@GetMapping("/v1/usuarios/{id}/listas")
	public List<ListasResponseDTO> devuelveListasDeUsuario(@PathVariable long id) {
		return listaService.devuelveListasDeUsuario(id);
	}

	@DeleteMapping("/v1/listas/{id}")
	public ResponseEntity<String> eliminaLista(@PathVariable long id) {
		return listaService.eliminaLista(id);
	}

	@PutMapping("/v1/listas/{id}")
	public ResponseEntity<Object> actualizaLista(@RequestBody ListaUpdateRequestDTO lista, @PathVariable long id) {

		return listaService.actualizaLista(lista, id);
	}

	// El ID del usuario lo paso en el DTO
	@PostMapping("/v1/usuarios/listas")
	public ResponseEntity<Object> crearLista(@RequestBody ListaCreateRequestDTO lista) {
		return listaService.crearLista(lista);

	}

}
