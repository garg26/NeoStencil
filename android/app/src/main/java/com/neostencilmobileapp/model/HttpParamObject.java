package com.neostencilmobileapp.model;

import java.util.HashMap;
import java.util.Map;

public class HttpParamObject {

    private String url = "";
    private HashMap<String, Object> postParams = new HashMap<>();
    private HashMap<String, String> headers = new HashMap<String, String>();
    private String json = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, Object> getPostParams() {
        return postParams;
    }

    public void setPostParams(HashMap<String, Object> postParams) {
        this.postParams = postParams;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
