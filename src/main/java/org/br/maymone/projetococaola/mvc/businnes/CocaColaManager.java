package org.br.maymone.projetococaola.mvc.businnes;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.br.maymone.projetococacola.media.VideoPersonalizado;
import org.br.maymone.projetococacola.model.CocaCola;
import org.br.maymone.projetococacola.util.Propriedades;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;



@Startup
@Singleton
public class CocaColaManager {
	
	
	private static EntityManager em;
	
	private static IMediaReader[] videos;
	
	private Propriedades prop;
	
	private FacebookManager facebookManager;
	
	
		private VideoManager videoManager;

	
public CocaColaManager(){
	
	System.out.println("olar");
	
}
	

	public CocaColaManager(boolean olar) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("persistence");
		em = emf.createEntityManager();
		


	}

	public static void main(String[] args) {
		// 

		System.out.println("olar");

		int a = 0;

	}


	// esse metodo receberá as informacoes do WebService via PHP
	// as infos tem que ser os Dados do Usuario e Seu Token
	public String gerarVideo(CocaCola coca, String tokenFacebook) {

		String urlYoutube = "sem url";
		VideoPersonalizado v= new VideoPersonalizado(tokenFacebook);
		
		salvarUsuario(coca);
		//video com a primeira parte do usuario
		IMediaWriter videoInserir = v.gerarVideoUsuario(coca);
		//gerar video com o Texto do nome
		
		//pegar as respostas do usuario pra gerar o outro usuario
		//videos = gerarVideosUsuario(coca.getRespostas());
		
		

		return urlYoutube;

	}

	private IMediaReader[] gerarVideosUsuario(String respostas) {
		// metodo que, baseado nas respostas do usuario pega o video necessario
		String[] split = respostas.split(";");
		
		IMediaReader[] retorno = new IMediaReader[respostas.length()];
		
		for (int i = 0; i < split.length; i++) {
			
			String url = processarResposta(split[i]);
			IMediaReader temp = ToolFactory.makeReader(url);
			retorno[i] = temp;

			   
		}
		
		
		return retorno;
	}

	//metodo que resposta a url do video de acordo com a resposta
	private String processarResposta(String string) {
		// 
		
		return prop.getUrlVideosRespostas()[0];
		
	}

	private void salvarUsuario(CocaCola coca) {
		// 
		em.persist(coca);

	}

	private void excluirUsuario(CocaCola coca) {
		em.remove(coca);

	}

}
