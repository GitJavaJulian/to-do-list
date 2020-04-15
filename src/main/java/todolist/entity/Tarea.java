package todolist.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tarea")
public class Tarea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tarea")
	private long idTarea;
	@Column(name = "titulo_tarea")
	private String tituloTarea;
	@Column(name = "detalle_tarea")
	private String detalleTarea;
	@Column(name = "fecha_realizar_tarea")
	private LocalDate fechaRealizarTarea;
	@Column(name = "hora_realizar_tarea")
	private LocalTime horaRealizarTarea;
	@Column(name = "realizada")
	private boolean realizada;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_lista", updatable = false)
	private Lista lista;

	@Transient
	private LocalTime tiempoRestante;

	public Tarea() {

	}

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

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}

	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}

	public LocalTime getTiempoRestante() {
		return tiempoRestante;
	}

	public void setTiempoRestante(LocalTime duration) {
		this.tiempoRestante = duration;
	}

	@Override
	public String toString() {
		return "Tarea [idTarea=" + idTarea + ", tituloTarea=" + tituloTarea + ", detalleTarea=" + detalleTarea
				+ ", fechaRealizarTarea=" + fechaRealizarTarea + ", horaRealizarTarea=" + horaRealizarTarea
				+ ", realizada=" + realizada + ", lista=" + lista + "]";
	}

}
