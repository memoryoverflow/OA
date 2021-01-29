<template>
  <div>

    <yj-dialog
      :modal="dialog.modal"
      :width="dialog.width"
      :title="dialog.title"
      :dialogVisible="dialog.visible"
    >
      <template slot="dialog-content">
        <el-form :model="form" :rules="rules" ref="form" size="mini" label-width="80px">
          <el-form-item label="模型名称" prop="modelName">
            <el-input v-model="form.modelName"></el-input>
          </el-form-item>
          <el-form-item label="模型Key" prop="modelKey">
            <el-input v-model="form.modelKey"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="modelDesc">
            <el-input type="textarea" v-model="form.modelDesc"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" style="text-align:center;" class="dialog-footer">
          <el-button size="mini" @click="cancelDialogBtn">取 消</el-button>
          <el-button size="mini" type="primary" @click="saveDialogBtn('form')">确 定</el-button>
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
      </template>

      <template slot="columns">
        <el-table-column prop="id" label="模型ID"></el-table-column>
        <el-table-column prop="name" label="模型名称"></el-table-column>
        <el-table-column prop="key" label="模型key"/>
        <el-table-column prop="version" label="版本">
          <template slot-scope="obj">
            {{obj.row.version}}.v
          </template>
        </el-table-column>
        <el-table-column prop="deploymentId" label="部署ID"/>
        <el-table-column prop="metaInfo" label="备注">
          <template slot-scope="obj">
            {{JSON.parse(obj.row.metaInfo).description==""?"description":JSON.parse(obj.row.metaInfo).description}}
          </template>
        </el-table-column>
        <el-table-column width="150" align="center" prop="createTime" sortable label="创建时间"></el-table-column>
        <el-table-column width="150" align="center" prop="lastUpdateTime" sortable label="更新时间"></el-table-column>
      </template>
      <template slot="operate">
        <el-table-column fixed="right" label="操作" width="250">
          <template slot-scope="scope">



            <el-button
              icon="el-icon-success"
              type="text"
              @click="deploy(scope.row)"
              size="mini"
            >部署
            </el-button>


            <el-button
              icon="el-icon-edit"
              type="text primary"
              @click="edit(scope.row)"
              size="mini"
            >流程设计
            </el-button>
            <el-button
              icon="el-icon-delete"
              type="text"
              @click="del(scope.row.id)"
              size="mini"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </template>
    </table-frame>


    <!--    流程图-->
    <el-drawer
      :visible.sync="dialogRole.visible"
      :size="dialogRole.width"
      direction="rtl"
      :before-close="handleClose"
      :show-close="false"
      :wrapperClosable="false"
    >
      <div class="drawer_content">
        <div style="width:100%; height:70%">
          <iframe :src="iframUrl" ref="iframe" frameborder="0" width="100%" height="800px" scrolling="auto"
                  style="background-color: #fff;"></iframe>
        </div>
        <div slot="footer" style="text-align:center;margin-top:10px" class="dialog-footer">
          <el-button size="mini" @click="cancelCargoRoleBtn">返 回</el-button>
        </div>
      </div>
    </el-drawer>

  </div>
</template>
<script>
  let _this = {};
  import searchModel from "../form/Search.vue";

  export default {
    data() {
      return {
        // 流程图设计
        dialogRole: {
          width: "80%",
          title: "流程图设计",
          visible: false,
          modal: true,
          loading: false
        },
        dialog: {
          width: "40%",
          title: "新增/修改",
          visible: false,
          modal: true
        },

        iframUrl: 'http://baidu.com',


        prop: {
          searchShow: true,
          operateShow: true,
          location: " 系统管理 / 权限管理 / 角色列表",
          getDataUrl: "/activity/model/list",
          removeUrl: "/activity/model/delete/?",
          searchModel: searchModel,
          reloadTable: false,
          colunmsCount: 7,
          showDelBtn: true,
          isPage: true
        },

        form: {
          id: "",
          modelName: "",
          modelKey: "",
          modelDesc: ""
        },
        rules: {
          modelName: [{required: true, message: "请输入名称", trigger: "blur"}],
          modelKey: [{required: true, message: "请输入Key", trigger: "blur"}]
        },

        URL: {
          iframUrl: this.$doMain +this.$serverContextPath+ "/modeler.html?modelId={id}",
          list: "/activity/model/list",
          remove: "/activity/model/delete/?",
          save: "/activity/model/create",
          deploy: "/activity/model/deploy/?",
        }
      };
    },
    watch: {},
    created() {
    },
    methods: {
      add() {
        this.dialog.visible = true;
        this.setDataToForm(null);
      },
      // 设计流程
      edit(row) {
        let url = this.URL.iframUrl.replace("{id}", row.id);
        debugger
        this.iframUrl = url;
        this.dialogRole.visible = true;
      },

      // 取消右侧窗口
      cancelCargoRoleBtn() {
        this.dialogRole.visible = false;
        this.iframUrl = _this.URL.iframUrl;
      },

      // 弹框
      cancelDialogBtn() {
        this.dialog.visible = false;
        this.setDataToForm(null);
      },
      // 添加模型保存
      saveDialogBtn(form) {
        this.$refs[form].validate(valid => {
          if (valid) {
            let arr = ['', undefined, null];
            if (arr.indexOf(this.form.id) >= 0) {
              this.save();
            } else {
              this.update();
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

      deploy(row) {
        this.$confirm('是否确定部署该流程?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$postJson(this.URL.deploy.replace("?", row.id),{}).then(res => {
            if (res.R) {
              this.$success("success")
            }
          })
        }).catch(() => {

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
