<template>
  <div>
    <!--  表单申请填写  -->
    <yj-dialog
      :modal="applyDialog.modal"
      :width="applyDialog.width"
      :title="applyDialog.title"
      :dialogVisible="applyDialog.visible"
    >
      <template slot="dialog-content">
        <el-form
          :model="applyform"
          :rules="applyformRules"
          ref="applyform"
          size="mini"
          label-width="80px"
        >
          <el-form-item label="名称" prop="formName">
            <el-input v-model="applyform.formName"></el-input>
          </el-form-item>

          <el-form-item label="内容" prop="formContent">
            <el-input
              :autosize="{ minRows: 4, maxRows: 8 }"
              type="textarea"
              v-model="applyform.formContent"
            ></el-input>
          </el-form-item>

          <el-form-item label="起止时间" prop="endTime">
            <el-date-picker
              style="width: 100%"
              v-model="datePickerValue"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              @change="timeChange"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>

          <el-form-item label="审批人" prop="agentUserCode">
            <el-select
              style="width: 100%"
              size="mini"
              v-model="applyform.agentUserCode"
              placeholder="选择审批人"
            >
              <el-option
                v-for="(user, idx) in taskUsers"
                :key="idx"
                :label="user.userName"
                :value="user.empCode"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="审核意见" prop="reply">
            <el-input
              :autosize="{ minRows: 4, maxRows: 8 }"
              disabled
              type="textarea"
              v-model="applyform.reply"
            ></el-input>
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-tag v-if="applyform.status == status.表单填写">待提交</el-tag>
            <el-tag
              v-else-if="applyform.status == status.表单审核中"
              type="warning"
              >审核中</el-tag
            >
            <el-tag v-else-if="applyform.status == status.同意" type="danger"
              >同意</el-tag
            >
            <el-tag v-else-if="applyform.status == status.拒绝" type="danger"
              >拒绝</el-tag
            >
            <el-tag v-else type="danger">取消</el-tag>
          </el-form-item>
        </el-form>

        <div slot="footer" style="text-align: center" class="dialog-footer">
          <el-button size="mini" @click="applyCancelDialogBtn">取 消</el-button>
          <el-button
            size="mini"
            type="primary"
            @click="applySaveDialogBtn('applyform')"
            >确 定</el-button
          >
        </div>
      </template>
    </yj-dialog>

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
        <el-button type="primary" icon="el-icon-plus" @click="add()" size="mini"
          >添加</el-button
        >
      </template>

      <template slot="columns">
        <el-table-column prop="formName" label="名称"></el-table-column>
        <el-table-column prop="formContent" label="内容"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template slot-scope="obj">
            <el-tag
              size="mini"
              v-if="obj.row.status == status.表单填写"
              type="primary"
              >待提交</el-tag
            >
            <el-tag
              size="mini"
              v-else-if="obj.row.status == status.表单审核中"
              type="warning"
              >表单审核中</el-tag
            >
            <el-tag
              size="mini"
              v-else-if="obj.row.status == status.同意"
              type="success"
              >申请通过</el-tag
            >
            <el-tag
              size="mini"
              v-else-if="obj.row.status == status.拒绝"
              type="danger"
              >拒绝</el-tag
            >
            <el-tag
              size="mini"
              v-else-if="obj.row.status == status.取消"
              type="danger"
              >取消</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="reply" label="审核意见" />
        <el-table-column prop="agentUserCode" label="代理人">
          <template slot-scope="obj">
            {{ obj.row.agentUser == null ? "无" : obj.row.agentUser.name }}
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" />
        <el-table-column prop="endTime" label="结束时间" />
        <el-table-column
          prop="createTime"
          sortable
          label="创建时间"
        ></el-table-column>
        <el-table-column
          prop="updateTime"
          sortable
          label="更新时间"
        ></el-table-column>
      </template>
      <template slot="operate">
        <el-table-column fixed="right" label="操作" width="250">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-edit"
              type="text"
              @click="edit(scope.row)"
              size="mini"
              >编辑表单
            </el-button>

            <el-popconfirm
              confirm-button-text="好的"
              cancel-button-text="不用了"
              icon="el-icon-info"
              icon-color="red"
              title="这是一段内容确定删除吗？"
              @confirm="applyStart(scope.row)"
            >
              <el-button
                slot="reference"
                icon="el-icon-success"
                type="text"
                size="mini"
                v-if="status.表单填写 == scope.row.status"
                >提交申请
              </el-button>
            </el-popconfirm>

            &nbsp;
            <el-button
              icon="el-icon-view"
              type="text primary"
              @click="checkProcess(scope.row)"
              size="mini"
              >查看进度
            </el-button>
            &nbsp;
            <el-button
              icon="el-icon-edit"
              type="text primary"
              @click="cancelForm(scope.row)"
              size="mini"
              >放弃
            </el-button>
          </template>
        </el-table-column>
      </template>
    </table-frame>
  </div>
</template>
<script>
let _this = {};
import searchModel from "../model/Search.vue";

