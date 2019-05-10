package com.wisewin.api.entity.bo;



import com.wisewin.api.common.base.BaseModel;

public class QuestionBO extends BaseModel {
    //id
    private Object id;
    //题目
    private Object topic;
    //选项
    private Object option;
    //答案和解析
    private Object answer;
    //分值
    private Object score;
    //判断（judge） 阅读（`read`） 课后测试:常规（common）翻译（translate）
    // 拼写（`write`） 听力和文本匹配（hearingAndTest）
    // 听音完成句子（hearingAndSentence） 图文匹配(imageText)
    private Object questionType;
    //语言能力测试（languageTest）课程考证题（courseCertificate）课时测试题（chapterTest）
    private Object testType;
    //学前热身(warmUp) 课后测试（test）
    private Object chapterType;
   //关联id
    private Object relevanceId;
    //创建人id
    private Object createUserId;
    //修改人id
    private Object updateUserId;
    //创建时间
    private Object createTime;
    //修改时间
    private Object updateTime;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

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

    public Object getChapterType() {
        return chapterType;
    }

    public void setChapterType(Object chapterType) {
        this.chapterType = chapterType;
    }

    public Object getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Object relevanceId) {
        this.relevanceId = relevanceId;
    }

    public Object getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Object createUserId) {
        this.createUserId = createUserId;
    }

    public Object getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Object updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }
}
