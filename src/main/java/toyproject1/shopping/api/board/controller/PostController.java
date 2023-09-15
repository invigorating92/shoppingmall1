package toyproject1.shopping.api.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject1.shopping.api.board.dto.PostDto;
import toyproject1.shopping.api.board.service.PostService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> createPost(@Valid @RequestBody PostDto.PostCreateDto postDto) {
        Long postId = postService.createPost(postDto);

        if (postId == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(postId);
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId,
                                           @Valid @RequestBody PostDto.PostUpdateDto postDto) {
        postService.updatePost(postId, postDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
