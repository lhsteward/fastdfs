$(function () {
    layui.use(['form','layer','upload','element'],function(){
        var form = layui.form,
            layer = layui.layer,
            upload = layui.upload,
            element = layui.element;

        var getPath = getRootPath();
        function getRootPath() {
            var local = window.location;
            return local.protocol + "//" + local.host + "/";
        };

        upload.render({
            elem: '#content'
            , url: getPath+'file/uploadFile'
            , auto: false
            , size: 300
            , accept: 'images' //图片
            , bindAction: '#upload'
            , choose: function (obj) {
                //读取本地文件
                obj.preview(function (index, file, result) {
                    $("#content #imgList").siblings().hide();
                    $("#imgList").html('<img src="' + result + '" style="width: 100%;height: 100%;cursor: pointer;" alt="点击更换图片">');
                });
            }
            ,done: function(res, index, upload){
                layer.msg("上传成功!",{icon:1,time:500},function(){
                    console.log(res);
                })
            }
        });

    })
})