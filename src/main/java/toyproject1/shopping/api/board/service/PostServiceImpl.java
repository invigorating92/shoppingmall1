package toyproject1.shopping.api.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject1.shopping.api.board.domain.Post;
import toyproject1.shopping.api.board.dto.PostDto;
import toyproject1.shopping.api.board.repository.PostRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    @Override
    @Transactional
    public Long createPost(PostDto.PostCreateDto postDto) {
        var post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        return postRepository.save(post).getId();
    }

    @Override
    @Transactional
    public void updatePost(Long postId, PostDto.PostUpdateDto postDto) {
        var post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
        post.changeText(postDto.getTitle(), postDto.getContent());
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        var post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
        postRepository.delete(post);
    }
}
