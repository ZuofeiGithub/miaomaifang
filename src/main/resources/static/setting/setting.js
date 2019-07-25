layui.config({
    base: '/mods/'
    , version: '1.0'
});
layui.use(['form','layarea'], function(){
    var form = layui.form,
        layarea = layui.layarea;
    layarea.render({
        elem: '#area-picker',
        change: function (res) {
            //选择结果
            console.log(res.city);
        }
    });

    //监听提交
    form.on('submit(setting)', function(data){
        // layer.confirm(JSON.stringify(data.field), {
        //     title: '最终的提交信息'
        // })
        layer.confirm('确定开通'+data.field.city+"?", {icon: 3, title:'提示'}, function(index){
            $.post('/back/opencity',{cityname:data.field.city},function (resp) {
                if(resp.code == 0){
                    layer.msg("开通成功");
                }
            });

        });
        return false;
    });
});