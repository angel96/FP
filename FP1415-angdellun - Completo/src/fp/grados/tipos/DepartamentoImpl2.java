package fp.grados.tipos;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DepartamentoImpl2 extends DepartamentoImpl implements Departamento {

	public DepartamentoImpl2(String nombre) {
		super(nombre);
	}

	public void borraTutorias() {
		getProfesores().stream().forEach(Profesor::borraTutorias);
	}

	public void borraTutorias(Categoria categoria) {
		getProfesores().stream().filter(x -> x.getCategoria().equals(categoria)).forEach(Profesor::borraTutorias);
	}

	public Boolean existeProfesorAsignado(Asignatura a) {
		return getProfesores().stream().filter(x -> x.getAsignaturas().contains((a))).findAny().isPresent();
	}

	public Boolean estanTodasAsignaturasAsignadas() {
		return getProfesores().stream().flatMap(x -> x.getAsignaturas().stream()).collect(Collectors.toSet())
				.containsAll(getAsignaturas());
	}

	public void eliminaAsignacionProfesorado(Asignatura a) {
		getProfesores().stream().forEach(x -> x.eliminaAsignatura(a));
	}

	public SortedMap<Profesor, SortedSet<Tutoria>> getTutoriasPorProfesor() {
		/**
		 * getProfesores().stream() .collect(Collectors.toMap(p -> p, p ->
		 * p.getTutorias(), (p1, p2) -> p1, TreeMap::new));
		 */
		return new TreeMap<Profesor, SortedSet<Tutoria>>(
				getProfesores().stream().collect(Collectors.toMap(p -> p, p -> p.getTutorias())));
	}

}
