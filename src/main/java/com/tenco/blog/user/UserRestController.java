package com.tenco.blog.user;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog._core.utils.Define;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관리 API")
@RequiredArgsConstructor
@RestController
public class UserRestController {

	private final UserService userService;

	@Operation(summary = "회원가입", description = "회원가입")
	@PostMapping("/join")
	public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO joinDTO, Errors errors) {
		UserResponse.JoinDTO joinedUser = userService.join(joinDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(joinedUser));
	}

	@Operation(summary = "로그인", description = "로그인")
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO, Errors errors) {
		String jwtToken = userService.login(loginDTO);
		return ResponseEntity.ok()
				.header(Define.AUTH, Define.BEARER + jwtToken)
				.body(new ApiUtil<>(jwtToken));
	}

	@Operation(summary = "회원정보 조회", description = "회원정보 조회")
	@GetMapping("/api/users/{id}")
	public ResponseEntity<?> getUserInfo(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Define.SESSION_USER) SessionUser sessionUser) {
		if (sessionUser == null) {
			throw new Exception401("Please login, Unauthorized info");
		} // authorization check
		UserResponse.DetailDTO userDatil = userService.findUserById(id, sessionUser.getId());
		return ResponseEntity.ok(new ApiUtil<>(userDatil));
	}

	@Operation(summary = "회원정보 수정", description = "회원정보 수정")
	@PutMapping("/api/users/{id}")
	public ResponseEntity<?> updateUser(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Define.SESSION_USER) SessionUser sessionUser,
			@Valid @RequestBody UserRequest.UpdateDTO updateDTO, Errors errors) {
		if (sessionUser == null) {
			throw new Exception401("Please login, Unauthorized info");
		} // authorization check
		UserResponse.UpdateDTO updatedUser = userService.updateById(id, sessionUser.getId(), updateDTO);
		return ResponseEntity.ok().body(new ApiUtil<>(updatedUser));
	}

	@Operation(summary = "로그아웃", description = "로그아웃")
	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		return ResponseEntity.ok(new ApiUtil<>("Logout Success"));
	}

}
