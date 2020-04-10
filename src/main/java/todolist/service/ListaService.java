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

import todolist.dto.ListaCreateRequestDTO;
import todolist.dto.ListaResponseDTO;
import todolist.dto.ListaUpdateRequestDTO;
import todolist.dto.ListasResponseDTO;
import todolist.dto.TareaResponseDTO;
import todolist.entity.Lista;
import todolist.entity.Tarea;
import todolist.entity.Usuario;
import todolist.error.CustomerNotFoundException;
import todolist.repository.ListaRepository;
import todolist.repository.UsuarioRepository;

@Service
public class ListaService {

	@Autowired
	private ListaRepository listaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	private String mensaje = "No existe el ID = ";

	public ListaResponseDTO devuelveLista(long id) {

		Optional<Lista> lista = listaRepository.findById(id);

		if (!(lista.isPresent())) {
			throw new CustomerNotFoundException(mensaje + id);
		}

		ModelMapper modelMapper = new ModelMapper();

		List<Tarea> tareas = lista.get().getTareas();

		List<TareaResponseDTO> tareasDTO = new ArrayList<>();

		ListaResponseDTO listaResponseDTO = modelMapper.map(lista.get(), ListaResponseDTO.class);

		for (Tarea t : tareas) {
			tareasDTO.add(modelMapper.map(t, TareaResponseDTO.class));
		}

		listaResponseDTO.setTarea(tareasDTO);

		return listaResponseDTO;
	}

	public List<ListasResponseDTO> devuelveListasDeUsuario(long id) {

		ModelMapper modelMapper = new ModelMapper();

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (!(usuario.isPresent())) {
			throw new CustomerNotFoundException(mensaje + id);
		}

		List<ListasResponseDTO> listasDTO = new ArrayList<>();

		for (Lista l : usuario.get().getListas()) {
			listasDTO.add(modelMapper.map(l, ListasResponseDTO.class));
		}

		return listasDTO;

	}

	public ResponseEntity<String> eliminaLista(long id) {

		Optional<Lista> lista = listaRepository.findById(id);

		if (!(lista.isPresent())) {
			throw new CustomerNotFoundException(mensaje + id);
		}

		listaRepository.deleteById(id);

		return new ResponseEntity<>("Lista eliminada", HttpStatus.ACCEPTED);
	}

	public ResponseEntity<Object> actualizaLista(ListaUpdateRequestDTO listaDTO, long id) {

		Optional<Lista> listaOptional = listaRepository.findById(id);

		if (!listaOptional.isPresent())
			throw new CustomerNotFoundException(mensaje + id);

		ModelMapper modelMapper = new ModelMapper();

		Lista lista = modelMapper.map(listaDTO, Lista.class);

		lista.setIdLista(id);

		listaRepository.save(lista);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(location).build();
	}

	public ResponseEntity<Object> crearLista(ListaCreateRequestDTO listaDTO) {

		Lista lista = new Lista();
		lista.setTituloLista(listaDTO.getTituloLista());

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(listaDTO.getIdUsuario());

		lista.setUsuario(usuario);

		Lista listaGuardada = listaRepository.save(lista);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(listaGuardada.getIdLista()).toUri();

		return ResponseEntity.created(location).build();
	}

}
