package todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.entity.Lista;

public interface ListaRepository extends JpaRepository<Lista, Long>, ListaCustomerRepository {

}
