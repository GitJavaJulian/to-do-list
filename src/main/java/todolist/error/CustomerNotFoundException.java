package todolist.error;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7364179314653634905L;

	public CustomerNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

	public CustomerNotFoundException(String arg0) {
		super(arg0);

	}

	public CustomerNotFoundException(Throwable arg0) {
		super(arg0);

	}

}
