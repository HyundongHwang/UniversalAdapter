package com.hhd2002.universaladaptertest.ArticleComment;

import java.util.ArrayList;

public class ArticleCommentDAO extends ArrayList<Object> {

    public ArticleCommentDAO() {
        this.add(new ArticleItem("this is article..."));

        this.add(new CommentItem("user0", "this is comment from user0"));
        this.add(new CommentOfCommentItem("user00", "user0 is good"));
        this.add(new CommentOfCommentItem("user01", "user0 is great"));
        this.add(new CommentOfCommentItem("user02", "user0 is bad"));
        this.add(new CommentItem("user1", "this is comment from user1"));
        this.add(new CommentItem("user2", "this is comment from user2"));
        this.add(new CommentItem("user3", "this is comment from user3"));
        this.add(new CommentOfCommentItem("user30", "user3 is good"));
        this.add(new CommentOfCommentItem("user31", "user3 is great"));
        this.add(new CommentOfCommentItem("user32", "user3 is bad"));
        this.add(new CommentItem("user4", "this is comment from user0"));
        this.add(new CommentOfCommentItem("user40", "user4 is good"));
        this.add(new CommentOfCommentItem("user41", "user4 is great"));
        this.add(new CommentOfCommentItem("user42", "user4 is bad"));
        this.add(new CommentItem("user5", "this is comment from user1"));
        this.add(new CommentItem("user6", "this is comment from user2"));
        this.add(new CommentItem("user7", "this is comment from user3"));
        this.add(new CommentOfCommentItem("user70", "user7 is good"));
        this.add(new CommentOfCommentItem("user71", "user7 is great"));
        this.add(new CommentOfCommentItem("user72", "user7 is bad"));
        this.add(new CommentItem("user8", "this is comment from user0"));
        this.add(new CommentOfCommentItem("user80", "user8 is good"));
        this.add(new CommentOfCommentItem("user81", "user8 is great"));
        this.add(new CommentOfCommentItem("user82", "user8 is bad"));
        this.add(new CommentItem("user9", "this is comment from user1"));
        this.add(new CommentItem("user10", "this is comment from user2"));
        this.add(new CommentItem("user11", "this is comment from user3"));
        this.add(new CommentOfCommentItem("user110", "user11 is good"));
        this.add(new CommentOfCommentItem("user111", "user11 is great"));
        this.add(new CommentOfCommentItem("user112", "user11 is bad"));
    }
}
