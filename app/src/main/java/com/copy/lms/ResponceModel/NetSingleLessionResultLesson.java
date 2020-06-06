package com.copy.lms.ResponceModel;

public class NetSingleLessionResultLesson {
    private int course_id;
    private int free_lesson;
    private String image;
    private String short_text;
    private String created_at;
    private int published;
    private String title;
    private String deleted_at;
    private NetSingleLessionResultLessonCourse_timeline course_timeline;
    private String updated_at;
    private String full_text;
    private NetSingleLessionResultLessonCourse course;
    private NetSingleLessionResultLessonMedia_video media_video;
    private NetSingleLessionResultLessonMedia_pdf media_p_d_f;
    private NetSingleLessionResultLessonMedia_audio media_audio;
    private String lesson_image;
    private int lesson_readtime;
    private int id;
    private int position;
    private String slug;

    public int getCourse_id() {
        return this.course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getFree_lesson() {
        return this.free_lesson;
    }

    public void setFree_lesson(int free_lesson) {
        this.free_lesson = free_lesson;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public NetSingleLessionResultLessonMedia_pdf getMedia_p_d_f() {
        return media_p_d_f;
    }

    public void setMedia_p_d_f(NetSingleLessionResultLessonMedia_pdf media_p_d_f) {
        this.media_p_d_f = media_p_d_f;
    }

    public String getShort_text() {
        return this.short_text;
    }

    public void setShort_text(String short_text) {
        this.short_text = short_text;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getPublished() {
        return this.published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public NetSingleLessionResultLessonMedia_audio getMedia_audio() {
        return media_audio;
    }

    public void setMedia_audio(NetSingleLessionResultLessonMedia_audio media_audio) {
        this.media_audio = media_audio;
    }

    public NetSingleLessionResultLessonCourse_timeline getCourse_timeline() {
        return this.course_timeline;
    }

    public void setCourse_timeline(NetSingleLessionResultLessonCourse_timeline course_timeline) {
        this.course_timeline = course_timeline;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getFull_text() {
        return this.full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public NetSingleLessionResultLessonCourse getCourse() {
        return this.course;
    }

    public void setCourse(NetSingleLessionResultLessonCourse course) {
        this.course = course;
    }

    public NetSingleLessionResultLessonMedia_video getMedia_video() {
        return this.media_video;
    }

    public void setMedia_video(NetSingleLessionResultLessonMedia_video media_video) {
        this.media_video = media_video;
    }

    public String getLesson_image() {
        return this.lesson_image;
    }

    public void setLesson_image(String lesson_image) {
        this.lesson_image = lesson_image;
    }

    public int getLesson_readtime() {
        return this.lesson_readtime;
    }

    public void setLesson_readtime(int lesson_readtime) {
        this.lesson_readtime = lesson_readtime;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
