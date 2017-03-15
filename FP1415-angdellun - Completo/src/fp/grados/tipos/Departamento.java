package fp.grados.tipos;

import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public interface Departamento extends Comparable<Departamento> {
	String getNombre();

	Set<Asignatura> getAsignaturas();
	
	Set <Profesor> getProfesores();

	void nuevaAsignatura(Asignatura a);

	void eliminaAsignatura(Asignatura a);

	void eliminaProfesor(Profesor p);

	void nuevoProfesor(Profesor p);

	void borraTutorias(); 
	void borraTutorias(Categoria c);
	Boolean existeProfesorAsignado(Asignatura a);
	Boolean estanTodasAsignaturasAsignadas();
	void eliminaAsignacionProfesorado(Asignatura a);
	
	SortedMap<Asignatura, SortedSet<Profesor>> getProfesoresPorAsignatura();
	SortedMap<Profesor, SortedSet<Tutoria>> getTutoriasPorProfesor();
	
	Profesor getProfesorMaximaDedicacionMediaPorAsignatura();
}
