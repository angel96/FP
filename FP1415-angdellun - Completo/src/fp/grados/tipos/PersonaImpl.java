package fp.grados.tipos;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import fp.grados.excepciones.ExcepcionPersonaNoValida;

public class PersonaImpl implements Persona {

	private String dni;
	private String nombre;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String email;

	/************************************************ CONSTRUCTORES ***************************************/
	public PersonaImpl(String dni, String nombre, String apellidos,
			LocalDate fechaNacimiento, String email) {
		
		checkDNI(dni);
		checkEmail(email);
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
	}

	public PersonaImpl(String dni, String nombre, String apellidos,
			LocalDate fechaNacimiento) {
		checkDNI(dni);
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = "";

	}

	public PersonaImpl(String s) {
		String[] partes = s.split(",");
		if (!(partes.length == 5)) {
			throw new IllegalArgumentException("Cadena con formato no valido");
		}
		String dni = partes[0].trim();
		String nombre = partes[1].trim();
		String apellidos = partes[2].trim();
		LocalDate fechaNacimiento = LocalDate.parse(partes[3].trim(),
				DateTimeFormatter.ofPattern("d/M/yyyy"));
		String email = partes[4].trim();

		checkDNI(dni);
		checkEmail(email);

		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
	}

	/************************************************ METODOS ***************************************/

	public String getDNI() {
		return dni;
	}

	public void setDNI(String dni) {
		checkDNI(dni);
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {

		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String nuevoEmail) {

		this.email = nuevoEmail;
		checkEmail(nuevoEmail);
	}

	public Integer getEdad() {

		LocalDate now = LocalDate.now();
		LocalDate nacimiento = getFechaNacimiento();

		Period transcurrido = nacimiento.until(now);
		Long numeroMeses = transcurrido.toTotalMonths();

		return numeroMeses.intValue() / 12;

	}

	public int hashCode() {
		return getDNI().hashCode() + getNombre().hashCode() * 31
				+ getApellidos().hashCode() * 31 * 31;
	}

	public boolean equals(Object o) {

		return o instanceof Persona? getDNI().equals(((Persona) o).getDNI())
				&& getNombre().equals(((Persona) o).getNombre())
				&& getApellidos().equals(((Persona) o).getApellidos()):false;
				
	}

	public int compareTo(Persona o) {

		int result = getDNI().compareTo(o.getDNI());
		if (result == 0) {
			result = getNombre().compareTo(o.getNombre());
			if (result == 0) {
				result = getApellidos().compareTo(o.getApellidos());
			}
		}
		return result;
	}

	/************************************************ CHECKERS ***************************************/
	private void checkEmail(String email) {

		if (!email.contains("@") && !(email == "")) {
			throw new ExcepcionPersonaNoValida();
		}

	}

	private void checkDNI(String dni2) {

		if (dni2.length() != 9) {
			throw new ExcepcionPersonaNoValida("Longitud del DNI insuficiente");

		}
		Integer numNIF = 0;
		try {
			String numero = dni2.substring(0, dni2.length() - 1);
			numNIF = new Integer(numero);
		} catch (NumberFormatException e) {
			throw new ExcepcionPersonaNoValida();
		}
		Character letra = dni2.charAt(dni2.length() - 1);

		if (!Character.isAlphabetic(letra)) {
			throw new ExcepcionPersonaNoValida();
		}

		String nifs = "TRWAGMYFPDXBNJZSQVHLCKE";

		Character letraReal = nifs.charAt(numNIF % nifs.length());

		if (!letraReal.equals(letra)) {
			throw new ExcepcionPersonaNoValida("wrong NIF");
		}
	}

	/************************************************ TOSTRING ***************************************/
	public String toString() {
		return getDNI()
				+ " - "
				+ getApellidos()
				+ ","
				+ getNombre()
				+ " - "
				+ getFechaNacimiento().format(
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

}
