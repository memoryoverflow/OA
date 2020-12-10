<template>
  <div>
    <yj-dialog
      :modal="dialog.modal"
      :width="dialog.width"
      :title="dialog.title"
      :dialogVisible="dialog.visible"
      @close="closeDialog"
    >
      <template slot="dialog-content">
        <el-form
          :model="form"
          :rules="rules"
          ref="userForm"
          size="mini"
          label-width="100px"
        >
          <el-form-item label="登陆名" prop="username">
            <el-input
              v-model="form.username"
              :disabled="loginInputDisable"
            ></el-input>
          </el-form-item>

          <el-form-item label="用户名" prop="nickName">
            <el-input v-model="form.nickName"></el-input>
          </el-form-item>

          <el-form-item label="角色" prop="roleIds">
            <el-checkbox-group v-model="form.roleIds">
              <el-checkbox
                v-for="role in roleList"
                :label="role.key"
                :key="role.value"
              >{{ role.value }}
              </el-checkbox
              >
            </el-checkbox-group>
          </el-form-item>
        </el-form>
        <div slot="footer" style="text-align: center" class="dialog-footer">
          <el-button size="mini" @click="cancelDilogBtn('userForm')"
          >取 消
          </el-button
          >
          <el-button
            size="mini"
            type="primary"
            @click="saveDilogBtn('userForm')"
          >确 定
          </el-button
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
        <auth :code="'user:add'">
          <template slot="auth">
            <el-button
              type="primary"
              icon="el-icon-plus"
              @click="add()"
              size="mini"
            >添加
            </el-button
            >
          </template>
        </auth>

        <!-- <el-button type="success" icon="el-icon-edit" @click="add()" size="mini">修改</el-button> -->
      </template>


      <template slot="columns">
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="loginName" label="登陆账户"></el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="roles" label="角色">
          <template slot-scope="obj">
            <div v-for="(role, idx) in obj.row.roles" :key="idx">
              <span
                :style="{ color: role.roleCode == 'superAdmin' ? 'orange' : '' }"
              >{{ role.roleName }}</span
              >
            </div>
          </template>
        </el-table-column>
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
        <el-table-column fixed="right" label="操作" width="300">
          <template slot-scope="scope">
            <auth :code="'user:update'">
              <el-button
                icon="el-icon-edit"
                type="text"
                @click="edit(scope.row)"
                size="mini"
                slot="auth"
              >修改
              </el-button
              >
            </auth>
            <auth :code="'user:del'">
              <el-button
                icon="el-icon-delete"
                type="text"
                v-if="scope.row.username != 'admin'"
                @click="del(scope.row.id)"
                size="mini"
                slot="auth"
              >删除
              </el-button
              >
            </auth>
          </template>
        </el-table-column>
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
        roleList: [],
        delCode: false,
        loginInputDisable: true,
        dialog: {
          width: "40%",
          title: "新增/修改",
          visible: false,
          modal: true,
        },
        prop: {
          searchShow: true,
          operateShow: true,
          location: " 用户管理 / 用户列表",
          getDataUrl: "/user/list",
          removeUrl: "/user/del/?",
          searchModel: searchModel,
          reloadTable: false,
          showDelBtn: true,
          isPage: true,
        },
        formRoleIds:[],
        form: {
          id: "",
          username: "",
          nickName: "",
          roleIds: [],
        },

        permission: {
          add: "user:add",
          remove: "user:del",
          update: "user:update",
        },
        rules: {
          id: [{required: false}],
          username: [
            {required: true, message: "请输入用户名", trigger: "blur"},
            {min: 1, max: 5, message: "长度在 1 到 6 个字符", trigger: "blur"},
          ],
          nickName: [
            {required: true, message: "请输入登陆名", trigger: "blur"},
            {min: 1, max: 5, message: "长度在 1 到 6 个字符", trigger: "blur"},
          ],
          roleIds: [
            {
              type: "array",
              required: true,
              message: "请选择角色",
              trigger: "change",
            },
          ],
          remark: [{required: false}],
        },

        visible: false,

        URL: {
          list: "/user/list",
          save: "/user/save",
          update: "/user/update",
          toBlack: "/user/toBlack/?",
          remove: "/user/del/?",
          roles: "/role/listIdNameAll",
        },
      };
    },
    created() {
      this.getRoles();
    },
    methods: {
      getRoles() {
        this.roleList=[]
        this.$get(this.URL.roles, {}).then((res) => {
          if (res.R) {
            this.roles = res.data;
            for (var i = 0; i < this.roles.length; i++) {
              var roleObj = new Object();
              roleObj.key = this.roles[i].id;
              roleObj.value = this.roles[i].roleName;
              this.roleList.push(roleObj);
            }
          }
        });
      },
      add() {
        this.dialog.visible = true;
        this.setDataToForm(null);
      },
      edit(row) {
        this.setDataToForm(row);
        this.dialog.visible = true;
      },
      userStatusChange(row) {
        this.$put(
          this.URL.toBlack.replace("?", row.id) + "/" + row.status,
          {}
        ).then((res) => {
          if (res.R) {
            this.$success("操作成功");
          }
        });
      },
      cancelDilogBtn(userForm) {
        this.dialog.visible = false;
        this.setDataToForm(null);
        this.$refs[userForm].resetFields();
      },
      closeDialog() {
        this.$emit("closeModal", false);
      },
      saveDilogBtn(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            if (
              this.form.id != undefined &&
              this.form.id != null &&
              this.form.id != ""
            ) {
              this.update();
            } else {
              this.save();
            }
          } else {
            this.$warning("参数必填");
            return false;
          }
        });
      },
      save() {
        let tempRole = this.form.roleIds;
        let params=_this.form;
        params.roleIds=tempRole.toString();
        this.$postJson(this.URL.save, params).then((res) => {
          if (res.R) {
            this.reqResult(res);
          }
        });
      },
      update() {
        let tempRole = this.form.roleIds;
        let params=_this.form;
        params.roleIds=tempRole.toString();
        this.$put(this.URL.update, params).then((res) => {
          if (res.R) {
            this.reqResult(res);
          }
        });
      },
      del(id) {
        this.$confirm("此操作将永久删除已选数据, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.$del(this.URL.remove, id).then((res) => {
            if (res.R) {
              this.$success("已删除");
              this.reloadTable();
            }
          });
        });
      },
      reqResult(res) {
        if (res.R) {
          this.$success("操作成功");
          this.dialog.visible = false;
          this.setDataToForm(null);
          this.reloadTable();
        }
      },
      setDataToForm(row) {
        this.form.roleIds = [];
        if (row == null) {
          this.form.username = "";
          this.form.nickName = "";
          this.form.id = "";
          this.loginInputDisable = false;
        } else {
          this.form.nickName = row.nickName;
          this.form.id = row.id;
          this.form.username = row.username;
          this.loginInputDisable = true;
          for (var i = 0; i < row.roleList.length; i++) {
            this.form.roleIds[i] = row.roleList[i].id;
          }
        }
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
</style>
<style scoped>
  .bg-purple {
    background: #d3dce6;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
</style>
