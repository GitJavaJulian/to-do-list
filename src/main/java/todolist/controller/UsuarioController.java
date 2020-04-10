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

import todolist.dto.UsuarioResponseDTO;
import todolist.dto.UsuarioUpdateRequestDTO;
import todolist.dto.UsuariosCreateRequestDTO;
import todolist.dto.UsuariosResponseDTO;
import todolist.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/v1/usuarios/{id}")
	public UsuarioResponseDTO devuelveUsuario(@PathVariable long id) {
		return usuarioService.devuelveUsuario(id);
	}

	// SOlO ACCEDIDO POR ADMINISTRADOR
	@GetMapping("/v1/usuarios")
	public List<UsuariosResponseDTO> devuelveUsuarios() {
		return usuarioService.devuelveUsuarios();
	}

	// SOlO ACCEDIDO POR ADMINISTRADOR
	@DeleteMapping("/v1/usuarios/{id}")
	public ResponseEntity<String> eliminaUsuario(@PathVariable long id) {
		return usuarioService.eliminaUsuario(id);
	}

	@PutMapping("/v1/usuarios/{id}")
	public ResponseEntity<Object> actualizaUsuario(@RequestBody UsuarioUpdateRequestDTO usuario,
			@PathVariable long id) {

		return usuarioService.actualizaUsuario(usuario, id);
	}

	@PostMapping("/v1/usuarios")
	public ResponseEntity<Object> crearUsuario(@RequestBody UsuariosCreateRequestDTO usuario) {
		return usuarioService.crearUsuario(usuario);

	}
}
