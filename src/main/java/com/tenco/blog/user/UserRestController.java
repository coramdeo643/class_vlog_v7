package com.tenco.blog.user;

import com.tenco.blog._core.common.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

	//회원가입 API 설계
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

	//로그인요청
	//회원정보조회
	//회원정보수정
	//로그아웃 처리

}
