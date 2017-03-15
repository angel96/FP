package fp.grados.tipos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import fp.grados.excepciones.ExcepcionCentroNoValido;
import fp.grados.excepciones.ExcepcionCentroOperacionNoPermitida;

public class CentroImpl implements Centro {

	private String nombre, direccion;
	private Integer numeroPlantas, numeroSotanos;
	private Set<Espacio> espacios;

	/******************************************* CONSTRUCTORES **********************************/

	public CentroImpl(String nombre, String direccion, Integer numeroPlantas, Integer numeroSotanos) {

		this.nombre = nombre;
		this.direccion = direccion;
		this.numeroPlantas = numeroPlantas;
		this.numeroSotanos = numeroSotanos;
		this.espacios = new HashSet<Espacio>();
		checkNumeroPlantas(numeroPlantas);
		checkNumeroSotanos(numeroSotanos);

	}

	/************************************************ METODOS ***************************************/
	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public Integer getNumeroPlantas() {
		return numeroPlantas;
	}

	public Integer getNumeroSotanos() {
		return numeroSotanos;
	}

	public int hashCode() {
		return getNombre().hashCode();
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Centro) {
			Centro c = (Centro) o;
			result = getNombre().equals(c.getNombre());
		}
		return result;
	}

	public int compareTo(Centro o) {
		return getNombre().compareTo(o.getNombre());
	}

	public Set<Espacio> getEspacios() {
		return new HashSet<Espacio>(espacios);
	}

	public void nuevoEspacio(Espacio e) {

		if (-numeroSotanos > e.getPlanta() || e.getPlanta() > numeroPlantas - 1) {

			throw new ExcepcionCentroOperacionNoPermitida();

		}

		espacios.add(e);
	}

	public void eliminaEspacio(Espacio e) {

		if (espacios.contains(e)) {
			espacios.remove(e);
		} else {
			e = null;
		}
	}

	public Integer[] getConteosEspacios() {

		Integer[] conteoespacios = new Integer[TipoEspacio.values().length];
		Arrays.fill(conteoespacios, 0);

		for (Espacio espacio : getEspacios()) {

			conteoespacios[espacio.getTipo().ordinal()]++;

		}

		return conteoespacios;
	}

	public Set<Despacho> getDespachos() {
		Set<Despacho> result = new HashSet<Despacho>();
		for (Espacio espacios : getEspacios()) {
			if (espacios instanceof Despacho) {
				result.add((Despacho) espacios);
			}
		}
		return result;
	}

	public Set<Despacho> getDespachos(Departamento d) {
		Set<Despacho> result = new HashSet<Despacho>();
		Set<Profesor> profsDpto = d.getProfesores();
		for (Despacho despacho : getDespachos()) {
			Set<Profesor> profsDespacho = despacho.getProfesores();
			if (profsDespacho.equals(profsDpto)) {
				result.add(despacho);
			}
		}
		return result;
	}

	public Set<Profesor> getProfesores() {
		Set<Profesor> result = new HashSet<Profesor>();
		for (Despacho d : getDespachos()) {
			Set<Profesor> p = d.getProfesores();
			result.addAll(p);
		}
		return result;
	}

	public Set<Profesor> getProfesores(Departamento d) {

		Set<Profesor> prof = d.getProfesores();

		for (Profesor p : getProfesores()) {

			if (getProfesores().contains(d)) {
				prof.add(p);
			}
		}
		return prof;
	}

	public Espacio getEspacioMayorCapacidad() {

		Espacio espacio = null;
		 Integer b = 0;
		 for (Espacio e : getEspacios()) {
		 Integer c = e.getCapacidad();
		 if (getEspacios().isEmpty()) {
		 throw new ExcepcionCentroNoValido();
		 } else if (c > b) {
		 b = c;
		 espacio = e;
		 }
		 }

		return espacio;
	}

	public SortedMap<Profesor, Despacho> getDespachosPorProfesor() {
		SortedMap<Profesor, Despacho> map = new TreeMap<Profesor, Despacho>();
		for (Despacho desp : this.getDespachos()) {
			for (Profesor prof : desp.getProfesores()) {
				map.put(prof, desp);
			}
		}
		return map;
	}

	public SortedSet<Espacio> getEspaciosOrdenadosPorCapacidad() {
		Comparator<Espacio> cmp = Comparator.comparing(Espacio::getCapacidad);
		cmp = cmp.reversed();
		cmp = cmp.thenComparing(Comparator.naturalOrder());
		SortedSet<Espacio> esp = new TreeSet<Espacio>(cmp);
		esp.addAll(espacios);
		return esp;
	}

	/*********************************** CHECKERS ***********************************************/
	private void checkNumeroSotanos(Integer sotanos) {

		if (sotanos < 0) {
			throw new ExcepcionCentroNoValido();
		}

	}

	private void checkNumeroPlantas(Integer plantas) {
		if (plantas < 1) {
			throw new ExcepcionCentroNoValido();
		}
	}

	/************************************************ TOSTRING ***************************************/
	public String toString() {

		return nombre;

	}

}
