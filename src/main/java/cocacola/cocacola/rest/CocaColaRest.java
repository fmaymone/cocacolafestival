package cocacola.cocacola.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.br.maymone.projetococacola.mvc.businnes.CocaColaManager;

@Path("/cocacolarest")
public class CocaColaRest {
	//Este método será acessado via método "GET". Lembre-se, em protocolo HTTP o método  
    //"GET" é utilizado para buscar coisas, o "PUT" para incluir, o "POST" para editar   
    //e "DELETE" para excluir.  
    @GET  
    //Indica que este método, ao ser chamado, retornará um texto comum.  
    @Produces(MediaType.TEXT_PLAIN)  
    public String helloWordTexto() {  
     return "Hello Word!! (texto)";  
    }  
 
    //Este método será acessado via método "GET".  
    @GET  
    //Indica que este método, ao ser chamado, retornará um XML.  
    @Produces(MediaType.TEXT_XML)  
    public String helloWordXML() {  
     return "<?xml version=\"1.0\"?> "  
               + "<hello> Hello Word!! (xml) </hello>";  
    }  
 
    //Este método será acessado via método "GET".  
    @GET  
    //Indica que este método, ao ser chamado, retornará um HTML.  
    @Produces(MediaType.TEXT_HTML)  
    public String helloWordHTML() {  
     return "<html>"  
                    + "<title>Hello Word</title> "  
                    + "<body>"  
                         + "<h1>Hello Word (html)</h1>"  
                    + "</body>"  
               + "</html> ";  
    }  
    
    @GET
    @Path("/user")
    public String getUsers() {
        CocaColaManager cm = new CocaColaManager();
    	
    	return cm.enviarDadosUsuario();
    	
    	}
   
    @GET
    @Path("/user/{id}")
    public String getUser(@PathParam("id") String id) {
       return "get User" + id;
    }
    
    @PUT
    //@Consumes("application/json") 
    @Path("/enviarDadosUsuario")
    public String enviarDados(@PathParam("id") String id) {
    	
    	CocaColaManager cm = new CocaColaManager();
    	
    	return cm.enviarDadosUsuario();
       
    	
    }

    @PUT
    @Path("/user/{id}")
    public void addUser(@PathParam("id") String id, @QueryParam("name") String name) {}

    @DELETE
    @Path("/user/{id}")
    public void removeBook(@PathParam("id") String id ){
    	
    	
    }
	

}
