<template>
  <div>
    <yj-dialog
      :modal="dialog.modal"
      :width="dialog.width"
      :title="dialog.title"
      :dialogVisible="dialog.visible"
      :closeFun="cancelDilogBtn"
    >
      <!--      用户添加-->
      <template slot="dialog-content">
        <el-form
          :model="form"
          :rules="rules"
          ref="userForm"
          size="mini"
          label-width="100px"
        >
          <el-form-item label="登陆名" prop="loginName">
            <el-input
              v-model="form.loginName"
            ></el-input>
          </el-form-item>

          <el-form-item label="姓名" prop="name">
            <el-input
              v-model="form.name"
            ></el-input>
          </el-form-item>

          <el-form-item label="电话" prop="phone">
            <el-input v-model="form.phone"></el-input>
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email"></el-input>
          </el-form-item>


          <el-form-item label="编号" prop="empCode">
            <el-input v-model="form.empCode"></el-input>
          </el-form-item>

          <el-form-item prop="deptId" label="选择部门">

            <el-select
              size="mini"
              v-model="form.deptId"
              placeholder="请选上级部门"
              style="width: 100%"
              ref="selectDept"
            >
              <el-option
                :label="form.deptName"
                :value="form.deptId"
                style="width: 100%;height:200px;overflow: auto;background-color:#fff"
              >

                <el-tree :data="dept.treeData" :default-expand-all="true"
                         :props="dept.defaultProps" @node-click="selectTreeDataNodeSelect"></el-tree>

              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item prop="positionId" label="选择岗位">

            <el-select
              size="mini"
              v-model="form.positionId"
              placeholder="请选择岗位"
              style="width: 100%"
            >
              <el-option
                v-for="(post, idx) in positionData"
                :key="idx"
                :label="post.name"
                :value="post.id"
              ></el-option>
            </el-select>
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
    <main-frame :location="location">
      <template slot="mainFrame">
        <div class="search">
          <el-form :inline="true">
            <el-form-item label="姓名">
              <el-input
                size="mini"
                clearable
                v-model="params.name"
                placeholder="请输入姓名"
              ></el-input>
            </el-form-item>

            <el-form-item label="电话">
              <el-input
                size="mini"
                clearable
                v-model="params.phone"
                placeholder="请输入电话"
              ></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input
                size="mini"
                clearable
                v-model="params.email"
                placeholder="请输入邮箱"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                @click="search()"
                icon="el-icon-search"
                type="primary"
                size="mini"
                class="search-btn-class"
              >搜索
              </el-button
              >
            </el-form-item>
            <el-form-item>
              <el-button
                @click="resetFormAndTale()"
                icon="el-icon-refresh"
                type="default"
                size="mini"
              >刷新
              </el-button
              >
            </el-form-item>
          </el-form>
        </div>

        <div class="list_frame row-content">
          <el-row :gutter="20">
            <!--            部门列表-->
            <el-col :span="4">
              <div class="treeDept" style="">
                <h2 style="margin-top:0 "><i class="el-icon-menu"></i>&nbsp;组织机构&nbsp;&nbsp;
                  <i class="el-icon-edit" style="cursor:pointer" @click="jumpToDeptPage"></i>
                </h2>
                <hr style="padding: 0;margin: 0">
                <el-tree :data="dept.treeData" :highlight-current="true" :default-expand-all="true"
                         :props="dept.defaultProps" @node-click="treeDataNodeSelect"></el-tree>
              </div>
            </el-col>
            <el-col :span="20">
              <div class="top_operate">
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
                <auth :code="'user:export'">
                  <template slot="auth">
                    <el-button
                      type="success"
                      icon="el-icon-upload2"
                      @click="exportData()"
                      size="mini"
                    >导出
                    </el-button
                    >
                  </template>
                </auth>
              </div>
              <el-table
                :data="table.dataList"
                v-loading="table.tableLoading"
                body-style="height:100%;"
                width="100%"
                size="mini"
                @selection-change="selectionChange"
              >
                <el-table-column
                  type="selection"
                  align="left"
                  width="50"
                ></el-table-column>

                <el-table-column prop="name" label="姓名"></el-table-column>
                <el-table-column prop="name" label="状态">
                  <template slot-scope="obj">
                    <el-switch
                      :disabled="obj.row.loginName==user.admin"
                      @change="v=>activityUser(v,obj.row.id)"
                      style="display: block"
                      v-model="obj.row.status"
                      active-color="#13ce66"
                      inactive-color="#ff4949"
                      active-text=""
                      :active-value="user.status_normal"
                      :inactive-value="user.status_lock"
                      inactive-text="">
                    </el-switch>
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="loginName" label="登陆账户"></el-table-column>
                <el-table-column  align="center" show-overflow-tooltip="true" prop="phone" label="电话"></el-table-column>
                <el-table-column align="center" prop="empCode" label="编号"></el-table-column>
                <el-table-column  align="center" show-overflow-tooltip="true" prop="email" label="邮箱"></el-table-column>
                <el-table-column align="center"  prop="dept" label="部门">
                  <template slot-scope="obj">
                    {{ obj.row.dept.name }}
                  </template>
                </el-table-column>
                <el-table-column prop="position" label="岗位">
                  <template slot-scope="obj">
                    {{ obj.row.position.name }}
                  </template>
                </el-table-column>
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
                  width="150" align="center"
                ></el-table-column>
                <el-table-column
                  prop="updateTime"
                  sortable
                  label="更新时间"
                  width="150" align="center"
                ></el-table-column>
                <el-table-column fixed="right" label="操作" width="200">
                  <template slot-scope="scope">
                    <auth :code="permission.update">
                      <el-button
                        icon="el-icon-edit"
                        type="text"
                        v-if="scope.row.loginName != user.admin"
                        @click="edit(scope.row)"
                        size="mini"
                        slot="auth"
                      >修改
                      </el-button
                      >
                    </auth>
                    <auth :code="permission.remove">
                      <el-button
                        icon="el-icon-delete"
                        type="text"
                        v-if="scope.row.loginName != user.admin"
                        @click="del(scope.row.id)"
                        size="mini"
                        slot="auth"
                      >删除
                      </el-button
                      >
                    </auth>
                    <auth :code="'user:reloadPwd'">
                      <el-button
                        icon="el-icon-key"
                        type="text"
                        v-if="scope.row.loginName != user.admin"
                        @click="reloadPwdBtn(scope.row.id)"
                        size="mini"
                        slot="auth"
                      >密码重置
                      </el-button
                      >
                    </auth>
                  </template>
                </el-table-column>
              </el-table>


              <div class="pageNation">
                <el-row>
                  <el-col :span="2">
                    <div style="margin-top: 10px">
                      <el-button
                        type="danger"
                        size="small"
                        :disabled="table.delBtnFlag"
                        @click="delBatch()"
                      >删除已选
                      </el-button
                      >
                    </div>
                  </el-col>
                  <el-col :span="22">
                    <div style="margin-top: 10px; text-align: right">
                      <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="page.currentPage"
                        :page-sizes="[15, 30, 50, 100]"
                        :page-size="page.pagesize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="page.total"
                      ></el-pagination>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </el-col>
          </el-row>
        </div>
      </template>
    </main-frame>

  </div>
