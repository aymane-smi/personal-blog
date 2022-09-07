package com.example.personalblog.Services.PostServices;

import com.example.personalblog.DTO.PostDTO;
import com.example.personalblog.Exceptions.FileStorageException;
import com.example.personalblog.Models.Post;

import org.springframework.web.multipart.MultipartFile;

public interface PostServices {
	Post save(PostDTO post);
	String saveImage(MultipartFile file);
//	Post getPost(Long id);
//	void deletePost(Long id);
//	Post editPost(PostDTO post, Long id);
}
