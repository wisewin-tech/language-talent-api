package com.wisewin.api.entity.bo;

/**
 * Created by 王彬 on 2019/5/22.
 */
public class AlipaymentOrder {
    //id
    private Integer id;
    //支付时传入的商户订单号
    private String outTradeNo;
    //支付宝28位交易号
    private String tradeNo;
    //交易当前状态
    private String tradeStatus;
    //本次退款请求流水号，部分退款时必传
    private String outRequestNo;
    //本次退款金额
    private String refundAmount;
    //该笔交易已退款的总金额
    private String refundFee;
    //固定传入 trade
    private String billType;
    //需要下载的账单日期，最晚是当期日期的前一天
    private String billDate;
    //账单文件下载地址，30秒有效
    private String billDownloadUrl;
    //支付宝分配给开发者的应用ID
    private String appId;
    //接口名称 alipay.data.dataservice.bill.downloadurl.query
    private String method;
    //请求使用的编码格式，如utf-8,gbk,gb2312等 utf-8
    private String charset;
    //商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2 RSA2
    private String signType;
    //商户请求参数的签名串，详见签名
    private String sign;
    //发送请求的时间，格式"yyyy-MM-dd HH:mm:ss" 2014-07-24 03:07:50
    private String timestamp;
    //调用的接口版本，固定为：1.0 1.0
    private String version;
    //请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
    private String bizContent;
    //网关返回码,详见文档 40004
    private String code;
    //网关返回码描述,详见文档 Business Failed
    private String msg;
    //本次交易支付的订单金额，单位为人民币（元）
    private String totalAmount;
    //商家在交易中实际收到的款项，单位为元
    private String receiptAmount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        this.receiptAmount = receiptAmount;
    }
}
