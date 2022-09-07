package com.example.personalblog.Services.PostServices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

import javax.transaction.Transactional;

import com.example.personalblog.DTO.PostDTO;
import com.example.personalblog.Exceptions.FileStorageException;
import com.example.personalblog.Exceptions.InvalidFileFormat;
import com.example.personalblog.Models.Post;
import com.example.personalblog.Repositories.PathRepository;
import com.example.personalblog.Repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServicesImpl implements PostServices{
	
	private final PostRepository postRepository;
	//private final PathRepository path;
	
	public Post save(PostDTO post) {
		
		Post newPost = new Post();
		
		newPost.setContent(post.getContent());
		newPost.setImagePath(post.getImagePath());
		newPost.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		
		postRepository.save(newPost);
		
		return newPost;
	}
	
	public String saveImage(MultipartFile file){
		log.info("inside the saveImage Method!");
		log.info("{}", file);
		String Filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if(Filename.contains(".."))
				throw new InvalidFileFormat("invalid file format");
			Path targetLocation = (Paths.get("~/Desktop/personal-blog/personal-blog/images")).resolve(Filename);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException | InvalidFileFormat e) {
			System.out.println(e.getMessage());
		}
		return Filename;
	}

}
