package fp.grados.tipos;



import java.time.DayOfWeek;
import java.time.LocalTime;

public interface Tutoria extends Comparable <Tutoria> {

	//La tutor�a representa un intervalo de tiempo que todo profesor 
	//tiene reservado para atender a sus alumnos. 
	//Una tutor�a tiene lugar un d�a de la semana (de lunes a viernes) 
	//y tiene una hora de comienzo, una hora de fin y una duraci�n en minutos, 
	//que es la diferencia entre la hora de fin y la hora de comienzo. 
	//Ninguna de las propiedades que definen a una tutor�a var�a una vez creada la misma.
	
	LocalTime getHoraComienzo();
	LocalTime getHoraFin();
	Integer getDuracion();
	DayOfWeek getDiaSemana();
	
	
	
	 
	
}
