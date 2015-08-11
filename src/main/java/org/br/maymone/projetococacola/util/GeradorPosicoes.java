package org.br.maymone.projetococacola.util;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class GeradorPosicoes {

	private static Integer idVideo;

	private static InputStream urlArquivoPropriedades;
	private static ArrayList<DadosImagem> dadosImagem;

	public GeradorPosicoes(Integer idVideo) throws Exception {

		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("timeline/video" + idVideo + ".json");

		urlArquivoPropriedades = in;
		dadosImagem = new ArrayList<DadosImagem>();

		// logger.debug("Olar");
		this.readJson();

	}
	public GeradorPosicoes(String nomeArquivo) throws Exception {

		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("timeline/" + nomeArquivo + ".json");

		urlArquivoPropriedades = in;
		dadosImagem = new ArrayList<DadosImagem>();

		// logger.debug("Olar");
		this.readJson();

	}

	public static InputStream getUrlArquivoPropriedades() {
		return urlArquivoPropriedades;
	}

	public static void setUrlArquivoPropriedades(
			InputStream urlArquivoPropriedades) {
		GeradorPosicoes.urlArquivoPropriedades = urlArquivoPropriedades;
	}

	public void read() throws Exception {

		InputStreamReader reader = new InputStreamReader(urlArquivoPropriedades);

		BufferedReader br = new BufferedReader(reader);
		String s = "";

		while (s != null) {

			s = br.readLine();
			if (s != null) {

				DadosImagem temp = new DadosImagem();
				String[] split = s.split(";");
				temp.setTempoInicial(new Long(split[0]));
				temp.setTempoFinal(new Long(split[1]));
				temp.setX(new Integer(split[2]));
				temp.setY(new Integer(split[3]));

				dadosImagem.add(temp);

			}

		}

	}

	private static DadosImagem parse(BufferedReader br) throws Exception {

		DadosImagem retorno = new DadosImagem();

		String nString = br.readLine();
		if (nString == null) {
			throw new EOFException();
		}

		String[] split = nString.split(";");
		Integer x = new Integer(split[0]);
		Integer y = new Integer(split[1]);

		return retorno;
	}

	public static Integer getIdVideo() {
		return idVideo;
	}

	public static void setIdVideo(Integer idVideo) {
		GeradorPosicoes.idVideo = idVideo;
	}

	public static ArrayList<DadosImagem> getDadosImagem() {
		return dadosImagem;
	}

	public static void setDadosImagem(ArrayList<DadosImagem> dadosImagem) {
		GeradorPosicoes.dadosImagem = dadosImagem;
	}

	// metodo que irá gerar do Json um arquivo no nosso formato:
	public void readJson() {

		Reader reader = new InputStreamReader(urlArquivoPropriedades);

		JsonReader jsonReader = new JsonReader(reader);

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonElement jElement = parser.parse(jsonReader);

		JsonObject jsonObject = jElement.getAsJsonObject();

		JsonArray jArray = jsonObject.get("position").getAsJsonObject()
				.get("frameData").getAsJsonArray();

		// pegar o primeiro elemento
		int size = jArray.size();

		int i = 0;
		while (i < size) {

			if (i < size - 1) {

				JsonObject first = jArray.get(i).getAsJsonObject();
				
				String tempo = first.getAsJsonObject().get("t").getAsString();
				Integer tempoInicial = getValorTimeStamp(tempo);
				

				DadosImagem dadosTemp = new DadosImagem();
				dadosTemp.setTempoInicial(new Long(tempoInicial));
								
				JsonArray tracking = first.getAsJsonObject().get("val").getAsJsonArray();
				Integer x = getValorTimeStamp(tracking.get(0).getAsString());
				Integer y = getValorTimeStamp(tracking.get(1).getAsString());
				
				dadosTemp.setX(x/1000);
				dadosTemp.setY(y/1000);
				dadosTemp.setFrame(i);
				
				dadosImagem.add(dadosTemp);
				System.out.println(dadosTemp.toString());

			}

			i++;

			System.out.println(i);

		}
	}

	public Integer getValorTimeStamp(String value) {
		Integer direita = new Integer(0);
		Integer esquerda = new Integer(0);
		Integer retorno = new Integer(0);
		if (value.contains(".")) {

			String[] parts = value.split("\\.");

			if (parts.length > 0) {
				if (parts[0] != null) {

					esquerda = new Integer(parts[0]);
					esquerda = esquerda * 1000;

				}
				if (parts[1] != null) {

					int size = parts[1].length();

					// evitar que seja maior
					if (size > 3) {

						size = 3;

					}
					direita = new Integer(parts[1].substring(0, size));

				}
			}
		} else {

			esquerda = new Integer(value);
			esquerda = esquerda * 1000;
		}

		retorno = direita + esquerda;
		return retorno;

	}
	
	
	

}
