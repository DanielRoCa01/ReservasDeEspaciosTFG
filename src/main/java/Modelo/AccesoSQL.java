package Modelo;

import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 *  La calse Acceso SQL modela el acceso a una base de datos SQL
 */
public class AccesoSQL {
	private static AccesoSQL instancia;
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
		  String password="admin";
		  String urlCon="jdbc:mysql://localhost/tfg_reservas";
//		  String password="admin";
//		  String urlCon="jdbc:mysql://localhost/myAnimeList";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(urlCon,usr,password);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error en la base de datos ");
			alert.setHeaderText("Ha ocurrido un error con la conexion a la base de datos ");
			alert.showAndWait();
		}
    }
	

	public ArrayList<Reserva> leerReservas( LocalDate fecha, Time hora1, Time hora2, Espacio espacio,Usuario usuario,String estado,int idInstalacion)
	{
		ArrayList<Reserva> listaReservas=new ArrayList<Reserva> ();
		try
		{
			conectar();
			//Añade a la lista todos los registros de la tabla mangas
			listaReservas.addAll(consultar(generarConsultaReserva(  fecha,  hora1,  hora2,  espacio,  usuario,estado,idInstalacion)));
			desconectar();
			return listaReservas;
		}catch(NullPointerException e) 
		{
			e.printStackTrace();
			return null;
		}	
	}

	public String generarConsultaReserva( LocalDate fecha, Time hora1, Time hora2, Espacio espacio,Usuario usuario,String estado,int idInstalacion) {
		StringBuilder consulta = new StringBuilder("SELECT * FROM reservas WHERE idEspacio in\n" +
				"(SELECT idEspacio FROM tfg_reservas.espacios where idInstalacion="+idInstalacion+")");

		// Agregar condiciones según los parámetros recibidos
		if (fecha != null) {
			consulta.append(" AND fechaReserva = ").append("'"+fecha.toString()+"'");
		}

		if (hora1 != null) {
			consulta.append(" AND horaInicio >= ").append("'"+hora1+"'");
		}

		if (hora2 != null) {
			consulta.append(" AND horaFinal <= ").append("'"+hora2+"'");
		}

		if (espacio != null) {
			consulta.append(" AND idEspacio = ").append(espacio.getIdEspacio());
		}

		if (usuario != null) {
			consulta.append(" AND idUsuario = ").append(usuario.getIdUsuario());
		}
		if (!estado.contentEquals("TODOS") ) {
			consulta.append(" AND estado = ").append("'"+estado+"'");
		}



		System.out.println(consulta.toString());
		return consulta.toString();
	}

	public String generarConsultaUsuario( Seccion seccion,String rol,int idInstalacion) {
		StringBuilder consulta = new StringBuilder("SELECT * FROM usuarios WHERE idInstalacion= "+idInstalacion);

		// Agregar condiciones según los parámetros recibidos
		if (seccion != null) {
			consulta.append(" AND idSeccion = ").append(seccion.getIdSeccion());
		}

		if (rol != null) {
			consulta.append(" AND rol = ").append("'"+rol+"'");
		}

		System.out.println(consulta.toString());
		return consulta.toString();
	}


	public boolean escribirReservas(ArrayList<Reserva> listaReservas)
	{

		return escribir(new ArrayList<Persistente>(listaReservas),Reserva.CAMPOS_SQL);

	}
	public boolean escribirReserva(Reserva reserva)
	{

		return escribir(reserva,Reserva.CAMPOS_SQL);

	}
	public boolean escribir(ArrayList<Persistente> lista, String campos)
	{

		String cadena = generarStringInsercion(lista);
		if(!lista.isEmpty())
		{
			boolean bool=true;

			bool=ejecutarSentencia("INSERT INTO "+campos+" VALUES "
					+cadena.replaceAll("'NULL'", "null")); //Se pone el formato correcto para el tipo null
			return bool; 	//Devuelve si el resusltado de la operaciónse se ha realziado correctamente
		}
		return false;		//Devuelve si el resusltado de la operaciónse no se ha realziado correctamente

	}
	public boolean escribir(Persistente object, String campos)
	{

		if(object!=null)
		{
			boolean bool=true;
			System.out.println("INSERT INTO "+campos+" VALUES "
					+object.toValoresSQL().replaceAll("'NULL'", "null"));
			bool=ejecutarSentencia("INSERT INTO "+campos+" VALUES "
					+object.toValoresSQL().replaceAll("'NULL'", "null")); //Se pone el formato correcto para el tipo null
			return bool; 	//Devuelve si el resusltado de la operaciónse se ha realziado correctamente
		}
		return false;		//Devuelve si el resusltado de la operaciónse no se ha realziado correctamente

	}
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


	public boolean modificar(Persistente persistente)
	{

		if(persistente!=null)
		{
			boolean bool=true;
			System.out.println("UPDATE "+persistente.getUpdateSQL());
			bool=ejecutarSentencia("UPDATE "+persistente.getUpdateSQL()); //Se pone el formato correcto para el tipo null
			return bool; 	//Devuelve si el resusltado de la operaciónse se ha realziado correctamente
		}
		return false;		//Devuelve si el resusltado de la operaciónse no se ha realziado correctamente

	}

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


	public boolean escribirEspacios(ArrayList<Espacio> listaEspacios) {
		return escribir(new ArrayList<Persistente>(listaEspacios),Espacio.CAMPOS_SQL);
	}
	public boolean escribirEspacio(Espacio espacio) {
		return escribir(espacio,Espacio.CAMPOS_SQL);
	}



	public ArrayList<Instalacion> leerInstalaciones() {
        try
		{
			conectar();
			//Añade a la lista todos los registros de la tabla mangas
            ArrayList<Instalacion> listaInstalaciones = new ArrayList<Instalacion>(consultarListaInstalaciones("SELECT * FROM instalaciones"));
			desconectar();
			return listaInstalaciones;
		}catch(NullPointerException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public boolean escribirInstalaciones(ArrayList<Instalacion> listaInstalaciones) {
		return escribir(new ArrayList<Persistente>(listaInstalaciones),Instalacion.CAMPOS_SQL);
	}
	public boolean escribirInstalacion(Instalacion instalacion) {
		return escribir(instalacion,Instalacion.CAMPOS_SQL);
	}


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
	public boolean escribirSeccion(Seccion seccion) {
		return escribir(seccion,Seccion.CAMPOS_SQL);
	}


	public boolean escribirUsuarios(ArrayList<Usuario> listaUsuarios) {
		return escribir(new ArrayList<Persistente>(listaUsuarios),Usuario.CAMPOS_SQL);
	}
	public boolean escribirUsuario(Usuario usuario) {
		return escribir(usuario,Usuario.CAMPOS_SQL);
	}



	/**
	 * Se genera un String con los datos de los mangas a insertar
	 * @param listaReservas listado de registros manga
	 * @return String con los resgistros de los mangas
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

	public boolean comprobarNombreEspacio(String nombre,int idInstalacion){


		return ejecutarFuncion("select comprobarNombreEspacio('"+nombre+"',"+idInstalacion+")");
	}

	public boolean comprobarDisponibilidad(int idEspacio,LocalDate fecha,Time horaInicio,Time horaFinal){
		boolean b=ejecutarFuncion("select comprobarDisponibilidad("+idEspacio+",'"+fecha+"','"+horaInicio+"','"+horaFinal+"')");
		return b;
	}
	public boolean comprobarDisponibilidad(int idEspacio,LocalDate fecha,Time horaInicio,Time horaFinal, int idReserva){
		boolean b=ejecutarFuncion("select comprobarDisponibilidadModif("+idEspacio+",'"+fecha+"','"+horaInicio+"','"+horaFinal+"',"+idReserva+")");
		return b;
	}
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
	 * Realiza la consulta para devolver una lista de mangas
	 * @param consulta consulta sobre la tabla de mangas dada
	 * @return ArrayList<Manga>
	 */
	private  ArrayList<Reserva> consultar(String consulta)
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

	private  ArrayList<Instalacion> consultarListaInstalaciones(String consulta)
	{
		ArrayList<Instalacion> listaIntalaciones= new ArrayList<Instalacion> ();
		try {
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery(consulta);//Resultado de la consulta
			//Inserción del resultado en la lista
			while(result.next()) {
				listaIntalaciones.add(new Instalacion (result.getInt(1),result.getString(2),result.getString(3)));
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaIntalaciones;
	}
	private static int horaAMediasHoras(Time hora) {
		// Convertimos la hora a minutos y luego dividimos por 30 minutos (una media hora)
		LocalTime localTime = hora.toLocalTime();

		// Calcular minutos desde la medianoche
		int minutos = localTime.getHour() * 60 + localTime.getMinute();

		// Convertir a medias horas y retornar como int
		return minutos / 30;
	}
	public boolean[]consultarHorario(int idEspacio, LocalDate fecha)
	{
		boolean[] arrayMediasHoras = new boolean[48];
		try {
			conectar();
			Statement st=con.createStatement();//instantanea de la base de datos
			ResultSet result= st.executeQuery("SELECT horaInicio, horaFinal FROM reservas WHERE idEspacio="+idEspacio+" AND fechaReserva='"+fecha.toString()+"' AND estado!='CANCELADA'");//Resultado de la consulta
			//Inserción del resultado en la lista
			while(result.next()) {

				int indiceInicio = horaAMediasHoras(result.getTime(1));
				int indiceFinal = horaAMediasHoras(result.getTime(2));

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
