package com.example.neha.student_guide;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Belal on 2/23/2017.
 */
@IgnoreExtraProperties
public class Remind{
    public String subject;
    public String rem;
    public String dos;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)

    public String getSubject() { return  subject;}
    public String getRem() {
        return rem;
    }

    public String getDos() {
        return dos;
    }
    public Remind(String subject, String rem,String dos) {
        this.subject = subject;
        this.rem = rem;
        this.dos= dos;
    }

    public Remind(){}
}
