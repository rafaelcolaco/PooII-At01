package at01;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class PersistenciaJSON {
	public void salvar(RelatorioConsolidado relatorio, String nomeArquivo) throws IOException {
	    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	    mapper.writeValue(new java.io.File(nomeArquivo + ".json"), relatorio);
	    System.out.println("JSON salvo!");
	}
}