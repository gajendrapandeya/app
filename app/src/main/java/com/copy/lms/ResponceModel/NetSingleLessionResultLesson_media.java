package com.copy.lms.ResponceModel;

public class NetSingleLessionResultLesson_media {
    private Object downloadable_media;
    private Object pdf;
    private NetSingleLessionResultLesson_mediaVideo video;
    private Object audio;

    public Object getDownloadable_media() {
        return this.downloadable_media;
    }

    public void setDownloadable_media(Object downloadable_media) {
        this.downloadable_media = downloadable_media;
    }

    public Object getPdf() {
        return this.pdf;
    }

    public void setPdf(Object pdf) {
        this.pdf = pdf;
    }

    public NetSingleLessionResultLesson_mediaVideo getVideo() {
        return this.video;
    }

    public void setVideo(NetSingleLessionResultLesson_mediaVideo video) {
        this.video = video;
    }

    public Object getAudio() {
        return this.audio;
    }

    public void setAudio(Object audio) {
        this.audio = audio;
    }
}
