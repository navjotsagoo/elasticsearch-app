/*package io.pivotal;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHit;

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
	
	JestClientFactory factory = new JestClientFactory();
	factory.setHttpClientConfig(new HttpClientConfig
										.Builder(hostname)
										.multiThreaded(true)
										.build());
	
	JestClient client = factory.getObject();
    
    Spark.post("/save", (request, response) -> { 
    	StringBuilder json = new StringBuilder("{");
    	json.append("\"name\":\""+request.raw().getParameter("name")+"\",");
    	json.append("\"artist\":\""+request.raw().getParameter("artist")+"\"}");
    	
    	XContentBuilder builder = jsonBuilder()
    			.startObject()
    				.field("name","myname")
    				.field("artist","robert")
    			.endObject();
    	
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
    	SearchResponse sr = ((Object) client).prepareSearch("music")
    			.setTypes("lyrics").execute().actionGet(); 
    	SearchHit[] hits = sr.getHits().getHits();
    	
    	Map<String, Object> attributes = new HashMap<>();
    	attributes.put("songs", hits);
    	
    	return new ModelAndView(attributes, "index.mustache");
    }, new MustacheTemplateEngine());
    
  }
}
*/