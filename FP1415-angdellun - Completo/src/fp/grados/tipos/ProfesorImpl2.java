package fp.grados.tipos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import fp.grados.excepciones.ExcepcionProfesorNoValido;
import fp.grados.excepciones.ExcepcionProfesorOperacionNoPermitida;

public class ProfesorImpl2 extends PersonaImpl implements Profesor {

	private SortedSet<Tutoria> tutorias;
	private Categoria categoria;
	private Departamento departamento;
	private Map<Asignatura, Double> asignaturasycreditos;
	private static final Double credito = 24.;

	/************************************************ CONSTRUCTORES ***************************************/
	public ProfesorImpl2(String dni, String nombre, String apellidos,
			LocalDate fechaNacimiento, String email, Categoria categoria,
			Departamento departamento) {
		super(dni, nombre, apellidos, fechaNacimiento, email);

		tutorias = new TreeSet<Tutoria>();
		this.categoria = categoria;
		asignaturasycreditos = new HashMap<Asignatura, Double>();
		checkEdad(getEdad());
		setDepartamento(departamento);

	}

	/************************************************ METODOS ***************************************/

	public List<Asignatura> getAsignaturas() {
		return new ArrayList<Asignatura>(asignaturasycreditos.keySet());
	}

	public List<Double> getCreditos() {
		return new ArrayList<Double>(asignaturasycreditos.values());
	}

	public Categoria getCategoria() {

		return categoria;
	}

	public void setCategoria(Categoria categoria) {

		this.categoria = categoria;

	}

	public SortedSet<Tutoria> getTutorias() {

		return tutorias;
	}

	public void nuevaTutoria(LocalTime horaComienzo, Integer duracionMinutos,
			DayOfWeek dia) {
		Tutoria t = new TutoriaImpl(dia, horaComienzo, duracionMinutos);
		tutorias.add(t);
	}

	public void borraTutoria(LocalTime horaComienzo, DayOfWeek dia) {
		tutorias.remove(this);
	}

	public void borraTutorias() {

		tutorias.removeAll(tutorias);
	}

	public void imparteAsignatura(Asignatura asig, Double dedicacion) {

		if (asignaturasycreditos.containsKey(asig)) {
			checkAsignatura(asig);
			asignaturasycreditos.put(asig, dedicacion);
			checkCreditos(getDedicacionTotal());
		} else {
			checkDedicacion(dedicacion, asig.getCreditos());
			checkAsignatura(asig);
			asignaturasycreditos.put(asig, dedicacion);

		}

		checkCreditos(getDedicacionTotal());

	}

	public Double dedicacionAsignatura(Asignatura asig) {

		Double result = 0.0;

		if (asignaturasycreditos.containsKey(asig)) {
			result = asignaturasycreditos.get(asig);
		}
		return result;

	}

	public void eliminaAsignatura(Asignatura asig) {
		asignaturasycreditos.remove(asig);
	}

	public void setDepartamento(Departamento newDpto) {

		Departamento oldDpto = this.departamento;

		if (newDpto != oldDpto) {
			this.departamento = newDpto;

			if (oldDpto != null) {

				oldDpto.eliminaProfesor(this);
			}

			if (newDpto != null) {
				newDpto.nuevoProfesor(this);
			}

		}

	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		super.setFechaNacimiento(fechaNacimiento);
		checkEdad(getEdad());
	}

	public Double getDedicacionTotal() {
		Double result = 0.0;
		for (int i = 0; i < getCreditos().size(); i++) {
			result += getCreditos().get(i);
		}

		return result;
	}

	/************************************************ CHECKERS ***************************************/
	private void checkCreditos(Double dedtotal) {
		if (dedtotal > credito) {
			throw new ExcepcionProfesorOperacionNoPermitida();
		}
	}

	private void checkEdad(Integer edad) {

		if (edad < 18) {
			throw new ExcepcionProfesorNoValido();
		}

	}

	private void checkAsignatura(Asignatura asig) {

		if ((getDepartamento() == null && asig.getDepartamento() != null)
				|| (getDepartamento() != null && !(getDepartamento()
						.equals(asig.getDepartamento())))) {
			throw new ExcepcionProfesorOperacionNoPermitida();
		}

	}

	private void checkDedicacion(Double dedicacion, Double credit) {

		if (!(dedicacion > 0.0 && dedicacion <= credit)) {

			throw new ExcepcionProfesorOperacionNoPermitida();

		}

	}

	/************************************************ TOSTRING ***************************************/
	public String toString() {

		return super.toString() + "(" + categoria + ")";

	}

}
