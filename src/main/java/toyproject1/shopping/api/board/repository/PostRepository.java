package toyproject1.shopping.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject1.shopping.api.board.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
