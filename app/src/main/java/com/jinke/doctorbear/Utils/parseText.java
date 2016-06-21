package com.jinke.doctorbear.Utils;

/**
 * 解析富文本内容
 * Created by Max on 2016/6/21.
 */
public class ParseText {
    String content;

    public void ParseText(String content) {
        this.content = content;
    }

    public String tranHtml(String result){
        this.content = result;
        content = content.replace("\n","<br />");
        content = content.replace("<bold>","<b>");
        content = content.replace("</bold>","</b>");

        return content;
    }
}
