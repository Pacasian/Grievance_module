package com.example.grevienceapp.Quiz;

public class QuizPreviewModel {
    public String qid;
    public String question;
    public String opt1;
    public String opt2;
    public String opt3;
    public String opt4;
    public String ans;

    public QuizPreviewModel(String qid, String question, String opt1, String opt2, String opt3, String opt4, String ans) {
        this.qid = qid;
        this.question = question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.ans = ans;
    }
    public String getQid() {
        return qid;
    }

    public String getQuestion() {
        return question;
    }

    public String getOpt1() {
        return opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public String getAns() {
        return ans;
    }


}
