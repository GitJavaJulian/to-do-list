package todolist.domain.dto;

public class TareaSegunRealizacionResponseV2DTO {

	private String tituloTarea;
	private String detalleTarea;
	private static final String MENSAJE = "Nueva version";

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

	public String getMenaje() {
		return MENSAJE;
	}

}
