package toyproject1.shopping.api.board.service;

import toyproject1.shopping.api.board.dto.PostDto;

public interface PostService {

    Long createPost(PostDto.PostCreateDto postDto);
    void updatePost(Long postId, PostDto.PostUpdateDto postDto);
    void deletePost(Long postId);
}
