<#import "*/common/common.macro.ftl" as netCommon>

<#macro table showNested>
    <div class="layuimini-container">
        <div class="layuimini-main">

            <#if showNested>
                <fieldset class="table-search-fieldset">
                    <legend>搜索信息</legend>
                    <div style="margin: 10px 10px 10px 10px">
                        <form class="layui-form layui-form-pane" action="">
                            <div class="layui-form-item">
                                <#nested>
                                <div class="layui-inline">
                                    <button type="submit" class="layui-btn layui-btn-primary"  lay-submit lay-filter="data-search-btn"><i class="layui-icon"></i> 搜 索</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </fieldset>
            </#if>

            <script type="text/html" id="toolbarDemo">
                <div class="layui-btn-container">
                    <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-event="add"> 添加 </button>
                    <button class="layui-btn layui-btn-sm layui-btn-danger data-delete-btn" lay-event="delete"> 删除 </button>
                </div>
            </script>

            <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>

            <script type="text/html" id="currentTableBar">
                <a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete">删除</a>
            </script>

        </div>
    </div>
</#macro>

<#macro tableScript baseUrl>
    <@netCommon.commonScript />
    <script>
        layui.use(['form', 'table'], function () {
            var $ = layui.jquery, form = layui.form, table = layui.table;

            table.render({
                elem: '#currentTableId', url: APP_BASE + '${baseUrl}/list', toolbar: '#toolbarDemo',
                limits: [10, 15, 20, 25, 50, 100], limit: 15, page: true, limitName: 'size', skin: 'line',
                cols: cols_def,
            });

            // 监听搜索操作
            form.on('submit(data-search-btn)', function (data) {
                var result = JSON.stringify(data.field);
                //执行搜索重载
                table.reload('currentTableId', {page: {curr: 1}, where: {searchParams: result}}, 'data');
                return false;
            });

            /** toolbar监听事件*/
            table.on('toolbar(currentTableFilter)', function (obj) {
                if (obj.event === 'add') {  // 监听添加操作
                    var index = layer.open({
                        title: '添加记录', type: 2, shade: 0.2, maxmin:true, shadeClose: true, area: ['100%', '100%'],
                        content: APP_BASE + '${baseUrl}/add',
                    });
                    $(window).on("resize", function () {
                        layer.full(index);
                    });
                } else if (obj.event === 'delete') {  // 监听删除操作
                    var checkStatus = table.checkStatus('currentTableId')
                        , data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                }
            });

            table.on('tool(currentTableFilter)', function (obj) {
                var data = obj.data;
                if (obj.event === 'edit') {

                    var index = layer.open({
                        title: '编辑用户', type: 2, shade: 0.2, maxmin:true, shadeClose: true, area: ['100%', '100%'],
                        content: APP_BASE + '${baseUrl}/edit?id=' + data.id,
                    });
                    $(window).on("resize", function () {
                        layer.full(index);
                    });
                    return false;
                } else if (obj.event === 'delete') {
                    layer.confirm('确认删除？', function (index) {
                        layer.close(index);
                        $.post(APP_BASE + "${baseUrl}/delete", {id : obj.data.id}, function (rv) {
                            if (rv && rv.stat){
                                var iframeIndex = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(iframeIndex);
                                table.reload()
                            }else {
                                layer.alert(data.msg);
                            }
                        }).error(function () {
                            layer.alert("连接服务器失败！")
                        })
                    });
                }
            });

        });
    </script>
</#macro>

<#macro scriptAdd>
    <@netCommon.commonScript />
    <script>
        layui.use(['form'], function () {
            var form = layui.form, layer = layui.layer, $ = layui.$;

            //监听提交
            form.on('submit(saveBtn)', function (data) {
                $.ajax({
                    url: location.href, type: 'POST', cache: false,
                    data: new FormData(document.getElementById('form')),
                    processData: false, contentType: false,
                }).done(function(rv) {
                    if (rv && rv.stat){
                        var iframeIndex = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(iframeIndex);
                    }else {
                        layer.alert(data.msg);
                    }
                }).fail(function(res) {
                    layer.alert("连接服务器失败！")
                });
                return false;
            });

        });
    </script>
</#macro>
<#macro scriptEdit>
    <@netCommon.commonScript />
    <script>
        layui.use(['form'], function () {
            var form = layui.form, layer = layui.layer, $ = layui.$;
            //监听提交
            form.on('submit(saveBtn)', function (data) {
                $.post(location.href, data.field, function (rv) {
                    if (rv && rv.stat){
                        var iframeIndex = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(iframeIndex);
                    }else {
                        layer.alert(data.msg);
                    }
                }).error(function () {
                    layer.alert("连接服务器失败！")
                })
                return false;
            });
        });
    </script>
</#macro>