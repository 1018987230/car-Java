package com.example.nft.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author ：bbdxgg
 * @date ：Created By 2022/7/23 下午6:24
 * @description：文章实体
 * @modified By：
 * @version: $
 */
@Data
public class Article {

    private int blogId;
    private String blogEmail;
    private String blogAuthor;
    private String blogTitle;
    private String blogType;
    private String blogContent;
    private String createTime;
    private String updateTime;

}
