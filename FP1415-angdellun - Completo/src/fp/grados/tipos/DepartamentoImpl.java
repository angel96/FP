package fp.grados.tipos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class DepartamentoImpl implements Departamento {

	private String nombre;
	private Set<Asignatura> asignaturas;
	private Set<Profesor> profesores;

	/************************************************ CONSTRUCTORES ***************************************/

	public DepartamentoImpl(String nombre) {

		// this.----Atributo

		this.nombre = nombre;
		this.profesores = new HashSet<Profesor>();
		this.asignaturas = new HashSet<Asignatura>();

	}

	/************************************************ METODOS ***************************************/
	public String getNombre() {
		return nombre;
	}

	public Set<Asignatura> getAsignaturas() {
		return new HashSet<Asignatura>(asignaturas);
	}

	public Set<Profesor> getProfesores() {
		return new HashSet<Profesor>(profesores);
	}

	// 1. Actualizar informacion de la entidad unica (1)
	public void nuevaAsignatura(Asignatura a) {
		// 1.1 Añadir informacion
		asignaturas.add(a);
		// 1.2 Actualizar la informacion del elemento añadido
		a.setDepartamento(this);
	}

	public void eliminaAsignatura(Asignatura a) {
		// 1.3 Eliminar entidad de la lista
		asignaturas.remove(a);
		// 1.4 Eliminar la relacion que existe con el elemento unico(1)
		a.setDepartamento(null);

	}

	public void nuevoProfesor(Profesor p) {
		profesores.add(p);
		p.setDepartamento(this);

	}

	public void eliminaProfesor(Profesor p) {
		profesores.remove(p);
		p.setDepartamento(null);
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Departamento) {
			Departamento dp = (Departamento) o;
			result = getNombre().equals(dp.getNombre());
		}
		return result;
	}

	public int compareTo(Departamento d) {
		return getNombre().compareTo(d.getNombre());
	}

	public int hashCode() {
		return nombre.hashCode();
	}

	public void borraTutorias() {

		for (Profesor p : getProfesores()) {
			p.borraTutorias();
		}

	}

	public void borraTutorias(Categoria c) {

		for (Profesor p : getProfesores()) {

			c = p.getCategoria();

			profesores.contains(c);

			profesores.clear();

		}
	}

	public Boolean existeProfesorAsignado(Asignatura a) {
		Boolean result = false;

		for (Profesor p : getProfesores()) {
			result = result || p.getAsignaturas().contains(a);
		}

		return result;
	}

	public Boolean estanTodasAsignaturasAsignadas() {
		Boolean result = false;
		List<Asignatura> asig = new ArrayList<Asignatura>();
		for (Profesor p : getProfesores()) {
			asig = p.getAsignaturas();
		}
		if (asig.containsAll(getAsignaturas())) {
			result = true;
		}
		
		return result;
	}

	public void eliminaAsignacionProfesorado(Asignatura a) {
		for (Profesor p : getProfesores()) {
			p.eliminaAsignatura(a);
		}
	}

	public SortedMap<Asignatura, SortedSet<Profesor>> getProfesoresPorAsignatura() {
		SortedMap<Asignatura, SortedSet<Profesor>> map = new TreeMap<Asignatura, SortedSet<Profesor>>();
		for (Profesor p : this.getProfesores()) {
			for (Asignatura a : p.getAsignaturas()) {
				if (!map.containsKey(a)) {
					SortedSet<Profesor> profesores = new TreeSet<Profesor>();
					profesores.add(p);
					map.put(a, profesores);
				} else {
					SortedSet<Profesor> prof = map.get(a);
					prof.add(p);
					map.put(a, prof);
				}

			}
		}
		return map;
	}

	public SortedMap<Profesor, SortedSet<Tutoria>> getTutoriasPorProfesor() {
		SortedMap<Profesor, SortedSet<Tutoria>> map = new TreeMap<Profesor, SortedSet<Tutoria>>();
		SortedSet<Tutoria> tuto = new TreeSet<Tutoria>();
		for (Profesor p : getProfesores()) {
			tuto = p.getTutorias();
			map.put(p, tuto);
		}
		return map;
	}

	public Profesor getProfesorMaximaDedicacionMediaPorAsignatura() {
		
		Comparator<Profesor> cmp = Comparator.comparing(x -> x
				 .getDedicacionTotal() / x.getAsignaturas().size());
		
		Set<Profesor> prof = new HashSet<Profesor>();
		
		prof.addAll(getProfesores());
		
		return prof.stream().filter(x-> !x.getAsignaturas().isEmpty())
				 .max(cmp).get();
		
	}

	/************************************************ TOSTRING ***************************************/

	public String toString() {
		return nombre;
	}

}
