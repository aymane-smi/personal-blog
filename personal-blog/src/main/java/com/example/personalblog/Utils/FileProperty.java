package com.example.personalblog.Utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "file")
@Data
@RequiredArgsConstructor
public class FileProperty {
	
	private String uploadDir;

}
