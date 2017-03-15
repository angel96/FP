package fp.grados.tipos;


import fp.grados.excepciones.ExcepcionBecaNoValida;

public final class BecaInmutableImpl implements BecaInmutable {

	private final String codigo;
	private final Double CuantiaTotal;
	private final Integer duracion;
	private final TipoBeca tipo;

	private static final Double CUANTIA_MINIMA = 1500.0;
	private static final Integer DURACION_MINIMA = 1;

	/*******************************************CONSTRUCTORES**********************************/
	
	public BecaInmutableImpl(String codigo, Double cuantiaTotal, Integer duracion,
			TipoBeca tipo) {
		checkCodigo(codigo);
		checkCuantiaTotal(cuantiaTotal);
		checkDuracion(duracion);
		this.codigo = codigo;
		this.CuantiaTotal = cuantiaTotal;
		this.duracion = duracion;
		this.tipo = tipo;
	}
	
	public BecaInmutableImpl(String codigo, TipoBeca tipo) {

		checkCodigo(codigo);
		this.codigo = codigo;
		this.tipo = tipo;
		CuantiaTotal = CUANTIA_MINIMA;
		duracion = DURACION_MINIMA;
	}
	
	public BecaInmutableImpl(String s) {
		String [] partes = s.split(",");
		if(partes.length != 4){
			throw new IllegalArgumentException();
		}
		String codigo = partes[0].trim();
		Double CuantiaTotal = new Double (partes[1].trim());
		Integer duracion = new Integer (partes [2].trim());
		TipoBeca tipo = TipoBeca.valueOf(partes[3].trim());
		checkCodigo(codigo);
		checkCuantiaTotal(CuantiaTotal);
		checkDuracion(duracion);
		
		this.codigo = codigo;
		this.CuantiaTotal = CuantiaTotal;
		this.duracion = duracion;
		this.tipo = tipo;
	}



	/*******************************************METODOS**********************************/

	
	public Double getCuantiaTotal() {
		return CuantiaTotal;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public String getCodigo() {
		return codigo;
	}

	public TipoBeca getTipo() {
		return tipo;
	}

	public Double getCuantiaMensual() {

		return getCuantiaTotal() / getDuracion();
	}

	public int hashCode() {
		return getCodigo().hashCode() + getTipo().hashCode()*31;

	}

	public boolean equals(Object o) {

		boolean result = false;

		if (o instanceof BecaInmutable) {

			BecaInmutable b = (BecaInmutable) o;

			result = getCodigo().equals(b.getCodigo())
					&& getTipo().equals(b.getTipo());

		}

		return result;
	}

	public int compareTo(BecaInmutable o) {

		int result;

		String codigo1 = getCodigo();
		String codigo2 = o.getCodigo();

		result = codigo1.compareTo(codigo2);

		if (result == 0) {
			TipoBeca tipo1 = getTipo();
			TipoBeca tipo2 = o.getTipo();
			result = tipo1.compareTo(tipo2);

		}
		return result;
	}
	
	
	/*******************************************CHECKERS**********************************/
	private void checkCuantiaTotal(Double cuantiaTotal2) {

		if (cuantiaTotal2 < CUANTIA_MINIMA) {
			throw new ExcepcionBecaNoValida();
		}

	}

	private void checkDuracion(Integer duracion2) {

		if (duracion2 < DURACION_MINIMA) {

			throw new ExcepcionBecaNoValida();
		}

	}
	
	private void checkCodigo(String codigo2) {

		Boolean bienHecho = codigo2.length() == 7
				&& Character.isAlphabetic(codigo2.charAt(0))
				&& Character.isAlphabetic(codigo2.charAt(1))
				&& Character.isAlphabetic(codigo2.charAt(2))
				&& Character.isDigit(codigo2.charAt(3))
				&& Character.isDigit(codigo2.charAt(4))
				&& Character.isDigit(codigo2.charAt(5))
				&& Character.isDigit(codigo2.charAt(6));
		if (bienHecho==false) {
			throw new ExcepcionBecaNoValida();
		}

	}
	/************************************************TOSTRING***************************************/
	
	public String toString() {
		return "[" + codigo + "," + tipo + "]";
	}

	

}
