package com.squarecross.photoalbum.dto;


import lombok.Data;

import java.util.Date;

@Data
public class PhotoDto {
    private long photoId;
    private String fileName;
    private String thumbUrl;
    private String originalUrl;
    private Date uploadedAt;

    private long albumId;
    private int fileSize;

}
