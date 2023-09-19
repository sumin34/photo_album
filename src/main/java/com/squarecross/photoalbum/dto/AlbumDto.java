package com.squarecross.photoalbum.dto;

import java.util.Date;
import java.util.List;

public class AlbumDto {
    long AlbumId;
    String AlbumName;
    Date createAt;
    int count;

    private List<String> thumbUrls;

    public List<String> getThumbUrls() {
        return thumbUrls;
    }

    public void setThumbUrls(List<String> thumbUrls) {
        this.thumbUrls = thumbUrls;
    }

    public long getAlbumId() {
        return AlbumId;
    }

    public void setAlbumId(long albumId) {
        AlbumId = albumId;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }


    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreatedAt() {
        return createAt;
    }

    
}
