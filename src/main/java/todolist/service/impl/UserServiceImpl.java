package todolist.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import todolist.domain.MyUserPrincipal;
import todolist.domain.User;
import todolist.domain.dto.CurrentUserDTO;
import todolist.domain.dto.UsuarioResponseDTO;
import todolist.domain.dto.UsuarioUpdateRequestDTO;
import todolist.domain.dto.UsuariosCreateRequestDTO;
import todolist.domain.dto.UsuariosResponseDTO;
import todolist.error.CustomerNotFoundException;
import todolist.repository.RoleRepository;
import todolist.repository.UserRepository;
import todolist.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository usuarioRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	private String mensaje = "No existe el Usuario con id = ";

	@Override
	public UsuarioResponseDTO devuelveUsuario(long id) {
		Optional<User> usuario = usuarioRepository.findById(id);

		if (!(usuario.isPresent())) {
			throw new CustomerNotFoundException(mensaje + id);
		}

		ModelMapper modelMapper = new ModelMapper();

		return modelMapper.map(usuario.get(), UsuarioResponseDTO.class);
	}

	@Override
	public List<UsuariosResponseDTO> devuelveUsuarios() {

		ModelMapper modelMapper = new ModelMapper();

		List<User> usuarioList = usuarioRepository.findAll();

		List<UsuariosResponseDTO> dtoList = new ArrayList<>();

		for (User u : usuarioList) {
			dtoList.add(modelMapper.map(u, UsuariosResponseDTO.class));
		}

		return dtoList;
	}

	@Override
	public ResponseEntity<String> eliminaUsuario(long id) {

		Optional<User> usuario = usuarioRepository.findById(id);

		if (!(usuario.isPresent())) {
			throw new CustomerNotFoundException(mensaje + id);
		}

		usuarioRepository.deleteById(id);

		return new ResponseEntity<>("Usuario Eliminado", HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Object> actualizaUsuario(UsuarioUpdateRequestDTO usuarioDTO, long id) {

		Optional<User> usuarioOptional = usuarioRepository.findById(id);

		if (!usuarioOptional.isPresent())
			throw new CustomerNotFoundException(mensaje + id);

		User user = usuarioOptional.get();
		if (usuarioDTO.getNombreUsuario() != null && !usuarioDTO.getNombreUsuario().equals(user.getNombreUsuario())) {
			user.setNombreUsuario(usuarioDTO.getNombreUsuario());
		}
		if (usuarioDTO.getMailUsuario() != null && !usuarioDTO.getMailUsuario().equals(user.getMailUsuario())) {
			user.setMailUsuario(usuarioDTO.getMailUsuario());
		}

		usuarioRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(location).build();
	}

	@Override
	public User crearUsuario(UsuariosCreateRequestDTO usuarioDTO) {

		User existingUser = usuarioRepository.findByUsername(usuarioDTO.getUsername());

		if (existingUser != null) {
			throw new EntityExistsException("Ya existe un Usuario con ese nombre de usuario.");
		}

		ModelMapper modelMapper = new ModelMapper();

		User usuario = modelMapper.map(usuarioDTO, User.class);

		usuario.setPassword(bcryptEncoder.encode(usuarioDTO.getPassword()));
		usuario.setTempPassword(true);
		usuario.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("USER"))));

		return usuarioRepository.save(usuario);
	}

	@Override
	public void changeFirstTimePassword(String newPassword, UserDetails userDetails) {
		CurrentUserDTO currentUserDTO = findByUsername(userDetails.getUsername());

		ModelMapper modelMapper = new ModelMapper();
		User usuario = modelMapper.map(currentUserDTO, User.class);

		usuario.setPassword(bcryptEncoder.encode(newPassword));
		usuario.setTempPassword(false);
		usuarioRepository.save(usuario);
	}

	@Override
	public CurrentUserDTO findByUsername(String username) throws EntityNotFoundException {

		User currentUser = usuarioRepository.findByUsername(username);

		ModelMapper modelMapper = new ModelMapper();

		return modelMapper.map(currentUser, CurrentUserDTO.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = usuarioRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new MyUserPrincipal(user);
	}

}
