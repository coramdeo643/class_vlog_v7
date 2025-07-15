package com.tenco.blog.user;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j //
@RestController //@Controller + @ResponseBody
public class UserRestController {
	// @Slf4j 선언시 자동 완성
	// private static final Logger log = LoggerFactory.getLogger(UserRestController.class)

	@Autowired
	private final UserService userService;
	// 생성자 의존 주임 - dependencies inject
	// UserService userService = new UserService();
	// public UserRestController(UserService userService) {
	// this.userService = userService;
	// }

	//Join API 설계
	@PostMapping("/join")
	// public ResponseEntity<?>
	// JSON 형식 데이터를 추출할때 @RequestBody 선언
	public ResponseEntity<ApiUtil<UserResponse.JoinDTO>> join(@RequestBody UserRequest.JoinDTO reqDTO) { // DTO; Data Transfer Object
		log.info("회원가입 API 호출 - 사용자명 : {}, 이메일 : {}", reqDTO.getUsername(), reqDTO.getEmail());
		reqDTO.validate();
		// Service에 위임 처리
		UserResponse.JoinDTO joinUser = userService.join(reqDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(joinUser));
	}

	//Login API Design (POST)
	// http://localhost:8080/login
	@PostMapping("/login")
	public ResponseEntity<ApiUtil<UserResponse.LoginDTO>> login(@RequestBody UserRequest.LoginDTO reqDTO,
																HttpSession session) {
		log.info("Login API Call -- Username : {}", reqDTO.getUsername());
		reqDTO.validate(); // Validation
		UserResponse.LoginDTO loginUser = userService.login(reqDTO);
		session.setAttribute(Define.SESSION_USER, loginUser);
		return ResponseEntity.ok(new ApiUtil<>(loginUser));
	}

	//회원정보조회
	@GetMapping("/api/users/{id}")
	public ResponseEntity<ApiUtil<UserResponse.DetailDTO>> getUserInfo(@PathVariable(name = "id") Long id,
																	   HttpSession session) {
		log.info("User Info API Call -- ID : {}", id);
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		// UserResponse.DetailDTO userInfo = userService.findById(id); id 만으로만 하면 남의 id 정보요청 가능
		UserResponse.DetailDTO userDetail = userService.findUserById(id, sessionUser);
		return ResponseEntity.ok(new ApiUtil<>(userDetail));

	}

	//회원정보수정
	@PutMapping("/api/users/{id}")
	public ResponseEntity<ApiUtil<UserResponse.UpdateDTO>> update(@PathVariable(name = "id") Long id,
																  @RequestBody UserRequest.UpdateDTO updateDTO) {
		log.info("User Info Update API Call");
		// identification at the interceptor
		updateDTO.validate(); // validation
		UserResponse.UpdateDTO updatedUser = userService.updateById(id, updateDTO);
		return ResponseEntity.ok(new ApiUtil<>(updatedUser));

	}

	//로그아웃 처리
	@GetMapping("/logout")
	public ResponseEntity<ApiUtil<String>> logout(HttpSession session) {
		log.info("Logout API Call");
		session.invalidate();
		return ResponseEntity.ok(new ApiUtil<>("Logout Success"));
	}

}
