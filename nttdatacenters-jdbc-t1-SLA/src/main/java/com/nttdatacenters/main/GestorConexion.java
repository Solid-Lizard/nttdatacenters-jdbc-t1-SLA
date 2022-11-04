package com.nttdatacenters.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NTTData Taller práctico jdbc - Clase GestorConexion
 * 
 * Esta clase se encarga de iniciar y cerrar la conexión con la BDD
 * 
 * @author Santiago
 * @version 1.0
 */
public class GestorConexion {
	// Variables //	
	private String url;
	private String pssw;
	private String user;
		
	// Objetos //
	private Connection con;
	
	// Log que escupe la información en el archivo "logs/info.log"
	private static final Logger log = LoggerFactory.getLogger("logInfo");
	
	// Log que escupe la información en el archivo "logs/errors.log"
	private static final Logger log2 = LoggerFactory.getLogger("logError");
	
	/**
	 * Constructor por defecto, contiene los parámetros necesarios para iniciar conexión en mi BDD
	 * 		
	 */
	public GestorConexion() {
		url = "jdbc:mysql://localhost:3307/nttdata_jdbc_ex";
		pssw = "manolo";
		user = "root";		
		
		log.debug("Nombre de usuario: {}", user);
		log.debug("Contraseña: {}", pssw);
		log.debug("URL a la base de datos: {}", url);
	}
	
	/**
	 * Constructor sobrecargado, permite integrar tros parámetros si los parámetros por defecto no sirven
	 * 
	 * @param url - Url de la base de datos
	 * @param user - Nombre del usuario
	 * @param pssw - Contraseña del usuario	 
	 */
	public GestorConexion(String url, String pssw, String user) {
		this.url = url;
		this.pssw = pssw;
		this.user = user;
		
		log.debug("Nombre de usuario: {}", user);
		log.debug("Contraseña: {}", pssw);
		log.debug("URL a la base de datos: {}", url);
	}	

	// Métodos get y set//	
	/**
	 * Devuelve la URL de la base de datos
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Asigna el valor pasado por parámetro a la url de la base de datos
	 * @param url - Parámetro
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Devuelve la contraseña del usuario
	 * @return contraseña
	 */
	public String getPssw() {
		return pssw;
	}

	/**
	 * Asigna el valor pasado por parámetro a la contraseña del usuario
	 * @param pssw - Parámetro
	 */
	public void setPssw(String pssw) {
		this.pssw = pssw;
	}

	/**
	 * Devuelve el nombre del usuario
	 * @return nombre
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Asigna el valor pasado por parámetro al nombre del usuario
	 * @param user - Parámetro
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	
	/**
	 * Devuelve un objeto de tipo "connection"
	 * @return connection
	 */	 
	public Connection getCon() {
		return con;
	}

	/**
	 * Método que se encarga de iniciar la conexión con la base de datos
	 */
	public void beginConnection() {		
		log.debug("Iniciando conexión...");
		
		try {
			con = DriverManager.getConnection(url, user, pssw);			
			log.debug("Conexión iniciada con éxito");
			
		} catch (SQLException e) {
			log2.error("Error, ha habido un problema al iniciar la conexión: {}", e.getMessage());
						
		}
		
	}
	
	
	/**
	 * Método que se encarga de cerrar la conexión con la base de datos
	 */	
	public void closeConnection() {
		
		log.debug("Cerrando conexión...");	
		
		try {
			con.close();
			log.debug("Conexión cerrada con éxito");
			
		} catch (SQLException e) {
			log2.error("Error, ha habido un problema al iniciar la conexión: {}", e.getMessage());
		}
		
	}
	
	/**
	 * Ejecuta una sentencia definida por el desarrollador
	 * 
	 * @throws SQLException	 
	 */
	public void executeSentence() throws SQLException {
		Statement st = con.createStatement();		
		ResultSet rs;
		
		HashMap<String, String> jugadores = new HashMap<>();
		
		log.debug("Ejecutando sentencia...");
		
		try {										
				
			rs = st.executeQuery("SELECT nttdata_mysql_soccer_player.name AS Jugador, nttdata_mysql_soccer_team.name AS Equipo FROM nttdata_mysql_soccer_team, nttdata_mysql_soccer_player WHERE nttdata_mysql_soccer_player.id_soccer_team = nttdata_mysql_soccer_team.id_soccer_team;");		
			
			while (rs.next()) {
				String jugador = rs.getNString(1);
				String equipo = rs.getNString(2);
				
				jugadores.put(jugador, equipo);
			}		
			
			rs.close();							
			
			log.debug("Sentencia ejecutada correctamente, resultado: {}", jugadores);						
					
			
		} finally {
			st.close();		
			log.debug("Objeto sentencia liberado de la memoria correctamente");
		}				
		
	}

}
