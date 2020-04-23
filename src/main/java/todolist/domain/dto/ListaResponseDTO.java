package todolist.domain.dto;

import java.util.List;

public class ListaResponseDTO {

	private long idLista;
	private String tituloLista;
	private List<TareaResponseDTO> tarea;

	public long getIdLista() {
		return idLista;
	}

	public void setIdLista(long idLista) {
		this.idLista = idLista;
	}

	public String getTituloLista() {
		return tituloLista;
	}

	public void setTituloLista(String tituloLista) {
		this.tituloLista = tituloLista;
	}

	public List<TareaResponseDTO> getTarea() {
		return tarea;
	}

	public void setTarea(List<TareaResponseDTO> tarea) {
		this.tarea = tarea;
	}

}
