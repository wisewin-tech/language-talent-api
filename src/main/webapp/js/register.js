var inviteUserId = 0, intervalId = 0, i = 60;
$(function () {
    inviteUserId = getUrl('userId');
});


var source = 'user';

$('#register').on('click', function (e) {
    var phone = $('input[name="phone"]').val();
    var verify = $('input[name="verify"]').val();
    if (!phone || !verify || !source) {
        alert('请填写注册信息！');
        return;
    }
    if (!inviteUserId) {
        console.error('传值错误');
        return;
    }
    $.ajax({
        url: '/user/register',
        type: 'POST',
        dataType: 'json',
        data: {
            inviteUserId: inviteUserId,
            phone: phone,
            verify: verify,
            source: source
        },
        success: function (response) {
            if (response.code === '0000000') {
                alert("注册" + response.msg);
                location.href = '/re_success.html';
            }
        }
    })
});

$('#sendBtn').on('click', function (e) {
    var phone = $('input[name="phone"]').val();
    if (phone && !intervalId) {
        $.ajax({
            url: '/user/send',
            type: 'POST',
            dataType: 'json',
            data: {
                phone: phone,
                type: 'amend'
            },
            success: function (response) {
                alert(response.msg);
                if (response.code !== '0000058') {
                    intervalId = setInterval(function () {
                        $('#sendBtn').text(i--);
                        if (i === 0 && intervalId) {
                            $('#sendBtn').text('发送验证码');
                            i = 60;
                            clearInterval(intervalId);
                            intervalId = 0;
                        }
                    }, 1000);
                }
            }
        })

    } else if (!phone) {
        alert('填上你的手机号！！！！')
    }
});

function gotoDownload() {
    var url = "https://itunes.apple.com/cn/app/id350962117";
}

$('#download').on('click', function (e) {
    if (browser.userAgent.ios) {
        alert('IOS手机');
    } else if (browser.userAgent.android && !browser.userAgent.weiXin) {
        $.ajax({
            url: '/Versions/queryVersions',
            type: 'POST',
            dataType: 'json',
            data: {platform: '安卓'},
            success: function (res) {
                window.location.href = res.data.downurl;
            }
        })
    } else if (browser.userAgent.weiXin || browser.userAgent.QQ) {
        $('#md').removeClass('hide-mongolia').addClass('mongolia');
    }
});


// 判断浏览器内核、手机系统等，使用 browser.userAgent.mobile
var browser = {
    userAgent: function () {
        var ua = navigator.userAgent;
        var ualower = navigator.userAgent.toLocaleLowerCase();
        return {
            trident: ua.indexOf('Trident') > -1, // IE内核
            presto: ua.indexOf('Presto') > -1, // opera内核
            webKit: ua.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: ua.indexOf('Gecko') > -1 && ua.indexOf('KHTML') == -1, // 火狐内核
            mobile: !!ua.match(/AppleWebKit.*Mobile.*/) || !!ua.match(/AppleWebKit/), // 是否为移动终端
            ios: !!ua.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // IOS终端
            android: ua.indexOf('Android') > -1 || ua.indexOf('Mac') > -1, // 安卓终端
            iPhone: ua.indexOf('iPhone') > -1 || ua.indexOf('Mac') > -1, // 是否为iphone或QQHD浏览器
            iPad: ua.indexOf('iPad') > -1, // 是否为iPad
            webApp: ua.indexOf('Safari') == -1, // 是否web应用程序，没有头部与底部
            QQbrw: ua.indexOf('MQQBrowser') > -1, // QQ浏览器(手机上的)
            weiXin: ua.indexOf('MicroMessenger') > -1, // 微信
            QQ: ualower.match(/\sQQ/i) == " qq", // QQ App内置浏览器（需要配合使用）
            weiBo: ualower.match(/WeiBo/i) == "weibo", // 微博
            ucLowEnd: ua.indexOf('UCWEB7.') > -1, //
            ucSpecial: ua.indexOf('rv:1.2.3.4') > -1,
            webview: !(ua.match(/Chrome\/([\d.]+)/) || ua.match(/CriOS\/([\d.]+)/)) && ua.match(/(iPhone|iPod|iPad).*AppleWebKit(?!.*Safari)/),
            ucweb: function () {
                try {
                    return parseFloat(ua.match(/ucweb\d+\.\d+/gi).toString().match(/\d+\.\d+/).toString()) >= 8.2
                } catch (e) {
                    if (ua.indexOf('UC') > -1) {
                        return true;
                    }
                    return false;
                }
            }(),
            Symbian: ua.indexOf('Symbian') > -1,
            ucSB: ua.indexOf('Firofox/1.') > -1
        };
    }()
};

function getUrl(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (name) {
        if (r != null)
            return decodeURI(r[2]);
        return null;
    } else {
        r = window.location.search.substr(1);
        if (r != null)
            return decodeURI(r);
        return null;
    }
}

