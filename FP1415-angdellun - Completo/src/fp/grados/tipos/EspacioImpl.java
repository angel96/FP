package fp.grados.tipos;

import fp.grados.excepciones.ExcepcionEspacioNoValido;

public class EspacioImpl implements Espacio {

	private String Nombre;
	private TipoEspacio tipo;
	private Integer Planta, capacidad;

	/************************************************ CONSTRUCTORES ***************************************/
	public EspacioImpl(TipoEspacio type, String name, Integer capacidad,
			Integer stage) {

		checkCapacidad(capacidad);

		Nombre = name;
		tipo = type;
		Planta = stage;
		this.capacidad = capacidad;

	}

	public EspacioImpl(String s) {
		String[] partes = s.split(",");
		if (partes.length != 4) {
			throw new IllegalArgumentException("Cadena con formato no valido");
		}
		String Nombre = partes[0].trim();
		Integer Planta = new Integer(partes[1].trim());
		Integer capacidad = new Integer(partes[2].trim());
		TipoEspacio tipo = TipoEspacio.valueOf(partes[3].trim());
		checkCapacidad(capacidad);
		this.Nombre = Nombre;
		this.Planta = Planta;
		this.capacidad = capacidad;
		this.tipo = tipo;

	}
	/************************************************ METODOS ***************************************/

	public String getNombre() {

		return Nombre;
	}

	public TipoEspacio getTipo() {

		return tipo;
	}

	public Integer getCapacidad() {

		return capacidad;
	}

	public Integer getPlanta() {

		return Planta;
	}

	public void setNombre(String n) {

		Nombre = n;
	}

	public void setTipo(TipoEspacio type) {
		this.tipo = type;
	}

	public void setCapacidad(Integer capacidad) {

		checkCapacidad(capacidad);
		this.capacidad = capacidad;

	}

	public int hashCode() {
		return getNombre().hashCode() + getPlanta().hashCode() * 31;
	}

	public boolean equals(Object o) {

		boolean result = false;

		if (o instanceof Espacio) {

			Espacio e = (Espacio) o;

			result = getNombre().equals(e.getNombre())
					&& getPlanta().equals(e.getPlanta());

		}

		return result;
	}

	public int compareTo(Espacio e) {

		String Nombre1 = getNombre();
		String Nombre2 = e.getNombre();
		int res = Nombre1.compareTo(Nombre2);
		if(res == 0){
			Integer Planta1 = getPlanta();
			Integer Planta2 = e.getPlanta();
			res = Planta1.compareTo(Planta2);
		}
		return res;
	}

	/************************************************ CHECKERS ***************************************/
	private void checkCapacidad(Integer capacidad) {

		if (!(capacidad > 0)) {
			throw new ExcepcionEspacioNoValido();
		}

	}

	/************************************************ TOSTRING ***************************************/
	public String toString() {
		return "" + Nombre + "(" + "Planta" + Planta + ")";
	}

}
