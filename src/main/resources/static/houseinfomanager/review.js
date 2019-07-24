layui.config({
    base: "/js/"
})
layui.use(['form', 'layedit', 'laydate','upload'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,$ = layui.$
        ,upload = layui.upload
        ,laydate = layui.laydate;

    /**
     * 获取地址栏参数,如果地址栏为空获取当前地址
     * @param url
     * @returns {Object}
     */
    function getParams(url) {
        var theRequest = new Object();
        if (!url)
            url = location.href;
        if (url.indexOf("?") !== -1)
        {
            var str = url.substr(url.indexOf("?") + 1) + "&";
            var strs = str.split("&");
            for (var i = 0; i < strs.length - 1; i++)
            {
                var key = strs[i].substring(0, strs[i].indexOf("="));
                var val = strs[i].substring(strs[i].indexOf("=") + 1);
                theRequest[key] = val;
            }
        }
        return theRequest;
    }
    var params = getParams();
    //获取地址栏上的userName
    var house_id =  params.id;

    console.log(house_id);
    //日期
    laydate.render({
        elem: '#date'
    });
    laydate.render({
        elem: '#date1'
    });

    //创建一个编辑器
    var editIndex = layedit.build('LAY_demo_editor');

    //自定义验证规则
    form.verify({
        title: function(value){
            if(value.length < 5){
                return '标题至少得5个字符啊';
            }
        }
        ,pass: [
            /^[\S]{6,12}$/
            ,'密码必须6到12位，且不能出现空格'
        ]
        ,content: function(value){
            layedit.sync(editIndex);
        }
    });
    //监听指定开关
    form.on('switch(switchTest)', function(data){
        layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
            offset: '6px'
        });
        layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
    });

    //监听提交
    form.on('submit(demo1)', function(data){
        // layer.alert(JSON.stringify(data.field), {
        //     title: '最终的提交信息'
        // })
        data.field.houseid = house_id;
        $.post("/back/approve",data.field,function (resp) {
            
        })
        return false;
    });

    //表单初始赋值
    // form.val('example', {
    //     "username": "贤心" // "name": "value"
    //     ,"password": "123456"
    //     ,"interest": 1
    //     ,"like[write]": true //复选框选中状态
    //     ,"close": true //开关状态
    //     ,"sex": "女"
    //     ,"desc": "我爱 layui"
    // });


    //多图片上传
    // upload.render({
    //     elem: '#test2'
    //     ,url: '/upload/upload_image1'
    //     ,auto:false
    //     ,bindAction:'#uploadimg'
    //     ,multiple: true
    //     ,choose: function(obj){
    //         //将每次选择的文件追加到文件队列
    //         var files = obj.pushFile();
    //         //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
    //         obj.preview(function(index, file, result){
    //             $('#demo2').append('<img width="150" height="150" src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">')
    //             console.log(file.name)
    //         });
    //     }
    //     ,before: function(obj){
    //         //预读本地文件示例，不支持ie8
    //         // obj.preview(function(index, file, result){
    //         //     $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">')
    //         // });
    //     }
    //     ,done: function(res){
    //         //上传完毕
    //     }
    // });
    upload.render({
        elem: '#addZmImg'
        , url: '/upload/upload_image'
        ,auto:false
        ,bindAction:'#uploadimg'
        , multiple: true
        ,choose: function(obj){
            //将每次选择的文件追加到文件队列
            var files = obj.pushFile();
            console.log(files)

            //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
            obj.preview(function(index, file, result){
                // console.log(index); //得到文件索引
                // console.log(file); //得到文件对象
                // console.log(result); //得到文件base64编码，比如图片
                $('#imgZmList').prepend('<li style="position:relative"><img name="imgZmList" src="' + result + '"width="150" height="113"><div class="title_cover"  name="imgZmName" onclick="divClick(this)"></div><div class="img_close" id="del" onclick="deleteElement(this)">X</div></li>');
                form.render();
                imgMove("imgZmList");
                //obj.resetFile(index, file, '123.jpg'); //重命名文件名，layui 2.3.0 开始新增

                //这里还可以做一些 append 文件列表 DOM 的操作
                // $('#del').click(function () {
                //     delete files[index];
                // })
                //obj.upload(index, file); //对上传失败的单个文件重新上传，一般在某个事件中使用
                //delete files[index]; //删除列表中对应的文件，一般在某个事件中使用
            });
        }
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            // obj.preview(function (index, file, result) {
            //     $('#imgZmList').append('<li style="position:relative"><img name="imgZmList" src="' + result + '"width="150" height="113"><div class="title_cover" name="imgZmName" onclick="divClick(this)"></div><div class="img_edit layui-icon" onclick="croppers_pic(this)">&#xe642;</div><div class="img_close" onclick="deleteElement(this)">X</div></li>');
            //     form.render();
            //     imgMove("imgZmList");
            // });
        }
        , done: function (res) {
            console.log(res);
            $.post('/back/saveImg',{houseid:house_id,imgurl:res.data.src},function (resp) {
                    if(resp.code == "0"){
                        layer.msg(resp.msg);
                    }else{
                        layer.msg(resp.msg);
                    }
            })
        }
    });

});
