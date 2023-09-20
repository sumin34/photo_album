package com.squarecross.photoalbum.mapper;

import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.PhotoDto;

public class PhotoMapper {
    public static PhotoDto convertToDto(Photo photo){
        PhotoDto photoDto = new PhotoDto();
        photoDto.setPhotoId(photo.getPhotoId());
        photoDto.setThumbUrl(photo.getThumbUrl());
        photoDto.setFileSize(photo.getFileSize());
        photoDto.setUploadedAt(photo.getUploadedAt());
        return photoDto;
    }

//    public static Photo converToModel(PhotoDto photoDto){
//        Photo photo = new Photo();
//        photo.setFileName(photoDto.getFileName());
//        photo.setPhotoId(photoDto.getPhotoId());
//        photo.setFileSize(photoDto.getFileSize());
//        photo.setThumbUrl(photoDto.getThumbUrl());
//        photo.setUploadedAt(photoDto.getUploadedAt());
//
//        return photo;
//    }
}
