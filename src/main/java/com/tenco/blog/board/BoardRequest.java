package com.tenco.blog.board;

import com.tenco.blog.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BoardRequest {

    // 게시글 저장 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveDTO {

        @NotEmpty(message = "제목을 입력해주세요")
        @Size(min = 4, max = 30, message = "제목은 4~30자 이내로 작성해주세요")
        private String title;

        @NotEmpty(message = "내용을 입력해주세요")
        @Size(min = 4, max = 2000, message = "내용은 4~2000자 이내로 작성해주세요")
        private String content;

        public Board toEntity(User user) {
            return Board.builder()
                    .title(this.title)
                    .user(user)
                    .content(this.content)
                    .build();
        }
    }

    // 게시글 수정용 DTO 설계
    @Data
    public static class UpdateDTO {

        @NotEmpty(message = "제목을 입력해주세요")
        @Size(min = 4, max = 30, message = "제목은 4~30자 이내로 작성해주세요")
        private String title;

        @NotEmpty(message = "내용을 입력해주세요")
        @Size(min = 4, max = 2000, message = "내용은 4~2000자 이내로 작성해주세요")
        private String content;

    }
}
