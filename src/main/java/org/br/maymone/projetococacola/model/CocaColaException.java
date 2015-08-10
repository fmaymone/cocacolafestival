package org.br.maymone.projetococacola.model;

public class CocaColaException extends Exception{
	
	private String msg;
	 
    public CocaColaException(String msg, String cause) {
        super(msg, new Exception(cause));
        this.msg = msg;
    }
 
    public String getMsg() {
        return msg;
    }

}
