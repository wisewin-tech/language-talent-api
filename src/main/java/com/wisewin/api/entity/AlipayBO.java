package com.wisewin.api.entity;

import com.wisewin.api.entity.bo.common.base.BaseModel;

import java.util.Date;


/**
 * 支付宝
 */
public class AlipayBO extends BaseModel {

    private  int id; //支付宝
    private String  outTradeNo;//支付时传入的商户订单号
    private String tradeNo; //支付宝28位交易号
    private String tradeStatus; //交易当前状态
    private String outRequestNo;//本次退款请求流水号，部分退款时必传
    private Double refundAmount;//本次退款金额
    private Double refundFee;//该笔交易已退款的总金额
    private String billType;//固定传入 trade
    private String billDate;//需要下载的账单日期，最晚是当期日期的前一天
    private String billDownloadUrl;//账单文件下载地址，30秒有效
    private Integer appId;//支付宝分配给开发者的应用ID
    private String method;//接口名称  alipay.data.dataservice.bill.downloadurl.query
    private String charset;//请求使用的编码格式，如utf-8,gbk,gb2312等  utf-8
    private String signType;//商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2  RSA2
    private String sign;//商户请求参数的签名串，详见签名
    private Date timestamp;//发送请求的时间，格式"yyyy-MM-dd HH:mm:ss" 2014-07-24 03:07:50
    private String version;//调用的接口版本，固定为：1.0  1.0
    private String  bizContent;//请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
    private String code;//网关返回码,详见文档  40004
    private String msg;//网关返回码描述,详见文档  Business Failed
    private String total_amount;//本次交易支付的订单金额，单位为人民币（元）
    private String receiptAmount;//商家在交易中实际收到的款项，单位为元


    public  AlipayBO(){}

    public AlipayBO(String outTradeNo, String tradeStatus, Date timestamp) {
        this.outTradeNo = outTradeNo;
        this.tradeStatus = tradeStatus;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getOutRequestNo() {
        return outRequestNo;
    }

    public void setOutRequestNo(String outRequestNo) {
        this.outRequestNo = outRequestNo;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Double getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Double refundFee) {
        this.refundFee = refundFee;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillDownloadUrl() {
        return billDownloadUrl;
    }

    public void setBillDownloadUrl(String billDownloadUrl) {
        this.billDownloadUrl = billDownloadUrl;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        this.receiptAmount = receiptAmount;
    }
}
