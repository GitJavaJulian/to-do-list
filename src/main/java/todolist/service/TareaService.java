package todolist.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import todolist.domain.dto.TareaActualizaCompletoRequestDTO;
import todolist.domain.dto.TareaCreateRequestDTO;
import todolist.domain.dto.TareaResponseDTO;
import todolist.domain.dto.TareaSegunRealizacionResponseDTO;
import todolist.domain.dto.TareaSegunRealizacionResponseV2DTO;
import todolist.domain.dto.TareasDeleteRequestDTO;
import todolist.domain.dto.TareasPorVencerResponseDTO;
import todolist.domain.dto.TareasResponseDTO;
import todolist.domain.dto.TareasSegunFechaResponseDTO;

public interface TareaService {

	public TareaResponseDTO devuelveTarea(long id);

	public List<TareasResponseDTO> devuelveTareasDeLista(long id);

	public List<TareaSegunRealizacionResponseDTO> devuelveTareasUnaListaSegunRealizacion(boolean isActive,
			long idLista);

	public List<TareaSegunRealizacionResponseV2DTO> devuelveTareasUnaListaSegunRealizacionV2(boolean isActive,
			long idLista);

	public List<TareasSegunFechaResponseDTO> devuelveTareasDeUsuarioSinRealizarSegunFecha(LocalDate fecha,
			long idUsuario);

	public List<TareasPorVencerResponseDTO> devuelveTareasDeUsuarioPorVencer(long idUsuario);

	public ResponseEntity<String> eliminaTarea(long id);

	public ResponseEntity<String> eliminaTareas(List<TareasDeleteRequestDTO> tareasDTO);

	public ResponseEntity<Object> actualizaTarea(TareaActualizaCompletoRequestDTO tareaDTO, long id);

	public ResponseEntity<Object> crearTarea(TareaCreateRequestDTO tareaDTO, long id);
}
