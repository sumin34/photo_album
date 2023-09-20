package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/albums/{albumId}/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity<List<PhotoDto>> getPhotoList(@PathVariable("albumId") Long albumId,
                                                       @RequestParam(value = "sort",defaultValue = "byDate",required = false)final String sort,
                                                       @RequestParam(value = "keyword",defaultValue = "",required = false)final String keyword)
    {
        photoService.
    }


//    @RequestMapping(value = "",method = RequestMethod.GET)
//    public ResponseEntity<PhotoDto> getPhotoInfo(@PathVariable("albumId") Long albumId){
//
//    }
}
