package Modelo;
import java.sql.Date;
import java.sql.Time;

public class Reserva {

    private int id;
    public static String CAMPOS_SQL="`reservas` ( `idUsuario`, `idEspacio`, `horaInicio`, `horaFinal`, `fechaReserva`, `estado`, `descripcion`)";
    private Espacio espacio;

    private Usuario usuario;

    private Time horaInicio;

    private Time horaFinal;

    private Date fecha;
    private String descripcion;

    private String estado;

    public Reserva(int id, Espacio espacio, Usuario usuario, Time horaInicio, Time horaFinal, Date fecha, String descripcion,String estado) {
        this.id = id;
        this.espacio = espacio;
        this.usuario = usuario;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return
                "("  + espacio.getidEspacio() +
                "," + usuario.getIdUsuario()+
                ",'" + horaInicio + '\''+
                ",'" + horaFinal + '\''+
                ",'" + fecha + '\''+
                ",'" + descripcion + '\'' +
                ",'" + estado + '\'' +
                ')';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Time horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getestado() {
        return estado;
    }

    public void setestado(String estado) {
        this.estado = estado;
    }
}
