<!DOCTYPE html>
<html>
<head>
    <title>layui</title>
    <#import "*/common/common.macro.ftl" as netCommon>
    <#import "*/common/curd.macro.ftl" as curd>
    <@netCommon.commonStyle />
</head>
<body>
<@curd.table true>
    <div class="layui-inline">
        <label class="layui-form-label">名称：</label>
        <div class="layui-input-inline">
            <input type="text" name="name" autocomplete="off" class="layui-input">
        </div>
    </div>
</@curd.table>

<@netCommon.commonScript />
<script>
    cols_def = [[
        {field: 'id',  title: 'ID'},
        {field: 'name',  title: '名称'},
        {field: 'mainPic',  title: '主图'},
        {field: 'lastUpdate',  title: '修改时间', sort: true},
        {title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center"}
    ]];
</script>
<@curd.tableScript "/xmlset"></@curd.tableScript>
</body>
</html>