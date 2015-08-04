package cocacola.cocacola.test;

import java.io.IOException;
import java.util.ArrayList;

import org.br.maymone.projetococacola.model.CocaCola;
import org.br.maymone.projetococacola.mvc.businnes.CocaColaManager;

public class App {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		


		CocaColaManager cm = new CocaColaManager(false);
		//ArrayList<CocaCola> c  = cm.processarSolicitacoes();
		
		//System.out.println(c.size());
		CocaCola c = new CocaCola();
		c.setId(new Long(2));
		c.setUrlVideo("olar");
		cm.enviarLinkUsuario(c);
	}
	



}
