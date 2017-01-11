package io.pivotal;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PostController {
	
	private PostRepository repo;
	
	public PostController(PostRepository repository){
		this.repo = repository;
	}
	
	@GetMapping("/post/{id}")
	public String postById(@PathVariable String id){
		return repo.findById(id);
	}
	
	@GetMapping("/post/{title}")
	public List<Post> postByTitle(@PathVariable String title){
		return repo.findByTitle(title);
	}
	
	@PostMapping("/post")
	public ResponseEntity<?> add(@RequestBody Post post){
		assert post != null; 
		Post mypost = repo.save(post);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/" + mypost.getId())
				.buildAndExpand().toUri());
		
		return new ResponseEntity<>(mypost,httpHeaders,HttpStatus.CREATED);
	}


}
