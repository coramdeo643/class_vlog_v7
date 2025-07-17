package com.tenco.blog.user;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog._core.utils.Define;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserRestController {

	private final UserService userService;

	// 회원가입 API : 인증 불필요
	@PostMapping("/join")
	public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO joinDTO, Errors errors) {
		UserResponse.JoinDTO joinedUser = userService.join(joinDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(joinedUser));
	}

	// 로그인 API : 인증 불필요
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO, Errors errors) {
		String jwtToken = userService.login(loginDTO);
		return ResponseEntity.ok()
				.header(Define.AUTH, Define.BEARER + jwtToken)
				.body(new ApiUtil<>(jwtToken));
	}

	// 회원정보조회 API : JWT Auth needed
	@GetMapping("/api/users/{id}")
	public ResponseEntity<?> getUserInfo(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Define.SESSION_USER) SessionUser sessionUser) {
		if (sessionUser == null) {
			throw new Exception401("Please login, Unauthorized info");
		} // authorization check
		UserResponse.DetailDTO userDatil = userService.findUserById(id, sessionUser);
		return ResponseEntity.ok(new ApiUtil<>(userDatil));
	}

	// 회원정보수정 API : JWT Auth needed
	@PutMapping("/api/users/{id}")
	public ResponseEntity<?> updateUser(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Define.SESSION_USER) SessionUser sessionUser,
			@Valid @RequestBody UserRequest.UpdateDTO updateDTO, Errors errors) {
		if (sessionUser == null) {
			throw new Exception401("Please login, Unauthorized info");
		} // authorization check
		// TODO - updateById parameter check
		UserResponse.UpdateDTO updatedUser = userService.updateById(id, updateDTO);
		return ResponseEntity.ok().body(new ApiUtil<>(updatedUser));
	}

	// Logout, 클라이언트 단에서 jwt 토큰 정보를 직접 삭제 처리한다
	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		return ResponseEntity.ok(new ApiUtil<>("Logout Success"));
	}

}
