package fp.grados.tipos;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AlumnoImpl2 extends AlumnoImpl implements Alumno {

	/*************************** CONSTRUCTORES *************************************************************************/
	public AlumnoImpl2(String dni, String nombre, String apellidos,
			LocalDate fechaNacimiento, String email) {

		super(dni, nombre, apellidos, fechaNacimiento, email);
	}

	public AlumnoImpl2(String s) {
		super(s);
	}

	/********************************************* METODOS *********************************************************/

	public Integer getCurso() {
		return getAsignaturas().isEmpty() ? 0 : getAsignaturas().stream()
				.max(Comparator.comparing(Asignatura::getCurso))
				.map(x -> x.getCurso()).get();
	}

	public SortedMap<Asignatura, Calificacion> getCalificacionPorAsignatura() {
		return getExpediente()
				.getNotas()
				.stream()
				.collect(
						Collectors.toMap(Nota::getAsignatura,
								Nota::getCalificacion,
								(c1, c2) -> c2.compareTo(c1)> 0? c2:c1, TreeMap::new));
	}
}