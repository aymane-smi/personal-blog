package com.example.personalblog.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	private String username;
	private String Fullname;
	private String newUsername;
	private String newPassword;
}
