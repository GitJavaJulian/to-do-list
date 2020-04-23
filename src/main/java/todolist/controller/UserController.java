package todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todolist.domain.AuthToken;
import todolist.domain.dto.CurrentUserDTO;
import todolist.domain.dto.UsuarioResponseDTO;
import todolist.domain.dto.UsuarioUpdateRequestDTO;
import todolist.domain.dto.UsuariosResponseDTO;
import todolist.security.JwtTokenProvider;
import todolist.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService usuarioService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserDetailsService userDetailsService;

	@GetMapping("/v1/usuarios/{id}")
	public UsuarioResponseDTO devuelveUsuario(@PathVariable long id) {
		return usuarioService.devuelveUsuario(id);
	}

	@GetMapping("/v1/usuarios")
	@PreAuthorize("hasRole('ADMIN')")
	public List<UsuariosResponseDTO> devuelveUsuarios() {
		return usuarioService.devuelveUsuarios();
	}

	@DeleteMapping("/v1/usuarios/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> eliminaUsuario(@PathVariable long id) {
		return usuarioService.eliminaUsuario(id);
	}

	@PutMapping("/v1/usuarios/{id}")
	public ResponseEntity<Object> actualizaUsuario(@RequestBody UsuarioUpdateRequestDTO usuario,
			@PathVariable long id) {

		return usuarioService.actualizaUsuario(usuario, id);
	}

	@GetMapping("/v1/usuarios/getCurrentUserInfo")
	public CurrentUserDTO getCurrentUserInfo(@AuthenticationPrincipal UserDetails loggedUser) {
		return usuarioService.findByUsername(loggedUser.getUsername());
	}

	@PostMapping("/v1/usuarios/changeFirstTimePassword")
	public ResponseEntity<?> changeFirstTimePassword(@RequestBody String newPassword,
			@AuthenticationPrincipal UserDetails loggedUser) {
		usuarioService.changeFirstTimePassword(newPassword, loggedUser);
		UserDetails updatedUser = userDetailsService.loadUserByUsername(loggedUser.getUsername());
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(updatedUser, null,
				currentAuth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		return ResponseEntity.ok(new AuthToken(jwtTokenProvider.generateToken(newAuth)));
	}

}
