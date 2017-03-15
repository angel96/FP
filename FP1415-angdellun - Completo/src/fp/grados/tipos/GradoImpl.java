package fp.grados.tipos;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import fp.grados.excepciones.ExcepcionGradoNoValido;

public class GradoImpl implements Grado {

	private String nombre;
	private Double numerominimocreditosoptativas;
	private Set<Asignatura> asignaturasObligatorias, asignaturasOptativas;

	public GradoImpl(String nombre, Set<Asignatura> asignaturasObligatorias,
			Set<Asignatura> asignaturasOptativas,
			Double numerominimodecreditosoptativas) {

		this.nombre = nombre;
		this.asignaturasObligatorias = asignaturasObligatorias;
		this.numerominimocreditosoptativas = numerominimodecreditosoptativas;
		this.asignaturasOptativas = asignaturasOptativas;
		checkAsignaturasOptativas(asignaturasOptativas);
		checkCreditosAsignaturasOptativas(numerominimocreditosoptativas);
	}

	/********************************************* METODOS *********************************************************/

	public String getNombre() {

		return nombre;
	}

	public Double getNumeroMinimoCreditosOptativas() {

		return numerominimocreditosoptativas;
	}

	public Set<Asignatura> getAsignaturasObligatorias() {

		return asignaturasObligatorias;
	}

	public Set<Asignatura> getAsignaturasOptativas() {

		return asignaturasOptativas;
	}

	public Double getNumeroTotalCreditos() {

		Double b = 0.0;
		for (Asignatura a : getAsignaturasObligatorias()) {
			b += a.getCreditos();
		}

		return b + getNumeroMinimoCreditosOptativas();
	}

	public Set<Departamento> getDepartamentos() {

		Set<Departamento> dep = new HashSet<Departamento>();
		for (Asignatura asig : getAsignaturasObligatorias()) {
			dep.add(asig.getDepartamento());
			for (Asignatura asig2 : getAsignaturasOptativas()) {
				dep.add(asig2.getDepartamento());
			}

		}

		return dep;
	}

	public Set<Profesor> getProfesores() {

		Set<Profesor> pro = new HashSet<Profesor>();

		for (Departamento d : getDepartamentos()) {
			Set<Profesor> p = d.getProfesores();
			pro.addAll(p);
		}
		return pro;
	}

	public Set<Asignatura> getAsignaturas(Integer curso) {

		Set<Asignatura> a = new HashSet<Asignatura>(curso);

		for (Asignatura asig : getAsignaturasObligatorias()) {
			if (asig.getCurso() == curso)
				a.add(asig);
			for (Asignatura asig2 : getAsignaturasOptativas()) {
				if (asig2.getCurso() == curso)
					a.add(asig2);
			}
		}

		return a;
	}

	public Asignatura getAsignatura(String codigo) {

		Asignatura result = null;

		for (Asignatura a1 : getAsignaturasObligatorias()) {

			if (a1.getCodigo().equals(codigo)) {
				result = a1;
			}

			for (Asignatura a2 : getAsignaturasOptativas()) {
				if (a2.getCodigo().equals(codigo)) {
					result = a2;
				}
			}
		}
		return result;
	}

	public int compareTo(Grado o) {

		return getNombre().compareTo(o.getNombre());
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Grado) {
			Grado g = (Grado) o;
			result = getNombre().equals(g.getNombre());
		}
		return result;
	}

	public int hashCode() {
		return nombre.hashCode();
	}

	public SortedMap<Asignatura, Double> getCreditosPorAsignatura() {
		SortedMap<Asignatura, Double> map = new TreeMap<Asignatura, Double>();
		for (Asignatura a : this.getAsignaturasObligatorias()) {
			map.put(a, a.getCreditos());
		}
		for (Asignatura a1 : this.getAsignaturasOptativas()) {
			map.put(a1, a1.getCreditos());
		}
		return map;
	}

	public SortedSet<Departamento> getDepartamentosOrdenadosPorAsignaturas() {
		// COJE UN DEPARTAMENTO x -> Departamento
		// ENTRA UN DEPARTAMENTO; Y LO TRANSFORMO EN EL TAMAÑO DE LAS
		// ASIGNATURAS QUE TIENE EL DEPARTAMENTO
		Comparator<Departamento> cmp = Comparator.comparing(x -> x
				.getAsignaturas().size());
		cmp = cmp.reversed().thenComparing(Comparator.naturalOrder());

		return getDepartamentos()
				.stream()
				.sorted(cmp)
				.collect(
						Collectors
								.toCollection(() -> new TreeSet<Departamento>()));
	}

	/******************************************************* CHECKERS **************************************************/

	private void checkAsignaturasOptativas(Set<Asignatura> asignaturasOptativas) {

		Double creditos = 0.0;
		for (Asignatura a : this.asignaturasOptativas) {
			creditos = a.getCreditos();
			for(Asignatura asigopt: asignaturasOptativas){
				if (!creditos.equals(asigopt.getCreditos())) {
					throw new ExcepcionGradoNoValido();
				}
			}
			

		}

	}

	private void checkCreditosAsignaturasOptativas(
			Double numerominimocreditosoptativas) {

		Double creditos = 0.0;

		for (Asignatura a : getAsignaturasOptativas()) {

			creditos += a.getCreditos();
		}
		if (numerominimocreditosoptativas < 0.
				|| numerominimocreditosoptativas > creditos) {
			throw new ExcepcionGradoNoValido();
		}
	}

	/************************************************ TOSTRING ***************************************/
	public String toString() {
		return nombre;
	}

}
