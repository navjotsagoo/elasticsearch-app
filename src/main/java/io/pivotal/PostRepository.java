package io.pivotal;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostRepository extends ElasticsearchRepository<Post, String> {
	
	String findById(String id);
	
	List<Post> findByTitle(String title);
	
	
}
