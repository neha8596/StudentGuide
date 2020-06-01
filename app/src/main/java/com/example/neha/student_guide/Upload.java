package com.example.neha.student_guide;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Belal on 2/23/2017.
 */
@IgnoreExtraProperties
public class Upload{
    public String subject;
    public String name;
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public String getSubject() { return  subject;}
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Upload(String subject, String name,String url) {
        this.subject = subject;
        this.name = name;
        this.url= url;
    }
    public Upload(){}
}
