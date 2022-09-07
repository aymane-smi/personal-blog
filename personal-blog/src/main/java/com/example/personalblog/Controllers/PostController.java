package com.example.personalblog.Controllers;

import java.io.IOException;
import java.io.InputStream;
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

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
	
	private final PostServicesImpl PostServices;
	
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
	
}
