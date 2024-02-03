package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.service.PhotoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/albums/{albumId}/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

//    @RequestMapping(value = "",method = RequestMethod.GET)
//    public ResponseEntity<List<PhotoDto>> getPhotoList(@PathVariable("albumId") Long albumId,
//                                                       @RequestParam(value = "sort",defaultValue = "byDate",required = false)final String sort,
//                                                       @RequestParam(value = "keyword",defaultValue = "",required = false)final String keyword)
//    {
//        photoService.
//    }


    @RequestMapping(value = "/{photoId}",method = RequestMethod.GET)
    public ResponseEntity<PhotoDto> getPhotoInfo(@PathVariable("albumId") final Long albumId,
                                                 @PathVariable("photoId") Long photoId){
        PhotoDto photoDto = photoService.getPhoto(photoId);
        return new ResponseEntity<>(photoDto, HttpStatus.OK);
    }


    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity<List<PhotoDto>> uploadPhotos(@PathVariable("albumId") final Long albumId,
                                                       @RequestParam("photos") MultipartFile[] files) throws IOException {
        List<PhotoDto> photos = new ArrayList<>();
        for (MultipartFile file : files) {
                PhotoDto photoDto = photoService.savePhoto(file, albumId);
                photos.add(photoDto);
        }
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void downloadPhotos(@RequestParam("photoIds") Long[] photoIds,
                               HttpServletResponse response){
        try{
            if(photoIds.length == 1){ //사진 파일이 하나일때
                File file = photoService.getImageFile(photoIds[0]);
                OutputStream outputStream = response.getOutputStream();
                IOUtils.copy(new FileInputStream(file),outputStream);
                outputStream.close();
            }
            else{//다운로드 할 사진 파일이 여러개 일때 zip 으로 압축후 다운
                InputStream inputStream = photoService.getZipFile(photoIds);
                OutputStream outputStream = response.getOutputStream();
                IOUtils.copy(inputStream,outputStream);
                outputStream.close();
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException("Error");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
