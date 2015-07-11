package org.br.maymone.projetococacola.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CocaCola {
	
	 public CocaCola(){}
	
	private static FacebookUser facebookUser;
	 	

	private static VideoUsuario videoUsuario;

	
	public static VideoUsuario getVideoUsuario() {
		return videoUsuario;
	}


	public static void setVideoUsuario(VideoUsuario videoUsuario) {
		CocaCola.videoUsuario = videoUsuario;
	}


	@Override
	public String toString() {
		return "CocaCola [id=" + id + ", dataCriacao=" + dataCriacao
				+ ", respostas=" + respostas + ", urlVideo=" + urlVideo
				+ ", tokenFacebook=" + tokenFacebook + "]";
	}


	public CocaCola(Calendar dataCriacao, String respostas,
			String urlVideo, String tokenFacebook) {
		super();
		
		this.dataCriacao = dataCriacao;
		this.respostas = respostas;
		this.urlVideo = urlVideo;
		this.tokenFacebook = tokenFacebook;
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

	public String getTokenFacebook() {
		return tokenFacebook;
	}

	public void setTokenFacebook(String tokenFacebook) {
		this.tokenFacebook = tokenFacebook;
	}


	

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	private Calendar dataCriacao;

	@Column
	private String respostas;

	@Column
	private String urlVideo;

	@Column
	private String tokenFacebook;




	
	


	

}
