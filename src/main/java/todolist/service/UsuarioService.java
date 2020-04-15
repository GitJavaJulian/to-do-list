package todolist.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import todolist.dto.UsuarioResponseDTO;
import todolist.dto.UsuarioUpdateRequestDTO;
import todolist.dto.UsuariosCreateRequestDTO;
import todolist.dto.UsuariosResponseDTO;
import todolist.entity.Usuario;
import todolist.error.CustomerNotFoundException;
import todolist.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	private String mensaje = "No existe el Usuario con id = ";

	public UsuarioResponseDTO devuelveUsuario(long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (!(usuario.isPresent())) {
			throw new CustomerNotFoundException(mensaje + id);
		}

		ModelMapper modelMapper = new ModelMapper();

		return modelMapper.map(usuario.get(), UsuarioResponseDTO.class);
	}

	public List<UsuariosResponseDTO> devuelveUsuarios() {

		ModelMapper modelMapper = new ModelMapper();

		List<Usuario> usuarioList = usuarioRepository.findAll();

		List<UsuariosResponseDTO> dtoList = new ArrayList<>();

		for (Usuario u : usuarioList) {
			dtoList.add(modelMapper.map(u, UsuariosResponseDTO.class));
		}

		return dtoList;
	}

	public ResponseEntity<String> eliminaUsuario(long id) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (!(usuario.isPresent())) {
			throw new CustomerNotFoundException(mensaje + id);
		}

		usuarioRepository.deleteById(id);

		return new ResponseEntity<>("Usuario Eliminado", HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<Object> actualizaUsuario(UsuarioUpdateRequestDTO usuarioDTO, long id) {

		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

		if (!usuarioOptional.isPresent())
			throw new CustomerNotFoundException(mensaje + id);

		ModelMapper modelMapper = new ModelMapper();

		Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

		usuario.setIdUsuario(id);

		usuarioRepository.save(usuario);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(location).build();
	}

	public ResponseEntity<Object> crearUsuario(UsuariosCreateRequestDTO usuarioDTO) {

		ModelMapper modelMapper = new ModelMapper();

		Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

		Usuario usuarioGuardado = usuarioRepository.save(usuario);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(usuarioGuardado.getIdUsuario()).toUri();

		return ResponseEntity.created(location).build();
	}

}
