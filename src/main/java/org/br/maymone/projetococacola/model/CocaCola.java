package org.br.maymone.projetococacola.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import javax.imageio.ImageIO;

import com.restfb.types.Url;



public class CocaCola {
	
	
	private ClasseJsonCoca jsonCoca;
	
	 public ClasseJsonCoca getJsonCoca() {
		return jsonCoca;
	}


	public void setJsonCoca(ClasseJsonCoca jsonCoca) {
		this.jsonCoca = jsonCoca;
	}


	public CocaCola(Calendar dataCriacao, String respostas,
			String urlVideo, String nome,  Status status) {
		super();
		
		
		
		this.dataCriacao = dataCriacao;
		this.respostas = respostas;
		this.urlVideo = urlVideo;
		this.nome = nome;
		
		this.status = status;
	}


	public CocaCola(){}
	

	@Override
	public String toString() {
		return "CocaCola [id=" + id + ", dataCriacao=" + dataCriacao
				+ ", respostas=" + respostas + ", urlVideo=" + urlVideo
				+ ", nome=" + nome + ", image=" 
				+ ", status=" + status + ", sexo=" + sexo + ", cor=" + cor
				+ "]";
	}


	public CocaCola(Calendar dataCriacao, String respostas,
			String urlVideo, String tokenFacebook) {
		super();
		
		this.dataCriacao = dataCriacao;
		this.respostas = respostas;
		this.urlVideo = urlVideo;
		this.nome = tokenFacebook;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getRespostas() {
		return respostas;
	}

	public void setRespostas(String respostas) {
		this.respostas = respostas;
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}



	


	private Long id;


	private Calendar dataCriacao;


	private String respostas;


	private String urlVideo;


	private String nome;
	
	
  
    private Status status;
	
	
    private Sexo sexo;
	
	
    private Cor cor;

	
	private BufferedImage  imagem;
		



	



	public void setImagem(BufferedImage imagem) {
		this.imagem = imagem;
	}



	public void setImagem(String s) throws IOException {
		//http://festivaldomeujeito.com.br/site/uploads/festival/10205026925565317/avatar_crop.jpg
		
		try {
				
			//ClassLoader classLoader = getClass().getClassLoader();
			//File file = new File(classLoader.getResource("avatar_crop.png").getPath());
			//String urlBaseVideos = file.getAbsolutePath();
			URL url = new URL("http://festivaldomeujeito.com.br/site/uploads/festival/" + getJsonCoca().getUsuFaceId() + "/avatar_crop.png");
			//URL url = new URL("http://festivaldomeujeito.com.br/site/uploads/festival/934089803315913/avatar_crop.png");
			//URL url = new URL(file.getAbsolutePath());
			imagem = ImageIO.read(url);
		
		    
		 
			
			System.out.println(imagem.getType());
			
			
			
			
			
		
		
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	public Sexo getSexo() {
		return sexo;
	}


	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}


	public Cor getCor() {
		return cor;
	}


	public void setCor(Cor cor) {
		this.cor = cor;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}

	
	//retorna o path dos videos
	public String getPathVideos(){
		
		String retorno = "";
		
		ClassLoader classLoader = getClass().getClassLoader();
		try{
			
			retorno = classLoader.getResource("temp/" + getNome() + "/").getPath();	
			
		}
		catch (Exception e){
			
		}
		//URL in = Thread.currentThread().getContextClassLoader().getResource("temp/" + getNome() + "/");
		
		
		return retorno;
		
	}




	public enum Status {
	    RECEBIDA, PROCESSANDO, CONCLUIDA, FINALIZADA, CANCELADA, PROBLEMA
	}
	
	public enum Sexo {
	    M,F
	}
	public enum Cor{
	    VERMELHO,VERDE,AMARELO
	}
	public BufferedImage getImagem() {
		return imagem;
	}


	

}
