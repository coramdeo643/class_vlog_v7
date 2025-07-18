package com.tenco.blog.board;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog._core.utils.Define;
import com.tenco.blog.user.SessionUser;
import com.tenco.blog.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardRestController {
	private final BoardService boardService;

	// http://localhost:8080/&page=0&size=10
	@GetMapping("/")
	public ResponseEntity<ApiUtil<List<BoardResponse.MainDTO>>> main(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		List<BoardResponse.MainDTO> boardList = boardService.list(page, size);
		return ResponseEntity.ok(new ApiUtil<>(boardList));
	}

	// Board Detail API Design
	// http://localhost:8080/api/boards/{id}
	@GetMapping("/api/boards/{id}/detail")
	public ResponseEntity<ApiUtil<BoardResponse.DetailDTO>> detail(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(value = Define.SESSION_USER, required = false) SessionUser sessionUser) {
		if (sessionUser == null) {
			throw new Exception401("Please login, Unauthorized info");
		}
		BoardResponse.DetailDTO boardDetail = boardService.detail(id, sessionUser);
		return ResponseEntity.ok(new ApiUtil<>(boardDetail));
	}

	// Board Save API
	@PostMapping("/api/boards")
	public ResponseEntity<?> save(
			@Valid @RequestBody BoardRequest.SaveDTO saveDTO,
			Errors errors,
			@RequestAttribute(Define.SESSION_USER) SessionUser sessionUser) {
		if (sessionUser == null) {
			throw new Exception401("Please login, Unauthorized info");
		}
		BoardResponse.SaveDTO savedBoard = boardService.save(saveDTO, sessionUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedBoard));
	}

	// Board Update API
	@PutMapping("/api/boards/{id}")
	public ResponseEntity<?> update(
			@PathVariable(name = "id") Long id,
			@Valid @RequestBody BoardRequest.UpdateDTO updateDTO, Errors errors,
			@RequestAttribute(Define.SESSION_USER) SessionUser sessionUser) {
		if (sessionUser == null) {
			throw new Exception401("Please login, Unauthorized info");
		}
		BoardResponse.UpdateDTO updatedBoard = boardService.update(id, updateDTO, sessionUser);
		return ResponseEntity.ok(new ApiUtil<>(updatedBoard));

	}

	// Board Delete API
	@DeleteMapping("/api/boards/{id}")
	public ResponseEntity<ApiUtil<String>> delete(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Define.SESSION_USER) SessionUser sessionUser) {
		if (sessionUser == null) {
			throw new Exception401("Please login, Unauthorized info");
		}
		boardService.deleteById(id, sessionUser);
		return ResponseEntity.ok(new ApiUtil<>("Board Delete Success"));
	}

}
