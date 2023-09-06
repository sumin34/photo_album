package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Album;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumRepositoryTest {
    AlbumRepository albumRepository;
        @Test
        public void findByAlbumNameLike() {
            Album album = new Album();
            album.setAlbumName("테스트 앨범");
            Album savedAlbum = albumRepository.save(album);

            Album album2 = new Album();
            album2.setAlbumName("테스트 앨범2");
            Album savedAlbum2 = albumRepository.save(album2);

            List<Album> albums = albumRepository.findByAlbumNameLike("테스트%");
            assertThat(albums).isNotEmpty();

            albums = albumRepository.findByAlbumNameLike("테스트");
            assertThat(albums).isEmpty();

        }
}