<!DOCTYPE html>
<html>
<head>
    <title>layui</title>
    <#import "*/common/common.macro.ftl" as netCommon>
    <#import "*/common/curd.macro.ftl" as curd>
    <@netCommon.commonStyle />
</head>
<body>
<#if !model.isPresent()>
    <p>数据未找到！</p>
</#if>
<#if model.isPresent()>
    <div class="layui-form layuimini-form">
        <div class="layui-form-item">
            <label class="layui-form-label required">名称</label>
            <div class="layui-input-block">
                <input type="text" name="name" lay-verify="required" lay-reqtext="名称不能为空" placeholder="请输入名称" value="${model.get().name!}" class="layui-input" autocomplete="off">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">主图</label>
            <div class="layui-input-block">
                <input type="text" value="${model.get().mainPic!}" class="layui-input" readonly>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">描述信息</label>
            <div class="layui-input-block">
                <textarea name="descText" class="layui-textarea" placeholder="请输入备注信息">${model.get().descText!}</textarea>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-normal" lay-submit lay-filter="saveBtn">确认保存</button>
            </div>
        </div>
    </div>
</#if>
<@curd.scriptEdit></@curd.scriptEdit>
</body>