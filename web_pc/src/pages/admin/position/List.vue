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
          <el-form-item label="岗位名称" :rule="rules" prop="positionName">
            <el-input
              v-model="form.positionName"
            ></el-input>
          </el-form-item>

          <el-form-item label="岗位编码" prop="positionCode">
            <el-input v-model="form.positionCode"></el-input>
          </el-form-item>

          <el-form-item label="备注说明" prop="remark">
            <el-input type="textarea"
                      :rows="2" v-model="form.remark"></el-input>
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
        <auth :code="permission.add">
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
        <el-table-column prop="positionName" label="岗位名称"></el-table-column>
        <el-table-column prop="positionCode" label="岗位编码"></el-table-column>
        <el-table-column prop="remark" label="备注说明"></el-table-column>
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
            <auth :code="permission.update">
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
            <auth :code="permission.del">
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
        dialog: {
          width: "40%",
          title: "新增/修改",
          visible: false,
          modal: true,
        },
        prop: {
          searchShow: true,
          operateShow: true,
          location: " 系统设置 / 岗位管理",
          getDataUrl: "/post/list",
          removeUrl: "/post/remove/?",
          searchModel: searchModel,
          reloadTable: false,
          showDelBtn: true,
          isPage: true,
        },
        form: {
          id: "",
          positionName: "",
          positionCode: "",
          remark: [],
        },

        permission: {
          add: "post:add",
          remove: "post:del",
          update: "post:update",
        },

        rules: {
          id: [{required: false}],
          positionName: [
            {required: true, message: "请输入用户名", trigger: "blur"}
          ],
          positionCode: [
            {required: true, message: "请输入登陆名", trigger: "blur"},
          ],
          remark: [{required: false}],
        },
        URL: {
          list: "/post/list",
          save: "/post/save",
          update: "/post/update",
          remove: "/post/remove/?",
        },
      };
    },
    created() {
    },
    methods: {
      // 添加
      add() {
        this.dialog.visible = true;
        this.setDataToForm(null);
      },
      // 编辑
      edit(row) {
        this.setDataToForm(row);
        this.dialog.visible = true;
      },
      // 取消
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
            this.$warning("缺少必填数据");
            return false;
          }
        });
      },
      save() {
        this.$postJson(this.URL.save, this.form).then((res) => {
          this.reqResult(res);
        });
      },
      update() {
        this.$put(this.URL.update, this.form).then((res) => {
          this.reqResult(res);
        });
      },
      del(id) {
        this.$confirm("此操作将永久删除已选数据, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.$del(this.URL.remove.replace("?",id),{}).then((res) => {
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
</style>
