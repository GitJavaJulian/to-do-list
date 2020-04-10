package todolist.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TareaActualizaCompletoRequestDTO {

	private String tituloTarea;
	private String detalleTarea;
	private LocalDate fechaRealizarTarea;
	private LocalTime horaRealizarTarea;

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

	public LocalDate getFechaRealizarTarea() {
		return fechaRealizarTarea;
	}

	public void setFechaRealizarTarea(LocalDate fechaRealizarTarea) {
		this.fechaRealizarTarea = fechaRealizarTarea;
	}

	public LocalTime getHoraRealizarTarea() {
		return horaRealizarTarea;
	}

	public void setHoraRealizarTarea(LocalTime horaRealizarTarea) {
		this.horaRealizarTarea = horaRealizarTarea;
	}

}
