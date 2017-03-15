package fp.grados.tipos;

import fp.grados.excepciones.ExcepcionAsignaturaNoValida;

public class AsignaturaImpl implements Asignatura {

	private String nombre, codigo;
	private Double creditos;
	private TipoAsignatura tipo;
	private Integer curso;
	private Departamento departamento;

	/**************************************** CONSTRUCTORES ******************************************/

	public AsignaturaImpl(String nombre, String codigo, Double creditos,
			TipoAsignatura tipo, Integer curso, Departamento departamento) {

		checkCodigo(codigo);
		checkCreditos(creditos);
		checkCurso(curso);

		this.nombre = nombre;
		this.codigo = codigo;
		this.creditos = creditos;
		this.tipo = tipo;
		this.curso = curso;
		// 3. Delego la inicializacion en el set.
		setDepartamento(departamento);

	}

	public AsignaturaImpl(String s) {
		String[] partes = s.split("#");
		if (partes.length != 5) {
			throw new IllegalArgumentException();
		}

		String nombre = partes[0].trim();
		String codigo = partes[1].trim();
		Double creditos = new Double(partes[2].trim());
		TipoAsignatura tipo = TipoAsignatura.valueOf(partes[3].trim());
		Integer curso = new Integer(partes[4].trim());

		checkCodigo(codigo);
		checkCreditos(creditos);
		checkCurso(curso);

		this.nombre = nombre;
		this.codigo = codigo;
		this.creditos = creditos;
		this.tipo = tipo;
		this.curso = curso;

	}

	/******************************************* METODOS **********************************/

	public String getNombre() {

		return nombre;
	}

	public String getAcronimo() {

		// String result = "";
		// String nombre = getNombre();
		// for(Character c: nombre.toCharArray()){
		//
		// if (Character.isUpperCase(c)){
		// result = result + c; //== result +=c;
		// }
		//
		// }
		// return result;
		String result = "";

		for (int i = 0; i < getNombre().toCharArray().length; i++) {
			char c = getNombre().toCharArray()[i];
			if (Character.isUpperCase(c)) {
				result += c;
			}
		}

		return result;

	}

	public String getCodigo() {

		return codigo;
	}

	public Double getCreditos() {

		return creditos;
	}

	public TipoAsignatura getTipo() {

		return tipo;
	}

	public Integer getCurso() {

		return curso;
	}

	public int hashCode() {
		return getCodigo().hashCode();
	}

	public boolean equals(Object o) {

		boolean result = false;

		if (o instanceof Asignatura) {

			// Casting = transformar de un tipo a otro

			Asignatura a = (Asignatura) o;
			result = getCodigo().equals(a.getCodigo());

		}
		return result;
	}

	public int compareTo(Asignatura o) {

		String codigo1 = getCodigo();
		String codigo2 = o.getCodigo();

		return codigo1.compareTo(codigo2);
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	// 2. Tiene que modificar la informacion del elemento multiple
	public void setDepartamento(Departamento newDpto) {
		// 2.1 Tomar elemento actual que se va a cambiar
		Departamento oldDpto = this.departamento;
		// 2.2 Chequear identidad (Si lo nuevo es igual a lo de antes, ¿para que
		// sigues?)
		if (newDpto != oldDpto) {
			// 2.3 Actualizar la propiedad
			this.departamento = newDpto;

			// 2.4 Eliminarme del objeto unico al que pertenece
			if (oldDpto != null) {

				oldDpto.eliminaAsignatura(this);
			}

			// 2.5 Añadirme al nuevo objeto

			if (newDpto != null) {
				newDpto.nuevaAsignatura(this);
			}

		}

	}

	/******************************************* CHECKERS **********************************/
	private void checkCodigo(String code) {

		if (code.length() != 7) {
			throw new ExcepcionAsignaturaNoValida();
		}

		try {

			new Integer(code);

		} catch (NumberFormatException e) {

			throw new ExcepcionAsignaturaNoValida();

		}

	}

	private void checkCreditos(Double credits) {

		if (credits <= 0)

			throw new ExcepcionAsignaturaNoValida();
	}

	private void checkCurso(Integer year) {
		// "o" ||
		if (year < 1 || year > 4) {
			throw new ExcepcionAsignaturaNoValida();
		}

	}

	/************************************************ TOSTRING ***************************************/

	public String toString() {

		return "(" + getCodigo() + ")" + getNombre();
	}
}
