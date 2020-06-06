package com.copy.lms.ResponceModel;

public class NetSingleLessionResult {
    private int course_progress;
    private boolean is_certified;
    private NetSingleLessionResultLesson lesson;
    private Object previous_lesson;
    private NetSingleLessionResultCourse course;
    private NetSingleLessionResultLesson_media lesson_media;
    private NetSingleLessionResultNext_lesson next_lesson;

    public int getCourse_progress() {
        return this.course_progress;
    }

    public void setCourse_progress(int course_progress) {
        this.course_progress = course_progress;
    }

    public boolean getIs_certified() {
        return this.is_certified;
    }

    public void setIs_certified(boolean is_certified) {
        this.is_certified = is_certified;
    }

    public NetSingleLessionResultLesson getLesson() {
        return this.lesson;
    }

    public void setLesson(NetSingleLessionResultLesson lesson) {
        this.lesson = lesson;
    }

    public Object getPrevious_lesson() {
        return this.previous_lesson;
    }

    public void setPrevious_lesson(Object previous_lesson) {
        this.previous_lesson = previous_lesson;
    }

    public NetSingleLessionResultCourse getCourse() {
        return this.course;
    }

    public void setCourse(NetSingleLessionResultCourse course) {
        this.course = course;
    }

    public NetSingleLessionResultLesson_media getLesson_media() {
        return this.lesson_media;
    }

    public void setLesson_media(NetSingleLessionResultLesson_media lesson_media) {
        this.lesson_media = lesson_media;
    }

    public NetSingleLessionResultNext_lesson getNext_lesson() {
        return this.next_lesson;
    }

    public void setNext_lesson(NetSingleLessionResultNext_lesson next_lesson) {
        this.next_lesson = next_lesson;
    }
}
