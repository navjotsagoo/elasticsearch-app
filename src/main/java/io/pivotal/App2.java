package io.pivotal;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static spark.Spark.port;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

public class App2 {
	
	static int getPort(){
		
		if (System.getenv("PORT")!= null){
			return Integer.parseInt(System.getenv("PORT"));
		}
		return 8080; 
	}

	public static void main(String[] args) {
		
		String hostname = ""; 
		
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(hostname).multiThreaded(true).build());
		JestClient client = factory.getObject();
		
		
		port(getPort());
		
		
		Spark.post("/save", (req, res) -> {
			
			String json = jsonBuilder().startObject()
											.field("name", "daniel")
											.field("year", 1978)
											.endObject().string();
			
			Index index = new Index.Builder(json).index("music").type("lyrics").build();
			client.execute(index);
			
			Map<String, Object> attributes = new HashMap<>();
			return new ModelAndView(attributes, "index.mustache");
			
		}, new MustacheTemplateEngine());
		
		
		Spark.get("/", (req, res) -> {
			
			SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
			searchBuilder.query(QueryBuilders.matchQuery("name", "Deck the Halls"));
			
			Search search = new Search.Builder(searchBuilder.toString())
										.addIndex("music").build();
			SearchResult result = client.execute(search);
			
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("songs", result.getSourceAsStringList());
			return new ModelAndView(attributes,"index.mustache");
			
		}, new MustacheTemplateEngine());
		

	}

}
