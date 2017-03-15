package fp.grados.tipos;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GradoImpl2 extends GradoImpl implements Grado{

	public GradoImpl2(String nombre, Set<Asignatura> asignaturasObligatorias,
			Set<Asignatura> asignaturasOptativas,
			Double numerominimodecreditosoptativas) {
		super(nombre, asignaturasObligatorias, asignaturasOptativas,
				numerominimodecreditosoptativas);
	}

	public Double getNumeroTotalCreditos() {
		return getAsignaturasObligatorias().stream()
				.mapToDouble(x -> x.getCreditos()).sum()
				+ getNumeroMinimoCreditosOptativas();
	}

	public Set<Asignatura> getAsignaturas(Integer curso) {
		return Stream
				.concat(getAsignaturasObligatorias().stream(),
						getAsignaturasOptativas().stream())
				.filter(x -> x.getCurso().equals(curso))
				.collect(Collectors.toSet());
	}

	public Asignatura getAsignatura(String codigo) {

		return Stream
				.concat(getAsignaturasObligatorias().stream(),
						getAsignaturasOptativas().stream())
				.filter(x -> x.getCodigo().equals(codigo)).findFirst().get();
	}

	public Set<Departamento> getDepartamentos() {

		return Stream
				.concat(getAsignaturasObligatorias().stream(),
						getAsignaturasOptativas().stream())
				.map(Asignatura::getDepartamento).collect(Collectors.toSet());
	}

	public Set<Profesor> getProfesores() {
		return getDepartamentos().stream().flatMap(x -> x.getProfesores().stream())
				.collect(Collectors.toSet());
		
	}

	public SortedMap<Asignatura, Double> getCreditosPorAsignatura() {
		return Stream.concat(getAsignaturasObligatorias().stream(),
				getAsignaturasOptativas().stream()).collect(
				Collectors.toMap(a -> a, a -> a.getCreditos(), (a1,a2)-> a1, TreeMap::new));
	}

}
