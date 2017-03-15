package fp.grados.tipos;

import java.util.stream.Collectors;

public class ExpedienteImpl2 extends ExpedienteImpl implements Expediente {

	public Double getNotaMedia() {

		return getNotas().stream().filter(x -> x.getValor()>= 5)
				.collect(Collectors.averagingDouble(Nota::getValor))
				.doubleValue();
	}
	
}
