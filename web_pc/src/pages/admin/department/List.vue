<style scoped>
  .top_operate {
    text-align: left;
  }
</style>
<template>
  <div>
    <el-drawer
      :visible.sync="drawer.visible"
      direction="rtl"
      :before-close="handleClose"
      :show-close="false"
      :wrapperClosable="false"
    >
      <div class="drawer_content">
        <div class="title">添加部门</div>
        <el-form
          :model="form"
          :rules="rules"
          ref="form"
          size="mini"
          label-width="120px"
        >
          <el-form-item label="上级部门">
            <el-select
              size="mini"
              v-model="form.deptParentId"
              placeholder="请选上级部门"
            >
              <el-option
                v-for="(dept, idx) in parentDeptDatas"
                :key="idx"
                :label="dept.deptName"
                :value="dept.id"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="部门名称" prop="deptName">
            <el-input v-model="form.deptName"></el-input>
          </el-form-item>

          <el-form-item label="部门编码" prop="deptCode">
            <el-input v-model="form.deptCode"></el-input>
          </el-form-item>

          <el-form-item label="部门地址" prop="deptAddress">
            <el-input v-model="form.deptAddress"></el-input>
          </el-form-item>

          <el-form-item label="联系电话" prop="deptPhone">
            <el-input v-model="form.deptPhone"></el-input>
          </el-form-item>

          <el-form-item label="备注说明" prop="remark">
            <el-input type="textarea"
                      :rows="2" v-model="form.remark"></el-input>
          </el-form-item>


          <el-form-item label="部门负责人">
            <el-select
              size="mini"
              v-model="form.userId"
              placeholder="请选上级部门"
            >
              <el-option
                v-for="(user, idx) in userDatas"
                :key="idx"
                :label="user.userName"
                :value="user.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div style="text-align: center" class="dialog-footer">
          <el-button size="mini" @click="cancelDilogBtn">取 消</el-button>
          <el-button size="mini" type="primary" @click="saveDilogBtn('form')"
          >确 定
          </el-button
          >
        </div>
      </div>
    </el-drawer>

    <!-- 表格 -->
    <main-frame ref="frame" :location="prop.location">


      <template slot="mainFrame">


        <div class="top_operate">
          <auth :code="permission.addPlus">
            <template slot="auth">
              <el-button
                type="primary"
                icon="el-icon-plus"
                @click="add()"
                size="mini"
              >添加部门
              </el-button>
            </template>
          </auth>
        </div>

        <!-- <el-card style="height:auto"> -->
        <el-table
          height="350"
          v-loading="tableLoading"
          size="mini"
          :data="tableData"
          row-key="id"
          :default-expand-all="true"
          :tree-props="{ children: 'children' }"
        >
          <el-table-column
            prop="deptName"
            label="部门名称"
            width="180"
          ></el-table-column>

          <el-table-column prop="deptCode" label="部门编码"/>
          <el-table-column prop="deptAddress" label="部门地址"/>
          <el-table-column prop="deptPhone" label="电话" width="180"/>

          <el-table-column prop="user" label="负责人">
            <template slot-scope="scope">
              <span v-if="scope.row.user != null && scope.row.user != ''">
                <el-tag type="primary" size="mini">{{
                  scope.row.user.userName
                }}</el-tag>
                <el-tag type="warning" size="mini">{{
                  scope.row.user.userPhone
                }}</el-tag>
              </span>
            </template>
          </el-table-column>

          <el-table-column prop fixed="right" label="操作">
            <template slot-scope="scope">
              <auth :code="permission.update">
                <template slot="auth">
                  <el-button
                    type="text"
                    @click="edit(scope.row)"
                    size="mini"
                    icon="el-icon-edit-outline"
                    circle
                  >修改
                  </el-button
                  >
                </template>
              </auth>
              <auth :code="permission.remove">
                <template slot="auth">
                  <el-button
                    type="text"
                    icon="el-icon-delete"
                    @click="del(scope.row)"
                    size="mini"
                    circle
                  >删除
                  </el-button>
                </template>
              </auth>
            </template>
          </el-table-column>
        </el-table>
        <!-- </el-card> -->
      </template>
    </main-frame>
  </div>
