<template>
  <main-frame :location="location">
    <template slot="mainFrame">
      <div class="main-frame-content">
      <!-- 搜索组建 -->
      <div class="search" v-show="searchShow">
        <el-form :inline="true">
          <component ref="searchCom" v-bind:is="searchModel"></component>
          <el-form-item>
            <el-button @click="search()" icon="el-icon-search" type="primary" size="mini">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              @click="resetFormAndTale()"
              icon="el-icon-refresh"
              type="default"
              size="mini"
            >刷新</el-button>
          </el-form-item>
        </el-form>
        <!-- </el-card> -->
      </div>

      <!-- 操作栏 -->
      <div class="top_operate" v-show="operateShow">
        <slot :slot-scope="resParam" name="top_operate"></slot>
      </div>

      <div class="list_frame">
        <!-- <el-card style="height:auto"> -->
        <el-table
          :header-cell-style="headerCellStyle"
          :header-row-style="headerRowCss"
          :data="dataList"
          v-loading="tableLoading"
          body-style="height:100%;"
          size="mini"
          border=""
          @select="select"
          @select-all="selectAll"
          @selection-change="selectionChange"
        >
          <el-table-column type="selection" align="left" width="50"></el-table-column>
          <slot name="columns"></slot>
          <slot name="operate"></slot>
        </el-table>

        <div class="pageNation">
          <el-row>
            <el-col :span="2">
              <!-- <auth role="admin"> -->
              <!-- <template slot="auth"> -->
              <div v-if="showDelBtn" style="margin-top:10px">
                <el-button type="danger" size="small" :disabled="delBtnFlag" @click="del()">删除已选</el-button>
              </div>
              <!-- </template> -->
              <!-- </auth> -->
            </el-col>
            <el-col v-if="tempIsPage" :span="22">
              <div style="margin-top:10px;text-align:right">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="currentPage"
                  :page-sizes="[15, 30, 50, 100]"
                  :page-size="pageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="total"
                ></el-pagination>
              </div>
            </el-col>
          </el-row>
        </div>
        <!-- </el-card> -->
      </div>
      </div>
    </template>
  </main-frame>
