package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.HashPrintJobAttributeSet;
import javax.swing.text.html.parser.Entity;
import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @RequestMapping(value = "/{albumId}", method = RequestMethod.GET)
    public ResponseEntity<AlbumDto> getAlbum(@PathVariable("albumId") final long albumId) {
        AlbumDto album = albumService.getAlbum(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ResponseEntity<AlbumDto> getAlbumByQuery(long albumId){
        System.out.println("querty come in!@#!@#");
        AlbumDto album = albumService.getAlbum(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "json_body",method = RequestMethod.POST)
    public ResponseEntity<AlbumDto> getAlbumByJson(@RequestBody HashMap<String,Object> map){
        long albumValue = (long)map.get("albumId");
        AlbumDto album = albumService.getAlbum(albumValue);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody final AlbumDto albumDto) throws IOException {
        AlbumDto savedAlbumDto = albumService.createAlbum(albumDto);
        return new ResponseEntity<>(savedAlbumDto, HttpStatus.OK);
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<List<AlbumDto>>
    getAlbumList(@RequestParam(value="keyword", required=false, defaultValue="") final String keyword,
                 @RequestParam(value="sort", required=false, defaultValue = "byDate") final String sort,
                 @RequestParam(value = "orderBy",required = false,defaultValue = "") final  String order) {
        List<AlbumDto> albumDtos = albumService.getAlbumList(keyword, sort, order);
        return new ResponseEntity<>(albumDtos, HttpStatus.OK);
    }

    @RequestMapping(value="/{albumId}", method = RequestMethod.PUT)
    public ResponseEntity<AlbumDto> updateAlbum(@PathVariable("albumId") final long albumId,
                                                @RequestBody final AlbumDto albumDto){
        AlbumDto res = albumService.changeName(albumId, albumDto);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping(value = "/{albumId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAlbum(@PathVariable("albumId") final long albumId) throws IOException {
        albumService.deleteAlbum(albumId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
