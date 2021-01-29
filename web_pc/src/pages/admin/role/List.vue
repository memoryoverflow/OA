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
        <el-table-column prop="roleName" align="center" label="名称"></el-table-column>
        <el-table-column prop="code" align="center" label="编码"/>
        <el-table-column prop="remark" align="center" label="备注"></el-table-column>
        <el-table-column prop="createTime" align="center" width="150" sortable label="创建时间"></el-table-column>
        <el-table-column prop="updateTime" align="center" width="150"  sortable label="更新时间"></el-table-column>
      </template>
      <template slot="operate">
        <el-table-column fixed="right" align="center" label="操作" width="250">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-setting"
              type="text"
              v-if="scope.row.code!='superAdmin'"
              @click="auth(scope.row)"
              size="mini"
            >角色权限
            </el-button>
            <el-button
              icon="el-icon-edit"
              type="text primary"
              v-if="scope.row.code!='superAdmin'"
              @click="edit(scope.row)"
              size="mini"
            >修改
            </el-button>
            <el-button
              icon="el-icon-delete"
              type="text"
              v-if="scope.row.code!='superAdmin'"
              @click="del(scope.row.id)"
              size="mini"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </template>
    </table-frame>

    <!-- 角色授权弹框 -->
    <!--    <yj-dialog-->
    <!--      :modal="dialogRole.modal"-->
    <!--      :width="dialogRole.width"-->
    <!--      :title="dialogRole.title"-->
    <!--      :dialogVisible="dialogRole.visible"-->
    <!--      v-loading="dialogRole.loading"-->
    <!--    >-->
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
          :data="treeData"
          show-checkbox
          ref="tree"
          node-key="id"
          check-strictly
          highlight-current
          :default-expanded-keys="roleMenuTreeIds"
          :default-checked-keys="roleMenuTreeIds"
          :props="defaultProps"
          @check-change="check"
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
        selectedMenIds: {},
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
          loading: false
        },
        prop: {
          searchShow: true,
          operateShow: true,
          location: " 系统管理 / 权限管理 / 角色列表",
          getDataUrl: "/role/list",
          removeUrl: "/role/remove/?",
          searchModel: searchModel,
          reloadTable: false,
          colunmsCount: 7,
          showDelBtn: true,
          isPage: true
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
      this.getMenuTree();
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
        this.getRoleMenuTreeIds(row.id);
      },

      // 根据角色id获取权限菜单的所有Id
      getRoleMenuTreeIds(roleId) {
        this.selectedMenIds = {};
        this.$refs.tree.setCheckedKeys([], true);
        this.$get(this.URL.roleMenuTreeIds.replace("?", roleId), {}).then(
          res => {
            if (res.R) {
              this.roleMenuTreeIds = res.data;
              this.roleMenuTreeIds.forEach(ele => {
                this.selectedMenIds[ele] = ele;
              });
            }
          }
        );

      },

      // 获取权限菜单
      getMenuTree() {
        this.$get(this.URL.menuTreeAll, {}).then(res => {
          //console.log(res);
          this.treeData = res.data;
        });
      },
      check(data, checked, d) {
        // 不选中父元素的时候，子元素也不选中
        if (!checked) {
          console.log("cancel");
          this.filterExcludeChildrenIds(data);
        } else {
          // 获取它的的上级id,一同选上
          this.filterIncludeParenIds(data);
        }
      },
      filterExcludeChildrenIds(data) {
        // 如果不是按钮 就取消所有的子节点 选中状态
        if (data.type != "按钮") {
          delete this.selectedMenIds[data.id];
          this.$get(this.URL.getMenuSonIdsByMenId.replace("?", data.id), {}).then(
            res => {
              res.data.forEach(id => {
                delete this.selectedMenIds[id];
              });
              this.changRoleMenuIds();
            }
          );
        } else {
          delete this.selectedMenIds[data.id];
          this.changRoleMenuIds();
        }
      },
      filterIncludeParenIds(data) {
        console.log("选中" + data.perName + " " + data.id);
        this.selectedMenIds[data.id] = data.id;
        if (data.parentId != 0) {
          this.$get(
            this.URL.getMenIdsByParentId.replace("?", data.parentId),
            {}
          ).then(res => {
            res.data.forEach(e => {
              this.selectedMenIds[e] = e;
            });
            this.changRoleMenuIds();
          });
        } else {
          this.selectedMenIds[data.id] = data.id;
          this.changRoleMenuIds();
        }
      },
      changRoleMenuIds() {
        let newRoleMenuIds = [];
        for (const key in this.selectedMenIds) {
          if (this.selectedMenIds.hasOwnProperty(key)) {
            const element = this.selectedMenIds[key];
            newRoleMenuIds.push(element);
          }
        }
        this.$refs.tree.setCheckedKeys(newRoleMenuIds, true);
        this.roleMenuTreeIds = newRoleMenuIds;
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
