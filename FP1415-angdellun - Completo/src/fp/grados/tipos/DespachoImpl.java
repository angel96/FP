package fp.grados.tipos;


import java.util.HashSet;
import java.util.Set;

import fp.grados.excepciones.ExcepcionDespachoNoValido;

public class DespachoImpl extends EspacioImpl implements Despacho {

	private Set<Profesor> profesores;
	/************************************************CONSTRUCTORES***************************************/
	public DespachoImpl(String name, Integer capacity, Integer stage,
			Set<Profesor> profesores) {
		super(TipoEspacio.OTRO, name, capacity, stage);
		
		this.profesores = new HashSet <Profesor>();
		this.profesores.addAll(profesores);
		
		checkCapacidad(capacity, profesores);
	}

	public DespachoImpl(String name, Integer capacity, Integer stage,
			Profesor profesor) {
		super(TipoEspacio.OTRO, name, capacity, stage);
	
		this.profesores = new HashSet <Profesor>();
		this.profesores.add(profesor);
			
		checkCapacidad(capacity, getProfesores());
	}
	public DespachoImpl(String name, Integer capacity, Integer stage) {
		super(TipoEspacio.OTRO, name, capacity, stage);
		this.profesores = new HashSet <Profesor>();
	}
	public DespachoImpl (String s){
		super(s + "," + TipoEspacio.OTRO);
		this.profesores = new HashSet<Profesor>();		
	}

			
	/************************************************METODOS***************************************/

	public Set<Profesor> getProfesores() {
		
		return profesores;
	}

	public void setProfesores(Set<Profesor> profesores) {
		
		this.profesores = profesores;
		this.checkCapacidad(getCapacidad(), profesores);
		
	}

	public void setTipo(TipoEspacio type){
		throw new UnsupportedOperationException();
	}
	/************************************************CHECKERS***************************************/
	private void checkCapacidad(Integer capacity, Set<Profesor> profesores) {

		if (profesores.size() > capacity) {
			throw new ExcepcionDespachoNoValido();
		}

	}
	/************************************************TOSTRING***************************************/
	public String toString() {
		return super.toString() +  profesores;
	}
}
