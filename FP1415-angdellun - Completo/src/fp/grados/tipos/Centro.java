
package fp.grados.tipos;

import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public interface Centro extends Comparable<Centro> {
	
	String getNombre();
	String getDireccion();
	Integer getNumeroPlantas();
	Integer getNumeroSotanos();
	Set <Espacio> getEspacios();
	Integer[] getConteosEspacios();
	
	Espacio getEspacioMayorCapacidad(); 
	void nuevoEspacio (Espacio e);
	void eliminaEspacio(Espacio e);
	
	Set<Despacho> getDespachos();
	Set<Despacho> getDespachos(Departamento d);
	
	Set<Profesor> getProfesores(); 
	Set<Profesor> getProfesores(Departamento d);
	
	SortedMap<Profesor, Despacho> getDespachosPorProfesor();
	SortedSet<Espacio> getEspaciosOrdenadosPorCapacidad();
	
}
