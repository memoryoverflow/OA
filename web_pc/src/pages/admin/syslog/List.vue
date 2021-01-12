<template>
  <div>
    <table-frame
      ref="frame"
      :getDataUrl="prop.getDataUrl"
      :removeUrl="prop.removeUrl"
      :searchShow="prop.searchShow"
      :operateShow="prop.operateShow"
      :location="prop.location"
      :searchModel="prop.searchModel"
      :reloadTable="prop.reloadTable"
      :colunmsCount="prop.colunmsCount"
      :showDelBtn="prop.showDelBtn"
    >
      <template slot="top_operate" slot-scope="currentRow">
        <el-button type="primary" icon="el-icon-plus" @click="add()" size="mini">添加</el-button>
        <el-button
          type="success"
          icon="el-icon-edit"
          @click="editTop(currentRow.slotScope)"
          size="mini"
        >修改
        </el-button>
      </template>

      <template slot="columns">
        <el-table-column width="150" prop="descr" align="center" label="操作功能">
          <template slot-scope="scope">
            <el-popover placement="top-start" trigger="hover">
              <span v-html="scope.row.descr"></span>
              <span slot="reference">
                {{
                  scope.row.descr.length > 7
                    ? scope.row.descr.substring(0, 7) + "..."
                    : scope.row.descr
                }}
              </span>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column prop="operUser" align="center" label="操作用户"/>
        <el-table-column prop="host" width="150" align="center" label="主机"/>
        <el-table-column prop="url" width="200" align="center" label="URL"/>

        <el-table-column prop="params" width="150" align="center" label="参数">
          <template slot-scope="scope">
            <el-popover placement="top-start" trigger="hover">
              <span v-html="scope.row.params"></span>
              <span slot="reference">
                {{
                  scope.row.params.length > 7
                    ? scope.row.params.substring(0, 7) + "..."
                    : scope.row.params
                }}
              </span>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column prop="method" width="200" align="center" label="操作方法">
          <template slot-scope="scope">
            <el-popover placement="top-start" trigger="hover">
              <span v-html="scope.row.method"></span>
              <span slot="reference">
                {{
                  scope.row.method.length > 25
                    ? scope.row.method.substring(0, 25) + "..."
                    : scope.row.method
                }}
              </span>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column prop="sys" align="center" label="操作系统"/>
        <el-table-column prop="browser" align="center" label="浏览器"/>


        <el-table-column prop="type" fixed="right" align="center" label="类型">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type=='正常'" type="success">{{ scope.row.type }}</el-tag>
            <el-tag v-else type="danger">{{ scope.row.type }}</el-tag>
          </template>
        </el-table-column>


        <el-table-column width="150" prop="createTime" align="center" label="操作时间"/>

        <el-table-column prop="errorMsg" width="100" align="center" label="异常信息">
          <template slot-scope="scope">
            <el-popover placement="top-start" trigger="hover">
              <span v-html="scope.row.errorMsg"></span>

              <span v-if="scope.row.errorMsg!=null" slot="reference">
                {{
                  scope.row.errorMsg.length > 20
                    ? scope.row.errorMsg.substring(0, 20) + "..."
                    : scope.row.errorMsg
                }}
              </span>
            </el-popover>
          </template>
        </el-table-column>


      </template>


      <template slot="operate">

      </template>
    </table-frame>
  </div>
</template>
<script>
let _this = {};
import searchModel from "./Search.vue";

export default {
  data() {
    return {
      prop: {
        searchShow: true,
        operateShow: false,
        location: " ",
        getDataUrl: "/sysLog/list",
        removeUrl: "",
        searchModel: searchModel,
        reloadTable: false,
        showDelBtn: true,
        isPage: true
      },

      URL: {}
    };
  },
  created() {
  },
  methods: {}
};
</script>
<style>

</style>
