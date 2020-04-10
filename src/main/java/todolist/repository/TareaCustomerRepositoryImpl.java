package todolist.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import todolist.entity.Tarea;

@Repository
public class TareaCustomerRepositoryImpl implements TareaCustomerRepository {

	@Autowired
	public EntityManager theEntity;

	@Override
	public List<Tarea> buscarTareasUnaListaSegunRealizacion(boolean isActive, long idLista) {

		Query query = theEntity.unwrap(Session.class).createQuery("SELECT T FROM Lista L INNER JOIN Tarea T \r\n"
				+ "ON L.idLista = T.lista.idLista\r\n" + "WHERE L.idLista = :id_lista AND T.realizada = :is_active");
		query.setParameter("id_lista", idLista);
		query.setParameter("is_active", isActive);
		List<Tarea> results = query.list();
		return results;
	}

	@Override
	public List<Tarea> devuelveTareasDeUsuarioSinRealizarSegunFecha(LocalDateTime fechaDesde, LocalDateTime fechaHasta,
			long idUsuario) {
		Query query = theEntity.unwrap(Session.class).createQuery("SELECT T FROM Lista L INNER JOIN Tarea T \r\n"
				+ "ON L.idLista = T.lista.idLista\r\n"
				+ "WHERE L.usuario.idUsuario = :id_usuario AND (timestamp(T.fechaRealizarTarea) BETWEEN timestamp(:fecha_desde) AND timestamp(:fecha_hasta)) AND T.realizada = 'false'");
		query.setParameter("id_usuario", idUsuario);
		query.setParameter("fecha_desde", fechaDesde);
		query.setParameter("fecha_hasta", fechaHasta);
		List<Tarea> results = query.list();
		return results;
	}

	@Override
	public List<Tarea> buscarTareasDeUsuarioPorVencer(long idUsuario) {
		Query query = theEntity.unwrap(Session.class).createQuery("SELECT T FROM Lista L INNER JOIN Tarea T \r\n"
				+ "ON L.idLista = T.lista.idLista\r\n"
				+ "WHERE L.usuario.idUsuario = :id_usuario AND T.realizada = 'false' AND timediff(T.horaRealizarTarea,time(now())) <= '01:00:00' and timediff(T.horaRealizarTarea,time(now())) > '00:00:00'  \r\n"
				+ "and T.fechaRealizarTarea = date(now())");
		query.setParameter("id_usuario", idUsuario);
		List<Tarea> results = query.list();
		return results;
	}

}
