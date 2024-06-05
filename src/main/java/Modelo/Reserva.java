package Modelo;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase que modela una reserva sobre un espacio hecha por un usuario concreto dentro de la aplicacion
 */
public class Reserva implements Persistente {

    private int id;
    public static String CAMPOS_SQL="`reservas` ( `idUsuario`, `idEspacio`, `horaInicio`, `horaFinal`, `fechaReserva`, `estado`, `descripcion`)";//Campos de la clase para la insercion SQL
    public static String[] CAMPOS= {"idReserva","idUsuario", "idEspacio", "horaInicio", "horaFinal", "fechaReserva", "estado", "descripcion"};//Campos de la clase para SQL

    public final static ArrayList<String> ESTADOS = new ArrayList<>(Arrays.asList("CANCELADA", "RESERVADA", "MODIFICADA", "REALIZADA","TODOS"));//Posibles estados que puede tener una reserva

    private Espacio espacio;     //Espacio que se reserva

    private Usuario usuario;     //Usuario que reserva

    private Time horaInicio;

    private Time horaFinal;

    private LocalDate fecha;
    private String descripcion;

    private String estado;

    public Reserva(int id, Espacio espacio, Usuario usuario, Time horaInicio, Time horaFinal, LocalDate fecha,String estado, String descripcion) {
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
                "("  + usuario.getIdUsuario() +
                        "," +espacio.getIdEspacio() +
                        ",'" + horaInicio + '\''+
                        ",'" + horaFinal + '\''+
                        ",'" + fecha + '\''+
                        ",'" + estado+ '\'' +
                        ",'" +descripcion  + '\'' +
                        ')';
    }
public boolean isUpadatable(){
        return !LocalDate.now().isAfter(fecha.minusWeeks(1)) ;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toValoresSQL() {
          return toString();
    }

    @Override
    public String getUpdateSQL() {
        return "reservas SET "+Reserva.CAMPOS[1]+"="+usuario.getIdUsuario()+" , "
                +Reserva.CAMPOS[2]+"="+espacio.getIdEspacio()+" , "
                +Reserva.CAMPOS[3]+"= '"+horaInicio+"' , "
                +Reserva.CAMPOS[4]+"= '"+horaFinal+"' , "
                +Reserva.CAMPOS[5]+"= '"+fecha+"' , "
                +Reserva.CAMPOS[6]+"= 'MODIFICADA' , "
                +Reserva.CAMPOS[7]+"= '"+descripcion+"'"
                +" WHERE "+Reserva.CAMPOS[0]+"="+id;
    }

    /**
     * Funcion de apoyo que genera todas las horas dentro de un dia cada media hora
     * @return List<Time>
     */
    public static List<Time> generarHorasDelDia() {
        List<Time> horasDelDia = new ArrayList<>();

        for (int hora = 0; hora < 24; hora++) {
            for (int minuto = 0; minuto < 60; minuto += 30) {
                horasDelDia.add(Time.valueOf(String.format("%02d:%02d:00", hora, minuto)));
            }
        }

        return horasDelDia;
    }

    /**
     * Funcion de apoyo que devuelve la posicion en el dia de la media hora dada
     * @param hora media hora concreta
     * @return int
     */
    public static int horaAMediasHoras(Time hora) {
        // Convertimos la hora a minutos y luego dividimos por 30 minutos (una media hora)
        LocalTime localTime = hora.toLocalTime();

        // Calcular minutos desde la medianoche
        int minutos = localTime.getHour() * 60 + localTime.getMinute();

        // Convertir a medias horas y retornar como int
        return minutos / 30;
    }
}
