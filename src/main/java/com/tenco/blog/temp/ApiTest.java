package com.tenco.blog.temp;

import com.tenco.blog.user.User;
import com.tenco.blog.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // @Controller + @Response
public class ApiTest {

	// Dependencies Inject
	private final UserService userService;


	@GetMapping("/api-test/user")
	public User getUsers() {
		return userService.findById(1L);
	}
}
