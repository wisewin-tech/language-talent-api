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
    public static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCUMp/IgX1r3QetT+lZDzkX979hKZVZVoWrCLeHVEwe5ovh2zKvclyaJSeLm9ACVHZz1UIDCiEEwWTqJu2I7SgWgaUN6eVjuxItwkaddK05dT2npmqsjp+UN1jrxELq1rMqS3M4yYGMfnWIQxdTCdd1G/JYTjE7opXM0mMzAOfxPBoif/h0uMhyZipqYv0ZiApxs0OnCW95nQ50lzJ3zOH/U2mKbdi3+vQFR8AyVRtySdYAuuoF6kcw89twfrV1K7OsG88ljucIaw+8g7dcQ88jQ9pn11sTSRU1EeGqtYM0VyqYP39sIMfZtopQGkeHAl5fvrEBM1YKZXI0LyQ92OmrAgMBAAECggEBAIIguaDL4x91Y6pRAusKrO1Yw5lDdDqP+Ksn6k9QO87eTXGykZzw5FMN0N2LoWiKVcB8P4x/tmKAlmXsbKLQ62RnzlCbUqrxtpKrhmn366sKQWsWe5CtY34340KSgNfIUmYO/kuc1L5SxE0z7CUj/SZ9tZzIe/6LUtIY4rYEU6XD8ly9sHWyGxK6WLe1YtSINn4ba8Qrc2vhX8iyllbIkBjQi7P4/JGq2c4O4LkyqhPw9GV37AouBSxa5JRvrRKKvmo0ZUQfna/LR1RD8BJOnJWcFAAsP4Cz85ylLCmLSucWitsw5WGESvhC8ZIyIKFTD83Mis8leyZdCJRJ/B6p0bECgYEA2Zl/spZRmicu3QX8vqEVqOBfqTV2Nwq2YQONGSbOU9hsawlL988c2ND3pVvBibHFNF8HZuDa1JvB8cGlD0O/n2+67p+CkJZ8OIpQ2yJxoHOQRt2jeHnNN/G4pLmxTpA6PknxvifroYi99DjgZlBJasSQrWSEHgOlyCJtZiKcoiUCgYEArlnBrPInArYi+X/9PtOlzXQ97iIAcdUL8MsT6G3MsPg3N9qJm+QTF3Res6ONtZQW8d+2p7rPMdYC0MzprN+4QLxO3xR4m+G0XzXHU6uK2WxWbI9aYomYh5/k4qh4iPDM+Qn7phVkT802XEoMZLeeNdwp38sONpg3s5nvb9x7y48CgYEAmijTfVRdj5a+7rt1KiC5VaFQZHKJw8kFGIoJho4lkuU7OkGl3nSO/VRefnoZDVIyYj4I4mve3M1Ug9Aq/juDIJNpnfDH8WOLsgrb6cPnOENnLa2MSKgSKdZId/tOkFhByytb5tolZ5WW7dT1tcafnNKRpSYXK6Rt6PHrC3bt/TkCgYAe+BB/4M9vGt0hhkt8cmm8Ec7CUsXTcm1ZiyjxGzfFo4FjaW0SBLQjcWc073NHcpOUyREPwA33ozUC07MaIu7+iLRVybQWtEOuucW7JfhW4HiFdf0vDZPLW2pznEs0v9kYz14kxR35UtE6GWkUJs1ycbX6a34N0oHqZAh0zSwsHwKBgElOVIi9OCBu/JLD+0SkRs17vrlT4HZK0jchYlO6K/2TNMrCOUMWnQ113cE0lWuVZ68zsRPlp6ntOMwXM0BiwHJIneJlOaIWd+i4KX4oQvbtldZXsbW0tiTbxQeiYBY3Nm5Y0cwoenelTBQCpMODMHVcw5GflngiVcjvq3keFjWN";
    //传输格式
    public static final String FORMAT = "JSON";
    //编码
    public static final String CHARSET = "utf-8";
    //支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkvjtsadXHxdToO+RGTz6OkMoem+XhKxpQkqPeqBWkYNzK1GQSSxF0HReuh0PeLZW8BvwwLoA2+xmqZBbrykPYj6W7GFFEEx3CUUWdPqzh3khIs4HYrjgD04WhzalDQC24+0ZedvLzebaPOla0FoaISHsZV/EJEwSU/HOojjJqYox2vBhu2Qz9rciRw/eFOZYau2g9KndVht6xiiota/GovMQLEBSVakUVa9tXSN38B8KGYq2fKzNJwkHDTBCxHCedYFoExrAghwqed78A8vPKHYwWhzDU3vC5nm/GH/EGhpNWysw8QWEiEeIacoBrD4fNCDMUFirA0UunAQ4jiBiyQIDAQAB";
    //商户生成签名字符串所使用的签名算法类型
    public static final String SIGN_TYPE = "RSA2";
    //服务器异步通知页面路径
    public static final String fy_url = "http://ngikgb.natappfree.cc/wbalipay/alipayurl";
    //页面跳转同步通知页面路径
    public static final String return_url = "http://ngikgb.natappfree.cc/wbalipay/return_url";

}
