package todolist.dto;

public class UsuarioUpdateRequestDTO {

	private long idUsuario;
	private String nombreUsuario;
	private String mailUsuario;

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

}
