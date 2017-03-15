package fp.grados.tipos;

public interface Espacio extends Comparable <Espacio> {
	//Un espacio representa un lugar físico ubicado en un centro y 
	//en el cual se realizan tareas docentes. 
	//Un espacio puede ser de varios tipos: un aula de teoría, un laboratorio, un seminario, un aula de examen o de otro tipo.
	//Cada espacio tiene un nombre (por ejemplo, “A3.10”) y una capacidad dada por el número máximo de personas que admite. 
	//Además, un espacio está ubicado en una determinada planta. Todas las propiedades de un espacio pueden variar una vez creado, 
	//con excepción de la planta.
	
	String getNombre();
	Integer getPlanta();
	TipoEspacio getTipo();
	Integer getCapacidad();
	
	
	void setNombre(String n);
	void setTipo(TipoEspacio getTipo);
	void setCapacidad(Integer Capacidad);

	
}
