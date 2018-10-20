(function ($) {
    $.extend({
        table: {
            _option: {},
            _params: {},
            init: function (options) {
                $.table._option = options;
                $.table._params = $.common.isEmpty(options.queryParams) ? $.table.queryParams : options.queryParams;
                _sortOrder = $.common.isEmpty(options.sortOrder) ? "asc" : options.sortOrder;
                _sortName = $.common.isEmpty(options.sortName) ? "" : options.sortName;
                $("#bootstrap-table").bootstrapTable({
                    url: options.url,
                    contentType: "application/x-www-form-urlencoded",
                    method: "post",
                    cache: false,
                    sortable: false,
                    sortName: _sortName,
                    sortOrder: _sortOrder,
                    sortStable: false,
                    pagination: true,
                    searchOnEnterKey: true,//回车触发搜索
                    trimOnSearch: true,//自动去除搜索内容前后空格
                    clickToSelect: false,                //是否启用点击选中行
                    pageNumber: 1,
                    pageSize: 8,
                    pageList: [8, 25, 50],
                    iconSize: "outline",
                    toolbar: "#toolbar",
                    sidePagination: "server", //服务端分页
                    search: $.common.visible(options.search), //是否显示表格搜索
                    showRefresh: $.common.visible(options.showRefresh), //是否显示刷新按钮
                    showColumns: $.common.visible(options.showColumns),
                    showToggle: $.common.visible(options.showToggle),
                    showExport: $.common.visible(options.showExport),
                    columns: options.columns,
                    queryParams: $.table._params,        //得到查询的参数
                })
            }, queryParams: function (params) {
                return {
                    pageSize: params.limit,
                    pageNum: params.offset / params.limit + 1,
                    searchValue: params.search,
                    orderByColumn: params.sort,
                    isAsc: params.order
                }
                //    表单搜索
            }, search: function (form) {

                var params = $("#bootstrap-table").bootstrapTable("getOptions");
                params.queryParams = function (params) {
                    var search = {};
                    $.each($("#" + form).serializeArray(), function (i, field) {
                        search[field.name] = field.value
                    });
                    search.pageSize = params.limit;
                    search.pageNum = params.offset / params.limit + 1;
                    search.searchValue = params.search;
                    search.orderByColumn = params.sort;
                    search.isAsc = params.order;
                    return search;
                };
                $("#bootstrap-table").bootstrapTable("refresh", params);
                //点击树节点
            }, clickTree: function (file, val) {
                var params = $("#bootstrap-table").bootstrapTable("getOptions");
                params.queryParams = function (params) {
                    var search = {};
                    search[file] = val;
                    search.pageSize = params.limit;
                    search.pageNum = params.offset / params.limit + 1;
                    search.searchValue = params.search;
                    search.orderByColumn = params.sort;
                    search.isAsc = params.order;
                    return search
                };
                $("#bootstrap-table").bootstrapTable("refresh", params);

            }, exportExcel: function (form) {
                $.post($.table._option.exportUrl, $("#" + form).serializeArray(), function (result) {
                    if (result.code == web_status.SUCCESS) {
                        window.location.href = ctx + "common/download?fileName=" + result.msg + "&delete=" + true
                    } else {
                        $.modal.alertError(result.msg)
                    }
                })

                //    刷新按钮
            }, refresh: function () {
                $("#bootstrap-table").bootstrapTable("refresh", {url: $.table._option.url, silent: true})
            }, selectColumns: function (column) {
                return $.map($("#bootstrap-table").bootstrapTable("getSelections"), function (row) {
                    return row[column]
                })
            }, selectFirstColumns: function () {
                return $.map($("#bootstrap-table").bootstrapTable("getSelections"), function (row) {
                    return row[$.table._option.columns[1].field]
                })
            }
        }, treeTable: {
            init: function (options) {
                $.table._option = options;
                var treeTable = $('#bootstrap-table').bootstrapTreeTable({
                    code: options.id,             // 用于设置父子关系
                    parentCode: options.parentId, // 用于设置父子关系
                    type: 'get',                   // 请求方式（*）
                    url: options.url,              // 请求后台的URL（*）
                    ajaxParams: {},               // 请求数据的ajax的data属性
                    expandColumn: '0',            // 在哪一列上面显示展开按钮
                    striped: false,               // 是否各行渐变色
                    bordered: true,               // 是否显示边框
                    expandAll: $.common.visible(options.expandAll), // 是否全部展开
                    columns: options.columns
                });
                $._treeTable = treeTable;
            },
            // 条件查询
            search: function (formId) {
                var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                var params = {};
                $.each($("#" + currentId).serializeArray(), function (i, field) {
                    params[field.name] = field.value;
                });
                $._treeTable.bootstrapTreeTable('refresh', params);
            },
            // 刷新
            refresh: function () {
                $._treeTable.bootstrapTreeTable('refresh');
            },
            remove: function (id) {
                $.modal.confirm("确定删除该条" + $.table._option.modalName + "信息吗？", function () {
                    var url = $.common.isEmpty(id) ? $.table._option.removeUrl : $.table._option.removeUrl.replace("{id}", id);
                    var data = {"ids": id};

                    function submit(url, type, dataType, data) {
                        $.modal.loading("正在处理中，请稍后...");
                        var config = {
                            //traditional:true 开启数组传输
                            url: url,
                            type: type,
                            dataType: dataType,
                            data: data,
                            traditional: true,
                            success: function (result) {
                                if (result.code == web_status.SUCCESS) {
                                    $.modal.msgSuccess(result.msg);
                                    $.treeTable.refresh();
                                } else {
                                    $.modal.alertError(result.msg)
                                }
                                $.modal.closeLoading();
                            }
                        };
                        $.ajax(config)
                    }

                    submit(url, "post", "json", data);
                })
            }
        }, form: {
            // 表单重置
            reset: function (formId) {
                var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                $("#" + currentId)[0].reset();
            },
            selectCheckeds: function (name) {
                var checkeds = "";
                $('input:checkbox[name="' + name + '"]:checked').each(function (i) {
                    if (0 == i) {
                        checkeds = $(this).val()
                    } else {
                        checkeds += ("," + $(this).val())
                    }
                });
                return checkeds
            }, selectSelects: function (name) {
                var selects = "";
                selects = $("#" + name + " option:selected").val();
                return selects
            },
            selectRadioCheckeds: function (name) {
                var radios = "";
                $('input:radio[name="' + name + '"]:checked').each(function (i) {
                    if (0 == i) {
                        radios = $(this).val()
                    } else {
                        radios += ("," + $(this).val())
                    }
                });
                return radios;

            }
        }, modal: {
            icon: function (type) {
                var icon = "";
                if (type == modal_status.WARNING) {
                    icon = 0
                } else {
                    if (type == modal_status.SUCCESS) {
                        icon = 1
                    } else {
                        if (type == modal_status.FAIL) {
                            icon = 2
                        } else {
                            icon = 3
                        }
                    }
                }
                return icon
            }, msg: function (content, type) {
                if (type != undefined) {
                    layer.msg(content, {icon: $.modal.icon(type), time: 1000, shift: 5, shade: 0.1})
                } else {
                    layer.msg(content)
                }
            },
            loading: function (message) {
                $.blockUI({message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + "</div>"})
            }, msgError: function (content) {
                $.modal.msg(content, modal_status.FAIL)
            }, msgSuccess: function (content) {
                $.modal.msg(content, modal_status.SUCCESS)
            }, msgWarning: function (content) {
                $.modal.msg(content, modal_status.WARNING)
            }, alert: function (content, type) {
                layer.alert(content, {
                    icon: $.modal.icon(type),
                    title: "系统提示",
                    btn: ["确认"],
                    btnclass: ["btn btn-primary"],
                })
            }, msgReload: function (msg, type) {
                layer.msg(msg, {icon: $.modal.icon(type), time: 500, shade: [0.1, "#8F8F8F"]}, function () {
                    $.modal.reload()
                })
            }, alertError: function (content) {
                $.modal.alert(content, modal_status.FAIL)
            }, alertSuccess: function (content) {
                $.modal.alert(content, modal_status.SUCCESS)
            }, alertWarning: function (content) {
                $.modal.alert(content, modal_status.WARNING)
            }, close: function () {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index)
            }, confirm: function (content, callBack) {
                layer.confirm(content, {
                    icon: 3,
                    title: "系统提示",
                    btn: ["确认", "取消"],
                    btnclass: ["btn btn-primary", "btn btn-danger"],
                }, function (index) {
                    layer.close(index);
                    callBack(true)
                })
            }, open: function (title, url, width, height) {
                if ($.common.isEmpty(title)) {
                    title = false
                }
                if ($.common.isEmpty(url)) {
                    url = "404.html"
                }
                if ($.common.isEmpty(width)) {
                    width = 800
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 50)
                }
                layer.open({
                    type: 2,
                    area: [width + "px", height + "px"],
                    fix: false,
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: url
                })
            }, openFull: function (title, url, width, height) {
                if ($.common.isEmpty(title)) {
                    title = false
                }
                if ($.common.isEmpty(url)) {
                    url = "404.html"
                }
                if ($.common.isEmpty(width)) {
                    width = 800
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 50)
                }
                var index = layer.open({
                    type: 2,
                    area: [width + "px", height + "px"],
                    fix: false,
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: url
                });
                layer.full(index)
            }, loginloading: function (message) {
                $.blockUI({message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + "</div>"})
            }
            , closeLoading: function () {
                setTimeout(function () {
                    $.unblockUI()
                }, 50)
            }, reload: function () {
                parent.location.reload()
            }, openDiv: function (width, height, title, nodeId) {
                var index = layer.open({
                    type: 1,
                    area: [width + "px", height + "px"],
                    fix: false,
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: $("#" + nodeId)
                });
            }
        }, operate: {
            submit: function (url, type, dataType, data) {
                $.modal.loading("正在处理中，请稍后...");
                var config = {
                    //traditional:true 开启数组传输
                    url: url,
                    type: type,
                    dataType: dataType,
                    data: data,
                    traditional: true,
                    success: function (result) {
                        $.operate.ajaxSuccess(result)
                    }
                };
                $.ajax(config)
            }, post: function (url, data) {
                $.operate.submit(url, "post", "json", data)
            }, remove: function (id) {
                $.modal.confirm("确定删除该条" + $.table._option.modalName + "信息吗？", function () {
                    var url = $.common.isEmpty(id) ? $.table._option.removeUrl : $.table._option.removeUrl.replace("{id}", id);
                    var data = {"ids": id};
                    $.operate.submit(url, "post", "json", data)
                })
            }, batRemove: function () {
                var rows = $.common.isEmpty($.table._option.id) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.id);
                if (rows.length == 0) {
                    $.modal.alertWarning("请至少选择一条记录");
                    return
                }
                $.modal.confirm("确认要删除选中的" + rows.length + "条数据吗?", function () {
                    var url = $.table._option.removeUrl;
                    var data = {"ids": rows};

                    $.operate.submit(url, "post", "json", data)
                })
            }, add: function (id) {
                var url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                $.modal.open("添加" + $.table._option.modalName, url)
            }, FlowCharPng: function (id) {
                var url = $.common.isEmpty(id) ? $.table._option.pictureUrl : $.table._option.pictureUrl.replace("{id}", id);
                $.modal.open("查看流程图" + $.table._option.modalName, url)
            }, edit: function (id) {
                var url = $.table._option.updateUrl.replace("{id}", id);
                $.modal.open("修改" + $.table._option.modalName, url)
            }, addFull: function (id) {
                var url = $.common.isEmpty(id) ? $.table._option.createUrl : $.table._option.createUrl.replace("{id}", id);
                $.modal.openFull("添加" + $.table._option.modalName, url)
            }, editFull: function (id) {
                var url = $.table._option.updateUrl.replace("{id}", id);
                $.modal.openFull("修改" + $.table._option.modalName, url)
            }, save: function (url, data) {
                $.modal.loading("正在处理中，请稍后...");
                var config = {
                    url: url,
                    type: "post",
                    dataType: "json",
                    data: data,
                    traditional: true,
                    success: function (result) {
                        $.operate.saveSuccess(result)
                    }
                };
                $.ajax(config)
            }, ajaxSuccess: function (result) {
                if (result.code == web_status.SUCCESS) {
                    $.modal.msgSuccess(result.msg);
                    $.table.refresh()
                } else {
                    $.modal.alertError(result.msg)
                }
                $.modal.closeLoading()
            }, saveSuccess: function (result) {
                if (result.code == web_status.SUCCESS) {
                    $.modal.msgReload("保存成功,正在刷新数据请稍后……", modal_status.SUCCESS)
                } else {
                    $.modal.alertError(result.msg)
                }
                $.modal.closeLoading()
            }


            //    公共
        }, common: {
            isEmpty: function (value) {
                if (value == null || this.trim(value) == "") {
                    return true
                }
                return false
            }, visible: function (value) {
                if ($.common.isEmpty(value) || value == true) {
                    return true
                }
                return false
            }, trim: function (value) {
                if (value == null) {
                    return ""
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "")
            }, random: function (min, max) {
                return Math.floor((Math.random() * max) + min)

            }, dateFormat: function (value) {
                if (value == null) {
                    return null
                }
                var date = new Date(value);//如果date为13位不需要乘1000
                var Y = date.getFullYear() + '-';
                var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
                var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
                var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
                var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
                return Y + M + D + h + m + s;
            }, dateFormatGetTimeSort: function (value) {
                if (value == null) {
                    return null
                }
                var date = new Date(value);//如果date为13位不需要乘1000
                var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
                var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
                var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
                return h + m + s;
            }, dateFormatToDate: function (value) {
                if (value == null) {
                    return null
                }
                var date = new Date(value);//如果date为13位不需要乘1000
                var Y = date.getFullYear() + '-';
                var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
                return Y + M + D;
            }
        }
    })
})(jQuery);
web_status = {SUCCESS: 0, FAIL: 500};
modal_status = {SUCCESS: "success", FAIL: "error", WARNING: "warning"};

//搜索栏的日期时间
layui.use('laydate', function () {
    var laydate = layui.laydate;
    //时间选择器
    laydate.render({
        elem: '#beginTime'
        , type: 'date'
    });
    laydate.render({
        elem: '#overTime'
        , type: 'date'
        //,format:"yyyy-MM-dd HH:mm:ss"
    });
})