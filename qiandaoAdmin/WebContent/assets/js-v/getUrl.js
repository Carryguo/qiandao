//获取链接参数函数
function getUrlParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return unescape(r[2]); return null;
};

//自定义链接获取参数函数
function customGetUrlParam(url,name){
    var reg = new RegExp("(\\?|&)"+ name +"=([^&]*)(&|$)");
    var r = url.substr(1).match(reg);
    if (r!=null) return unescape(r[2]); return null;
};