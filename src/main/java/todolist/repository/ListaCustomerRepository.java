package todolist.repository;

import java.util.List;

import todolist.entity.Lista;

public interface ListaCustomerRepository {

	List<Lista> devuelveListasDeUsuario(long id);

}
