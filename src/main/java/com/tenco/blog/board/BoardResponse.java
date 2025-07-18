package com.tenco.blog.board;

import com.tenco.blog.reply.Reply;
import com.tenco.blog.user.SessionUser;
import com.tenco.blog.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

	// 게시물 목록 응답 DTO
	@Data
	public static class MainDTO {
		private Long id;
		private String title;
		private String content;
		private String writerName;
		private String createdAt;

		@Builder
		public MainDTO(Board board) {
			this.id = board.getId();;
			this.title = board.getTitle();
			this.content = board.getContent();
			this.writerName = board.getUser().getUsername();
			this.createdAt = board.getTime();
		}
	}

	// 게시글 상세보기 응답 DTO 설계
	@Data
	public static class DetailDTO {
		private Long id;
		private String title;
		private String content;
		private String writerName;
		private String createdAt;
		private boolean isBoardOwner;
		private List<ReplyDTO> replies;

		// 채용공고 :
		//private List<SkillDTO> skill;
		//private List<ApplicationDTO> application;
		//private List<RatingDTO> rating;


		@Builder
		public DetailDTO(Board board, SessionUser sessionUser) {
			this.id = board.getId();
			this.title = board.getTitle();
			this.content = board.getContent();
			this.writerName = board.getUser().getUsername();
			this.createdAt = board.getTime();
			this.isBoardOwner = sessionUser != null && board.isOwner(sessionUser.getId());
			this.replies = new ArrayList<>();
			for(Reply reply : board.getReplies()) {
				// 응답 DTO 변수 안에 값을 할당
				this.replies.add(new ReplyDTO(reply, sessionUser));
			}
		}
	}

	@Data
	private static class ReplyDTO {
		private Long id;
		private String comment;
		private String writerName;
		private String createdAt;
		private boolean isReplyOwner;

		@Builder
		public ReplyDTO(Reply reply, SessionUser sessionUser) {
			this.id = reply.getId();
			this.comment = reply.getComment();
			this.writerName = reply.getUser().getUsername();
			this.createdAt = reply.getCreatedAt().toString();
			this.isReplyOwner = sessionUser != null && reply.isOwner(sessionUser.getId());
		}
	}

	@Data
	public static class SaveDTO {
		private Long id;
		private String title;
		private String content;
		private String writerName;
		private String createdAt;

		@Builder
		public SaveDTO(Board board) {
			this.id = board.getId();
			this.title = board.getTitle();
			this.content = board.getContent();
			this.writerName = board.getUser().getUsername();
			this.createdAt = board.getCreatedAt().toString();
		}
	}

	@Data
	public static class UpdateDTO {
		private Long id;
		private String title;
		private String content;
		private String writerName;
		private String createdAt;

		@Builder
		public UpdateDTO(Board board) {
			this.id = board.getId();
			this.title = board.getTitle();
			this.content = board.getContent();
			this.writerName = board.getUser().getUsername();
			this.createdAt = board.getCreatedAt().toString();
		}
	}
}