</template>
<script>
  import searchModel from "./Search.vue";

  let _this = {};
  export default {
    data() {
      return {
        drawer: {
          visible: false,
        },
        tableData: [],
        colunmsCount: 7,
        tableLoading: false,

        // 添加表单
        form: {
          id: "",
          deptName: "",
          deptParentId: "",
          deptCode: "",
          deptPhone: "",
          deptAddress: "",
          userId: "",
          remark: ""
        },

        // 上级部门
        parentDeptDatas: [],
        userDatas: [],

        // 表单验证
        rules: {
          id: [{required: false}],
          deptName: [
            {required: true, message: "请输入用户名", trigger: "blur"},
          ],
          deptCode: [
            {required: true, message: "请输入登陆名", trigger: "blur"},
            {min: 1, max: 20, message: "长度在 1 到 10 个字符", trigger: "blur"},
          ],
          remark: [{required: false}],
        },

        // 权限编码
        permission: {
          update: "dept:update",
          remove: "dept:del",
          addPlus: "dept:add",
        },

        // 搜索参数
        search: {
          name: "",
        },

        prop: {
          location: " 系统管理 / 部门列表",
          searchModel: searchModel,
        },

        // 接口URL
        URL: {
          tableData: "/dept/treeData",
          remove: "/dept/remove/?",
          save: "/dept/save",
          update: "/dept/update",
          deptListAll: "/dept/listIdName",
          userListAll: "/user/listIdName",
        },
      };
    },
    created() {
      _this = this;
      this.getTableData();
    },
    methods: {
      // 用户数据-下拉
      getDeptList() {
        this.$get(this.URL.deptListAll, {}).then((res) => {
          if (res.R) {
            this.parentDeptDatas = res.data;
          }
        });
      },
      // 部门下拉
      getuserList() {
        this.$get(this.URL.userListAll, {}).then((res) => {
          if (res.R) {
            this.userDatas = res.data;
          }
        });
      },
      // 表格
      getTableData() {
        this.tableLoading = true;
        this.$get(this.URL.tableData, {}).then((res) => {
          if (res.R) {
            this.tableData = res.data;
          }
          this.tableLoading = false;
        });
      },
      // 编辑弹框
      edit(row) {
        // alert("编辑：" + JSON.stringify(row));
        this.drawer.visible = true;
        this.setDataToForm(row);
        this.getuserList();
        this.getDeptList();
      },
      // 添加弹框
      add() {
        this.drawer.visible = true;
        this.setDataToForm(null);
        this.getuserList();
        this.getDeptList();
      },

      // 添加修改保存
      saveDilogBtn() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            if (
              this.form.id != null &&
              this.form.id != "" &&
              this.form.id != undefined
            ) {
              this.$put(this.URL.update, this.form).then((res) => {
                this.reqResult(res);
              });
            } else {
              this.$postJson(this.URL.save, this.form).then((res) => {
                this.reqResult(res);
              });
            }
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },

      handleClose(){
        this.drawer.visible = false;
        this.setDataToForm(null);
      },
      // 即结果处理
      reqResult(res) {
        if (res.R) {
          this.$success("操作成功");
          this.drawer.visible = false;
          this.setDataToForm(null);
          this.reloadTable();
        }
      },
      // 取消弹框
      cancelDilogBtn() {
        this.drawer.visible = false;
        this.setDataToForm(null);
      },

      // 数据恢复赋值
      setDataToForm(row) {
        if (row != null) {
          for (const key in row) {
            if (row.hasOwnProperty(key)) {
              const element = row[key];
              if (this.form.hasOwnProperty(key)) {
                this.form[key] = element;
              }
            }
          }
        } else {
          for (const key in this.form) {
            if (this.form.hasOwnProperty(key)) {
              this.form[key] = "";
            }
          }
        }
      },
      // 删除
      del(row) {
        this.$confirm(
          "将删除已经分配该角色的用户的权限以及当前菜单下所有子菜单?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
        )
          .then(() => {
            this.$warning("不允许操作");
            // this.$del(this.URL.remove.replace("?", row.id), {}).then(res => {
            //   if (res.R) {
            //     this.reqResult(res);
            //   }
            // });
          })
          .catch(() => {
          });
      },

      reloadTable() {
        this.getTableData();
      },
    },
  };
</script>
<style scoped>
  .top_operate {
    padding-top: 10px;
    padding-bottom: 10px;
  }

  .drawer_content {
    padding: 0 30px;
  }
  .drawer_content .title{
    font-size: 25px;
    text-align: center;
    margin-bottom: 15px;
  }
</style>
