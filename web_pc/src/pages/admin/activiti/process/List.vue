<template>
  <div>
    <!--    编辑-->
    <yj-dialog
      :modal="userDialog.modal"
      :width="userDialog.width"
      :title="userDialog.title"
      :dialogVisible="userDialog.visible"
    >
      <template slot="dialog-content">
        <el-form
          :model="form"
          :rules="rules"
          ref="userForm"
          size="mini"
          label-width="100px"
        >
          <el-form-item label="属于" prop="type">
            <el-select
              style="width: 100%"
              size="mini"
              v-model="form.type"
              placeholder="请选类别"
            >
              <el-option
                v-for="(name, idx) in types"
                :key="idx"
                :label="name"
                :value="name"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-form-item>

          <el-form-item label="角色" prop="useRole">
            <el-checkbox-group v-model="form.useRole">
              <el-checkbox
                v-for="role in roleList"
                :label="role.key"
                :key="role.value"
              >{{ role.value }}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item>
            <span style="color: #bec6cf"
            >不选择角色，就是所有登陆用户都可以发起该申请</span
            >
          </el-form-item>
        </el-form>

        <div slot="footer" style="text-align: center" class="dialog-footer">
          <el-button size="mini" @click="cancelDilogBtn('userForm')"
          >取 消
          </el-button>
          <el-button
            size="mini"
            type="primary"
            @click="saveDilogBtn('userForm')"
          >确 定
          </el-button>
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
        <el-button
          type="primary"
          icon="el-icon-position"
          @click="jumpToMyAply()"
          size="mini"
        >前往我的申请列表
        </el-button
        >
      </template>

      <template slot="columns">
        <el-table-column prop="id" label="id"></el-table-column>
        <el-table-column prop="key" label="名称">
          <template slot-scope="obj">
            <strong>{{ obj.row.key }}</strong>
          </template>
        </el-table-column>
        <el-table-column prop="version" label="版本">
          <template slot-scope="obj"> v.{{ obj.row.version }}</template>
        </el-table-column>
        <el-table-column prop="deploymentId" label="部署id"></el-table-column>
        <el-table-column prop="resourceName" label="资源文件"></el-table-column>
        <el-table-column prop="diagramResourceName" label="流程图">
          <template slot-scope="obj">
            <el-link @click="checkImg(obj.row.deploymentId)" type="primary">{{
              obj.row.diagramResourceName
              }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="suspensionState" label="是否激活">
          <template slot-scope="obj">
            <el-tag
              v-if="obj.row.suspensionState == SuspensionState.激活"
              type="success"
            >激活
            </el-tag
            >
            <el-tag v-else type="danger">挂起</el-tag>
          </template>
        </el-table-column>
      </template>

      <template slot="operate">
        <el-table-column fixed="right" label="操作" width="250">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-lock"
              type="text"
              v-if="scope.row.suspensionState == SuspensionState.激活"
              @click="deploy(scope.row, SuspensionState.挂起)"
              size="mini"
            >挂起
            </el-button>
            &nbsp;
            <el-button
              icon="el-icon-unlock"
              type="text"
              v-if="scope.row.suspensionState == SuspensionState.挂起"
              @click="deploy(scope.row, SuspensionState.激活)"
              size="mini"
            >&nbsp;激活
            </el-button>

            <el-button
              icon="el-icon-set-up"
              type="text"
              @click="nodeSetting(scope.row)"
              size="mini"
            >节点设置
            </el-button>

            <el-button
              icon="el-icon-edit"
              type="text"
              @click="edit(scope.row)"
              size="mini"
            >编辑
            </el-button>

            <el-button
              icon="el-icon-s-promotion"
              type="text"
              @click="apply(scope.row)"
              size="mini"
            >申请
            </el-button>
          </template>
        </el-table-column>
      </template>
    </table-frame>
    <yj-dialog
      :modal="dialog.modal"
      :width="dialog.width"
      :title="dialog.title"
      :dialogVisible="dialog.visible"
    >
      <template slot="dialog-content">
        <div style="text-align: center">
          <img :src="resourceImgUrl"/>
        </div>
        <div slot="footer" style="text-align: center" class="dialog-footer">
          <el-button size="mini" @click="dialog.visible = false"
          >取 消
          </el-button
          >
        </div>
      </template>
    </yj-dialog>

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
          ref="form"
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

          <el-form-item label="审核意见" prop="reply">
            <el-input
              :autosize="{ minRows: 4, maxRows: 8 }"
              disabled
              type="textarea"
              v-model="applyform.reply"
            ></el-input>
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-tag v-if="applyform.status == status.表单填写">表单填写</el-tag>
            <el-tag
              v-else-if="applyform.status == status.表单审核中"
              type="warning"
            >审核中
            </el-tag
            >
            <el-tag v-else-if="applyform.status == status.同意" type="danger"
            >同意
            </el-tag
            >
            <el-tag v-else-if="applyform.status == status.拒绝" type="danger"
            >拒绝
            </el-tag
            >
            <el-tag v-else type="danger">取消</el-tag>
          </el-form-item>
        </el-form>

        <div slot="footer" style="text-align: center" class="dialog-footer">
          <el-button size="mini" @click="applyCancelDialogBtn">取 消</el-button>
          <el-button
            size="mini"
            type="primary"
            @click="applySaveDialogBtn('form')"
          >确 定
          </el-button
          >
        </div>
      </template>
    </yj-dialog>

    <el-drawer
      :visible.sync="drawer.visible"
      direction="rtl"
      :size="drawer.size"
    >
      <iframe
        style="width: 100%; height: 100%"
        ref="iframe"
        :src="drawer.src"
      />
    </el-drawer>
  </div>
</template>
<script>
  let _this = {};
  import searchModel from "./Search.vue";

  export default {
    data() {
      return {
        drawer: {
          src: "",
          visible: false,
          size: "70%",
        },
        dialog: {
          title: "流程图查看",
          visible: false,
          modal: true,
        },
        applyDialog: {
          width: "40%",
          title: "申请表单填写",
          visible: false,
          modal: true,
        },
        userDialog: {
          width: "40%",
          title: "编辑",
          visible: false,
          modal: true,
        },
        resourceImgUrl: "",
        prop: {
          searchShow: true,
          operateShow: true,
          location: " 系统管理 / 工作流 / 已发布流程",
          getDataUrl: "/activity/processDef/list",
          removeUrl: "",
          searchModel: searchModel,
          reloadTable: false,
          showDelBtn: false,
          isPage: true,
        },
        types: ["请假", "物品使用申请", "其他"],
        form: {
          id: "",
          type: "",
          useRole: [],
          remark: "",
        },

        // 表单验证
        rules: {
          id: [{required: true}],
          type: [{required: true, message: "请输入用户名", trigger: "blur"}],
          useRole: [
            {
              type: "array",
              required: false,
              message: "请选择角色",
              trigger: "change",
            },
          ],
          remark: [{required: false}],
        },
        applyform: {
          id: "",
          formName: "",
          formContent: "",
          startTime: "",
          endTime: "",
          status: 100,
          procDefId: "",
          reply: "",
        },
        datePickerValue: [],
        applyformRules: {
          formName: [{required: true, message: "请输入名称", trigger: "blur"}],
          formContent: [
            {required: true, message: "请输入名称", trigger: "blur"},
          ],
        },
        status: {
          表单填写: 100,
          表单审核中: 101,
          同意: 102,
          拒绝: 103,
          取消: 104,
        },
        SuspensionState: {
          激活: 1,
          挂起: 2,
        },
        roleList: [],
        URL: {
          list: "/activity/processDef/list",
          activity: "/activity/processDef/active/{id}/{status}",
          checkImg:
            this.$serverContextPath + "/activity/processDef/checkImg/{deployId}",
          roles: "/role/listIdNameAll",
          editExtendUpdate: "/activity/processDef/editExtendUpdate",
          apply: "/form/save",
          formPage: "/admin/activiti/form/list",
          NodeSetting: "/#/activiti/process/nodeSetting",
        },
      };
    },
    watch: {},
    created() {
      //this.nodeSetting(null)
    },
    methods: {
      edit(row) {
        this.getRoles(() => {
          this.setDataToForm(row);
          this.userDialog.visible = true;
        });
      },
      setDataToForm(rowParent) {
        if (rowParent != null) {
          let row = rowParent.extendForm;
          if (row != null) {
            for (const key in row) {
              if (row.hasOwnProperty(key)) {
                const element = row[key];
                if (this.form.hasOwnProperty(key)) {
                  this.form[key] = element;
                }
              }
            }

            this.form.useRole = [];
            if (row.useRole != null && row.useRole != "") {
              let arr = row.useRole.split(",");
              for (var i = 0; i < arr.length; i++) {
                this.form.useRole[i] = arr[i];
              }
            }
          } else {
            for (const key in this.form) {
              if (this.form.hasOwnProperty(key)) {
                this.form[key] = "";
              }
            }
            this.form.useRole = [];
          }
          this.form.id = rowParent.id;
        }
      },

      // 节点设置 nodeSetting
      nodeSetting(row) {
        this.drawer.visible = true;
        //并行测试:1:42507
        this.drawer.src = this.URL.NodeSetting + "?id="+row.id;
      },

      // --------------------------------
      jumpToMyAply() {
        this.$router.push({
          path: this.URL.formPage,
        });
      },
      // 激活 挂起
      deploy(row, status) {
        this.$postJson(
          this.URL.activity.replace("{id}", row.id).replace("{status}", status),
          {}
        ).then((res) => {
          this.reqResult(res);
        });
      },
      //apply 申请
      apply(row) {
        this.applyDialog.visible = true;
        this.applyform["procDefId"] = row.id;
      },
      timeChange(date) {
        this.applyform.startTime = date[0];
        this.applyform.endTime = date[1];
      },

      // 弹框
      applyCancelDialogBtn() {
        this.applyDialog.visible = false;
        //this.setDataToForm(null);
      },

      // 添加模型保存
      applySaveDialogBtn(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            let arr = ["", undefined, null];
            if (arr.indexOf(this.applyform.id) >= 0) {
              this.$postJson(this.URL.apply, this.applyform).then((res) => {
                if (res.R) {
                  this.$success("success");
                  this.applyDialog.visible = false;
                  for (const key in this.applyform) {
                    if (this.applyform.hasOwnProperty(key)) {
                      this.applyform[key] = "";
                    }
                  }
                  this.applyform["status"] = this.status.表单填写;
                }
              });
            }
          } else {
            this.$warning("参数必填");
            return false;
          }
        });
      },
      // - ----------------------------

      getRoles(callback) {
        this.role.roleList = [];
        this.$get(this.URL.roles, {}).then((res) => {
          if (res.R) {
            for (var i = 0; i < res.data.length; i++) {
              var roleObj = new Object();
              roleObj.key = res.data[i].roleCode;
              roleObj.value = res.data[i].roleName;
              this.roleList.push(roleObj);
            }
            if (callback != null) {
              callback(res);
            }
          }
        });
      },
      reqResult(res) {
        if (res.R) {
          this.$success("操作成功");
          this.userDialog.visible = false;
          this.setDataToForm(null);
          this.reloadTable();
        }
      },
      checkImg(deploymentId) {
        this.resourceImgUrl = this.URL.checkImg.replace(
          "{deployId}",
          deploymentId
        );
        this.dialog.visible = true;
      },
      reloadTable() {
        _this = this;
        this.prop.reloadTable = true;
        setTimeout(() => {
          _this.prop.reloadTable = false;
        }, 1000);
      },
      // g关闭弹框
      cancelDilogBtn(userForm) {
        this.userDialog.visible = false;
        this.setDataToForm(null);
      },
      // 添加保存
      saveDilogBtn(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            this.update();
          } else {
            this.$warning("参数必填");
            return false;
          }
        });
      },
      del(id) {
        this.$confirm("此操作将永久删除已选数据, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.$del(this.URL.remove.replace("?", id), {}).then((res) => {
            if (res.R) {
              this.$success("已删除");
              this.reloadTable();
            }
          });
        });
      },
      update() {
        let params = this.form;
        let tempRole = this.form.useRole;
        if (tempRole != "" && tempRole.length > 0) {
          params["useRole"] = tempRole.toString();
        }
        this.$put(this.URL.editExtendUpdate, params).then((res) => {
          if (res.R) {
            this.reqResult(res);
          }
        });
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
