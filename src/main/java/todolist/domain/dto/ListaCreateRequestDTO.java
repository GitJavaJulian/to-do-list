package todolist.domain.dto;

public class ListaCreateRequestDTO {

	private String tituloLista;
	private long idUsuario;

	public String getTituloLista() {
		return tituloLista;
	}

	public void setTituloLista(String tituloLista) {
		this.tituloLista = tituloLista;
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

}
