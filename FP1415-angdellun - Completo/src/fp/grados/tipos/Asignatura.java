package fp.grados.tipos;

public interface Asignatura extends Comparable <Asignatura>{

 
	//nombre “Fundamentos de Programación”)
	//, un acrónimo  “FP”) y un código numérico de 7 dígitos “0000230”
	//créditos (se admiten asignaturas con número no entero de créditos). 
	// tipos: anual, de primer cuatrimestre y de segundo cuatrimestre. un curso, 
	//un departamento concreto. 
	//Ninguna de las propiedades que definen a una asignatura, salvo el departamento, 
	//varía una vez creada la misma.
	
	String getNombre();
	String getAcronimo();
	String getCodigo();
	Double getCreditos();
	TipoAsignatura getTipo();
	Integer getCurso();
	
	Departamento getDepartamento();
	void setDepartamento (Departamento dpto);
	
}
