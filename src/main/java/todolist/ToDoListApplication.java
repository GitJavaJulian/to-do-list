package todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoListApplication {

	public static final String TIPO_DATO_DEVUELTO = "application/json";

	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}

}
