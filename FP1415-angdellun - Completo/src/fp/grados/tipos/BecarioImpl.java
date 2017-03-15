package fp.grados.tipos;

import java.time.LocalDate;

import fp.grados.excepciones.ExcepcionBecarioNoValido;

public class BecarioImpl extends AlumnoImpl implements Becario {

	private Beca beca;
	private LocalDate fechaComienzo;

	/******************************************* CONSTRUCTORES **********************************/
	public BecarioImpl(String dni, String nombre, String apellidos,
			LocalDate fechaNacimiento, String email, Beca beca,
			LocalDate fechaComienzo) {

		super(dni, nombre, apellidos, fechaNacimiento, email);

		this.beca = beca;
		this.fechaComienzo = fechaComienzo;
		checkFechaComienzo(fechaComienzo);

	}

	public BecarioImpl(String dni, String nombre, String apellidos,
			LocalDate fechaNacimiento, String email, String codigo,
			Double cuantiaTotal, Integer duracion, TipoBeca tipo,
			LocalDate fechaComienzo) {

		super(dni, nombre, apellidos, fechaNacimiento, email);
		checkFechaComienzo(fechaComienzo);
		this.beca = new BecaImpl(codigo, cuantiaTotal, duracion, tipo);
		this.fechaComienzo = fechaComienzo;

	}

	/******************************************* METODOS **********************************/

	

	public void setEmail(String email) {

		throw new UnsupportedOperationException();

	}

	public Beca getBeca() {

		return beca;
	}

	public LocalDate getFechaComienzo() {

		return fechaComienzo;
	}

	public LocalDate getFechaFin() {

		return getFechaComienzo().plusMonths(getBeca().getDuracion());
	}

	public void setFechaComienzo(LocalDate fechaComienzo) {

		checkFechaComienzo(fechaComienzo);

		this.fechaComienzo = fechaComienzo;

	}

	/***************************************** CHECKERS ***************************************************/

	private void checkFechaComienzo(LocalDate comienzo) {

		if (!comienzo.isAfter(LocalDate.now())) {

			throw new ExcepcionBecarioNoValido();

		}

	}

	/************************************************ TOSTRING ***************************************/
	public String toString() {

		return super.toString() + " " + getBeca();

	}
}
