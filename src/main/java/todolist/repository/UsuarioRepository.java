package todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
