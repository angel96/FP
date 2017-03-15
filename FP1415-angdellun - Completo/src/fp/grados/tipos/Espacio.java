package fp.grados.tipos;

public interface Espacio extends Comparable <Espacio> {
	//Un espacio representa un lugar f�sico ubicado en un centro y 
	//en el cual se realizan tareas docentes. 
	//Un espacio puede ser de varios tipos: un aula de teor�a, un laboratorio, un seminario, un aula de examen o de otro tipo.
	//Cada espacio tiene un nombre (por ejemplo, �A3.10�) y una capacidad dada por el n�mero m�ximo de personas que admite. 
	//Adem�s, un espacio est� ubicado en una determinada planta. Todas las propiedades de un espacio pueden variar una vez creado, 
	//con excepci�n de la planta.
	
	String getNombre();
	Integer getPlanta();
	TipoEspacio getTipo();
	Integer getCapacidad();
	
	
	void setNombre(String n);
	void setTipo(TipoEspacio getTipo);
	void setCapacidad(Integer Capacidad);

	
}