export default {
  data() {
    return {
      applyDialog: {
        width: "40%",
        title: "新增/修改",
        visible: false,
        modal: true,
      },

      prop: {
        searchShow: true,
        operateShow: true,
        location: " 系统管理 / 个人办公 / 表单申请",
        getDataUrl: "/form/selectMyForm",
        removeUrl: "/form/remove/?",
        searchModel: searchModel,
        reloadTable: false,
        colunmsCount: 7,
        showDelBtn: true,
        isPage: true,
      },
      status: {
        表单填写: 100,
        表单审核中: 101,
        同意: 102,
        拒绝: 103,
        取消: 104,
      },
      taskUsers: [],
      applyform: {
        id: "",
        formName: "",
        formContent: "",
        startTime: "",
        endTime: "",
        status: 100,
        procDefId: "",
        agentUserCode: "",
        reply: "",
      },
      datePickerValue: [],
      applyformRules: {
        formName: [{ required: true, message: "请输入名称", trigger: "blur" }],
        formContent: [
          { required: true, message: "请输入名称", trigger: "blur" },
        ],
        agentUserCode: [
          { required: true, message: "必须选择审批人", trigger: "blur" },
        ],
      },

      URL: {
        list: "/form/selectMyForm",
        remove: "/form/remove/?",
        update: "/form/update",
        startProcDef: "/procInst/start",
        users: "/user/listIdName",
        getProcDefFirstNode: "/activity/processDef/getProcDefFirstNode",
        getFormById: "/form/getFormById",
      },
    };
  },
  watch: {},
  created() {},
  methods: {
    // getProcDefFirstNode
    getProcDefFirstNode() {
      this.$get(this.URL.getProcDefFirstNode, {
        procDefId: this.applyform.procDefId,
      }).then((res) => {
        if (res.R && res.data != null) {
          this.applyform.agentUserCode = res.data.assignee;
        }
      });
    },
    getFormById() {
      this.$get(this.URL.getFormById, {
        procDefId: this.applyform.procDefId,
      }).then((res) => {
        if (res.R) {
          let form = res.data;
          for (const key in form) {
            if (form.hasOwnProperty(key)) {
              let element = form[key];
              if (this.applyform.hasOwnProperty(key)) {
                this.applyform[key] = element;
              }
            }
          }
          this.datePickerValue = [];
          this.datePickerValue.push(form.startTime);
          this.datePickerValue.push(form.endTime);
          this.getProcDefFirstNode();
        }
      });
    },
    // 获取任务用户
    getTaskUsers() {
      this.$get(this.URL.users, {}).then((res) => {
        if (res.R) {
          this.taskUsers = res.data;
        }
      });
    },
    // 表单编辑
    edit(row) {
      this.applyform.procDefId = row.procDefId;
      this.getTaskUsers();
      this.getFormById();
      this.applyDialog.visible = true;
    },

    // 弹框
    applyCancelDialogBtn() {
      this.applyDialog.visible = false;
      this.setDataToForm(null);
    },

    // 添加模型保存
    applySaveDialogBtn(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let arr = ["", undefined, null];
          if (arr.indexOf(this.applyform.id) < 0) {
            this.update();
          }
        } else {
          this.$warning("参数必填");
          return false;
        }
      });
    },

    update() {
      this.$put(this.URL.update, this.applyform).then((res) => {
        if (res.R) {
          this.reqResult(res);
        }
      });
    },
    reqResult(res) {
      if (res.R) {
        this.$success("操作成功");
        this.applyDialog.visible = false;
        this.setDataToForm(null);
        this.reloadTable();
      }
    },
    setDataToForm(row) {
      this.datePickerValue = [];
      if (row != null) {
        for (const key in row) {
          if (row.hasOwnProperty(key)) {
            const element = row[key];
            if (this.form.hasOwnProperty(key)) {
              this.applyform[key] = element;
            }
          }
        }
        this.datePickerValue.push(row.startTime);
        this.datePickerValue.push(row.endTime);
      } else {
        for (const key in this.form) {
          if (this.applyform.hasOwnProperty(key)) {
            this.applyform[key] = "";
          }
        }
      }
    },
    // 提交申请
    applyStart(row) {
      this.$postJson(this.URL.startProcDef, { procDefId: row.procDefId }).then(
        (res) => {
          if (res.R) {
            this.$success("success");
          }
        }
      );
    },
    checkProcess(row) {},
    cancelForm(row) {},
    timeChange(date) {
      this.applyform.startTime = date[0];
      this.applyform.endTime = date[1];
    },
    reloadTable() {
      _this = this;
      this.prop.reloadTable = true;
      setTimeout(() => {
        _this.prop.reloadTable = false;
      }, 1000);
    },
  },
};
</script>
<style>
.drawer_content {
  padding: 0 20px;
}

.drawer_content .el-tree-node__label {
  font-size: 15px !important;
}

.drawer_content span {
  font-size: 17px;
}

.roleName {
  padding-bottom: 15px;
  font-size: 18px;
}
</style>
