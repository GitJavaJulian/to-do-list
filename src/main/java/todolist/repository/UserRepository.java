package todolist.repository;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username) throws EntityNotFoundException;

}
