package todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.domain.Lista;

public interface ListaRepository extends JpaRepository<Lista, Long> {

}