</template>
<script>
let _this = {};
export default {
  data() {
    return {
      params: {
        name: "",
        phone: "",
        email: ""
      },
      roleList: [],
      delCode: false,
      loginInputDisable: true,
      dialog: {
        width: "40%",
        title: "新增/修改",
        visible: false,
        modal: true,
      },
      location: " 用户管理 / 用户列表",
      table: {
        dataList: [],
        tableLoading: false,
        selectedIds: [],
        delBtnFlag: true
      },
      page: {
        total: 0, // 列表内所有数据的长度
        currentPage: 1, // 初始页 0 jpa是0
        pageSize: 15, // 当前页面内的列表行数
      },
      user: {
        status_lock: -1, // 用户锁定
        status_normal: 1, // 用户正常
        admin: 'admin'
      },
      form: {
        id: "",
        name: "",
        loginName: "",
        deptId: "",
        empCode: "",
        deptName: "",
        email: "",
        phone: "",
        positionId: "",
        roleIds: [],
      },

      dept: {
        treeData: [],
        defaultProps: {
          label: "deptName",
          children: "children",
        }
      },
      positionData: [],

      // 权限Code
      permission: {
        add: "user:add",
        remove: "user:del",
        update: "user:update",
      },
      rules: {
        id: [{required: false}],
        name: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur"},
        ],
        loginName: [
          {required: true, message: "请输入登陆名", trigger: "blur"},
          {min: 1, max: 10, message: "长度在 1 到 10 个字符", trigger: "blur"},
        ],
        phone: [{required: true, message: "请输入登陆名", trigger: "blur"}],
        empCode: [{required: true, message: "请输入编号", trigger: "blur"}],
        email: [{required: true, message: "请输入邮件", trigger: "blur"}, {
          min: 1,
          max: 50,
          message: "长度在 1 到 50 个字符",
          trigger: "blur"
        },],
        deptId: [
          {required: true, message: "请选择部门", trigger: "blur"},
        ],
        positionId: [
          {required: true, message: "请选择岗位", trigger: "blur"},
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
        remove: "/user/remove/?",
        roles: "/role/listIdNameAll",
        deptTreeData: "/dept/treeData",
        postionData: "/post/listIdAndNameCode",
        reloadPwd: "/user/reloadPwd", // 密码重置
        userToBack: '/user/toBlack/{id}/{status}'
      },
    };
  },
  created() {
    _this = this;
    this.getRoles(null);
    this.initDeptData();
    this.initTableData();
  },
  methods: {
    // 数据表格
    initTableData() {
      this.table.tableLoading = true;
      this.setRequestPageParams(_this.params);
      this.$get(this.URL.list, _this.params).then(res => {
        this.table.tableLoading = false;
        if (res.R) {
          this.table.dataList = res.data.rows;
          this.setPage(res);
        }
      })
    },

    // 设置请求分页参数
    setRequestPageParams(param) {
      param["pageNum"] = this.page.currentPage > 0 ? this.page.currentPage : 1;
      param["pageSize"] = this.page.pageSize;
    },

    // 结果分页
    setPage(res) {
      this.page.pageSize = res.data.pageSize;
      this.page.total = res.data.total;
      this.page.currentPage = res.data.pageNum;
    },

    // 角色
    getRoles(callback) {
      this.roleList = []
      this.$get(this.URL.roles, {}).then((res) => {
        if (res.R) {
          for (var i = 0; i < res.data.length; i++) {
            var roleObj = new Object();
            roleObj.key = res.data[i].id;
            roleObj.value = res.data[i].roleName;
            this.roleList.push(roleObj);
          }
          if (callback != null) {
            callback(res);
          }
        }
      });
    },
    // 搜索按钮
    search() {
      _this.initTableData();
    },
    // 刷新按钮
    resetFormAndTale() {
      for (const key in this.params) {
        if (this.params.hasOwnProperty(key)) {
          this.params[key] = "";
        }
      }
      _this.initTableData();
    },
    // 点击部门
    treeDataNodeSelect(dept) {
      this.params['deptId'] = "";
      if (dept.children == null || dept.children == '' || dept.children.length == 0) {
        this.params['deptId'] = dept.id;
      }
      this.initTableData();
    },
    // 下拉框部门点击
    selectTreeDataNodeSelect(dept) {
      if (dept.children == null || dept.children == "" || children.length == 0) {
        this.form.deptId = dept.id;
        this.form.deptName = dept.deptName;
        this.$refs.selectDept.blur()
      }
    },
    // 获取部门树形数据
    initDeptData() {
      this.$get(_this.URL.deptTreeData, {}).then(res => {
        if (res.R) {
          this.dept.treeData = res.data;
        }
      })
    },
    initPostData() {
      this.$get(_this.URL.postionData, {}).then(res => {
        if (res.R) {
          this.positionData = res.data
        }
      })
    },
    // 添加
    add() {
      this.initDeptData();
      this.initPostData();
      this.getRoles(null);
      this.setDataToForm(null);
      this.dialog.visible = true;
    },
    // 编辑
    edit(row) {
      this.initDeptData();
      this.initPostData();
      this.getRoles((res) => {
        this.setDataToForm(row);
        this.dialog.visible = true;
      });
    },
    // 改变用户转台
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
    // 密码重置
    reloadPwdBtn(id) {
      this.$prompt('请输入新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        if (value == null || value.trim().length == 0) {
          _this.$info("请输入新密码");
          return
        }

        _this.$put(_this.URL.reloadPwd, {"id": id, password: value}).then(res => {
          if (res.R) {
            _this.$success("密码已重置")
          }
        })
      }).catch(() => {
        this.$info('取消密码重置');
      });

    },
    // g关闭弹框
    cancelDilogBtn(userForm) {
      this.dialog.visible = false;
      this.setDataToForm(null);
      if (userForm !== undefined) {
        this.$refs[userForm].resetFields();
      }
    },
    // 用户添加保存
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
      let params = _this.form;
      let tempRole = this.form.roleIds;
      params['roleIds'] = tempRole.toString();
      debugger
      this.$postJson(this.URL.save, params).then((res) => {
        if (res.R) {
          this.reqResult(res);
        }
      });
    },
    update() {
      let params = _this.form;
      let tempRole = this.form.roleIds;
      params['roleIds'] = tempRole.toString();
      this.$put(this.URL.update, params).then((res) => {
        if (res.R) {
          this.reqResult(res);
        }
      });
    },
    delBatch() {
      if (_this.table.selectedIds.length == 0) {
        this.$warning("请至少选择一条数据")
        return;
      }
      this.$confirm("此操作将永久删除已选数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$del(this.URL.remove.replace("?", _this.table.selectedIds.join(",")), {}).then((res) => {
          if (res.R) {
            this.$success("已删除");
            this.reloadTable();
          }
        });
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
    reqResult(res) {
      if (res.R) {
        this.$success("操作成功");
        this.dialog.visible = false;
        this.setDataToForm(null);
        this.reloadTable();
      }
    },
    setDataToForm(row) {
      if (row != null) {
        for (const key in row) {
          if (row.hasOwnProperty(key)) {
            const element = row[key];
            if (this.form.hasOwnProperty(key)) {
              this.form[key] = element;
            }
            if (key == "dept") {
              this.form['deptName'] = element.name
            }
          }
        }
        this.form.roleIds = [];
        for (var i = 0; i < row.roleIds.length; i++) {
          this.form.roleIds[i] = row.roleIds[i];
        }
      } else {
        for (const key in this.form) {
          if (this.form.hasOwnProperty(key)) {
            this.form[key] = "";
          }
        }
        this.form.roleIds = [];
      }
    },
    // 改变复选框状态触发
    selectionChange(selection) {
      this.table.selectedIds = [];
      selection.forEach((element) => {
        this.table.selectedIds.push(element.id);
      });
      if (selection.length > 0) {
        this.table.delBtnFlag = false;
      } else {
        this.table.delBtnFlag = true;
      }
    },
    reloadTable() {
      _this = this;
      _this.initTableData();
    },
    // 初始页page、初始每页数据数pagesize和数据data
    // 更换每页列内不同的行数：更新列表数据
    handleSizeChange: function (pagesize) {
      this.page.pageSize = pagesize;
      this.page.currentPage = 1;
      this.initTableData();
    },
    // 换页：更新列表数据
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
      this.initTableData();
    },
    jumpToDeptPage() {
      _this.$router.push({
        path: "/admin/department/list",
      });
    },
    activityUser(value, id) {
      _this.$put(this.URL.userToBack.replace("{id}", id).replace("{status}", value), {}).then(res => {
        if (res.R) {
          this.$success("success")
        }
      })
    }
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


/**
 * #13ce66
 */
.search-btn-class {
  background: #20b2aa;
  border: #20b2aa;
}

.search {
  text-align: left;
  border-radius: 5px;
  padding-top: 20px;
  width: 100%;
}

.top_operate {
  text-align: left;
  width: 100%;
  margin-bottom: 10px;
}

</style>
