package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

//service 어노테이션 bean등록d
//model?
@Service
public class AlbumService {
    //등록된 빈을 컨테이너에서 가져와서 사용 가능
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private PhotoRepository photoRepository;

    //
    public AlbumDto getAlbum(Long albumId){
        Optional<Album> res = albumRepository.findById(albumId);

        if(res.isPresent()){
            AlbumDto albumDto = AlbumMapper.convertToDto(res.get());
            albumDto.setCount(photoRepository.countByAlbum_AlbumId(albumId));
            return albumDto;
        }else {
            throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되지 않았습니다", albumId));
        }
    }

    public AlbumDto createAlbum(AlbumDto albumDto) throws IOException{
        Album album = AlbumMapper.convertToModel(albumDto);
        album = this.albumRepository.save(album);
        this.createAlbumDirectories(album);

        return AlbumMapper.convertToDto(album);
    }

    private void createAlbumDirectories(Album album) throws IOException {
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/original/" + album.getAlbumId()));
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + album.getAlbumId()));
    }

    public List<AlbumDto> getAlbumList(String keyword, String sort, String order) {
        List<Album> albums;
        if (Objects.equals(sort, "byName")){
            albums = albumRepository.findByAlbumNameContainingOrderByAlbumNameAsc(keyword);
        } else if (Objects.equals(sort, "byDate")) {
            albums = albumRepository.findByAlbumNameContainingOrderByCreatedAtDesc(keyword);
        } else {
            throw new IllegalArgumentException("알 수 없는 정렬 기준입니다");
        }
        List<AlbumDto> albumDtos = AlbumMapper.convertToDtoList(albums);

        for(AlbumDto albumDto : albumDtos){
            List<Photo> top4 = photoRepository.findTop4ByAlbum_AlbumIdOrderByUploadedAtDesc(albumDto.getAlbumId());
            albumDto.setThumbUrls(top4.stream().map(Photo::getThumbUrl).map(c -> Constants.PATH_PREFIX + c).collect(Collectors.toList()));
        }
        return albumDtos;
    }

    public AlbumDto changeName(Long AlbumId, AlbumDto albumDto){
        Optional<Album> album = this.albumRepository.findById(AlbumId);
        if(album.isEmpty()){
            throw new NoSuchElementException(String.format("Album ID '%d'가 존재하지 않습니다.",AlbumId));
        }

        Album updateAlbum = album.get();
        updateAlbum.setAlbumName(albumDto.getAlbumName());
        Album savedAlbum = this.albumRepository.save(updateAlbum);
        return AlbumMapper.convertToDto(savedAlbum);
    }

    public void deleteAlbum(Long AlbumId) throws IOException {
        Optional<Album> album = this.albumRepository.findById(AlbumId);
        if(album.isPresent()) {
            Album deleteAlbum = album.get();
            deleteAlbumDirectories(deleteAlbum);

            this.albumRepository.deleteById(AlbumId);
        }
    }

    private void deleteAlbumDirectories(Album album) throws IOException {
        String albumPath = Constants.PATH_PREFIX+"/photos/original/"+album.getAlbumId();
        String thumbPath = Constants.PATH_PREFIX + "/photos/thumb/" + album.getAlbumId();

        deleteDirectory(new File(albumPath));
        deleteDirectory(new File(thumbPath));
    }

    private void deleteDirectory(File directory) throws IOException {

    }
}
    /**
    public AlbumDto getAlbum(String albumName){
        String res = albumRepository.findByAlbumName(albumName);

        AlbumDto albumDto = AlbumMapper.convertToDto();
        return albumDto;
    }
     */

