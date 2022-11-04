package com.nttdatacenters.main;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NTTData Taller práctico jdbc - Clase principal
 * 
 *@author Santiago
 *@version 1.0
 */
public class App 
{				
	// Log que escupe la información en el archivo "logs/info.log"
	private static final Logger log = LoggerFactory.getLogger("logInfo");
	
	// Log que escupe la información en el archivo "logs/errors.log"
	private static final Logger log2 = LoggerFactory.getLogger("logError");
	
	
	/**
	 * Método principal de la aplicación
	 * @param args
	 */
    public static void main( String[] args ) 
    {           	    	    	    	
    	log.debug("Inicio del programa");  
    	    	
    	GestorConexion gc = new GestorConexion();
    	gc.beginConnection();
    	
    	
    	try {    		
			gc.executeSentence();			
			
		} catch (SQLException e) {			
			log2.error("Error al ejecutar la sentencia: {}", e.toString());
		}
    	
    	gc.closeConnection();
    	
    	log.debug("Programa finalizado con éxito");
    	
    	System.out.println("Porfavor, mire el archivo logs/info.log para ver el resultado del ejercicio");
    }
}
