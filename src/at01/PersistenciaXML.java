package at01;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class PersistenciaXML {
	public void salvar(RelatorioConsolidado relatorio, String nomeArquivo) throws Exception {
	    JAXBContext context = JAXBContext.newInstance(RelatorioConsolidado.class);
	    Marshaller mar = context.createMarshaller();
	    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    mar.marshal(relatorio, new java.io.File(nomeArquivo + ".xml"));
	    System.out.println("XML salvo!");
	}
}