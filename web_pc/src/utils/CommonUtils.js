import Vue from "vue";
import ElementUI from "element-ui";
Vue.use(ElementUI);
export default {
    dateFormatToStr: function (date) {
        if (date == null) {
            return null
        }
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
        var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        return Y + M + D + h + m + s;
    },
    timestampToStr: function (value) {
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
    },
    time() {
        var today = new Date();
        var hour;
        var time;
        var day;
        if (today.getHours() < 12 && today.getHours() >= 0) {
            hour = today.getHours();
            time = "AM";
        } else if (today.getHours() <= 24 && today.getHours() > 12) {
            hour = today.getHours();
        }
        var d = today.getDay();
        if (d == 0) {
            day = "日";
        } else if (d == 1) {
            day = "一";
        } else if (d == 2) {
            day = "二";
        } else if (d == 3) {
            day = "三";
        } else if (d == 4) {
            day = "四";
        } else if (d == 5) {
            day = "五";
        } else if (d == 6) {
            day = "六";
        }
        //document.getElementById(documentId).innerHTML = (today.getFullYear() + "年" + (today.getMonth() + 1) + "月" + today.getDate() + "日 " + hou + ": " + today.getMinutes() + ": " + today.getSeconds() + " " + time + " 星期" + day);
        return (
            today.getFullYear() +
            "年" +
            (today.getMonth() + 1) +
            "月" +
            today.getDate() +
            "日  " +
            hour +
            "时 " +
            today.getMinutes() +
            "分 " +
            today.getSeconds() +
            "秒  " +
            " 星期" +
            day
        );
        //var myTime = setInterval("time() ", 1000);
    }
}