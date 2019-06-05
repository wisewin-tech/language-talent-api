$(function () {
    $.ajax({
        url: '/sequence/getStsOss',
        type: 'POST',
        dataType: 'json',
        success: function (res) {
            console.log(res);
        }
    })
    // var id = getUrl('id');
    // if(id){
    //     getDataInfo(id);
    // }
})

function getDataInfo(id) {
    $.ajax({
        url: '/Special/selectSpecialBOById',
        type: 'POST',
        data: {specialId: id},
        dataType: 'json',
        success: function (res) {
            $('title').text(res.data.specialBO.title);
            for(var item in res.data.specialBO){
                $('#'+item).html(res.data.specialBO[item]);
            }
            $(".ql-video").contents().find("video").removeAttr('controls');
        }
    })
}

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