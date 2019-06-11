$(function () {
    var id = getUrl('id');
    if(id){
        getDataInfo(id);
    }
})

function getDataInfo(id) {
    $.ajax({
        url: '/Discover/queryDiscoveractivity',
        type: 'POST',
        data: {id: id,source:"html"},
        dataType: 'json',
        success: function (res) {
            $('title').text(res.data.title);
            for(var item in res.data){
                $('#'+item).html(res.data[item]);
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