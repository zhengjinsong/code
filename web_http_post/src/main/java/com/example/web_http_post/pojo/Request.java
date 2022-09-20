package com.example.web_http_post.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Request {
    private String access_token;
    private String username;
    private String act;
    private String ip;
}
