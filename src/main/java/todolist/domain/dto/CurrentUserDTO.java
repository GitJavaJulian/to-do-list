package todolist.domain.dto;

public class CurrentUserDTO {

	private long idUsuario;
	private String nombreUsuario;
	private String mailUsuario;
	private String username;
	private String password;
	private boolean tempPassword = true;

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getMailUsuario() {
		return mailUsuario;
	}

	public void setMailUsuario(String mailUsuario) {
		this.mailUsuario = mailUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(boolean tempPassword) {
		this.tempPassword = tempPassword;
	}

}
