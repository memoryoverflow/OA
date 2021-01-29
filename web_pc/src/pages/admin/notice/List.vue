<template>
  <div class="">
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
          <el-form-item label="名称" prop="title">
            <el-input v-model="applyform.title"></el-input>
          </el-form-item>

          <el-form-item label="类型" prop="type">
            <el-select
              style="width: 100%"
              size="mini"
              v-model="applyform.type"
              placeholder="选择紧急程度"
            >
              <el-option
                label="紧急"
                :value="status.紧急"
              ></el-option>

              <el-option
                label="一般"
                :value="status.一般"
              ></el-option>
            </el-select>


          </el-form-item>
          <el-form-item label="附件" prop="enclosure">
            <el-upload
              class="upload-demo"
              ref="upload"
              action
              :on-remove="handleRemove"
              :on-change="onChange"
              :auto-upload="false"
              :file-list="fileList"
              :limit="1"
            >
              <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
            </el-upload>
          </el-form-item>


          <el-form-item label="内容" prop="content">
            <mavon-editor
              @imgAdd="imgAdd"
              @imgDel="imgDel"
              @save="saveImg"
              :subfield="true"
              :ishljs="true"
              ref="md"
              v-model="applyform.content"
              @keyup.65="altV"
            ></mavon-editor>
          </el-form-item>
        </el-form>

        <div slot="footer" style="text-align: center" class="dialog-footer">
          <el-button size="mini" @click="applyDialog.visible=false">取 消</el-button>
          <el-button
            size="mini"
            type="primary"
            @click="saveDialogBtn('applyform')"
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
      :searchModel="prop.searchModel"
      :reloadTable="prop.reloadTable"
      :showDelBtn="prop.showDelBtn"
    >
      <template slot="top_operate" slot-scope="currentRow">
        <el-button type="primary" icon="el-icon-plus" @click="addBtn()" size="mini"
        >添加
        </el-button
        >
      </template>

      <template slot="columns">
        <el-table-column prop="title" align="center" label="title"></el-table-column>
        <el-table-column prop="content" align="center" show-overflow-tooltip="true" label="内容"></el-table-column>
        <el-table-column prop="type" align="center" label="类型">
          <template slot-scope="obj">
            <el-tag
              size="mini"
              v-if="obj.row.type == status.紧急"
              type="danger"
            >紧急
            </el-tag
            >
            <el-tag
              size="mini"
              v-else-if="obj.row.type == status.一般"
              type="warning"
            >一般
            </el-tag
            >
          </template>
        </el-table-column>


        <el-table-column align="center" prop="createBy" label="发布人"/>

        <el-table-column align="center" prop="enclosure" label="附件">
          <template slot-scope="obj">
            <span v-if="obj.row.enclosure == null ">无</span>
            <span v-else-if="obj.row.enclosure == '' ">无</span>
            <el-link size="mini" :under-line="false" type="primary" v-else @click="downFile(obj.row.enclosure)">下载附件
            </el-link>
          </template>
        </el-table-column>

        <el-table-column
          prop="createTime"
          sortable
          align="center"
          width="150"
          label="创建时间"
        ></el-table-column>
        <el-table-column
          prop="updateTime"
          sortable
          width="150"
          align="center"
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
            >编辑
            </el-button>
            &nbsp;
            <el-button
              icon="el-icon-delete"
              type="text primary"
              @click="deleteNotice(scope.row)"
              size="mini"
            >删除
            </el-button>
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
      applyDialog: {
        width: "80%",
        title: "新增/修改",
        visible: false,
        modal: true,
      },

      prop: {
        searchShow: true,
        operateShow: true,
        getDataUrl: "/notice/list",
        removeUrl: "/notice/remove/?",
        searchModel: searchModel,
        reloadTable: false,
        showDelBtn: true,
        isPage: true,
      },
      fileList: [],
      status: {
        紧急: 1,
        一般: 2,
      },
      taskUsers: [],
      applyform: {
        id: "",
        title: "",
        content: "",
        type: "",
        enclosure: ""
      },
      datePickerValue: [],
      applyformRules: {
        title: [{required: true, message: "请输入名称", trigger: "blur"}],
        content: [
          {required: true, message: "请输入名称", trigger: "blur"},
        ],
        type: [
          {required: true, message: "请选择类型", trigger: "blur"},
        ],
      },

      URL: {
        list: "/notice/list",
        remove: "/notice/remove/?",
        update: "/notice/update",
        save: "/notice/save",
        deleteImgFile: "/notice/deleteImgFile",
        downloadEnclosure: this.$server + "/notice/downloadEnclosure",
      },
    };
  },
  watch: {},
  created() {
  },
  methods: {
    addBtn() {
      this.setDataToForm(null)
      this.applyDialog.visible = true;
    },
    // 表单编辑
    edit(row) {
      this.applyDialog.visible = true;
      this.setDataToForm(row)
    },
    deleteNotice(row) {
      this.$del(this.URL.remove.replace("?", row.id), {}).then((res) => {
        if (res.R) {
          this.$success("操作成功");
          this.reloadTable();
        }
      });
    },
    downFile(url) {
      window.open(this.URL.downloadEnclosure + "?url=" + url, "_blank");
    },
    update() {
      this.$put(this.URL.update, this.applyform).then((res) => {
        if (res.R) {
          this.reqResult(res);
        }
      });
    },

    save() {
      this.$postJson(this.URL.save, this.applyform).then(res => {
        if (res.R) {
          this.reqResult(res);
        }
      });
    },
    saveDialogBtn(form) {
      this.$refs[form].validate(valid => {
        if (valid) {
          var arr = [undefined, null, ""];
          let i = arr.indexOf(this.applyform.id);
          debugger
          if (i > 0) {
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
    reqResult(res) {
      if (res.R) {
        this.$success("操作成功");
        this.applyDialog.visible = false;
        this.setDataToForm(null);
        this.reloadTable();
      }
      this.$refs.upload.clearFiles();
    },
    setDataToForm(row) {
      if (row != null) {
        for (const key in row) {
          if (row.hasOwnProperty(key)) {
            const element = row[key];
            if (this.applyform.hasOwnProperty(key)) {
              this.applyform[key] = element;
            }
          }
        }
      } else {
        for (const key in this.applyform) {
          if (this.applyform.hasOwnProperty(key)) {
            this.applyform[key] = "";
          }
        }
      }
      this.applyform['enclosure'] = "";
    },
    reloadTable() {
      let _this = this;
      this.prop.reloadTable = true;
      setTimeout(() => {
        _this.prop.reloadTable = false;
      }, 300);
    },

    // 绑定@imgAdd event
    imgAdd(pos, $file) {
      var _this = this;
      var formdata = new FormData();
      formdata.append("file", $file);
      this.$filePost("/notice/imgFile", formdata).then(res => {
        if (res.R) {
          _this.$refs.md.$img2Url(pos, res.msg);
          return;
        }
        _this.$refs.md.$img2Url(pos, "文件上传失败!");
      });
    },
    imgDel(file) {
      this.$del(this.URL.deleteImgFile + "?url=" + file[0], {}).then(res => {
      })
    },
    onChange(file, fileList) {
      var reader = new FileReader();
      reader.readAsDataURL(file.raw);
      let _this = this;
      reader.onload = function (e) {
        _this.applyform.enclosure = file.name + "&" + e.target.result;
      };
      return false;
    },
    handleRemove(file, fileList) {
      this.applyform.enclosure = "";
      this.$refs.upload.clearFiles();
    },
    saveImg(value, render) {
    }
  },
};
</script>
<style>
/* 导航条的 不让它扶起来 */
/* 导航条的 不让它扶起来 */
.main .v-note-wrapper .v-note-op.shadow,
.main .v-note-op,
.shadow {
  box-shadow: none !important;
}
</style>
