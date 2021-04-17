<template>
  <div>
    <!--    添加角色-->
    <yj-dialog
      :modal="dialog.modal"
      :width="dialog.width"
      :title="dialog.title"
      :dialogVisible="dialog.visible"
    >
      <template slot="dialog-content">
        <el-form :model="form" :rules="roles" ref="form" size="mini" label-width="80px">
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="form.roleName"></el-input>
          </el-form-item>
          <el-form-item label="角色编码" prop="code">
            <el-input v-model="form.code"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" style="text-align:center;" class="dialog-footer">
          <el-button size="mini" @click="cancelDilogBtn">取 消</el-button>
          <el-button size="mini" type="primary" @click="saveDilogBtn('form')">确 定</el-button>
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
      :delBtnPermission="prop.delBtnPermission"
    >
      <template slot="top_operate" slot-scope="currentRow">
        <auth :code="permission.add">
          <template slot="auth">
            <el-button type="primary" icon="el-icon-plus" @click="add()" size="mini">添加</el-button>
          </template>
        </auth>
        <auth :code="permission.update">
          <template slot="auth">
            <el-button
              type="success"
              icon="el-icon-edit"
              @click="editTop(currentRow.slotScope)"
              size="mini"
            >修改
            </el-button>
          </template>
        </auth>

      </template>

      <template slot="columns">
        <el-table-column prop="roleName" align="center" label="名称"></el-table-column>
        <el-table-column prop="code" align="center" label="编码"/>
        <el-table-column prop="remark" align="center" label="备注"></el-table-column>
        <el-table-column prop="createTime" align="center" width="150" sortable label="创建时间"></el-table-column>
        <el-table-column prop="updateTime" align="center" width="150" sortable label="更新时间"></el-table-column>
      </template>
      <template slot="operate">
        <el-table-column fixed="right" align="center" label="操作" width="250">
          <template slot-scope="scope">
            <auth :code="permission.auth">
              <template slot="auth">
                <el-button
                  icon="el-icon-setting"
                  type="text"
                  v-if="scope.row.code!='superAdmin'"
                  @click="auth(scope.row)"
                  size="mini"
                >角色权限
                </el-button>
              </template>
            </auth>
            <auth :code="permission.update">
              <template slot="auth">
                <el-button
                  icon="el-icon-edit"
                  type="text primary"
                  v-if="scope.row.code!='superAdmin'"
                  @click="edit(scope.row)"
                  size="mini"
                >修改
                </el-button>
              </template>
            </auth>
            <auth :code="permission.remove">
              <template slot="auth">
                <el-button
                  icon="el-icon-delete"
                  type="text"
                  v-if="scope.row.code!='superAdmin'"
                  @click="del(scope.row.id)"
                  size="mini"
                >删除
                </el-button>
              </template>

            </auth>
          </template>
        </el-table-column>
      </template>
    </table-frame>

    <!-- 角色授权弹框 -->
    <el-drawer
      :visible.sync="dialogRole.visible"
      direction="rtl"
      :before-close="handleClose"
      :show-close="false"
      :wrapperClosable="false"
    >
      <div class="drawer_content">
        <div class="roleName">角色名称：{{form.roleName}} &nbsp;&nbsp; &nbsp;角色编码：{{form.roleCode}}</div>
        <span>操作权限</span>
        <el-tree
          v-loading="dialogRole.drawerLoading"
          :data="treeData"
          show-checkbox
          check-strictly
          ref="tree"
          node-key="id"
          highlight-current
          :default-expanded-keys="roleMenuTreeIds"
          :default-checked-keys="roleMenuTreeIds"
          :props="defaultProps"
          @check-change="checkChange"
          @check="check"
          default-expand-all
        ></el-tree>
        <div slot="footer" style="text-align:center;margin-top:20px" class="dialog-footer">
          <el-button size="mini" @click="cancelCargoRoleBtn">取 消</el-button>
          <el-button size="mini" type="primary" @click="saveRoleCargoBtn()">确 定</el-button>
        </div>
      </div>
    </el-drawer>

    <!--    </yj-dialog>-->
  </div>
