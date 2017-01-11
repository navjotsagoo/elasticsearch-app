package io.pivotal;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "post", type="post")
public class Post {

	@Id
	private String id;
	
	private String title;
	
	public Post(){
		
	}
	
	public Post(String id, String title){
		this.id = id;
		this.title = title;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "Post [id=" + this.id + ", title=" + this.title + "]";
	}

}
