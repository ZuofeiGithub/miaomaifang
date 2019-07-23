layui.use(['form', 'layedit', 'laydate','upload','jquery'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,upload = layui.upload
        ,laydate = layui.laydate;



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
        layer.alert(JSON.stringify(data.field), {
            title: '最终的提交信息'
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
    upload.render({
        elem: '#test2'
        ,url: '/upload/upload_image1'
        ,auto:false
        ,bindAction:'#uploadimg'
        ,multiple: true
        ,choose: function(obj){
            //将每次选择的文件追加到文件队列
            var files = obj.pushFile();
            //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
            obj.preview(function(index, file, result){
                $('#demo2').append('<img width="150" height="150" src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">')
                console.log(file.name)
            });
        }
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            // obj.preview(function(index, file, result){
            //     $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">')
            // });
        }
        ,done: function(res){
            //上传完毕
        }
    });


});
