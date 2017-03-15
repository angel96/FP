package fp.grados.tipos;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.SortedSet;

public interface Profesor extends Persona {

	Departamento getDepartamento();
	void setDepartamento (Departamento dpto);
	
	Categoria getCategoria();
	SortedSet <Tutoria> getTutorias();
	
	List <Asignatura> getAsignaturas();
	List <Double> getCreditos();
	Double getDedicacionTotal();
	
	void setCategoria(Categoria Categoria);
	void nuevaTutoria(LocalTime horaComienzo, 
			Integer duracion, DayOfWeek dia);
	void borraTutoria(LocalTime horaComienzo, DayOfWeek dia);
	void borraTutorias();
	
	void imparteAsignatura(Asignatura asig, Double dedicacion);
	Double dedicacionAsignatura(Asignatura asig);
	void eliminaAsignatura(Asignatura asig);
}
