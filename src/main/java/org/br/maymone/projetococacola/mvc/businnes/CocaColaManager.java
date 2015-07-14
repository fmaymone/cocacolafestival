package org.br.maymone.projetococacola.mvc.businnes;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.br.maymone.projetococacola.media.VideoPersonalizado;
import org.br.maymone.projetococacola.model.CocaCola;
import org.br.maymone.projetococacola.model.CocaCola.Status;
import org.br.maymone.projetococacola.util.Propriedades;
import org.mortbay.log.Log;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

@Stateless
public class CocaColaManager {

	@Inject
	private EntityManager em;

	private static IMediaReader[] videos;

	@Inject
	private Propriedades prop;

	private FacebookManager facebookManager;

	@Inject
	private VideoManager videoManager;

	public CocaColaManager() {

		System.out.println("Construindo CocaColaManager");
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("persistence");
		em = emf.createEntityManager();

	}

	public CocaColaManager(boolean olar) {

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
		VideoPersonalizado v = new VideoPersonalizado(tokenFacebook);

		salvarUsuario(coca);
		// video com a primeira parte do usuario
		IMediaWriter videoInserir = v.gerarVideoUsuario(coca);
		// gerar video com o Texto do nome

		// pegar as respostas do usuario pra gerar o outro usuario
		// videos = gerarVideosUsuario(coca.getRespostas());

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

	// metodo que resposta a url do video de acordo com a resposta
	private String processarResposta(String string) {
		//

		return prop.getUrlVideosRespostas()[0];

	}

	public void salvarUsuario(CocaCola coca) {
		//
		em.persist(coca);

	}

	public void excluirUsuario(CocaCola coca) {
		em.remove(coca);

	}
	public List<CocaCola> getSolicitacoesAbertas(){
		
		
		return null;
	}
	
	public void init(){
		
		//vai no banco de dados verificar as solicitacoes em aberto pra gerar o video.
		List<CocaCola> list = getSolicitacoesAbertas();
		if(list.isEmpty()){
			
			Log.info("Sem solicitações pra processar");
			
		}else{
			
			//percorre a lista gerando os videos. 
			for(CocaCola coca: list){
				
				try {
					coca.setStatus(Status.PROCESSANDO);
					videoManager.gerarVideos(coca);
				} catch (Exception e) {
					coca.setStatus(Status.PROBLEMA);
					Log.info("Problemas ao gerar o video: "+ coca.toString());
					Log.info(e.getMessage());
								
				}
				
				coca.setStatus(Status.CONCLUIDA);
				
				
				
			}
			
			
			
			
			
			
			
		}
		
		
		
		
	}

}
