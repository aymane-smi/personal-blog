package com.example.personalblog.Repositories;

import java.util.Optional;

import com.example.personalblog.Models.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	Optional<Post> findById(Long id);
}
