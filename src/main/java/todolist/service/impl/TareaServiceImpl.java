package todolist.service.impl;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import todolist.domain.Lista;
import todolist.domain.Tarea;
import todolist.domain.dto.TareaActualizaCompletoRequestDTO;
import todolist.domain.dto.TareaCreateRequestDTO;
import todolist.domain.dto.TareaResponseDTO;
import todolist.domain.dto.TareaSegunRealizacionResponseDTO;
import todolist.domain.dto.TareaSegunRealizacionResponseV2DTO;
import todolist.domain.dto.TareasDeleteRequestDTO;
import todolist.domain.dto.TareasPorVencerResponseDTO;
import todolist.domain.dto.TareasResponseDTO;
import todolist.domain.dto.TareasSegunFechaResponseDTO;
import todolist.error.CustomerNotFoundException;
import todolist.repository.ListaRepository;
import todolist.repository.TareaRepository;
import todolist.service.TareaService;

@Service
public class TareaServiceImpl implements TareaService {

	@Autowired
	private TareaRepository tareaRepository;

	@Autowired
	private ListaRepository listaRepository;

	private String mensaje = "No existe el ID = ";

	@Override
	public TareaResponseDTO devuelveTarea(long id) {

		Optional<Tarea> tarea = tareaRepository.findById(id);

		if (!(tarea.isPresent())) {
			throw new CustomerNotFoundException(mensaje + id);
		}

		ModelMapper modelMapper = new ModelMapper();

		return modelMapper.map(tarea.get(), TareaResponseDTO.class);
	}

	@Override
	public List<TareasResponseDTO> devuelveTareasDeLista(long id) {

		ModelMapper modelMapper = new ModelMapper();

		Optional<Lista> lista = listaRepository.findById(id);

		if (!(lista.isPresent())) {
			throw new CustomerNotFoundException(mensaje + id);
		}

		List<TareasResponseDTO> tareasResponseDTO = new ArrayList<>();

		for (Tarea t : lista.get().getTareas()) {
			tareasResponseDTO.add(modelMapper.map(t, TareasResponseDTO.class));
		}

		return tareasResponseDTO;

	}

	@Override
	public List<TareaSegunRealizacionResponseDTO> devuelveTareasUnaListaSegunRealizacion(boolean isActive,
			long idLista) {

		List<Tarea> tareas = tareaRepository.buscarTareasUnaListaSegunRealizacion(isActive, idLista);

		List<TareaSegunRealizacionResponseDTO> tareaSegunRealizacionResponseDTO = new ArrayList<>();

		ModelMapper modelMapper = new ModelMapper();

		for (Tarea t : tareas) {
			tareaSegunRealizacionResponseDTO.add(modelMapper.map(t, TareaSegunRealizacionResponseDTO.class));
		}

		return tareaSegunRealizacionResponseDTO;
	}

	@Override
	public List<TareaSegunRealizacionResponseV2DTO> devuelveTareasUnaListaSegunRealizacionV2(boolean isActive,
			long idLista) {

		List<Tarea> tareas = tareaRepository.buscarTareasUnaListaSegunRealizacion(isActive, idLista);

		List<TareaSegunRealizacionResponseV2DTO> tareaSegunRealizacionResponseV2DTO = new ArrayList<>();

		ModelMapper modelMapper = new ModelMapper();

		for (Tarea t : tareas) {

			tareaSegunRealizacionResponseV2DTO.add(modelMapper.map(t, TareaSegunRealizacionResponseV2DTO.class));
		}

		return tareaSegunRealizacionResponseV2DTO;
	}

	@Override
	public List<TareasSegunFechaResponseDTO> devuelveTareasDeUsuarioSinRealizarSegunFecha(LocalDate fecha,
			long idUsuario) {
		List<Tarea> tareas = tareaRepository.devuelveTareasDeUsuarioSinRealizarSegunFecha(fecha.atStartOfDay(),
				fecha.atTime(LocalTime.MAX), idUsuario);

		List<TareasSegunFechaResponseDTO> tareaSegunRealizacionResponseDTO = new ArrayList<>();

		ModelMapper modelMapper = new ModelMapper();

		for (Tarea t : tareas) {
			tareaSegunRealizacionResponseDTO.add(modelMapper.map(t, TareasSegunFechaResponseDTO.class));
		}

		return tareaSegunRealizacionResponseDTO;
	}

	@Override
	public List<TareasPorVencerResponseDTO> devuelveTareasDeUsuarioPorVencer(long idUsuario) {

		List<Tarea> tareas = tareaRepository.buscarTareasDeUsuarioPorVencer(idUsuario);

		ModelMapper modelMapper = new ModelMapper();

		List<TareasPorVencerResponseDTO> tareasPorVencerResponseDTO = new ArrayList<>();

		for (Tarea t : tareas) {

			t.setTiempoRestante(t.getHoraRealizarTarea().minusNanos(LocalTime.now().toNanoOfDay()));

			tareasPorVencerResponseDTO.add(modelMapper.map(t, TareasPorVencerResponseDTO.class));
		}

		return tareasPorVencerResponseDTO;

	}

	@Override
	public ResponseEntity<String> eliminaTarea(long id) {

		Optional<Tarea> tarea = tareaRepository.findById(id);

		if (!(tarea.isPresent())) {
			throw new CustomerNotFoundException(mensaje + id);
		}

		tareaRepository.delete(tarea.get());

		return new ResponseEntity<>("Tarea eliminada", HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<String> eliminaTareas(List<TareasDeleteRequestDTO> tareasDTO) {

		ModelMapper modelMapper = new ModelMapper();

		List<Tarea> tareas = new ArrayList<>();

		for (TareasDeleteRequestDTO t : tareasDTO) {
			tareas.add(modelMapper.map(t, Tarea.class));
		}

		tareaRepository.deleteAll(tareas);

		return new ResponseEntity<>("Listas de Tareas eliminada", HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Object> actualizaTarea(TareaActualizaCompletoRequestDTO tareaDTO, long id) {

		Optional<Tarea> tareaOptional = tareaRepository.findById(id);

		if (!tareaOptional.isPresent())
			throw new CustomerNotFoundException(mensaje + id);

		ModelMapper modelMapper = new ModelMapper();

		Tarea tarea = modelMapper.map(tareaDTO, Tarea.class);

		tarea.setIdTarea(id);

		tarea.setLista(tareaOptional.get().getLista());

		tareaRepository.save(tarea);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(location).build();
	}

	@Override
	public ResponseEntity<Object> crearTarea(TareaCreateRequestDTO tareaDTO, long id) {

		ModelMapper modelMapper = new ModelMapper();

		Tarea tarea = modelMapper.map(tareaDTO, Tarea.class);

		Lista lista = new Lista();
		lista.setIdLista(id);

		tarea.setLista(lista);

		Tarea tareaGuardada = tareaRepository.save(tarea);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(tareaGuardada.getIdTarea()).toUri();

		return ResponseEntity.created(location).build();
	}

}
