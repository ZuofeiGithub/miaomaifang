layui.use(['layer','form'],function () {
    var layer = layui.layer,form = layui.form,$ = layui.$;

    // 判断是否为手机号
    function isPoneAvailable(pone) {
        var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
        if (!myreg.test(pone)) {
            return false;
        } else {
            return true;
        }
    }
    // 判断是否为电话号码
    function isTelAvailable (tel) {
        var myreg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
        if (!myreg.test(tel)) {
            return false;
        } else {
            return true;
        }
    }

    $('.submit').click(function (e) {
        var telphone = $('#user_telphone').val();
        if(isPoneAvailable(telphone)){
                layer.open({
                    title: false,
                    type:2,
                    content:['/record','no'],
                    area: ['500px', '300px'],
                    offset: 'auto',
                    scrollbar:false,
                    move: false,
                    closeBtn: 2,
                    fixed: false,

                })
        }else{
            layer.msg("请输入正确的手机号",{icon: 6});
        }
    })
})