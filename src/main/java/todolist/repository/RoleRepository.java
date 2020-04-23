package todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
