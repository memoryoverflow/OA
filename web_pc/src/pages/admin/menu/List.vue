<style scoped>
.top_operate {
  text-align: left;
}
</style>
<template>
  <div>
    <el-drawer
      :visible.sync="dialog.visible"
      direction="rtl"
      :before-close="handleClose"
      :show-close="false"
      :wrapperClosable="false"

    >
      <div v-loading="drawerLoading" class="drawer_content">
        <div class="title">添加目录/外部链接</div>

        <el-form :model="form" ref="form" size="mini" label-width="80px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.perName"></el-input>
          </el-form-item>

          <el-form-item v-if="dialog.showRadio" label="外链">
            <el-radio-group
              :disabled="dialog.isDisBaledRadio"
              @change="outJoinChange"
              v-model="dialog.outJoinType"
            >
              <el-radio :label="3">目录</el-radio>
              <el-radio :label="1">一级外链</el-radio>
              <el-radio :label="2">二级外链</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item v-if="dialog.showParent" label="上级目录">
            <el-select
              style="width: 100%"
              size="mini"
              v-model="form.parentId"
              placeholder="请选上级目录"
            >
              <el-option
                v-for="(menu, idx) in parentMenu"
                :key="idx"
                :label="menu.perName"
                :value="menu.id"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item v-if="dialog.showHref" label="链接" prop="url">
            <el-input v-model="form.url"></el-input>
          </el-form-item>

          <el-form-item label="图标" prop="icon">
            <el-input v-model="form.icon"></el-input>
            <i :class="form.icon"></i>
            <!--              href="https://element.eleme.cn/#/zh-CN/component/icon"-->
            <el-link
              type="primary"
              style="margin-left: 10px"
              target="_blank"
              @click="selectIcon"
            >图标选择
            </el-link
            >
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input-number
              v-model="form.sort"
              controls-position="right"
              :min="1"
              :max="100"
            ></el-input-number>
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
    <main-frame ref="frame">
      <template slot="mainFrame">
        <div class="top_operate">
          <auth :code="permission.add">
            <template slot="auth">
              <el-button
                type="primary"
                icon="el-icon-plus"
                @click="add()"
                size="mini"
              >添加外链
              </el-button
              >
            </template>
          </auth>
        </div>

        <el-table
          height="350"
          v-loading="tableLoading"
          size="mini"
          :data="tableData"
          row-key="id"
          :tree-props="{ children: 'children' }"
          :header-cell-style="headerCellStyle"
        >
          <el-table-column
            prop="perName"
            align="center"
            label="菜单名称"
            width="180"
          ></el-table-column>
          <el-table-column prop="icon" label="图标">
            <template slot-scope="scope">
              <i :class="scope.row.icon"></i>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="router" label="路由">
            <template slot-scope="scope">{{
              scope.row.router == "" ? "无" : scope.row.router
              }}
            </template>
          </el-table-column>
          <el-table-column prop="code" label="权限码">
            <template slot-scope="scope">{{
              scope.row.code == "" ? "无" : scope.row.code
              }}
            </template>
          </el-table-column>
          <el-table-column prop="type" align="center" label="类型">
            <template slot-scope="scope">
              <el-tag
                type="primary"
                v-if="scope.row.type == '目录'"
                size="mini"
              >{{ scope.row.type }}
              </el-tag
              >
              <el-tag
                type="warning"
                v-else-if="scope.row.type == '菜单'"
                size="mini"
              >{{ scope.row.type }}
              </el-tag
              >
              <el-tag type="danger" v-else size="mini">{{
                scope.row.type
                }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="sort"
            label="排序"
            width="60"
          ></el-table-column>
          <el-table-column align="center" prop="outJoin" label="是否外链" width="100">
            <template slot-scope="obj">{{
              obj.row.outJoin == true ? "是" : "否"
              }}
            </template>
          </el-table-column>
          <el-table-column v-if="showColum" prop fixed="right" align="center" label="操作" width="150">
            <template slot-scope="scope">
              <auth :code="permission.update">
                <template slot="auth">
                  <el-button
                    type="text"
                    @click="edit(scope.row)"
                    v-if="scope.row.outJoin"
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
                    v-if="scope.row.outJoin"
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

    <yj-dialog
      :modal="iconDialog.modal"
      :width="iconDialog.width"
      :title="iconDialog.title"
      :dialogVisible="iconDialog.visible">
      <template slot="dialog-content">
        <el-button @click="iconDialog.visible=false" style="position: relative;top:-20px" type="danger"
                   icon="el-icon-circle-close" size="mini">取消
        </el-button>
        <iframe
          style="width: 100%; height: 600px;border: none"
          ref="iframe"
          :src="iconUrl"
        />
      </template>
    </yj-dialog>
  </div>
</template>
<script>
import searchModel from "./Search.vue";

let _this = {};
export default {
  data() {
    return {
      showColum: true,
      drawerLoading: false,
      iconUrl: "https://element.eleme.cn/#/zh-CN/component/icon",
      iconDialog: {
        modal: true,
        visible: false,
        width: "80%",
        title: "图标选择",
      },
      dialog: {
        visible: false,
        width: "50%",
        title: "新增/修改",
        outJoinType: 3,
        showParent: false,
        showHref: false,
        isDisBaledRadio: false,
        showRadio: true,
      },

      tableData: [],
      tableLoading: false,
      form: {
        id: "",
        perName: "",
        parentId: "",
        icon: "",
        type: 2,
        url: "",
        sort: 99,
        outJoin: true,
      },
      parentMenu: [],
      code: {
        update: "/permission/update",
        remove:  "/permission/remove",
        add: "/permission/save",
      },
      search: {
        name: "",
      },
      permission: {
        add: "menu:add",
        remove: "menu:del",
        update: "menu:update",
      },
      URL: {
        tableData: "/permission/tree/all",
        remove: "/permission/?/remove",
        save: "/permission/save",
        update: "/permission/update",
        toUpdatePage: "/permission/?/update",
        toAuthPage: "/permission/manager",
        getMenuParentIdIsZero: "/permission/getMenuParentIdIsZero",
      },
    };
  },
  created() {
    _this = this;
    this.getTableData();
    this.getMenuParentIdIsZeroData();
  },
  methods: {
    selectIcon() {
      this.iconDialog.visible = true;
    },
    outJoinChange(val) {
      if (val === 2) {
        // 添加 二级外链
        this.dialog.showParent = true;
        this.dialog.showHref = true;
        this.form.type = 2;
        this.form.outJoin = true;
      } else if (val === 1) {
        // 添加一级外链
        this.form.type = 2;
        this.form.outJoin = true;
        this.dialog.showHref = true;
        this.dialog.showParent = false;
      } else {
        // 添加目录
        this.form.type = 1;
        this.form.outJoin = true;
        this.dialog.showParent = false;
        this.dialog.showHref = false;
      }
    },
    getTableData() {
      this.tableLoading = true;
      this.$get(this.URL.tableData, {}).then((res) => {
        this.tableData = res.data;
        this.tableLoading = false;
      });
    },
    getMenuParentIdIsZeroData() {
      this.$get(this.URL.getMenuParentIdIsZero, {}).then((res) => {
        if (res.R) {
          this.parentMenu = res.data;
        }
      });
    },
    edit(row) {
      // alert("编辑：" + JSON.stringify(row));
      this.dialog.visible = true;
      this.setDataToForm(row);
    },
    add() {
      this.dialog.visible = true;
      this.setDataToForm(null);
    },
    handleClose() {
      this.dialog.visible = false;
    },
    saveDilogBtn() {
      if (
        this.form.id != null &&
        this.form.id != "" &&
        this.form.id != undefined
      ) {
        this.drawerLoading = true
        this.$put(this.URL.update, this.form).then((res) => {
          this.reqResult(res);
        });
      } else {
        this.drawerLoading = true
        this.$postJson(this.URL.save, this.form).then((res) => {
          this.reqResult(res);
        });
      }
    },
    reqResult(res) {
      this.drawerLoading = false
      if (res.R) {
        this.$success("操作成功");
        this.dialog.visible = false;
        this.setDataToForm(null);
        this.reloadTable();
      }
    },

    cancelDilogBtn() {
      this.dialog.visible = false;
      this.setDataToForm(null);
    },
    setDataToForm(row) {
      if (row == null) {
        this.form.id = "";
        this.form.perName = "";
        this.form.parentId = "";
        this.form.icon = "";
        this.form.url = "";
        this.form.sort = 99;
        this.dialog.isDisBaledRadio = false;
      } else {
        this.form.id = row.id;
        this.form.perName = row.perName;
        this.form.sort = row.sort;
        this.form.icon = row.icon;
        this.form.url = row.url;
        this.form.type = row.type == "目录" ? 1 : row.type = "菜单" ? 2 : 3;

        this.dialog.isDisBaledRadio = true;

        // 一级外链
        if (row.parentId == 0 && row.type == "菜单" && row.outJoin) {
          this.dialog.showHref = true;
          this.dialog.outJoinType = 1;
          this.dialog.showRadio = true;
          this.form.outJoin = true;
          this.dialog.showParent = true;
        }

        // 一级非外链
        if (row.parentId == 0 && row.type == "菜单" && !row.outJoin) {
          this.dialog.showHref = false;
          this.dialog.showRadio = false;
          this.dialog.showParent = false;
        }

        // 二级外链
        if (row.parentId != 0 && row.type == "菜单" && row.outJoin) {
          this.dialog.showHref = true;
          this.dialog.outJoinType = 2;
          this.form.parentId = row.parentId;
          this.dialog.showParent = true;
          this.form.outJoin = true;
          this.dialog.showRadio = true;
        }

        if (row.parentId != 0 && row.type == "菜单" && !row.outJoin) {
          // 非外链接
          this.dialog.showHref = false;
          this.dialog.showParent = true;
          this.form.parentId = row.parentId;
          this.dialog.showRadio = false;
        }
        if (row.type == "目录") {
          this.dialog.outJoinType = 1;
          this.dialog.showHref = false;
          this.dialog.showParent = false;
          this.dialog.showRadio = false;
        }
      }
    },
    authPower(row) {
      this.$router.push({
        path: _this.URL.toAuthPage.replace("?", row.id),
      });
    },
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
    saveOutJoinDilogBtn() {
    },
    reloadTable() {
      this.getTableData();
    },
    headerCellStyle({row, column, rowIndex, columnIndex}) {
      let color = "#555E6F";
      let bg = "#F8F8F9";
      let borderTopRightRadius = "0px";
      let s = {
        background: bg,
        color: color,
        borderTopRightRadius: borderTopRightRadius,
      };
      if (rowIndex == 0 && columnIndex == 0) {
        return s;
      } else if (rowIndex == 0 && columnIndex == _this.colunmsCount - 1) {
        return s;
      } else if (rowIndex == 0) {
        return {
          background: bg,
          color: color,
        };
      }
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

.drawer_content .title {
  font-size: 25px;
  text-align: center;
  margin-bottom: 15px;
}
</style>
