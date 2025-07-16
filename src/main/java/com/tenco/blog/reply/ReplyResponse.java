package com.tenco.blog.reply;

import lombok.Builder;
import lombok.Data;

public class ReplyResponse {
	// 댓글 저장 응답 DTO
	@Data
	public static class SaveDTO {
		private Long id;
		private String comment;
		private String writerName;
		private String createdAt;
		private Long boardId;

		@Builder
		public SaveDTO(Reply reply) {
			this.id = reply.getId();
			this.comment = reply.getComment();
			this.writerName = reply.getUser().getUsername();
			this.createdAt = reply.getCreatedAt().toString();
			this.boardId = reply.getBoard().getId();
		}
	}
}
