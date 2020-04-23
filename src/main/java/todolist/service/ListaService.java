package todolist.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import todolist.domain.dto.ListaCreateRequestDTO;
import todolist.domain.dto.ListaResponseDTO;
import todolist.domain.dto.ListaUpdateRequestDTO;
import todolist.domain.dto.ListasResponseDTO;

public interface ListaService {

	public ListaResponseDTO devuelveLista(long id);

	public List<ListasResponseDTO> devuelveListasDeUsuario(long id);

	public ResponseEntity<String> eliminaLista(long id);

	public ResponseEntity<Object> actualizaLista(ListaUpdateRequestDTO listaDTO, long id);

	public ResponseEntity<Object> crearLista(ListaCreateRequestDTO listaDTO);

}
