package com.squarecross.photoalbum.dto;

import java.util.Date;

public class AlbumDto {
    long AlbumId;
    String AlbumName;
    Date createAt;
    int count;

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

    public Date getCreateAt() {
        return createAt;
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
}
