package com.squarecross.photoalbum.repository;
import com.squarecross.photoalbum.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

//간단한 CRUD 처리
//repository => bean 처리
//그럼 이게 model?
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>{
    List<Album> findByAlbumNameLike(String name);

    String findByAlbumName(String name);

    Album findByAlbumId(long id);

    List<Album> findByAlbumNameContainingOrderByCreatedAtDesc(String keyword);

    List<Album> findByAlbumNameContainingOrderByAlbumNameAsc(String keyword);
}
