package todolist.error;

public class CustomerBadRequestException extends RuntimeException {

	private static final long serialVersionUID = -5745395983992890493L;

	public CustomerBadRequestException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

	public CustomerBadRequestException(String arg0) {
		super(arg0);

	}

	public CustomerBadRequestException(Throwable arg0) {
		super(arg0);

	}

}
