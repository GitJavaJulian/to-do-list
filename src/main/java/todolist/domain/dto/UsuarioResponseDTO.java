package todolist.domain.dto;

import java.util.List;

public class UsuarioResponseDTO {

	private long idUsuario;
	private String nombreUsuario;
	private String mailUsuario;
	private List<ListaResponseDTO> listas;

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

	public List<ListaResponseDTO> getListas() {
		return listas;
	}

	public void setListas(List<ListaResponseDTO> listas) {
		this.listas = listas;
	}

}
