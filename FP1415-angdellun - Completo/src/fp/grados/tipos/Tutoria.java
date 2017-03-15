package fp.grados.tipos;



import java.time.DayOfWeek;
import java.time.LocalTime;

public interface Tutoria extends Comparable <Tutoria> {

	//La tutoría representa un intervalo de tiempo que todo profesor 
	//tiene reservado para atender a sus alumnos. 
	//Una tutoría tiene lugar un día de la semana (de lunes a viernes) 
	//y tiene una hora de comienzo, una hora de fin y una duración en minutos, 
	//que es la diferencia entre la hora de fin y la hora de comienzo. 
	//Ninguna de las propiedades que definen a una tutoría varía una vez creada la misma.
	
	LocalTime getHoraComienzo();
	LocalTime getHoraFin();
	Integer getDuracion();
	DayOfWeek getDiaSemana();
	
	
	
	 
	
}
