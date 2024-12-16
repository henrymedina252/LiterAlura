package com.literalura.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Formats {
    private String epub;
    private String html;
    private String textHtml;  // Nuevo campo para "text/html"


    // Getters y Setters
    public String getEpub() {
        return epub;
    }

    public void setEpub(String epub) {
        this.epub = epub;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTextHtml() {
        return textHtml;
    }

    public void setTextHtml(String textHtml) {
        this.textHtml = textHtml;
    }
}
