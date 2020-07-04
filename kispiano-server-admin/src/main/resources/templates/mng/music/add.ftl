<!DOCTYPE html>
<html>
<head>
    <title>layui</title>
    <#import "*/common/common.macro.ftl" as lib>
    <#import "*/common/curd.macro.ftl" as curd>
    <@lib.commonStyle />
</head>
<body>
<div class="layui-form layuimini-form">
    <form id="form" onsubmit="return false;">
        <div class="layui-form-item">
            <label class="layui-form-label required">名称</label>
            <div class="layui-input-block">
                <input type="text" name="name" lay-verify="required" lay-reqtext="名称不能为空" placeholder="请输入名称" value="" class="layui-input" autocomplete="off">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">主图</label>
            <div class="layui-input-block">
                <input type="file" name="mainPicFile" placeholder="主图" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">主文件类型</label>
            <div class="layui-input-block">
                <input type="checkbox" checked="" name="xmlType" value="true" lay-skin="switch" lay-filter="switchTest" lay-text="单文件|zip压缩包">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">主文件</label>
            <div class="layui-input-block">
                <input type="file" name="xmlFile" placeholder="主文件" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">描述信息</label>
            <div class="layui-input-block">
                <textarea name="descText" class="layui-textarea" placeholder="请输入备注信息"></textarea>
            </div>
        </div>
    </form>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="saveBtn">确认保存</button>
        </div>
    </div>
</div>
<@curd.scriptAdd />
</body>