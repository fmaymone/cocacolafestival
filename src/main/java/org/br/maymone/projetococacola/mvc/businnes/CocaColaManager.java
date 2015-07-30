package org.br.maymone.projetococacola.mvc.businnes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.br.maymone.projetococacola.media.VideoPersonalizado;
import org.br.maymone.projetococacola.model.ClasseJsonCoca;
import org.br.maymone.projetococacola.model.CocaCola;
import org.br.maymone.projetococacola.model.CocaCola.Status;
import org.br.maymone.projetococacola.util.Propriedades;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.mortbay.log.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
	
	public String enviarDadosUsuario(){
		
		CocaCola coca = new CocaCola();
		Gson gson = new Gson();
		
		
		String aux = "{\"respostas\":\"Respostas NAndo\",\"nome\":Nando }";
		
		coca = gson.fromJson(aux, CocaCola.class);
		
		System.out.println(coca.toString());
		return "Olar enviarDAdos";
	}
	
	//metodo que busca as solicitacoes abertas no JSON
	public ArrayList<CocaCola> processarSolicitacoes(){
		
		ArrayList<CocaCola> retorno = new ArrayList();
		 
		 try {
			 
			
			 
			ClientRequest request = new ClientRequest("http://festivaldomeujeito.com.br/server/index.php/festival/user/format/json");
			 
			 
			ClientResponse<String> response = request.get(String.class);
			
			
			Gson gson = new Gson();
		    JsonParser parser = new JsonParser();
		    JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
		    
		    for(JsonElement obj : jArray ){
		    	
		    	
		    	System.out.println(obj.toString());
		    	
		    	JsonElement jelem = gson.fromJson(obj, JsonElement.class);
		    	JsonObject jobj = jelem.getAsJsonObject();
		    	System.out.println(jobj.get("mon_pergunta_1"));
		    	ClasseJsonCoca cocaJson = gson.fromJson(obj, ClasseJsonCoca.class);
		    	System.out.println(cocaJson.getMonPergunta1());
		    	CocaCola temp = new CocaCola();
		    	
		    	temp.setId(new Long(cocaJson.getMonId().toString()));
		    	
		    	temp.setNome(cocaJson.getUsuFirstName().toString());
		    	
		    	String respostas = cocaJson.getMonPergunta1().toString() +";"+
		    			cocaJson.getMonAmbiente().toString() +";"+
		    			cocaJson.getMonPergunta3().toString() +";"+
		    			cocaJson.getMonPergunta4().toString()+";"+
		    			cocaJson.getMonPergunta5().toString();
		    	
		    	temp.setRespostas(respostas);
		    	
		    	temp.setImagem("urlUsuario");
		    	
		    	
		    	retorno.add(temp);
		    	
		    	
		    	
		    }
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	     
		
		
	


}
	
public void enviarLinkUsuario(CocaCola c) throws IOException{
	
	
	
	try {
		JsonObject json = new JsonObject();
		json.addProperty("mon_id", c.getId().toString());
		
		json.addProperty("mon_link_youtube", c.getUrlVideo());
		
		String baseUrl = "http://festivaldomeujeito.com.br/server/index.php/festival/user/id/" + c.getId().toString();
		URL url = new URL (baseUrl);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		out.write(json.toString());
		out.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		 
		while (in.readLine() != null) {
			System.out.println(in.readLine());
		}
		System.out.println("\n REST Service Invoked Successfully..");
		in.close();
	} catch (Exception e) {
		System.out.println("\nError while calling REST Service");
		System.out.println(e);
	}

	

	
	
}
	
}


