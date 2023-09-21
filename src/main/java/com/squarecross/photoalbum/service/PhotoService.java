package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.mapper.PhotoMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private final String original_path = Constants.PATH_PREFIX + "/photos/original";
    private final String thumb_path = Constants.PATH_PREFIX + "/photos/thumb";

    public PhotoDto getPhoto(Long PhotoId){
        Optional<Photo> res = photoRepository.findById(PhotoId);

        if(res.isPresent()){
            PhotoDto photoDto = PhotoMapper.convertToDto(res.get());
            return photoDto;
        }else {
            throw new EntityNotFoundException(String.format("사진 아이디 %d로 조회되지 않았습니다", PhotoId));
        }
    }

    public PhotoDto savePhoto(MultipartFile file, Long albumId){
        Optional<Album> res = albumRepository.findById(albumId);
        if(res.isEmpty()){
            throw new EntityNotFoundException("앨범이 존재하지 않습니다");
        }
        String fileName = file.getOriginalFilename();
        int fileSize = (int)file.getSize();

        fileName = getNextFileName(fileName, albumId);

    }

    private void saveFile(MultipartFile file, Long AlbumId, String fileName) throws IOException {
        String filePath = AlbumId + "/" + fileName;
        Files.copy(file.getInputStream(), Paths.get(original_path + "/" + filePath));
    }

    private String getNextFileName(String fileName, Long albumId){
        String fileNameNoExt = StringUtils.stripFilenameExtension(fileName);
        String ext = StringUtils.getFilenameExtension(fileName);

        Optional<Photo> res = photoRepository.findByFileNameAndAlbum_AlbumId(fileName, albumId);

        int count = 2;
        while(res.isPresent()){
            fileName = String.format("%s (%d).%s", fileNameNoExt, count, ext);
            res = photoRepository.findByFileNameAndAlbum_AlbumId(fileName, albumId);
            count++;
        }

        return fileName;
    }

//    public List<PhotoDto> getPhotoList(String keyword, String sort){
//
//    }
}
