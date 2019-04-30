package com.wisewin.api.entity.bo;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;

/**
 * 做题记录类(复习)
 */
public class SubjectreviewBO extends BaseModel {

    private Integer id; //做题记录类(复习)id
    private  Integer userId;//用户id
    private Date srReleasetime; //创建时间
    private  Integer hourId; //课时iD
    private Integer questionbankId; //题库id
    private String answer; //自己做题答案
    private String judge;//判断（题目是否正确）

    public SubjectreviewBO(){}

    public SubjectreviewBO(Integer userId, Date srReleasetime, Integer hourId, Integer questionbankId, String answer, String judge) {
        this.userId = userId;
        this.srReleasetime = srReleasetime;
        this.hourId = hourId;
        this.questionbankId = questionbankId;
        this.answer = answer;
        this.judge = judge;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setSrReleasetime(Date srReleasetime) {
        this.srReleasetime = srReleasetime;
    }

    public void setHourId(Integer hourId) {
        this.hourId = hourId;
    }

    public void setQuestionbankId(Integer questionbankId) {
        this.questionbankId = questionbankId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Date getSrReleasetime() {
        return srReleasetime;
    }

    public Integer getHourId() {
        return hourId;
    }

    public Integer getQuestionbankId() {
        return questionbankId;
    }

    public String getAnswer() {
        return answer;
    }

    public String getJudge() {
        return judge;
    }
}
