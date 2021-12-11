package com.example.quiz;

public class QuizTemplate {
    String question;
    String []options;
    String rightOption;
    String reasonForAnswer;
    public QuizTemplate(String question, String []options, String rightOption){
        this.question = question;
        this.options = options;
        this.rightOption = rightOption;
    }

    public QuizTemplate(String question, String[] options, String rightOption, String reasonForAnswer) {
        this.question = question;
        this.options = options;
        this.rightOption = rightOption;
        this.reasonForAnswer = reasonForAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getRightOption() {
        return rightOption;
    }

    public void setRightOption(String rightOption) {
        this.rightOption = rightOption;
    }

    public String getReasonForAnswer() {
        return reasonForAnswer;
    }

    public void setReasonForAnswer(String reasonForAnswer) {
        this.reasonForAnswer = reasonForAnswer;
    }
}
