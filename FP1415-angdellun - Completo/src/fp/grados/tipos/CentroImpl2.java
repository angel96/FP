package fp.grados.tipos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import fp.grados.excepciones.ExcepcionCentroNoValido;

public class CentroImpl2 extends CentroImpl implements Centro {
	/******************************************* CONSTRUCTORES **********************************/

	public CentroImpl2(String nombre, String direccion, Integer numeroPlantas,
			Integer numeroSotanos) {
		super(nombre, direccion, numeroPlantas, numeroSotanos);
	}

	public Espacio getEspacioMayorCapacidad() {

		Espacio espacio = null;
		
		if(getEspacios().isEmpty()){
			throw new ExcepcionCentroNoValido("Centro No Valido - Espacios Vacios");
		} else {
			espacio = getEspacios().stream().max(Comparator.comparing(Espacio::getCapacidad)).get();
		}
		
		return espacio;
		
	}

	public Integer[] getConteosEspacios() {
		Integer[] result = new Integer[TipoEspacio.values().length];
		Arrays.fill(result, 0);
		getEspacios().stream().map(x -> x.getTipo())
				.forEach(x -> result[x.ordinal()]++);
		return result;
	}

	public Set<Despacho> getDespachos() {
		return getEspacios().stream().filter(x -> x instanceof Despacho)
				.map(x -> (Despacho) x).collect(Collectors.toSet());
	}

	public Set<Despacho> getDespachos(Departamento d) {
		return getDespachos().stream()
				.filter(x -> x.getProfesores().equals(d.getProfesores()))
				.collect(Collectors.toSet());
	}

	public Set<Profesor> getProfesores() {
		return getDespachos().stream().flatMap(x -> x.getProfesores().stream())
				.collect(Collectors.toSet());
	}

	public Set<Profesor> getProfesores(Departamento d) {
		return getProfesores().stream()
				.filter(x -> x.getDepartamento().equals(d))
				.collect(Collectors.toSet());
	}

	public SortedMap<Profesor, Despacho> getDespachosPorProfesor() {

		return getProfesores()
				.stream()
				.filter(p -> tieneDespacho(p))
				.collect(
						Collectors.toMap(p -> p, p -> buscaDespacho(p),
								(d1, d2) -> d1, TreeMap::new));

	}

	private Boolean tieneDespacho(Profesor p) {
		return getDespachos().stream().anyMatch(
				x -> x.getProfesores().contains(p));
	}

	private Despacho buscaDespacho(Profesor p) {
		return getDespachos().stream()
				.filter(d -> d.getProfesores().contains(p)).findFirst().get();
	}
}
