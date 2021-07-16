package com.example.newattempt;

import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ChatsList {

    private int imageView;
    private String name;
    private String lastSeen;
    private String recentChat;

    public ChatsList(int imageView, String name, String lastSeen, String recentChat) {
        this.imageView = imageView;
        this.name = name;
        this.lastSeen = lastSeen;
        this.recentChat = recentChat;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getRecentChat() {
        return recentChat;
    }

    public void setRecentChat(String recentChat) {
        this.recentChat = recentChat;
    }
}
