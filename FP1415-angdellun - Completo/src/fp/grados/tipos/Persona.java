package fp.grados.tipos;

import java.time.LocalDate;

//1. Definir la estructura de la interface

public interface Persona extends Comparable <Persona>  {
	
	//un DNI (formado siempre por ocho dígitos y una letra), 
	//un nombre, unos apellidos, una fecha de nacimiento, 
	//una dirección de email y una edad.
	
	//2. Metodos consultores. Uno por cada propiedad visible
	//public abstract se puede omitir
	
	String getDNI();
	String getNombre();
	String getApellidos();
	LocalDate getFechaNacimiento();
	String getEmail();
	Integer getEdad();
	
	
	//3. Metodos modificadores. Uno por cada propiedad basica del tipo (Que sean modificables)
	
	void setDNI(String DNI);
	void setNombre(String name);
	void setApellidos(String surnames);
	void setFechaNacimiento(LocalDate ld);
	void setEmail(String email);
	
	
}
