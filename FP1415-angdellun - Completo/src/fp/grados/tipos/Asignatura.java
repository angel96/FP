package fp.grados.tipos;

public interface Asignatura extends Comparable <Asignatura>{

 
	//nombre �Fundamentos de Programaci�n�)
	//, un acr�nimo  �FP�) y un c�digo num�rico de 7 d�gitos �0000230�
	//cr�ditos (se admiten asignaturas con n�mero no entero de cr�ditos). 
	// tipos: anual, de primer cuatrimestre y de segundo cuatrimestre. un curso, 
	//un departamento concreto. 
	//Ninguna de las propiedades que definen a una asignatura, salvo el departamento, 
	//var�a una vez creada la misma.
	
	String getNombre();
	String getAcronimo();
	String getCodigo();
	Double getCreditos();
	TipoAsignatura getTipo();
	Integer getCurso();
	
	Departamento getDepartamento();
	void setDepartamento (Departamento dpto);
	
}
