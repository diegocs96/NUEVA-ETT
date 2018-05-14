package ett;

import static java.nio.file.StandardOpenOption.APPEND;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

import Modelos.Administrador;
import Modelos.Cliente;
import BBDD.BD_Administrador;
import BBDD.BD_Cliente;
import BBDD.BD_Intermediario;

public class Main {
/**
 * @author Diego
 * @param 
 * Main de el proyecto
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		BD_Cliente bd = new BD_Cliente("mysql-properties.xml");	
		BD_Administrador bd2 = new BD_Administrador("mysql-properties.xml");	
		BD_Intermediario bd3 = new BD_Intermediario("mysql-properties.xml");	
		Scanner sc=new Scanner(System.in);
		int opc=0;
		do {
			System.out.println("\n\nEmpresa ETT");
			System.out.println("***************");
			System.out.println("1.Acceso cliente\n"
					+ "2.Acceso como administrador\n3.Acceso como intermediario\n");
			System.out.print("\tTeclea opción: ");
			try{
			opc=sc.nextInt();
			}
			catch(NumberFormatException e ){
				System.out.println("Opcion incorrecta");
				opc=0;
			}
			catch(InputMismatchException e ){
				System.out.println("Debes introducir número 1-5");
				opc=0;
			}	
			sc.nextLine();
			switch (opc){
			
			case 1:
				System.out.println("SE HA CONECTADO COMO CLIENTE");
				System.out.println("1.REGISTRAR CURRICULUM\n"
						+ "2.SOLICITAR BAJA\n");
				System.out.print("\tTeclea opción: ");
				try{
				opc=sc.nextInt();
				}
				catch(NumberFormatException e ){
					System.out.println("Opcion incorrecta");
					opc=0;
				}
				catch(InputMismatchException e ){
					System.out.println("Debes introducir número 1-2");
					opc=0;
				}	
				sc.nextLine();
				switch (opc){
				
				case 1:
					System.out.println("\n\nREGISTRA TU CURRICULUM");
					System.out.print("Introduce nombre\t");
					String nombre=sc.nextLine();				
					System.out.print("Introduce apellido\t");
					String apellido=sc.nextLine();
					System.out.print("Introduce DNI\t");
					String dni=sc.nextLine();
					System.out.print("Introduce tu localidad\t");
					String loc=sc.nextLine();
					System.out.print("Introduce email\t");
					String email=sc.nextLine();	
					System.out.print("Introduce sector\t");
					String sector=sc.nextLine();
					System.out.print("Introduce nivel de estudios\t");
					String nivelEstudio=sc.nextLine();
					
					Cliente cl=new Cliente(nombre,apellido,dni,loc,email,sector,nivelEstudio);			
					int filas=bd.añadir_Cliente(cl);				
					switch (filas){
					case 1:
						System.out.println("\nCurriculum añadido.");
						break;
					case 0:
						System.out.println("\nNo añadido");
						break;
					case -1:
						System.out.println("\nProblemas técnicos");
						break;
						
					}
					break;
				case 2:
					System.out.println("\n\nSOLICITAR BAJA");
					System.out.print("Introduce DNI\t");
					String dniBaja=sc.nextLine();
					System.out.print("Introduce la razon\t");
					String razon=sc.nextLine();
					
					
					String cadena = dniBaja+"\t"+razon+"\t"+LocalDate.now();
					
					escribirBaja(cadena);
					
					
					break;
					
				
				}
			
				break;

			case 2:
				
				System.out.print("\nIntroduce correo: ");
				String mail=sc.nextLine();
				System.out.print("Introduce contraseña: ");
				String password=sc.nextLine();
							
				Administrador administrador=new Administrador(mail,password); // Creamos el usuario para poder buscarle
					
				String resultadoBusqueda= bd2.buscarAdmin(administrador); // Buscamos el usuario
				
				if (resultadoBusqueda==null)
					System.out.println("Por motivos técnicos no podemos obtener la información en este momento");
				else {
					
					System.out.println("SE HA CONECTADO COMO ADMINISTRADOR");
					System.out.println("1.Verificar curriculum\n"
							+ "2.Comprobar requisitos\n");
					sc.nextLine();
					switch (opc){
					
					case 1:
						
						break;
					case 2:
					
						
						break;
						
					
					}
					
					
					
				}
				
				
				
				break;
				
				
			case 3:
				
				
				
				
				
			}
			
		}while(opc!=4);
	

	}
	public static void escribirBaja(String cadena) {			
		/**
		 * Funcion que escribe el dni del usuario y la razon por la que pide la baja junto con la fecha de solicitud
		 * @author Diego
		 * @param baja
		 * @return 
		 */
        Path file= Paths.get("bajas.txt");
        Charset charset = Charset.forName("UTF-8");
        
        if (!Files.exists(file))
            System.out.println("\n-- ERROR el fichero donde debemos escribir (baja.txt) no existe\n");
        
        else{
        	
	        //Creamos un BufferedWriter de java.io de forma eficiente utilizando Files de java.nio
	        try (BufferedWriter writer = Files.newBufferedWriter(file, charset,APPEND)) {
	
	            Scanner sc=new Scanner(System.in);            
	
	            writer.newLine(); // Metemos un salto de linea
	            
	            writer.write(cadena); // Copiamos la cadena con correco clave y fecha de conexion que hemos generado antes
	            //Escribimos nueva línea para separarlas
	            writer.newLine();
	            
	            writer.close();
	            
	        } catch (IOException x) {
	            System.err.format("IOException: %s%n", x);
	        }
        }
	}	

}
