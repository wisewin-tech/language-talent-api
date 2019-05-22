package com.wisewin.api.util;

/**
 * Created by 王彬 on 2019/5/22.
 */
public class AlipayConfig {
    //商户appid
    public static final String APP_ID = "2019051464504496";
    //支付宝网关
    public static final String URL = "https://openapi.alipay.com/gateway.do";
    //开发者应用私钥
    public static final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJXKx5wmneZzf5bSVMv+z3yEYWv66J8Z8UuKuSKQKjrOvwoxhMP75gDW0dQuhtOZkAByXFu5V/YdGvoGT3fwPr9QxHjwucyMrTk9TA+bPn6J2wwGz7IS83r5F+sxxEcH2YEtMDx0tG7x5ZNi83rwrqgG8b521/PTucMXuOi9XS58Nf+Jg9UQSokOcCOQXpbZCHtVn5Pi9X6vqc6lVBFLlxVt1Mm+p52peixF7nob957goseILjnyN5+raH3JAWr/C3/wg4RhoeNL5gfCeY9Lw0kSz02Y26FrHIQ1tqFHV6bqni1WwpIDXjXJ/J6E24fOo7gdlthFyoRTwJMbfzJi/zAgMBAAECggEAWUmMM3lUJ+UgVVnFLSZ0enwMUy013yTYKzf/Dnrp5Eq68yiQ2r90mCsiCis7ugP+mC4NN4K/opWLH4Y7G0XM3svit4EcI0XB0dX+LZIih1SFbCcor/TaJHBTOvBiJ3qXqQpTb9lT7T/xkjvOZXu6tMPXdWPxoQvEe7sOfZIX44ceirywGldehtN/FA0cE7GG+PiJnltZypptZpaypgcLtFVAvUouQrxVsdNIvytqBw5P1hHgMnZyP5ML88IiPmxmVAJbcdc+QqrqHorsrmHaJBghOxRbPnTDeb0ueGOoHrN+vbzhISbVHkxYVYO1Xepp9q/EmpUMf9MlQcUhiGbZQQKBgQDNJUYY9M9omDYaCx/Xz8na6ETZr+5ZuecVoC2g18uak8qWKqcxSE3jQLwqt4gAwYdi03thP39bVNls1J5fe4xNSZcoywLR1V8GIoivvEZ0wCVg01rYY8zx+ZpSUbYE5MTfmQS5j+DZK9w5oJHvMJvZ12eyLioCLAjHW0pyLLsx7wKBgQCracqdJMomVrl5H/wwpK/njs70AoPybTGQ2YWBzs9iS2TTZoql+2bFUQHwSoRSAfQxiYjMz379hTNLfHLkZ1KH+lkqQSlq1sspmChhKahpKvLbQVXOdqSA4szBC5Fxxbd1efb45XJhWWjmB7Vlfe3N/h/avu8zFR3+Yo4rZYtWPQKBgACN1jehCaJdrt+5IffiPshRkkYIPJuPbflX+meRzaH4PjbTnKn8aQFxHlD7N+nQm/3/Vjj4A+8KNqAdnuPhIIrg5QCeM76/VcdddoMK/iG4lyFA0OD+vAH3S5xnTheLSqDT9/hMVC4h+TFKpro/plwbQdNwXnqtBcUMERJn3oWPAoGAZ/Yt0MkzYbVPoErp5Ya8uTBADHZKGIppeXS0KFqKJQ+dAyo6BRlI53uONXK8dhCBTlogAjgGyTQ0PCAqfqS7xYTs91e2CvpjFcMXGnbtkXte9+PUavhCJXlovdyKRfqonjNuut5OVQSpPnqGZhvOUxVBtKYA5XZ1TofvuhpIO/ECgYAjfNG/D00E4Nsjjft55aQcihVPPqZmKs0TEJM64Ckc/2APcJ+ztSRk6wyzOaiTzA7p3HvvsWkJhsK3u5jz3eHJb9VGHlrU/kLWp74oU5AJYUxxXpDyvgxICjPpN9bZF8uGXSiMsn0vG3TXMeyHYhrUpFwCIXfERXb96sd6CeHYHw==";
    //参数返回格式
    public static final String FORMAT = "json";
    //编码
    public static final String CHARSET = "UTF-8";
    //支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkvjtsadXHxdToO+RGTz6OkMoem+XhKxpQkqPeqBWkYNzK1GQSSxF0HReuh0PeLZW8BvwwLoA2+xmqZBbrykPYj6W7GFFEEx3CUUWdPqzh3khIs4HYrjgD04WhzalDQC24+0ZedvLzebaPOla0FoaISHsZV/EJEwSU/HOojjJqYox2vBhu2Qz9rciRw/eFOZYau2g9KndVht6xiiota/GovMQLEBSVakUVa9tXSN38B8KGYq2fKzNJwkHDTBCxHCedYFoExrAghwqed78A8vPKHYwWhzDU3vC5nm/GH/EGhpNWysw8QWEiEeIacoBrD4fNCDMUFirA0UunAQ4jiBiyQIDAQAB";
    //商户生成签名字符串所使用的签名算法类型
    public static final String SIGN_TYPE = "RSA2";
    //服务器异步通知页面路径
    public static final String notify_url = "http://www.duoyuka.com/wbalipay/notify_url";
    //页面跳转同步通知页面路径
    public static final String return_url = "http://www.duoyuka.com/wbalipay/return_url";
}
