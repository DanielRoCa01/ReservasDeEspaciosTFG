package Modelo;

import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *  La clase Acceso SQL modela el acceso a una base de datos SQL para la consulta de Reservas, Espacios, Usuarios, Instalaciones
 */
public class AccesoSQL {
	private static AccesoSQL instancia;   //Instancia unica para conectar con la base de datos

	/**
	 * Devuelve la intsnacia unica de la clase siguiendo el diseño Singleton
	 * @return instancia
	 */
	public static AccesoSQL obtenerInstancia() {
		if (instancia == null) {
			instancia = new AccesoSQL();
		}
		return instancia;
	}

	private static  Connection con;//Conexion con la base de datos

	/**
	 * Establece una conexión con la base de datos
	 */
	private  void conectar() {
		  String usr="root";
//		  String password="admin";
//		  String urlCon="jdbc:mysql://localhost/tfg_reservas";
		  String password="NuXldDWggfRmWnSEOOwvvxLmzdLpStuH";
		  String urlCon="jdbc:mysql://root:NuXldDWggfRmWnSEOOwvvxLmzdLpStuH@roundhouse.proxy.rlwy.net:37830/tfg_reservas";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(urlCon,usr,password);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			alertarErrorBBDD();
		}
    }

	/**
	 * Genera una ventana de alerta avisando de un error en la base de datos
	 */
	private static void alertarErrorBBDD()
	{
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error en la base de datos ");
		alert.setHeaderText("Ha ocurrido un error con la conexion a la base de datos ");
		alert.showAndWait();
	}

	/**
	 * Cierra la conexion con la base de datos
	 */
	private  void desconectar()
	{
		try
		{
			con.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Se genera un String con los datos de los reservas a insertar
	 * @param listaReservas listado de reservas
	 * @return String con las reservas para insertar
	 */
	private String generarStringInsercion(ArrayList<Persistente> listaReservas)
	{
		String cadena="";
		for(Persistente res:listaReservas)
		{
			if(listaReservas.indexOf(res)==0)
			{
				cadena=cadena+res.toValoresSQL();
			}
			else
			{
				cadena=cadena+","+res.toValoresSQL();
			}
		}
		return cadena;
	}

	/**
	 * Ejecuta un funcion SQL en la base de datos devolviendo un valor booleano
	 * @param sentencia sentencia SQL con la funcion a ejecutar
	 * @return resultado valor booleano con reusltado de la función
	 */
	private  boolean ejecutarFuncion(String sentencia)
	{
		boolean resultado = false;
		try  {
			conectar();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sentencia);
			if (rs.next()) {
				resultado = rs.getBoolean(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	/**
	 * Ejecuta una sentencia en lka base de datos
	 * @return boolean en función de si se realiza correctamente
	 */
	private  boolean ejecutarSentencia(String sentencia)
	{
		conectar();
		try
		{
			//Se genera una instantanea de la base de datos
			Statement st=con.createStatement();
			st.execute(sentencia);
			st.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		desconectar();
		return true;
	}

	/**
	 * Realiza la consulta para devolver una lista de reservas
	 * @param consulta consulta sobre la tabla de Reservas dada
	 * @return ArrayList<Reserva>
	 */
	private  ArrayList<Reserva> consultarListaReservas(String consulta)
	{
		ArrayList<Reserva> reservas= new ArrayList<Reserva> ();
		try {
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta
			//Inserción del resultado en la lista
			while(result.next()) {
				reservas.add(new Reserva(result.getInt(1),consultarEspacio(result.getInt(3)),
						consultarUsuario(result.getInt(2)),result.getTime(4),
						result.getTime(5), LocalDate.parse(result.getString(6)),
						result.getString(7),result.getString(8)));
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservas;
	}

	/**
	 * Realiza la consulta para devolver una lista de espacios
	 * @param consulta consulta sobre la tabla de Espacios dada
	 * @return ArrayList<Espacio>
	 */
	private  ArrayList<Espacio> consultarListaEspacios(String consulta)
	{
		ArrayList<Espacio> listaEspacios= new ArrayList<Espacio> ();
		try {
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta
			//Inserción del resultado en la lista
			while(result.next()) {
				listaEspacios.add(new Espacio (result.getInt(1),result.getString(2),
						result.getString(3),result.getTime(4),
						result.getTime(5),result.getString(6),
						consultarInstalacion(result.getInt(7))));
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaEspacios;
	}

	/**
	 * Realiza la consulta para devolver una lista de usuarios
	 * @param consulta consulta sobre la tabla de Usuarios dada
	 * @return ArrayList<Usuario>
	 */
	private  ArrayList<Usuario> consultarListaUsuarios(String consulta)
	{
		ArrayList<Usuario> listaUsuarios= new ArrayList<Usuario> ();
		try {
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta
			//Inserción del resultado en la lista
			while(result.next()) {
				listaUsuarios.add(new Usuario (result.getInt(1),result.getString(2),
						result.getString(3),consultarSeccion(result.getInt(4)),
						consultarInstalacion(result.getInt(5))));
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaUsuarios;
	}

	/**
	 * Realiza la consulta para devolver una lista de secciones
	 * @param consulta consulta sobre la tabla de Secciones dada
	 * @return ArrayList<Seccion>
	 */
	private  ArrayList<Seccion> consultarListaSecciones(String consulta)
	{
		ArrayList<Seccion> listaSecciones= new ArrayList<Seccion> ();
		try {
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta
			//Inserción del resultado en la lista
			while(result.next()) {
				listaSecciones.add(new Seccion (result.getInt(1),result.getString(2),
						result.getString(3),
						consultarInstalacion(result.getInt(4))));
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaSecciones;
	}

	/**
	 * Devuelve una lista de reservas en funcion de los parametros especificados
	 * @param fecha fecha de la reserva
	 * @param hora1 hora inicial de la reserva
	 * @param hora2 hora final de la reserva
	 * @param espacio espacio que se reserva
	 * @param usuario usuario que realiza la reserva
	 * @param estado estado en el que se encuentra la reserva
	 * @param idInstalacion id de la instalacion donde se reserva
	 * @return ArrayList<Reserva>
	 */
	public ArrayList<Reserva> leerReservas( LocalDate fecha, Time hora1, Time hora2, Espacio espacio,Usuario usuario,String estado,int idInstalacion)
	{
		ArrayList<Reserva> listaReservas=new ArrayList<Reserva> ();
		try
		{
			conectar();
			//Añade a la lista todos los registros de la tabla mangas
			listaReservas.addAll(consultarListaReservas(generarConsultaReserva(  fecha,  hora1,  hora2,  espacio,  usuario,estado,idInstalacion)));
			desconectar();
			return listaReservas;
		}catch(NullPointerException e) 
		{
			e.printStackTrace();
			return null;
		}	
	}

	/**
	 *Devuelve un String con la consulta de las reservas filtrando en funcion de los parametros
	 * @param fecha fecha de la reserva
	 * @param hora1 hora inicial de la reserva
	 * @param hora2 hora final de la reserva
	 * @param espacio espacio que se reserva
	 * @param usuario usuario que realiza la reserva
	 * @param estado estado en el que se encuentra la reserva
	 * @param idInstalacion id de la instalacion donde se reserva
	 * @return String
	 */
	public String generarConsultaReserva( LocalDate fecha, Time hora1, Time hora2, Espacio espacio,Usuario usuario,String estado,int idInstalacion) {
		StringBuilder consulta = new StringBuilder("SELECT * FROM reservas WHERE idEspacio in\n" +
				"(SELECT idEspacio FROM tfg_reservas.espacios where idInstalacion="+idInstalacion+")");

		// Si la fecha no es nula se añade el filtro de fecha
		if (fecha != null) {
			consulta.append(" AND fechaReserva = ").append("'"+fecha.toString()+"'");
		}
		// Si la hora de inicio no es nula se añade el filtro de hora1
		if (hora1 != null) {
			consulta.append(" AND horaInicio >= ").append("'"+hora1+"'");
		}
		// Si la hora de finalizacion no es nula se añade el filtro de hora2
		if (hora2 != null) {
			consulta.append(" AND horaFinal <= ").append("'"+hora2+"'");
		}
		// Si el espacio  no es nulo se añade el filtro de espacio
		if (espacio != null) {
			consulta.append(" AND idEspacio = ").append(espacio.getIdEspacio());
		}
		// Si el usuario  no es nulo se añade el filtro de usuario
		if (usuario != null) {
			consulta.append(" AND idUsuario = ").append(usuario.getIdUsuario());
		}
		// Si el estado  no es nulo se añade el filtro de estado
		if (!estado.contentEquals("TODOS") ) {
			consulta.append(" AND estado = ").append("'"+estado+"'");
		}
		return consulta.toString();
	}

	/**
	 * Devuelve un String con la consulta de laos usuarios filtrando en funcion de los parametros
	 * @param seccion seccion a la que pertenecen los usuarios
	 * @param rol rol que cumplen los usuaarios
	 * @param idInstalacion id de la instalacion de deonde es el usuario
	 * @return String
	 */
	private String generarConsultaUsuario( Seccion seccion,String rol,int idInstalacion) {
		StringBuilder consulta = new StringBuilder("SELECT * FROM usuarios WHERE idInstalacion= "+idInstalacion);

		// Si la seccion  no es nula se añade el filtro de seccion
		if (seccion != null) {
			consulta.append(" AND idSeccion = ").append(seccion.getIdSeccion());
		}
		// Si el rol no es nulo se añade el filtro de rol
		if (rol != null) {
			consulta.append(" AND rol = ").append("'"+rol+"'");
		}
		return consulta.toString();
	}

	/**
	 * Escribe en la base de datos el objeto persistente(Reservas,Espacios...)
	 * @param object objeto persistente(Reservas,Espacios...)
	 * @param campos campos de la clase concreta del objeto que se esta almacenando
	 * @return boolean
	 */
	public boolean escribir(Persistente object, String campos)
	{

		if(object!=null)
		{
			boolean bool=true;
			bool=ejecutarSentencia("INSERT INTO "+campos+" VALUES "
					+object.toValoresSQL().replaceAll("'NULL'", "null")); //Se pone el formato correcto para el tipo null
			return bool; 	//Devuelve si el resusltado de la operaciónse se ha realziado correctamente
		}
		return false;		//Devuelve si el resusltado de la operaciónse no se ha realziado correctamente

	}

	/**
	 * Modifica una reserva para darle el estado de cancelada
	 * @param reserva reserva a cancelar
	 * @return boolean
	 */
	public boolean cancelarReserva(Reserva reserva)
	{

		if(reserva!=null)
		{
			boolean bool=true;

			bool=ejecutarSentencia("UPDATE reservas SET "


					+Reserva.CAMPOS[6]+"= 'CANCELADA' "
					+" WHERE "+Reserva.CAMPOS[0]+" = "+reserva.getId()); //Se pone el formato correcto para el tipo null
			return bool; 	//Devuelve si el resusltado de la operaciónse se ha realziado correctamente
		}
		return false;		//Devuelve si el resusltado de la operaciónse no se ha realziado correctamente

	}

	/**
	 * Modifica el conjunto de reservas que no estan dentro del horario del espacio dado
	 * @param espacio espacio de las reservas
	 * @return boolean
	 */
	public boolean cancelarReservasProhibidas(Espacio espacio)
	{

		if(espacio!=null)
		{
			boolean bool=true;

			bool=ejecutarSentencia("Update reservas set estado='CANCELADA' where ('"+espacio.getHoraApertura()+"' > horaInicio or '"+espacio.getHoraCierre()+"' < horaInicio or  '"
					+espacio.getHoraApertura()+"' > horaFinal or '"+espacio.getHoraCierre()+"' <horaFinal) and idEspacio="+espacio.getIdEspacio()); //Se pone el formato correcto para el tipo null
			return bool; 	//Devuelve si el resusltado de la operaciónse se ha realziado correctamente
		}
		return false;		//Devuelve si el resusltado de la operaciónse no se ha realziado correctamente

	}

	/**
	 * Modifica el objeto persistente
	 * @param persistente objeto a modificar
	 * @return boolean
	 */
	public boolean modificar(Persistente persistente)
	{

		if(persistente!=null)
		{
			boolean bool=true;
			bool=ejecutarSentencia("UPDATE "+persistente.getUpdateSQL()); //Se pone el formato correcto para el tipo null
			return bool; 	//Devuelve si el resusltado de la operaciónse se ha realziado correctamente
		}
		return false;		//Devuelve si el resusltado de la operaciónse no se ha realziado correctamente

	}

	/**
	 * Pide la lista de Espacios de una instalacion concreta
	 * @param idInstalacion id de la instalacion concreta
	 * @return ArrayList<Espacio>
	 */
	public ArrayList<Espacio> leerEspacios(int idInstalacion) {
        try
		{
			conectar();
			//Añade a la lista todos los registros de la tabla mangas
            ArrayList<Espacio> listaEspacios = new ArrayList<Espacio>(consultarListaEspacios("SELECT * FROM espacios where idInstalacion="+idInstalacion));
			desconectar();
			return listaEspacios;
		}catch(NullPointerException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Pide la lista de Reservas de una instalacion concreta a una fecha y horas concretas
	 * @param idInstalacion id de la instalacion concreta
	 * @param fecha fecha de las reservas
	 * @param hora1 hora de inicio de las reservas
	 * @param hora2 hora final de las reservas
	 * @return ArrayList<Espacio>
	 */
	public ArrayList<Espacio> leerEspaciosLibres(int idInstalacion, String fecha, Time hora1, Time hora2) {
		try
		{
			conectar();
			//Añade a la lista todos los registros de la tabla mangas
			ArrayList<Espacio> listaEspacios = new ArrayList<Espacio>(consultarListaEspacios("call consultarEspaciosDisponibles( "+idInstalacion+",'"+fecha+"','"+hora1+"','"+hora2+"');"));
			desconectar();
			return listaEspacios;
		}catch(NullPointerException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Manda escribir espacio en la base de datos
	 * @param espacio espacio a almacenar
	 * @return boolean
	 */
	public boolean escribirEspacio(Espacio espacio) {
		return escribir(espacio,Espacio.CAMPOS_SQL);
	}

	/**
	 * Manda escribir espacio en la base de datos
	 * @param instalacion espacio a almacenar
	 * @return boolean
	 */
	public boolean escribirInstalacion(Instalacion instalacion) {
		return escribir(instalacion,Instalacion.CAMPOS_SQL);
	}

	/**
	 * Manda escribir espacio en la base de datos
	 * @param seccion espacio a almacenar
	 * @return boolean
	 */
	public boolean escribirSeccion(Seccion seccion) {
		return escribir(seccion,Seccion.CAMPOS_SQL);
	}

	/**
	 * Manda escribir espacio en la base de datos
	 * @param usuario espacio a almacenar
	 * @return boolean
	 */
	public boolean escribirUsuario(Usuario usuario) {
		return escribir(usuario,Usuario.CAMPOS_SQL);
	}

	/**
	 * Pide la lista de Usuarios de una instalacion concreta
	 * @param idInstalacion id de la instalacion concreta
	 * @return ArrayList<Usuario>
	 */
	public ArrayList<Usuario> leerUsuarios( int idInstalacion) {
        try
		{
			conectar();
			//Añade a la lista todos los registros de la tabla mangas
            ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>(consultarListaUsuarios("SELECT * FROM usuarios where idInstalacion="+idInstalacion));
			desconectar();
			return listaUsuarios;
		}catch(NullPointerException e)
		{
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * Pide la lista de Usuarios de una instalacion concreta con una seccion y rol concretos
	 * @param seccion seccion a la que pertenecen los usuarios
	 * @param rol rol que cumplen los usuaarios
	 * @param idInstalacion id de la instalacion de deonde es el usuario
	 * @return ArrayList<Usuario>
	 */
	public ArrayList<Usuario> leerUsuarios(  Seccion seccion,String rol,int idInstalacion) {
		try
		{
			conectar();
			//Añade a la lista todos los registros de la tabla mangas
			ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>(consultarListaUsuarios(generarConsultaUsuario( seccion, rol, idInstalacion)));
			desconectar();
			return listaUsuarios;
		}catch(NullPointerException e)
		{
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * Pide la lista de Secciones de una instalacion concreta
	 * @param idInstalacion id de la instalacion concreta
	 * @return ArrayList<Seccion>
	 */
	public ArrayList<Seccion> leerSecciones( int idInstalacion) {
		try
		{
			conectar();
			//Añade a la lista todos los registros de la tabla mangas
			ArrayList<Seccion> listaSecciones = new ArrayList<Seccion>(consultarListaSecciones("SELECT * FROM secciones where idInstalacion="+idInstalacion));
			desconectar();
			return listaSecciones;
		}catch(NullPointerException e)
		{
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * Se pide ejecutar una funcion a la base de datos que compruebe si el nombre del espacio existe
	 * @param nombre nombre del espacio
	 * @param idInstalacion id de la instalacion del espacio
	 * @return boolean
	 */
	public boolean comprobarNombreEspacio(String nombre,int idInstalacion){
		return ejecutarFuncion("select comprobarNombreEspacio('"+nombre+"',"+idInstalacion+")");
	}

	/**
	 * Se pide ejecutar una funcion a la base de datos que compruebe la disponibilidad del espacio
	 * @param idEspacio id del espacio a comprobar
	 * @param fecha fecha cde la reserva a comprobar la disponibilidad
	 * @param horaInicio hora de inicio de la reserva a comprobar la disponibilidad
	 * @param horaFinal hora de finalizacion de la reserva a comprobar la disponibilidad
	 * @return boolean
	 */
	public boolean comprobarDisponibilidad(int idEspacio,LocalDate fecha,Time horaInicio,Time horaFinal){
		boolean b=ejecutarFuncion("select comprobarDisponibilidad("+idEspacio+",'"+fecha+"','"+horaInicio+"','"+horaFinal+"')");
		return b;
	}

	/**
	 *Se pide ejecutar una funcion a la base de datos que compruebe la disponibilidad del espacio
	 * @param idEspacio id del espacio a comprobar
	 * @param fecha fecha cde la reserva a comprobar la disponibilidad
	 * @param horaInicio hora de inicio de la reserva a comprobar la disponibilidad
	 * @param horaFinal hora de finalizacion de la reserva a comprobar la disponibilidad
	 * @param idReserva id de la reserva modificada
	 * @return
	 */
	public boolean comprobarDisponibilidad(int idEspacio,LocalDate fecha,Time horaInicio,Time horaFinal, int idReserva){
		boolean b=ejecutarFuncion("select comprobarDisponibilidadModif("+idEspacio+",'"+fecha+"','"+horaInicio+"','"+horaFinal+"',"+idReserva+")");
		return b;
	}

	/**
	 * Se consulta el horario de disponibilidad de un espacio concreto
	 * @param idEspacio id del espacio que se quiere consultar
	 * @param fecha fecha cuando el horario se quiere consultar
	 * @return boolean[]
	 */
	public boolean[]consultarHorario(int idEspacio, LocalDate fecha)
	{
		boolean[] arrayMediasHoras = new boolean[48];
		try {
			conectar();
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery("SELECT horaInicio, horaFinal FROM reservas WHERE idEspacio="+idEspacio+" AND fechaReserva='"+fecha.toString()+"' AND estado!='CANCELADA'");//Resultado de la consulta
			//Inserción del resultado en la lista
			while(result.next()) {

				int indiceInicio = Reserva.horaAMediasHoras(result.getTime(1));
				int indiceFinal = Reserva.horaAMediasHoras(result.getTime(2));

				// Establecemos como false los elementos correspondientes en el rango de índices
				for (int i = indiceInicio; i < indiceFinal; i++) {
					arrayMediasHoras[i] = true;
				}
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		desconectar();
		return arrayMediasHoras;
	}

	/**
	 * Se consulta un espacio concreto
	 * @param id id del espacio
	 * @return Espacio
	 */
	public  Espacio consultarEspacio(int id)
	{
		conectar();
		String consulta="SELECT * FROM espacios WHERE idEspacio="+id;
		try {
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta
	result.next();
			//Inserción del resultado en la lista

			Espacio espacio= new Espacio (result.getInt(1),result.getString(2),
					result.getString(3),result.getTime(4),
					result.getTime(5),result.getString(6),consultarInstalacion(result.getInt(7)));;


			st.close();
			return espacio;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		desconectar();
		return null;
	}

	/**
	 * Consulta un usuario concreto
	 * @param id id de usuario concreto
	 * @return Usuario
	 */
	public  Usuario consultarUsuario(int id)
	{
		conectar();
		String consulta="SELECT * FROM usuarios WHERE idUsuario="+id;
		try {
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta
			result.next();
			//Inserción del resultado en la lista

			Usuario usuario= new Usuario (result.getInt(1),result.getString(2),
					result.getString(3),consultarSeccion(result.getInt(4)),
					consultarInstalacion(result.getInt(5)));


			st.close();
			return usuario;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		desconectar();
		return null;
	}

	/**
	 * Consulta una seecion concreta
	 * @param id id de la seccion concreta
	 * @return Seccion
	 */
	public  Seccion consultarSeccion(int id)
	{
		conectar();
		String consulta="SELECT * FROM secciones WHERE idSeccion="+id;
		try {
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta
			result.next();
			//Inserción del resultado en la lista

			Seccion seccion= new Seccion (result.getInt(1),result.getString(2),
					result.getString(3),
					consultarInstalacion(result.getInt(4)));


			st.close();
			return seccion;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		desconectar();
		return null;
	}

	/**
	 * Consulta una seccion concreta por la instalacion y el nombre
	 * @param instalacion id de la instalacion de la seccion
	 * @param nombreSeccion nombre de la seccion concreta
	 * @return Seccion
	 */
	public  Seccion consultarSeccion(int instalacion, String nombreSeccion)
	{
		conectar();
		String consulta="SELECT * FROM secciones WHERE idInstalacion="+instalacion+" AND nombreSeccion='"+nombreSeccion+"'";

		try {
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta
			result.next();
			//Inserción del resultado en la lista

			Seccion seccion= new Seccion (result.getInt(1),result.getString(2),
					result.getString(3),
					consultarInstalacion(result.getInt(4)));


			st.close();
			return seccion;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		desconectar();
		return null;
	}

	/**
	 * Consulta un usuario concreto por la instalacion y el nombre de usuario
	 * @param instalacion id de la instalacion del usuario
	 * @param nombreUsuario nombre del usuario concreto
	 * @return Usuario
	 */
	public  Usuario consultarUsuario(String instalacion, String nombreUsuario)
	{
		conectar();
		try {
		String consulta="SELECT * FROM usuarios WHERE nombreUsuario='"+nombreUsuario + "' AND idInstalacion="+consultarInstalacion(instalacion).getIdInstalacion();

			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta
			result.next();
			//Inserción del resultado en la lista

			Usuario usuario= new Usuario (result.getInt(1),result.getString(2),
					result.getString(3),consultarSeccion(result.getInt(4)),
					consultarInstalacion(result.getInt(5)));


			st.close();
			return usuario;
		} catch (SQLException | NullPointerException e)
		{
			desconectar();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Consulta una instalacion concreta
	 * @param id id de la instalacion concreta
	 * @return Instalacion
	 */
	public  Instalacion consultarInstalacion(int id)
	{

		try {
			conectar();
			String consulta="SELECT * FROM instalaciones WHERE idInstalacion="+id;
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta

			//Inserción del resultado en la lista
			result.next();
			Instalacion instalacion= new Instalacion (result.getInt(1),result.getString(2),result.getString(3));;


			st.close();
			return instalacion;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		desconectar();
		return null;
	}

	/**
	 * Consulta una instalacion concreta por su nombre
	 * @param nombreInstalacion nombre de la instalacion concreta
	 * @return Instalacion
	 */
	public  Instalacion consultarInstalacion(String nombreInstalacion){
		conectar();
		String consulta="SELECT * FROM instalaciones WHERE nombre='"+nombreInstalacion+"'";
		try {
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta

			//Inserción del resultado en la lista
			result.next();
			Instalacion instalacion= new Instalacion (result.getInt(1),result.getString(2),result.getString(3));;


			st.close();
			return instalacion;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		desconectar();
		return null;
	}


}
