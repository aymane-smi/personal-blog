package com.example.personalblog.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import com.example.personalblog.DTO.PostDTO;
import com.example.personalblog.DTO.PostRequest;
import com.example.personalblog.Exceptions.FileStorageException;
import com.example.personalblog.Services.PostServices.PostServicesImpl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
	
	private final PostServicesImpl PostServices;
	public static final String DIRECTORY = System.getProperty("user.home") + "/Desktop/personal-blog/personal-blog/images";
	@GetMapping("/test")
	public String test() {
		return "test upload";
	}
	
	@PostMapping("/add")
	public Map<String, Object> add(@ModelAttribute PostRequest post) throws IOException{
		Map<String, Object> response = new HashMap<>();
		System.out.println(post);
		String Filename = PostServices.saveImage(post.getFile());
		PostDTO newPost = new PostDTO();
		newPost.setImagePath(Filename);
		newPost.setContent(post.getContent());
		PostServices.save(newPost);
		response.put("object", newPost);
		return response;
	}
	
	@GetMapping("/images/{filename}")
	public ResponseEntity<Resource> showFile(@PathVariable("filename") String filename)throws IOException{
		Path Filepath = Paths.get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
		if(!Files.exists(Filepath))
			throw new FileNotFoundException(filename+" not found");
		Resource resource = new UrlResource(Filepath.toUri());
		HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(Filepath)))
                .headers(httpHeaders).body(resource);
	}
	
}
