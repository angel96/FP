package fp.grados.tipos;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import fp.grados.excepciones.ExcepcionProfesorNoValido;
import fp.grados.excepciones.ExcepcionProfesorOperacionNoPermitida;

public class ProfesorImpl extends PersonaImpl implements Profesor {

	private SortedSet<Tutoria> tutorias;
	private Categoria categoria;
	private List <Asignatura> asignaturas;
	private List <Double> creditos;
	private Departamento departamento;
	private static final Double credito = 24.;
	/************************************************CONSTRUCTORES***************************************/
	public ProfesorImpl(String dni, String nombre, String apellidos,
			LocalDate fechaNacimiento,String email, Categoria categoria, 
			Departamento departamento) {
		super(dni, nombre, apellidos, fechaNacimiento, email);
		
		tutorias = new TreeSet <Tutoria>();
		this.categoria = categoria;
		checkEdad(getEdad());
		asignaturas = new ArrayList <Asignatura>();
		creditos = new ArrayList <Double>();
		setDepartamento(departamento);
		

	}

	/************************************************METODOS***************************************/

	
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
		
		Tutoria o = new TutoriaImpl(dia, horaComienzo, 15);
		
//		tutorias.remove(this);
		
		if(tutorias.contains(horaComienzo) && tutorias.contains(dia)){
			tutorias.remove(o);
		}
		
	}

	
	public void borraTutorias() {
		
//		tutorias.removeAll(tutorias);
		tutorias.clear();
	}


	public List<Asignatura> getAsignaturas() {
		
		return new ArrayList <Asignatura> (asignaturas);
	}


	public List<Double> getCreditos() {
		return new ArrayList <Double> (creditos);
	}


	public void imparteAsignatura(Asignatura asig, Double dedicacion) {		
		Integer index = getAsignaturas().indexOf(asig);
		
		if (index.equals(-1)){
			asignaturas.add(asig);
			creditos.add(dedicacion);
			
		} else {
			
			creditos.set(index, dedicacion);
			
		} 
		checkAsignatura(asig);
		checkDedicacion(dedicacion, asig.getCreditos());
		checkCreditos(getDedicacionTotal());
	}
	
	
	public Double dedicacionAsignatura(Asignatura asig) {
		Double result = 0.0;
		
		Integer index = getAsignaturas().indexOf(asig);
		if (!index.equals(-1)){ 
			result = getCreditos().get(index);
			}
		return result;
		
	}


	public void eliminaAsignatura(Asignatura asig) {
		
		Integer index = getAsignaturas().indexOf(asig);
		if (!index.equals(-1)){
			asignaturas.remove(asig); 
			creditos.remove(asig.getCreditos());
			}
		
		
		
	}
	public void setDepartamento(Departamento newDpto){
		
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
	
	
	public void setFechaNacimiento(LocalDate fechaNacimiento){
		super.setFechaNacimiento(fechaNacimiento);
		checkEdad(getEdad());
	}

	public Double getDedicacionTotal() {
		Double result = 0.0;
		for (int i = 0; i< creditos.size(); i++){
				result += getCreditos().get(i);
				}
		
		return result;
	}
	/************************************************CHECKERS***************************************/
	private void checkCreditos(Double dedtotal) {
			if(dedtotal > credito){
				throw new ExcepcionProfesorOperacionNoPermitida();
			}
	}
	
	private void checkEdad(Integer edad) {
		
		if(edad < 18){
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
		
		if(!(dedicacion > 0.0  && dedicacion <= credit)){
			
			throw new ExcepcionProfesorOperacionNoPermitida();
			
		}
		
	}
	
	/************************************************TOSTRING***************************************/
	public String toString(){
		
		return super.toString() + "(" + categoria + ")";
		
	}

	
}
