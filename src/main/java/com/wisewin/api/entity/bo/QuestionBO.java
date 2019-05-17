package com.wisewin.api.entity.bo;



import com.wisewin.api.common.base.BaseModel;

public class QuestionBO extends BaseModel {
    //题目
    private Object topic;
    //选项
    private Object option;
    //答案和解析
    private Object answer;
    //分值
    private Object score;
    //关联id
    private Object relevanceId;
    //
    private Object questionType;
    //
    private Object testType;

    public Object getTopic() {
        return topic;
    }

    public void setTopic(Object topic) {
        this.topic = topic;
    }

    public Object getOption() {
        return option;
    }

    public void setOption(Object option) {
        this.option = option;
    }

    public Object getAnswer() {
        return answer;
    }

    public void setAnswer(Object answer) {
        this.answer = answer;
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }

    public Object getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Object relevanceId) {
        this.relevanceId = relevanceId;
    }

    public Object getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Object questionType) {
        this.questionType = questionType;
    }

    public Object getTestType() {
        return testType;
    }

    public void setTestType(Object testType) {
        this.testType = testType;
    }
}