</template>
<script>
let _this = {};
import searchModel from "./Search.vue";

export default {
  data() {
    return {
      treeData: [],
      // 默认展开选中的
      roleMenuTreeIds: [],
      defaultProps: {
        children: "children",
        label: "perName"
      },

      dialog: {
        width: "40%",
        title: "新增/修改",
        visible: false,
        modal: true
      },
      dialogRole: {
        width: "30%",
        title: "角色授权",
        visible: false,
        modal: true,
        loading: false,
        drawerLoading: false,
      },
      prop: {
        searchShow: true,
        operateShow: true,
        location: " 系统管理 / 权限管理 / 角色列表",
        getDataUrl: "/role/list",
        removeUrl: "/role/remove/?",
        searchModel: searchModel,
        reloadTable: false,
        showDelBtn: true,
        isPage: true,
        delBtnPermission: "role:del"
      },

      form: {
        id: "",
        code: "user",
        roleName: "管理员",
        remark: ""
      },
      roles: {
        roleName: [{required: true, message: "请输入名称", trigger: "blur"}],
        code: [{required: true, message: "请输入编码", trigger: "blur"}]
      },

      visible: false,
      // 权限Code
      permission: {
        add: "role:add",
        remove: "role:del",
        update: "role:update",
        auth: 'role:toAuth'
      },
      URL: {
        list: "/role/list",
        remove: "/role/remove/?",
        save: "/role/save",
        update: "/role/update",
        menuTreeAll: "/permission/tree/all",
        toAuthRole: "/role/?/toRoleAuth",
        getMenIdsByParentId: "/permission/getMenIdsByParentId/?",
        getMenuSonIdsByMenId: "/permission/getMenuSonIdsByMenId/?",
        roleMenuTreeIds: "/permission/getMenuIdsByRoleId/?"
      }
    };
  },
  created() {

  },
  mounted() {

  },
  methods: {
    add() {
      this.dialog.visible = true;
      this.setDataToForm(null);
    },
    edit(row) {
      //alert("编辑：" + JSON.stringify(row));
      this.setDataToForm(row);
      this.dialog.visible = true;
    },
    editTop(rows) {
      if (rows.rows.length > 1 || rows.rows.length < 1) {
        this.$warning("请选择一条");
        return;
      }
      this.edit(rows.rows[0]);
    },

    cancelDilogBtn() {
      this.dialog.visible = false;
      this.setDataToForm(null);
    },
    // 保存授权角色
    saveRoleAuth() {
    },
    roleStatusChange(row) {
      if (row.status == 1) {
        row.status = 0;
      } else {
        row.status = 1;
      }
      this.$error("不允许操作");
    },
    saveDilogBtn(form) {
      this.$refs[form].validate(valid => {
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
      this.$postJson(this.URL.save, this.form).then(res => {
        if (res.R) {
          this.reqResult(res);
        }
      });
    },
    update() {
      this.$put(this.URL.update, this.form).then(res => {
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
    // 关闭抽屉
    handleClose() {
      this.dialogRole.visible = false;
      this.setDataToForm(null);
    },
    toBlack(id, status) {
      this.$confirm("是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.$put(this.URL.toBlack.replace("?", id) + "/" + status, {}).then(
          res => {
            if (res.R) {
              this.$success("操作成功");
              this.reloadTable();
            }
          }
        );
      });
    },

    // 授权按钮 弹出
    auth(row) {
      this.form.id = row.id;
      this.form.roleName = row.roleName;
      this.form.roleCode = row.roleCode;
      this.dialogRole.visible = true;
      this.getMenuTree(() => {
        this.getRoleMenuTreeIds(row.id);
      });
    },

    // 根据角色id获取权限菜单的所有Id
    getRoleMenuTreeIds(roleId) {
      this.$refs.tree.setCheckedKeys([], true);
      this.dialogRole.drawerLoading = true
      this.$get(this.URL.roleMenuTreeIds.replace("?", roleId), {}).then(
        res => {
          if (res.R) {
            // 复选框回显
            this.setCheckedIds(res.data);
            console.log(this.roleMenuTreeIds)
            this.dialogRole.drawerLoading = false
          }
        }
      );

    },

    // 获取所有权限菜单
    getMenuTree(callback) {
      this.dialogRole.drawerLoading = true
      this.$get(this.URL.menuTreeAll, {}).then(res => {
        //console.log(res);
        this.treeData = res.data;
        this.dialogRole.drawerLoading = false
        callback()
      });
    },
    // 节点选中状态发生变化时的回调
    checkChange(data, checked, d) {

      // 勾选
      if (checked) {
        if (data.type === "按钮") {
          this.toBackCheckParent(data);
          return;
        }

        // 选中的是菜单
        if (data.type === "菜单") {
          this.toBackCheckParent(data);
          return;
        }
      } else {
        // 勾选复选框
        if (data.type === "菜单" || data.type === "目录") {
          this.cancelCheckedIds(data);
          return;
        }

      }
    },
    cancelCheckedIds(currentData) {
      // 获取所有子节点id
      this.dialogRole.drawerLoading = true
      this.$get(this.URL.getMenuSonIdsByMenId.replace("?", currentData.id), {}).then(
        res => {
          let sonIds = res.data;
          this.dialogRole.drawerLoading = false
          sonIds.forEach(id => {
            let index = this.roleMenuTreeIds.indexOf(id);
            if (index >= 0) {
              this.roleMenuTreeIds.splice(index, 1);
            }
          });
          this.setCheckedIds(this.roleMenuTreeIds);
        }
      );
    },

    toBackCheckParent(currentData) {
      let parentId = currentData.parentId;
      if (parentId != 0) {
        // 判断父id是已经勾选
        if (this.roleMenuTreeIds.indexOf(parentId) < 0) {
          // 未勾选，获取当前节点的所有上级id
          this.roleMenuTreeIds.push(parentId);
          this.dialogRole.drawerLoading = true
          this.$get(
            this.URL.getMenIdsByParentId.replace("?", parentId),
            {}
          ).then(res => {
            this.dialogRole.drawerLoading = false
            res.data.forEach(e => {
              // 勾选上上级节点
              if (this.roleMenuTreeIds.indexOf(parentId) < 0) {
                this.roleMenuTreeIds.push(e);
              }
            });
            this.setCheckedIds(this.roleMenuTreeIds);
          });
        }
      }
    },

    // 当复选框被点击的时候触发
    check(data, checked, d) {
      let selectedKeys = this.$refs.tree.getCheckedKeys();
      this.roleMenuTreeIds = selectedKeys;
    },

    setCheckedIds(ids) {
      this.roleMenuTreeIds = ids;
      this.$refs.tree.setCheckedKeys(ids, true);
    },

    cancelCargoRoleBtn() {
      this.form.id = "";
      this.form.name = "";
      this.dialogRole.visible = false;
    },
    saveRoleCargoBtn() {
      this.dialogRole.loading = true;
      let menusIds = this.$refs.tree.getCheckedKeys(false).join(",");
      this.$put(
        this.URL.toAuthRole.replace("?", this.form.id) + "/" + menusIds,
        {}
      ).then(res => {
        if (res.R) {
          this.$success("操纵成功");
          this.cancelCargoRoleBtn();
        }
        this.dialogRole.loading = false;
      });
    },

    reloadTable() {
      _this = this;
      this.prop.reloadTable = true;
      setTimeout(() => {
        _this.prop.reloadTable = false;
      }, 1000);
    }
  }
};
</script>
<style>

.drawer_content {
  padding: 0 20px;
}

.el-drawer {
  overflow: scroll
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
