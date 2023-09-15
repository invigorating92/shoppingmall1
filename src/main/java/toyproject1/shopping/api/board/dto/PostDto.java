package toyproject1.shopping.api.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PostDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostCreateDto {

        private String title;
        private String content;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostUpdateDto {

        private String title;
        private String content;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResponseDto {
        private Long postId;
        private String title;
        private String content;
        private Long commentCount;
    }
}
