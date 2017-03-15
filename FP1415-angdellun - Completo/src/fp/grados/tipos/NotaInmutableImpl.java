package fp.grados.tipos;

import fp.grados.excepciones.ExcepcionNotaNoValida;

public final class NotaInmutableImpl implements Nota {

	private final Convocatoria convocatoria;
	private final Integer CursoAcademico;
	private final Double Valor;
	private final Asignatura asignatura;
	private final Boolean MencionHonor;

	/************************************************ CONSTRUCTORES ***************************************/
	public NotaInmutableImpl(Asignatura asignatura, Integer Course,
			Convocatoria convocatory, Double value, Boolean mencionhonor) {

		checkValor(value);
		this.MencionHonor = mencionhonor;
		this.convocatoria = convocatory;
		this.CursoAcademico = Course;
		this.asignatura = new AsignaturaImpl(asignatura.getNombre(),
				asignatura.getCodigo(), asignatura.getCreditos(),
				asignatura.getTipo(), asignatura.getCurso(),
				asignatura.getDepartamento());
		this.Valor = value;
		checkMencionHonor(mencionhonor, value);

	}

	public NotaInmutableImpl(Asignatura asignatura, Integer Course,
			Convocatoria convocatoria, Double value) {

		checkValor(value);

		this.MencionHonor = false;
		this.asignatura = new AsignaturaImpl(asignatura.getNombre(),
				asignatura.getCodigo(), asignatura.getCreditos(),
				asignatura.getTipo(), asignatura.getCurso(),
				asignatura.getDepartamento());
		this.convocatoria = convocatoria;
		this.CursoAcademico = Course;
		this.Valor = value;

	}


	public NotaInmutableImpl(String s) {
		String[] partes = s.split(";");
		if (partes.length != 5) {
			throw new IllegalArgumentException("Cadena con formato no valido");
		}
		
		Asignatura asignatura = new AsignaturaImpl(partes[0].trim());
		Integer CursoAcademico = new Integer (partes[1].trim());
		Convocatoria convocatoria = Convocatoria.valueOf(partes[2].trim());
		Boolean MencionHonor = new Boolean (partes[4].trim());
		Double Valor = new Double (partes[3].trim());
		checkMencionHonor(MencionHonor, Valor);
		checkValor(Valor);

		
		
		this.asignatura = asignatura;
		this.CursoAcademico = CursoAcademico;
		this.convocatoria = convocatoria;
		this.Valor = Valor;
		this.MencionHonor = MencionHonor;
		
	}

	public Asignatura getAsignatura() {

		
		Asignatura asig = new AsignaturaImpl(this.asignatura.getNombre(),
				this.asignatura.getCodigo(), this.asignatura.getCreditos(),
				this.asignatura.getTipo(), this.asignatura.getCurso(),
				this.asignatura.getDepartamento());
		
		return asig;
	}

	public Convocatoria getConvocatoria() {

		return convocatoria;
	}

	public Integer getCursoAcademico() {

		return CursoAcademico;
	}

	public Calificacion getCalificacion() {

		Calificacion calificacion = null;

		if (Valor < 5.) {
			calificacion = Calificacion.SUSPENSO;
		}

		if (Valor >= 5. && Valor < 7.) {

			calificacion = Calificacion.APROBADO;

		}

		if (Valor >= 7. && Valor < 9.) {

			calificacion = Calificacion.NOTABLE;

		}

		if (Valor >= 9. && MencionHonor == false) {

			calificacion = Calificacion.SOBRESALIENTE;

		} else if (Valor >= 9. && MencionHonor == true) {

			calificacion = Calificacion.MATRICULA_DE_HONOR;

		}

		return calificacion;
	}

	public Double getValor() {

		return Valor;

	}

	public Boolean getMencionHonor() {

		return MencionHonor;
	}

	public int hashCode() {
		return getCursoAcademico().hashCode() + getAsignatura().hashCode() * 31
				+ getConvocatoria().hashCode() * 31 * 31;
	}

	public boolean equals(Object o) {

		boolean result = false;

		if (o instanceof Nota) {

			Nota n = (Nota) o;

			result = getCursoAcademico().equals(n.getCursoAcademico())
					&& getAsignatura().equals(n.getAsignatura())
					&& getConvocatoria().equals(n.getConvocatoria());

		}

		return result;
	}

	public int compareTo(Nota n) {

		int result;

		Integer c1 = getCursoAcademico();
		Integer c2 = n.getCursoAcademico();
		result = c1.compareTo(c2);

		if (result == 0) {

			Asignatura a1 = (Asignatura) getAsignatura();
			Asignatura a2 = (Asignatura) n.getAsignatura();
			result = a1.compareTo(a2);

			if (result == 0) {
				Convocatoria co1 = getConvocatoria();
				Convocatoria co2 = n.getConvocatoria();
				result = co1.compareTo(co2);
			}
		}

		return result;

	}

	/************************************************ CHECKERS ***************************************/

	private void checkMencionHonor(Boolean mencionhonor, Double value) {

		if (value < 9. && mencionhonor == true) {

			throw new ExcepcionNotaNoValida();

		}

	}

	private void checkValor(Double value) {

		if (value < 0. || value > 10.) {

			throw new ExcepcionNotaNoValida();

		}

	}

	/************************************************ TOSTRING ***************************************/
	public String toString() {

		// SUBSTRING = INTERVALO

		Integer c = CursoAcademico + 1;
		String cur = c.toString().substring(2, 4);

		return getAsignatura() + ", " + getCursoAcademico() + " - " + cur + ","
				+ getConvocatoria() + ", " + getValor() + ", "
				+ getCalificacion();

	}

}
