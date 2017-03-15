package fp.grados.tipos;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import fp.grados.excepciones.ExcepcionAlumnoNoValido;
import fp.grados.excepciones.ExcepcionAlumnoOperacionNoPermitida;

public class AlumnoImpl extends PersonaImpl implements Alumno {

	private Set<Asignatura> asignaturas;
	private Expediente expediente;

	/*************************** CONSTRUCTORES *************************************************************************/
	public AlumnoImpl(String dni, String nombre, String apellidos,
			LocalDate fechaNacimiento, String email) {

		super(dni, nombre, apellidos, fechaNacimiento, email);
		// HashSet = Valor Vacio
		asignaturas = new HashSet<Asignatura>();
		expediente = new ExpedienteImpl();
		checkemail(email);

	}

	public AlumnoImpl(String s) {
		super(s);
		checkemail(getEmail());
		asignaturas = new HashSet<Asignatura>();
		expediente = new ExpedienteImpl();

	}

	/********************************************* METODOS *********************************************************/
	public Set<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public Integer getCurso() {

		Integer result = 0;

		for (Asignatura asig : getAsignaturas()) {
			Integer curso = asig.getCurso();
			if (curso > result) {
				result = curso;
			}
		}
		return result;

	}

	public void matriculaAsignatura(Asignatura asig) {

		checkAsignatura(asig, false);

		asignaturas.add(asig);

	}

	public void setEmail(String email) {
		checkemail(email);
		super.setEmail(email);
	}

	public void eliminaAsignatura(Asignatura asig) {
		if (!estaMatriculadoEn(asig)) {
			throw new ExcepcionAlumnoOperacionNoPermitida();
		} else {
			asignaturas.remove(asig);
		}
	}

	public Boolean estaMatriculadoEn(Asignatura asig) {
		return getAsignaturas().contains(asig);
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void evaluaAlumno(Asignatura a, Integer curso,
			Convocatoria convocatoria, Double nota, Boolean mencionHonor) {
		if (!asignaturas.contains(a)) {
			throw new ExcepcionAlumnoOperacionNoPermitida();
		}
		Nota n = new NotaImpl(a, curso, convocatoria, nota, mencionHonor);
		expediente.nuevaNota(n);
	}

	public void evaluaAlumno(Asignatura a, Integer curso,
			Convocatoria convocatoria, Double nota) {
		if (!asignaturas.contains(a)) {
			throw new ExcepcionAlumnoOperacionNoPermitida();
		}
		Nota n = new NotaImpl(a, curso, convocatoria, nota);
		expediente.nuevaNota(n);
	}
	

	public SortedMap<Asignatura, Calificacion> getCalificacionPorAsignatura() {
		SortedMap<Asignatura, Calificacion> map 
		= new TreeMap<Asignatura, Calificacion>();
		for (Nota n : this.getExpediente().getNotas()) {
			if (map.containsKey(n.getAsignatura())) {
				if(map.get(n.getAsignatura()).ordinal() < n
						.getCalificacion().ordinal()){
				map.put(n.getAsignatura(), n.getCalificacion());
				}
			} else {

				map.put(n.getAsignatura(), n.getCalificacion());
			}
		}
		return map;
	}
	public SortedSet<Asignatura> getAsignaturasOrdenadasPorCurso() {

		Comparator<Asignatura> cmp = Comparator.comparing(Asignatura::getCurso)
				.reversed().thenComparing(Comparator.naturalOrder());
		SortedSet<Asignatura> result = new TreeSet<Asignatura>(cmp);
		result.addAll(getAsignaturas());

		return result;
	}

	/******************************************************* CHECKERS **************************************************/
	private void checkemail(String email) {

		if (email.equals("") || !email.endsWith("@alum.us.es")) {

			throw new ExcepcionAlumnoNoValido();
		}

	}

	private void checkAsignatura(Asignatura asig, Boolean esperado) {

		if (!estaMatriculadoEn(asig).equals(esperado)) {

			throw new ExcepcionAlumnoOperacionNoPermitida();
		}

	}

	/************************************************ TOSTRING ***************************************/
	public String toString() {
		return "(" + getCurso() + "º)" + super.toString();
	}

}
