package todolist.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import todolist.domain.User;
import todolist.domain.dto.CurrentUserDTO;
import todolist.domain.dto.UsuarioResponseDTO;
import todolist.domain.dto.UsuarioUpdateRequestDTO;
import todolist.domain.dto.UsuariosCreateRequestDTO;
import todolist.domain.dto.UsuariosResponseDTO;

public interface UserService {

	public UsuarioResponseDTO devuelveUsuario(long id);

	public List<UsuariosResponseDTO> devuelveUsuarios();

	public ResponseEntity<String> eliminaUsuario(long id);

	public ResponseEntity<Object> actualizaUsuario(UsuarioUpdateRequestDTO usuarioDTO, long id);

	public User crearUsuario(UsuariosCreateRequestDTO usuarioDTO);

	CurrentUserDTO findByUsername(String username) throws EntityNotFoundException;

	void changeFirstTimePassword(String newPassword, UserDetails userDetails);

}
