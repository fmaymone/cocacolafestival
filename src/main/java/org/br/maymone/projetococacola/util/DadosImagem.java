package org.br.maymone.projetococacola.util;

import java.util.Date;


//classe que contem os dados da imagem, baseado no timestamp + x,y
public class DadosImagem implements Comparable<DadosImagem> {
	@Override
	public String toString() {
		return "DadosImagem [tempoInicial=" + tempoInicial + ", tempoFinal=" + tempoFinal + ", x=" + x + ", y=" + y + "]";

	}

	public DadosImagem() {}


	private Long tempoInicial;
	private Long tempoFinal;
	
	private Integer frame;
	

	public Integer getFrame() {
		return frame;
	}

	public void setFrame(Integer frame) {
		this.frame = frame;
	}

	public Long getTempoInicial() {
		return tempoInicial;

	}

	private Integer x;
	private Integer y;
	

	public void setTempoInicial(Long tempoInicial) {
		this.tempoInicial = tempoInicial;
	}

	public Long getTempoFinal() {
		return tempoFinal;
	}

	public void setTempoFinal(Long tempoFinal) {
		this.tempoFinal = tempoFinal;
	}


	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public DadosImagem(Long tempoInicial, Long tempoFinal, Integer x, Integer y) {
		super();
		this.tempoInicial = tempoInicial;
		this.tempoFinal = tempoFinal;
		this.x = x;
		this.y = y;
	}

	
	
	
	
	


	@Override
	public int compareTo(DadosImagem o) {
		if (this.tempoInicial < o.tempoInicial) {
			return -1;
		}
		if (this.tempoInicial > o.tempoInicial) {
			return 1;
		}
		return 0;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void setY(Integer y) {
		this.y = y;
	}


}

