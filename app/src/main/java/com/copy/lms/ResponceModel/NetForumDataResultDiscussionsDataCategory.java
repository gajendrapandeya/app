package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetForumDataResultDiscussionsDataCategory {
    private String color;
    private String updated_at;
    private String parent_id;
    private String name;
    private String created_at;
    private int id;
    private String slug;
    private int order;
    private ArrayList<NetForumDataResultDiscussionsDataCategoryParents> parents;

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public ArrayList<NetForumDataResultDiscussionsDataCategoryParents> getParents() {
        return this.parents;
    }

    public void setParents(ArrayList<NetForumDataResultDiscussionsDataCategoryParents> parents) {
        this.parents = parents;
    }
}
