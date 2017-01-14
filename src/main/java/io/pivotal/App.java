package io.pivotal;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

public class App {

	static int getAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT")!= null){
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 8080;
	}
	
	public static void main(String[] args) throws Exception {
	  
	String hostname = ""; 
	
	ObjectMapper mapper = JsonFactory.create();
	
    @SuppressWarnings("resource")
	TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
    	.addTransportAddress(
    			new InetSocketTransportAddress(
    					InetAddress.getByName(hostname), 80));
    
    Spark.post("/save", (request, response) -> { 
    	StringBuilder json = new StringBuilder("{");
    	json.append("\"name\":\""+request.raw().getParameter("name")+"\",");
    	json.append("\"artist\":\""+request.raw().getParameter("artist")+"\"}");
    	
    	IndexRequest indexRequest = new IndexRequest("music","lyrics", UUID.randomUUID().toString());
    	indexRequest.source(json.toString());
    	IndexResponse esResponse = client.index(indexRequest).actionGet();
    	
    	Map<String, Object> attributes = new HashMap<>();
    	return new ModelAndView(attributes, "index.mustache");
    	
    }, new MustacheTemplateEngine());
    
    Spark.get("/add", (request,response) -> {
    	return new ModelAndView (new HashMap(), "add.mustache");
    }, new MustacheTemplateEngine());
   
    
    Spark.get("/", (request, response) -> {
    	SearchResponse sr = client.prepareSearch("music")
    			.setTypes("lyrics").execute().actionGet(); 
    	SearchHit[] hits = sr.getHits().getHits();
    	
    	Map<String, Object> attributes = new HashMap<>();
    	attributes.put("songs", hits);
    	
    	return new ModelAndView(attributes, "index.mustache");
    }, new MustacheTemplateEngine());
    
  }
}
