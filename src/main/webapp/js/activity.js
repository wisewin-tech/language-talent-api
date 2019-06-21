$(function () {
    var id = getUrl('id');
    if(id){
        getDataInfo(id);
    }
});

function getDataInfo(id) {
    $.ajax({
        url: '/Discover/queryDiscoveractivity',
        type: 'POST',
        dataType: 'json',
        data: {id: id},
        success: function (res) {
            $('title').text(res.data.title);
            for(var item in res.data){
                if(item=="phone"){
                    $('#'+item).text(res.data[item])
                    $('#'+item).attr('href', "tel:"+res.data[item]);
                }else if(item=="thumbnail"){
                    $('#'+item).attr('src', res.data[item]);
                }else {
                    $('#'+item).html(res.data[item])
                }

            }
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

