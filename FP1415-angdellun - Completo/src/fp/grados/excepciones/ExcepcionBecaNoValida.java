package fp.grados.excepciones;

public class ExcepcionBecaNoValida extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExcepcionBecaNoValida(){
		
	}
	public ExcepcionBecaNoValida(String s){
		super(s);
	}
}
