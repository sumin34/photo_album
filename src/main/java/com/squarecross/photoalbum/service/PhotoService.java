package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.mapper.PhotoMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.File;
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

    private final String original_path = Constants.PATH_PREFIX + "\\photos\\original";
    private final String thumb_path = Constants.PATH_PREFIX + "\\photos\\thumb";

    public PhotoDto getPhoto(Long PhotoId){
        Optional<Photo> res = photoRepository.findById(PhotoId);

        if(res.isPresent()){
            PhotoDto photoDto = PhotoMapper.convertToDto(res.get());
            return photoDto;
        }else {
            throw new EntityNotFoundException(String.format("사진 아이디 %d로 조회되지 않았습니다", PhotoId));
        }


    }



    private void saveFile(MultipartFile file, Long AlbumId, String fileName) throws IOException {
        try{
        String filePath = AlbumId + "\\" + fileName;
        Files.copy(file.getInputStream(), Paths.get(original_path + "\\" + filePath));

        BufferedImage thumbImg = Scalr.resize(ImageIO.read(file.getInputStream()), Constants.THUMB_SIZE, Constants.THUMB_SIZE);

        File thumbFile = new File(thumb_path + "\\" + filePath);
        String ext = StringUtils.getFilenameExtension(fileName);
        if (ext == null) {
            throw new IllegalArgumentException("No Extention");
        }
        ImageIO.write(thumbImg, ext, thumbFile);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public PhotoDto savePhoto(MultipartFile file, Long albumId) throws IOException {
        try {
            Optional<Album> res = albumRepository.findById(albumId);

            if (res.isEmpty()) {
                throw new EntityNotFoundException("앨범이 존재하지 않습니다");
            }
            String fileName = file.getOriginalFilename();
            int fileSize = (int) file.getSize();

            //파일 이름이 중복일 경우 숫자를 붙여 주는 부분
            fileName = getNextFileName(fileName, albumId);
            saveFile(file, albumId, fileName);


            Photo photo = new Photo();
            photo.setOriginalUrl("/photos/original/" + albumId + "/" + fileName);
            photo.setThumbUrl("/photos/thumb/" + albumId + "/" + fileName);
            photo.setFileName(fileName);
            photo.setFileSize(fileSize);
            photo.setAlbum(res.get());
            Photo createdPhoto = photoRepository.save(photo);
            return PhotoMapper.convertToDto(createdPhoto);
        }catch (MultipartException e){
            throw new MultipartException("올바른 파일 확장자가 아닙니다.");
        }
    }

    private String getNextFileName(String fileName, Long albumId){
        //파일 이름과 확장자 분리
        String fileNameNoExt = StringUtils.stripFilenameExtension(fileName);
        String ext = StringUtils.getFilenameExtension(fileName);

        //확장자 검사
        boolean isPermissionExt=false;
        String[] permission_file_ext = {"png","jpeg","gif","jpg","mp4"};
        for (String s : permission_file_ext) {
            if (s.equals(ext)) {
                isPermissionExt = true;
                break;
            }
        }
        if(!isPermissionExt){
            throw new MultipartException("정해진 확장자가 아닙니다.");
        }


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
