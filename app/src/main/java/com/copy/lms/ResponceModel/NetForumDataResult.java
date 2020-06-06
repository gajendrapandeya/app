package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetForumDataResult {
    private NetForumDataResultDiscussions discussions;
    private ArrayList<NetForumDataResultCategories> categories;

    public NetForumDataResultDiscussions getDiscussions() {
        return this.discussions;
    }

    public void setDiscussions(NetForumDataResultDiscussions discussions) {
        this.discussions = discussions;
    }

    public ArrayList<NetForumDataResultCategories> getCategories() {
        return this.categories;
    }

    public void setCategories(ArrayList<NetForumDataResultCategories> categories) {
        this.categories = categories;
    }
}
