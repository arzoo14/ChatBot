package com.example.arzoo.chatbot;

import android.net.Uri;

/**
 * Created by arzoo on 3/29/2018.
 */

public class chatImage {
    Uri uri;

    public chatImage(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
