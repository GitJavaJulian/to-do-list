package todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.entity.Tarea;

public interface TareaRepository extends JpaRepository<Tarea, Long>, TareaCustomerRepository {

}
