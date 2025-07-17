package com.tenco.blog.board;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog.user.User;
import com.tenco.blog._core.utils.Define;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class BoardRestController {
	private final BoardService boardService;

	// http://localhost:8080/&page=0&size=10
	@GetMapping("/")
	public ResponseEntity<ApiUtil<List<BoardResponse.MainDTO>>> main(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		log.info("Board List Select API - Page : {}, Size : {}", page, size);
		List<BoardResponse.MainDTO> boardList = boardService.list(page, size);
		return ResponseEntity.ok(new ApiUtil<>(boardList));
	}

	// Board Detail API Design
	// http://localhost:8080/api/boards/{id}
	@GetMapping("/api/boards/{id}/detail")
	public ResponseEntity<ApiUtil<BoardResponse.DetailDTO>> detail(
			@PathVariable(name = "id") Long id, HttpSession session) {
		log.info("Board Detail Select API - ID : {}", id);
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		BoardResponse.DetailDTO boardDetail = boardService.detail(id, sessionUser);
		return ResponseEntity.ok(new ApiUtil<>(boardDetail));
	}

	// Board Save API
	@PostMapping("/api/boards")
	public ResponseEntity<?> save(@Valid @RequestBody BoardRequest.SaveDTO saveDTO,
								  Errors errors, HttpSession session) {
		log.info("Board Save API - Title : {}", saveDTO.getTitle());
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		BoardResponse.SaveDTO savedBoard = boardService.save(saveDTO, sessionUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedBoard));
	}

	// Board Update API
	@PutMapping("/api/boards/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
									@Valid @RequestBody BoardRequest.UpdateDTO updateDTO,
									Errors errors, HttpSession session) {
		log.info("Board Update API - Title : {}", updateDTO.getTitle());
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		BoardResponse.UpdateDTO updatedBoard = boardService.update(id, updateDTO, sessionUser);
		return ResponseEntity.ok(new ApiUtil<>(updatedBoard));

	}

	// Board Delete API
	@DeleteMapping("/api/boards/{id}")
	public ResponseEntity<ApiUtil<String>> delete(
			@PathVariable(name = "id") Long id, HttpSession session) {
		log.info("Board Delete API - ID : {}", id);
		User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
		boardService.deleteById(id, sessionUser);
		return ResponseEntity.ok(new ApiUtil<>("Board Delete Success"));
	}

}
