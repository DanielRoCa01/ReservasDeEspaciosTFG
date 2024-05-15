package Modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 *  La calse Acceso SQL modela el acceso a una base de datos SQL
 */
public class AccesoSQL {

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
		}
    }
	

	public ArrayList<Reserva> leerReservas( Date fecha, Time hora1, Time hora2, Integer idEspacio, Integer idUsuario,String estado)
	{
		ArrayList<Reserva> listaReservas=new ArrayList<Reserva> ();
		try
		{
			conectar();
			//Añade a la lista todos los registros de la tabla mangas
			listaReservas.addAll(consultar(generarConsultaReserva(  fecha,  hora1,  hora2,  idEspacio,  idUsuario,estado)));
			desconectar();
			return listaReservas;
		}catch(NullPointerException e) 
		{
			e.printStackTrace();
			return null;
		}	
	}

	public String generarConsultaReserva( Date fecha, Time hora1, Time hora2, Integer idEspacio, Integer idUsuario,String estado) {
		StringBuilder consulta = new StringBuilder("SELECT * FROM reservas WHERE 1=1");

		// Agregar condiciones según los parámetros recibidos
		if (fecha != null) {
			consulta.append(" AND fechaReserva = ").append("'"+fecha+"'");
		}

		if (hora1 != null) {
			consulta.append(" AND horaInicio >= ").append("'"+hora1+"'");
		}

		if (hora2 != null) {
			consulta.append(" AND horaFin <= ").append("'"+hora2+"'");
		}

		if (idEspacio != null) {
			consulta.append(" AND idEspacio = ").append(idEspacio);
		}

		if (idUsuario != null) {
			consulta.append(" AND idUsuario = ").append(idUsuario);
		}
		if (!estado.contentEquals("TODOS")) {
			consulta.append(" AND idUsuario = ").append(idUsuario);
		}




		return consulta.toString();
	}


	public boolean escribirReservas(ArrayList<Reserva> listaReservas)
	{

		return escribir(new ArrayList<Object>(listaReservas),Reserva.CAMPOS_SQL);

	}
	public boolean escribirReserva(Reserva reserva)
	{

		return escribir(reserva,Reserva.CAMPOS_SQL);

	}
	public boolean escribir(ArrayList<Object> lista, String campos)
	{

		String cadena = generarStringInsercion(lista);
		if(!lista.isEmpty())
		{
			boolean bool=true;
			bool=ejecutarSentencia("TRUNCATE TABLE mangas");
			bool=ejecutarSentencia("INSERT INTO "+campos+" VALUES "
					+cadena.replaceAll("'NULL'", "null")); //Se pone el formato correcto para el tipo null
			return bool; 	//Devuelve si el resusltado de la operaciónse se ha realziado correctamente
		}
		return false;		//Devuelve si el resusltado de la operaciónse no se ha realziado correctamente

	}
	public boolean escribir(Object object, String campos)
	{

		if(object!=null)
		{
			boolean bool=true;

			bool=ejecutarSentencia("INSERT INTO "+campos+" VALUES "
					+object.toString().replaceAll("'NULL'", "null")); //Se pone el formato correcto para el tipo null
			return bool; 	//Devuelve si el resusltado de la operaciónse se ha realziado correctamente
		}
		return false;		//Devuelve si el resusltado de la operaciónse no se ha realziado correctamente

	}
	public boolean modificarReserva(Reserva reserva)
	{

		if(reserva!=null)
		{
			boolean bool=true;

			bool=ejecutarSentencia("UPDATE reservas SET "+Reserva.CAMPOS[1]+"="+reserva.getUsuario().getIdUsuario()+" , "
					+Reserva.CAMPOS[2]+"="+reserva.getEspacio().getidEspacio()+" , "
					+Reserva.CAMPOS[3]+"= '"+reserva.getHoraInicio()+"' , "
					+Reserva.CAMPOS[4]+"= '"+reserva.getHoraFinal()+"' , "
					+Reserva.CAMPOS[5]+"= '"+reserva.getFecha()+"' , "
					+Reserva.CAMPOS[6]+"= 'MODIFICADA' , "
					+Reserva.CAMPOS[7]+"= '"+reserva.getDescripcion()+"'"
					+" WHERE "+Reserva.CAMPOS[0]+"="+reserva.getId()); //Se pone el formato correcto para el tipo null
			return bool; 	//Devuelve si el resusltado de la operaciónse se ha realziado correctamente
		}
		return false;		//Devuelve si el resusltado de la operaciónse no se ha realziado correctamente

	}public boolean cancelarReserva(Reserva reserva)
	{

		if(reserva!=null)
		{
			boolean bool=true;

			bool=ejecutarSentencia("UPDATE reservas SET "


					+Reserva.CAMPOS[6]+"= 'CANCELADA' , "
					+" WHERE "+Reserva.CAMPOS[0]+"="+reserva.getId()); //Se pone el formato correcto para el tipo null
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
		return escribir(new ArrayList<Object>(listaEspacios),Espacio.CAMPOS_SQL);
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
		return escribir(new ArrayList<Object>(listaInstalaciones),Instalacion.CAMPOS_SQL);
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


	public boolean escribirUsuarios(ArrayList<Usuario> listaUsuarios) {
		return escribir(new ArrayList<Object>(listaUsuarios),Usuario.CAMPOS_SQL);
	}
	public boolean escribirUsuario(Usuario usuario) {
		return escribir(usuario,Usuario.CAMPOS_SQL);
	}



	/**
	 * Se genera un String con los datos de los mangas a insertar
	 * @param listaReservas listado de registros manga
	 * @return String con los resgistros de los mangas
	 */
	private String generarStringInsercion(ArrayList<Object> listaReservas)
	{
		String cadena="";
		for(Object res:listaReservas)
		{
			if(listaReservas.indexOf(res)==0)
			{
				cadena=cadena+res.toString();
			}
			else 
			{
			cadena=cadena+","+res.toString();
			}
		}
		return cadena;
	}

	public boolean comprobarNombreEspacio(String nombre){
		return ejecutarFuncion("select comprobarNombreEspacio('"+nombre+"')");
	}
	private  boolean ejecutarFuncion(String sentencia)
	{
		Boolean bool;
		conectar();
		try
		{
			//Se genera una instantanea de la base de datos
			Statement st=con.createStatement();
			bool=st.execute(sentencia);
			st.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		desconectar();
		return bool;
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
				reservas.add(new Reserva(result.getInt(1),consultarEspacio(result.getInt(2)),
						consultarUsuario(result.getInt(3)),result.getTime(4),
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
						result.getString(3),result.getString(4),
						consultarInstalacion(result.getInt(5))));
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaUsuarios;
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
					result.getString(3),result.getString(4),
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
	public  Instalacion consultarInstalacion(int id)
	{
		conectar();
	String consulta="SELECT * FROM instalaciones WHERE idInstalacion="+id;
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
