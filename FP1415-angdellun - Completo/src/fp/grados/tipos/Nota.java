package fp.grados.tipos;


import fp.grados.tipos.Asignatura;


public interface Nota extends Comparable <Nota> {
		
	
	Asignatura getAsignatura();
	Integer getCursoAcademico();
	Convocatoria getConvocatoria();
	Double getValor();
	Calificacion getCalificacion();
	Boolean getMencionHonor();
	
}
