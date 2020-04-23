package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import todolist.domain.AuthToken;
import todolist.domain.dto.LoginUserDTO;
import todolist.domain.dto.UsuariosCreateRequestDTO;
import todolist.security.JwtTokenProvider;
import todolist.service.UserService;

@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserService usuarioService;

	@PostMapping("/login")
	public ResponseEntity<Object> authenticate(@RequestBody LoginUserDTO loginUserDTO) {
		return ResponseEntity.ok(generateToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registrate(@RequestBody UsuariosCreateRequestDTO registerUser) {
		usuarioService.crearUsuario(registerUser);

		return ResponseEntity.ok(generateToken(registerUser.getUsername(), registerUser.getPassword()));
	}

	private AuthToken generateToken(String username, String password) {
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenProvider.generateToken(authentication);
		return new AuthToken(token);
	}

}
