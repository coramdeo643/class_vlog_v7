package com.tenco.blog.user;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog._core.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true) // 클래스 레벨에서의 읽기 전용 설정
public class UserService {
	private final UserJpaRepository userJpaRepository;

	// Join 회원가입 처리
	@Transactional
	public UserResponse.JoinDTO join(UserRequest.JoinDTO joinDTO) {
		userJpaRepository.findByUsername(joinDTO.getUsername())
				.ifPresent(user1 -> {
					throw new Exception400("이미 존재하는 사용자명입니다");
				});
		User savedUser = userJpaRepository.save(joinDTO.toEntity());
		return new UserResponse.JoinDTO(savedUser);
	}

	// 로그인 처리 - DTO 변환 책임
	public String login(UserRequest.LoginDTO loginDTO) {
		User selectedUser = userJpaRepository
				.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
				.orElseThrow(() -> new Exception401("사용자명 또는 비밀번호가 틀렸어요"));
		String jwt = JwtUtil.create(selectedUser);
		return jwt;
	}

	// 회원정보 조회
	public UserResponse.DetailDTO findUserById(Long requestUserId, Long sessionUserId) {
		if (!requestUserId.equals(sessionUserId)) {
			throw new Exception403("No Permission to get Other User Info");
		}
		User selctedUser = userJpaRepository.findById(requestUserId)
				.orElseThrow(() -> new Exception404("Not found User"));
		return new UserResponse.DetailDTO(selctedUser);
	}

	// 회원정보 수정 처리 (더티 체킹)
	@Transactional
	public UserResponse.UpdateDTO updateById(Long requestUserId,
											 Long sessionUserId,
											 UserRequest.UpdateDTO updateDTO) {
		if (!requestUserId.equals(sessionUserId)) {
			throw new Exception403("No Permission to get Other User Info");
		}
		User selectedUser = userJpaRepository.findById(requestUserId)
				.orElseThrow(() -> new Exception404("Not found User"));
		selectedUser.update(updateDTO);
		return new UserResponse.UpdateDTO(selectedUser);
	}
}
