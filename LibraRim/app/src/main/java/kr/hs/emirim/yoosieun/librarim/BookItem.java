package kr.hs.emirim.yoosieun.librarim;

import android.graphics.Bitmap;

/**
 * Created by Student on 2017-11-19.
 */

class BookItem {
    private Bitmap bitmap;
    private String title;
    private String writer;
    private String pub;
    private String status;

    public Bitmap getBookImg() {
        return bitmap;
    }

    public void setBookImg(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPub() {return pub;}

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
