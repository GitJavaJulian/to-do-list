package todolist.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import todolist.entity.Lista;

@Repository
public class ListaCustomerRepositoryImpl implements ListaCustomerRepository {

	@Autowired
	public EntityManager theEntity;

	@Override
	public List<Lista> devuelveListasDeUsuario(long id) {
		return null;

	}

}
