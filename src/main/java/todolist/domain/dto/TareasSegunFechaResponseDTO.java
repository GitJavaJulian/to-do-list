package todolist.domain.dto;

import java.time.LocalTime;

public class TareasSegunFechaResponseDTO {

	private long idTarea;
	private String tituloTarea;
	private String detalleTarea;
	private LocalTime horaRealizarTarea;

	public long getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(long idTarea) {
		this.idTarea = idTarea;
	}

	public String getTituloTarea() {
		return tituloTarea;
	}

	public void setTituloTarea(String tituloTarea) {
		this.tituloTarea = tituloTarea;
	}

	public String getDetalleTarea() {
		return detalleTarea;
	}

	public void setDetalleTarea(String detalleTarea) {
		this.detalleTarea = detalleTarea;
	}

	public LocalTime getHoraRealizarTarea() {
		return horaRealizarTarea;
	}

	public void setHoraRealizarTarea(LocalTime horaRealizarTarea) {
		this.horaRealizarTarea = horaRealizarTarea;
	}

}
