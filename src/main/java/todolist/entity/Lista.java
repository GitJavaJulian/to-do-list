package todolist.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "lista")
public class Lista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_lista")
	private long idLista;
	@Column(name = "titulo_lista")
	private String tituloLista;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_usuario", updatable = false)
	private Usuario usuario;
	@JsonManagedReference
	@OneToMany(mappedBy = "lista", cascade = CascadeType.ALL)
	private List<Tarea> tareas;

	public Lista() {
	}

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

	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public void addTarea(Tarea tarea) {
		tarea.setLista(this);
		this.tareas.add(tarea);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Lista [idLista=" + idLista + ", tituloLista=" + tituloLista + "]";
	}

}
