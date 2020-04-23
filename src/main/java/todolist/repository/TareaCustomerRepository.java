package todolist.repository;

import java.time.LocalDateTime;
import java.util.List;

import todolist.domain.Tarea;

public interface TareaCustomerRepository {

	List<Tarea> buscarTareasUnaListaSegunRealizacion(boolean isActive, long idLista);

	List<Tarea> devuelveTareasDeUsuarioSinRealizarSegunFecha(LocalDateTime fechaDesde, LocalDateTime fechaHasta,
			long idUsuario);

	List<Tarea> buscarTareasDeUsuarioPorVencer(long idUsuario);
}