</template>
<script>
let _this = {};
import util from "@/utils/CommonUtils.js";
export default {
  data() {
    return {
      delBtnFlag: true,
      dataList: [],
      ids: [],
      allList: [],
      total: 0, // 列表内所有数据的长度
      currentPage: 1, // 初始页 0 jpa是0
      index: 0, // jpa请求分页参数
      pageSize: 15, // 当前页面内的列表行数
      tableLoading: true,
      checkRowList: [], // 当前选中的数据
      tempIsPage: true,
      resParam: {
        rows: [],
        searchParam: {}
      },
      initSearchParam: {} // 初始化的搜索条件
    };
  },
  // 数据列表，位置，是否显示搜索模块，搜索组建，刷新列表（修改或者删除时候） 是否显示 表格上面的操作栏 ,是否显示删除按钮
  props1: [
    "getDataUrl",
    "removeUrl",
    "location",
    "searchShow",
    "searchModel",
    "reloadTable",
    "operateShow",
    "showDelBtn",
    "isPage"
  ],
  props: {
    getDataUrl: {  // 必须提供字段
      required: true,
      type: String,
    },
    removeUrl: {   // 可选字段，有默认值
      required: false,
      type: String,
    },
    location: {   // 可选字段，有默认值
      required: false,
      type: String,
    },
    searchShow: {   // 可选字段，有默认值
      required: false,
      type: Boolean,
      default:false,
    },
    searchModel: {   // 可选字段，有默认值
      required: false,
      type: Object,
      default: false,
    },
    reloadTable: {   // 可选字段，有默认值
      required: true,
      type: Boolean,
      default:false,
    },
    operateShow: {   // 可选字段，有默认值
      required: false,
      type: Boolean,
      default:true,
    },
    showDelBtn: {   // 可选字段，有默认值
      required: true,
      type: Boolean,
      default:false,
    },
    isPage: {   // 可选字段，有默认值
      required: false,
      type: Boolean,
      default:true,
    },
  },

  mounted() {
    this.initData(this.searchParam());
  },
  watch: {
    isPage: {
      handler(newVal, oldVal) {
        console.log(newVal);
        console.log(oldVal);
      }
    },
    reloadTable: {
      handler(newVal, oldVal) {
        if (newVal) {
          _this.initData({});
        }
      },
      immediate: true, //关键
      deep: true
    }
  },
  created() {
    _this = this;
    this.isStartPage();
    // _this.setTableHeight();
  },
  methods: {
    isStartPage() {
      if (this.isPage == undefined) {
        this.tempIsPage = true;
        this.isPage = true;
      }
    },
    // 重置搜索条件，刷新表格
    resetFormAndTale() {
      _this.resParam.searchParam = {};
       _this.$refs.searchCom.param={}
      _this.initData({});
    },
    select(selection, row) {},
    // 全选
    selectAll(selection) {
      //console.log(selection);
    },
    // 改变复选框状态触发
    selectionChange(selection) {
      _this.resParam.rows = [];
      this.ids = [];
      selection.forEach(element => {
        _this.resParam.rows.push(element);
        this.ids.push(element.id);
      });
      if (selection.length > 0) {
        this.delBtnFlag = false;
      } else {
        this.delBtnFlag = true;
      }
      //console.log(_this.checkRowList);
    },

    // 删除操作
    del() {
      if (
        this.removeUrl == undefined ||
        this.removeUrl == null ||
        this.removeUrl == ""
      ) {
        this.$warning("请配置删除数据地址");
        return;
      }
      this.$confirm("此操作将永久删除已选数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.$del(this.removeUrl.replace("?", this.ids.join(",")), {}).then(
          res => {
            if (res.R) {
              this.$success("已删除");
              this.initData({});
            }
          }
        );
      });
    },
    setPageParams(param) {
      if (this.tempIsPage) {
        param["pageNum"] = this.currentPage > 0 ? this.currentPage : 1;
        param["pageSize"] = this.pageSize;
      }
    },
    // 获取表格数据
    initData(param) {
      if (param == null || param == undefined) {
        param = {};
      }
      this.loading(true);
      let dataUrl = this.getDataUrl;
      this.setPageParams(param);
      this.fileterParams(param);
      this.$get(dataUrl, param).then(res => {
        _this.reqSuccess(res);
      });
    },

    fileterParams(param) {
      for (let key in param) {
        let val = param[key];
        if (val == undefined || val == null || val == "") {
          delete param[key];
        }
      }
    },

    reqSuccess(res) {
      if(this.isPage==true){
        _this.dataList = res.data.rows;
        _this.setPage(res);
      }else{
        _this.dataList = res.data;
      }
      _this.loading(false);
    },
    // 设置分页参数
    setPage(res) {
      if (res.code == 1) {
        this.pageSize = res.data.pageSize;
        this.total = res.data.total;
        this.currentPage = res.data.pageNum;
      }
    },
    // 关闭加载
    loading(flag) {
      _this.tableLoading = flag;
    },
    // 获取搜索餐素
    searchParam() {
      let param = _this.$refs.searchCom.params;
      let obj = {};
      for (let key in param) {
        let val = param[key];

        this.initSearchParam[key] = val;

        if (val instanceof Array) {
          console.log("数组转换字符串-->" + key);
          obj[key] = val.join(",");
        } else if (val instanceof Date) {
          obj[key] = util.dateFormatToStr(val);
        } else {
          obj[key] = val;
        }
      }
      return obj;
    },

    // 搜索按钮
    search() {
      _this.tableLoading = true;
      // 搜索参数
      // alert(JSON.stringify(_this.$refs.searchCom.params));
      this.resParam.searchParam = this.searchParam();
      this.initData(this.searchParam());
    },
    headerRowCss(rowIndex) {},
    headerCellStyle({ row, column, rowIndex, columnIndex }) {
      let color = "#555E6F";
      let bg = "#F8F8F9";
      let borderTopRightRadius = "0px";
      let s = {
        background: bg,
        color: color,
        borderTopRightRadius: borderTopRightRadius
      };
      if (rowIndex == 0 && columnIndex == 0) {
        return s;
      } else if (rowIndex == 0 && columnIndex == _this.colunmsCount - 1) {
        return s;
      } else if (rowIndex == 0) {
        return {
          background: bg,
          color: color
        };
      }
    }, // 初始页page、初始每页数据数pagesize和数据data
    // 更换每页列内不同的行数：更新列表数据
    handleSizeChange: function(pageSize) {
      this.pageSize = pageSize;
      this.currentPage = 1;
      _this.initData({});
    },
    // 换页：更新列表数据
    handleCurrentChange: function(currentPage) {
      this.currentPage = currentPage;
      _this.initData({});
    }
  }
};
</script>
<style scoped>
.main-frame-content{
  background: white;
  padding: 10px 20px 20px 20px;
}
.pageNation {
  margin: 25px 0;
}
.el-card {
  padding: 0;
}
.search {
  text-align: left;
  border-radius: 5px;
  padding-top: 20px;
  width: 100%;
  margin-bottom: 10px;
}
.search .el-form,.search div{
  display: flex;
  flex-direction: row;
}
.search .el-form--inline .el-form-item{
  height: 100%;
  margin-bottom: 10px;
}
.search .el-input{
  margin:0px !important;
}
.top_operate {
  text-align: left;
  width: 100%;
  margin-bottom: 10px;
}

.list_frame .el-table td,
.list_frame .el-table th {
  text-align: center;
  color: #555e6f;
}
.search div{
  display: inline-block;
}
</style>
