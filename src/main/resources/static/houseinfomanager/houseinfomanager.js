//使用指定的模块
layui.use(['layer', 'table'], function () {
    var layer = layui.layer, table = layui.table, $ = layui.$;

    //渲染数据表格
    table.render({
        elem: '#house_manager_id',
        url: '/back/get_house_info_list',
        cols: [[
            {
                field: 'id', title: '序号', width: 80, sort: true, fixed: 'left', align: 'center', templet: function (d) {
                    return d.LAY_INDEX;
                }
            }, {field: 'houseAddress', title: '小区'},
            {field: 'houseArea', title: '房屋面积'},
            {field: 'housePrice', title: '期望售价'},
            {
                field: 'assessor', title: '是否审核', align: 'center', width: 100, templet: function (d) {
                    if (d.assessor == 2) {
                        return "<a class='layui-btn-primary layui-btn-sm'>待审</a>"
                    } else if(d.assessor == 1) {
                        return "<a class='layui-btn-normal layui-btn-sm'>已审</a>"
                    } else if(d.assessor == 3){
                        return "<a class='layui-btn-warm layui-btn-sm'>被拒</a>"
                    }
                }
            },
            {
                field:'SaleStop',title:'是否停售',align:'center',width:100,templet:function (d) {
                    console.log(d.SaleStop);
                }
            },
            {fixed: 'right', title: '操作', align: "center", toolbar: '#houseInfo_tool_bar', width: 150}
        ]],
        page:true
        ,text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
        ,limit:10
        ,limits:[10,20,30,40,50,60,70,80,90]
    });

    //监听工具条
    table.on('tool(house_manager_filter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        console.log(data)
        if (layEvent === 'assessor') { //审核
            layer.open({
                title:'审核房源信息',
                type: 2,
                content: '/review?id='+data.id,
                area: screen() < 2 ? ['90%', '80%'] : ['1280px', '720px'],
            });

        } else if (layEvent === 'sold_out') { //下架
            //$.post()
        }
    });

    //判断浏览器大小方法
    function screen() {
        //获取当前窗口的宽度
        var width = $(window).width();
        if (width > 1200) {
            return 3;   //大屏幕
        } else if (width > 992) {
            return 2;   //中屏幕
        } else if (width > 768) {
            return 1;   //小屏幕
        } else {
            return 0;   //超小屏幕
        }
    }

});