<template>
  <div class="notice">
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

          <el-form-item label="权限" prop="powerType">
            <el-radio-group @change="val=>powerTypeChange(val,true)" v-model="applyform.powerType">
              <el-radio v-for="(item,i) in powerTypes" :key="item.type" :label="item.type">{{item.name}}</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item v-if="applyform.powerType===powerType.部门" prop="powerCodes" label="">
            <div style="border:1px dashed #ebebed;padding: 20px">
              <el-tree node-key="deptCode" show-checkbox :data="dept.treeData" :default-expand-all="true"
                       :props="dept.defaultProps" :default-checked-keys="applyform.powerCodes"
                       @check="dept_HandleCheckChange"></el-tree>
            </div>
          </el-form-item>

          <!--          用户选择-->
          <el-form-item v-else-if="applyform.powerType===powerType.选择用户" prop="powerCodes" label="">
            <div class="powerSelectUserDiv" style="border:1px dashed #ebebed;padding: 20px">

              <div class="transfterTableDiv">
                <div class="item">
                  <div style="margin-bottom: 10px">
                    <el-form :inline="true">
                      <el-form-item label="">
                        <el-input @change="userPageList" clearable v-model="user.inputValue" type="text"
                                  placeholder="姓名" size="mini"/>
                      </el-form-item>

                      <el-form-item label="">
                        <el-button size="mini" @click="userPageList" icon="el-icon-search" class="search-btn-class">搜索
                        </el-button>
                      </el-form-item>
                    </el-form>
                  </div>
                  <el-table
                    height="350"
                    v-loading="user.tablesLoading"
                    :data="user.userTableData"
                    border
                    @selection-change="user_SelectionChange">
                    <el-table-column type="selection" align="left" width="50"></el-table-column>
                    <el-table-column align="center" prop="name" label="姓名"></el-table-column>
                    <el-table-column align="center" prop="empCode" label="编号"></el-table-column>
                    <el-table-column align="center" prop="dept" label="部门">
                      <template slot-scope="obj">
                        {{ obj.row.dept.name }}
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
                <div class="arrow" style="height: 350px">
                  <el-button icon="el-icon-arrow-left" @click="toLeft" :disabled="user.leftArrow"
                             type="primary"></el-button>
                  <el-button icon="el-icon-arrow-right" @click="toRight" :disabled="user.rightArrow"
                             type="primary"></el-button>
                </div>
                <!--                右边的-->
                <div class="item">
                  <div style="margin-bottom: 10px">
                    <el-form :inline="true">
                      <el-form-item label="">
                        <el-input clearable @change="userPageListRight('search')" v-model="user.inputValue2" type="text"
                                  placeholder="姓名" size="mini"/>
                      </el-form-item>

                      <el-form-item label="">
                        <el-button size="mini" @click="userPageListRight('search')" icon="el-icon-search"
                                   class="search-btn-class">搜索
                        </el-button>
                      </el-form-item>
                    </el-form>
                  </div>
                  <el-table
                    height="350"
                    v-loading="user.tablesLoadingRight"
                    :data="user.userTableDataRight"
                    border
                    @selection-change="user_SelectionChangeRight">
                    <el-table-column type="selection" align="left" width="50"></el-table-column>
                    <el-table-column align="center" prop="name" label="姓名"></el-table-column>
                    <el-table-column align="center" prop="empCode" label="编号"></el-table-column>
                    <el-table-column align="center" prop="dept" label="部门">
                      <template slot-scope="obj">
                        {{ obj.row.dept.name }}
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>

              <div style="margin-top:10px;text-align:left">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="user.currentPage"
                  :page-sizes="[15, 30, 50, 100]"
                  :page-size="user.pageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="user.total"
                ></el-pagination>
              </div>
            </div>
          </el-form-item>

          <el-form-item v-else-if="applyform.powerType===powerType.角色" label="" prop="powerCodes">
            <div style="border:1px dashed #ebebed;padding: 20px">
              <el-checkbox-group @change="role_roleChang" v-model="role.selectCodes">
                <el-checkbox
                  v-for="role in role.roleList"
                  :label="role.key"
                  :key="role.value"
                >{{ role.value }}
                </el-checkbox
                >
              </el-checkbox-group>
            </div>
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
        <auth :code="permission.add">
          <template slot="auth">
            <el-button type="primary" icon="el-icon-plus" @click="addBtn()" size="mini">添加
            </el-button>
          </template>
        </auth>
      </template>

      <template slot="columns">
        <el-table-column prop="title" show-overflow-tooltip="true" align="center" label="title"></el-table-column>
        <el-table-column prop="content" align="center" show-overflow-tooltip="true" label="内容"></el-table-column>

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
        <el-table-column fixed="right" prop="type" align="center" label="类型">
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

        <el-table-column fixed="right" align="center" label="操作" width="200">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-view"
              type="text"
              @click="checkView(scope.row)"
              size="mini"
            >查看
            </el-button>


            <auth :code="permission.update">
              <template slot="auth">
                <el-button
                  icon="el-icon-edit"
                  type="text"
                  @click="edit(scope.row)"
                  size="mini"
                >编辑
                </el-button>
              </template>
            </auth>
            &nbsp;
            <auth :code="permission.remove">
              <template slot="auth">
                <el-button
                  icon="el-icon-delete"
                  type="text primary"
                  @click="deleteNotice(scope.row)"
                  size="mini"
                >删除
                </el-button>
              </template>
            </auth>
          </template>

        </el-table-column>
      </template>
    </table-frame>
    <!--   查看公告-->
    <el-drawer
      :visible.sync="drawer.visible"
      :show-close="false"
      :size="drawer.size"
      :withHeader="false"
      direction="ttb">
      <div class="content">
        <el-row :gutter="25">
          <el-col :span="14">
            <div class="noticeView">
              <el-badge v-if="drawer.content.type==status.紧急" :value="drawer.content.type==status.紧急?'紧急':'一般'"
                        class="item">
                <h2>
                  <span>{{drawer.content.title}}</span>
                </h2>
              </el-badge>
              <el-badge v-else :value="drawer.content.type==status.紧急?'紧急':'一般'" type="warning" class="item">
                <h2><span>{{drawer.content.title}}</span></h2>
              </el-badge>
              <p>
                由 {{drawer.content.createBy}} 发布于：{{drawer.content.createTime}}
              </p>
              <p v-if="drawer.content.enclosure!=null&&drawer.content.enclosure!=''"><span
                style="cursor: pointer;color: #0e9aef" @click="downFile(drawer.content.enclosure)">附件下载</span></p>
              <p>
                <mavon-editor
                  :ishljs="true"
                  ref="md"
                  :subfield="false"
                  defaultOpen="preview"
                  :editable="false"
                  :toolbars="drawer.toolbarView"
                  :navigation="false"
                  :shortCut="false"
                  previewBackground="#ffffff"
                  v-model="drawer.content.content"
                ></mavon-editor>
              </p>
            </div>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="box-card">
              <div slot="header" style="text-align: left">
                <span class="el-icon-price-tag">公告列表</span>
                <el-button style="float: right; padding: 3px 0" type="text"><span></span></el-button>
              </div>
              <div class="item" style="text-align: left">
                <ul>

                  <li @click="checkView(n)" v-for="n in drawer.noticeList" :key="n.id">
                    <span> <el-tag size="mini" effect="dark" v-if="n.type==status.紧急" type="danger">紧急</el-tag>
                      <el-tag size="mini" effect="dark" v-else
                              type="warning">一般</el-tag>{{n.createTime.substring(0, 16)}} {{n.title.length > 6 ? n.title.substring(0, 6) + '...' : n.title}}</span>
                  </li>
                </ul>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-drawer>
  </div>
</template>
<style>
.content {
  text-align: center;
}
</style>
<script>
let _this = {};
import searchModel from "./Search.vue";

export default {
  data() {
    return {
      drawer: {
        toolbarView: {
          navigation: true // 导航目录
        },
        visible: false,
        size: '80%',
        content: {
          title: "",
          content: '',
          createTime: '',
        },
        noticeList: []
      },
      props: {
        key: 'empCode',
        label: 'name'
      },
      applyDialog: {
        width: "80%",
        title: "新增/修改",
        visible: false,
        modal: true,
      },
      powerTypes: [{type: 1, name: "无"}, {type: 2, name: "角色"}, {type: 3, name: "部门"}, {type: 4, name: "选择用户"}],
      powerType: {"无": 1, "角色": 2, "部门": 3, "选择用户": 4},
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

      user: {
        status_lock: -1, // 用户锁定
        status_normal: 1, // 用户正常
        admin: 'admin',
        userTableData: [],
        userTableDataRight: [],
        userTableDataRightTemp: [],
        userTableDataRightMap: {},
        userMap: {},
        tablesLoading: false,
        tablesLoadingRight: false,
        pageSize: 15,
        currentPage: 1,
        total: 0,
        inputValue: "",
        inputValue2: "",
        selectUser: [],
        selectUserRight: [],
        leftArrow: true,
        rightArrow: true,
      },
      dept: {
        // 已选节点
        checkedCodes: [],
        //  数数据
        treeData: [],
        // 回显
        expandCheckedKeys: [],
        // 数属性
        defaultProps: {
          label: "deptName",
          children: "children",
        }
      },
      role: {
        selectCodes: [],
        roleList: [],
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
        powerType: 1,
        powerCodes: [],
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
        userPageList: "/user/list",
        remove: "/notice/remove/?",
        update: "/notice/update",
        save: "/notice/save",
        deleteImgFile: "/notice/deleteImgFile",
        downloadEnclosure: this.$server + "/notice/downloadEnclosure",
        roles: "/role/listIdNameAll",
        deptTreeData: "/dept/treeData",
        getUserListByEmpCodes: "/user/getUserListByEmpCodes",
        noticeList: '/notice/list'
      },
      permission: {
        add: 'notice:add',
        update: "notice:update",
        remove: "notice:del"
      }
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
      this.setDataToForm(row);
    },

    // 设置分页参数
    setPage(res) {
      if (res.R) {
        this.user.pageSize = res.data.pageSize;
        this.user.total = res.data.total;
        this.user.currentPage = res.data.pageNum;
      }
    },

    // 表格穿梭框 ----------//
    user_SelectionChange(selection) {
      this.user.selectUser = selection;
      if (selection.length == 0) {
        this.user.rightArrow = true;
        return;
      }
      this.user.rightArrow = false;
    },
    toLeft() {
      // this.user.userTableDataRight.push(element);
      let selectUser = this.user.selectUserRight;
      if (selectUser.length > 0) {
        let users = [];
        for (let j = 0; j < selectUser.length; j++) {
          let element = selectUser[j];
          delete this.user.userTableDataRightMap[element.empCode];
        }
        let selectUserMap = this.user.userTableDataRightMap;

        for (const key in selectUserMap) {
          if (selectUserMap.hasOwnProperty(key)) {
            let element = selectUserMap[key];
            users.push(element);
          }
        }
        this.user.userTableDataRight = users;
      }
    },
    toRight() {
      this.user.selectUser.forEach((element) => {
        let exitUser = this.user.userTableDataRightMap[element.empCode];
        if (exitUser === null || exitUser === undefined || exitUser === '') {
          this.user.userTableDataRight.push(element);
          this.user.userTableDataRightMap[element.empCode] = element;
        }
      })
    },
    user_SelectionChangeRight(selection) {
      this.user.selectUserRight = selection
      if (selection.length === 0) {
        this.user.leftArrow = true;
        return;
      }
      this.user.leftArrow = false;
    },
    // 右边表格
    userPageListRight(val) {
      let searchInput = this.user.inputValue2
      if (val == 'search') {
        let arr = [];
        let datas = this.user.userTableDataRight;
        if (searchInput != null && searchInput.trim().length > 0) {
          for (let i = 0; i < datas.length; i++) {
            let u = datas[i];
            if (u.name.indexOf(this.user.inputValue2) >= 0) {
              arr.push(u);
            }
          }
        } else {
          arr = this.user.userTableDataRightTemp;
        }
        this.user.userTableDataRight = arr;
        return;
      }

      let empCodes = this.applyform.powerCodes;

      let checkArr = [null, "", undefined]
      if (checkArr.indexOf(empCodes) >= 0) {
        return;
      }
      this.$get(this.URL.getUserListByEmpCodes, {empCodes: empCodes.join(",")}).then(res => {
        if (res.R) {
          this.user.userTableDataRight = res.data;
          this.user.userTableDataRightTemp = res.data;
          res.data.forEach(e => {
            this.user.userTableDataRightMap[e.empCode] = e;
          })
        }
      })
    },
    // 用户表格
    userPageList() {
      let params = {}
      params["pageNum"] = this.user.currentPage > 0 ? this.currentPage : 1;
      params["pageSize"] = this.user.pageSize;
      params["name"] = this.user.inputValue
      this.user.tablesLoading = true;
      this.$get(this.URL.userPageList, params).then(res => {
        this.user.tablesLoading = false;
        if (res.R) {
          this.user.userTableData = res.data.rows;

          // 转换成userMap
          for (let i = 0; i < res.data.rows; i++) {
            let user = res.data.rows[i];
            this.user.userMap[user.empCode] = user;
          }

          this.setPage(res);
        }
      })
    },
    // 表格穿梭框 ----------//


    // 部门选择 点击复选框
    dept_HandleCheckChange(data, checked) {
      let arr = []
      if (checked.checkedKeys.length > 0) {
        arr = checked.checkedKeys;
      }
      this.dept.checkedCodes = arr;
      this.applyform.powerCodes = arr;
    },
    // 获取部门树形数据
    dept_InitDeptData() {
      this.$get(this.URL.deptTreeData, {}).then(res => {
        if (res.R) {
          this.dept.treeData = res.data;
        }
      })
    },

    role_roleChang(val) {
      this.role.selectCodes = val;
      this.applyform.powerCodes = val;
    },
    // 角色
    getRoles(callback) {
      this.role.roleList = []
      this.$get(this.URL.roles, {}).then((res) => {
        if (res.R) {
          for (var i = 0; i < res.data.length; i++) {
            var roleObj = new Object();
            roleObj.key = res.data[i].roleCode;
            roleObj.value = res.data[i].roleName;
            this.role.roleList.push(roleObj);
          }
          if (callback != null) {
            callback(res);
          }
        }
      });
    },

    // 删除公告
    deleteNotice(row) {


      this.$confirm("此操作将永久删除已选数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$del(this.URL.remove.replace("?", row.id), {}).then((res) => {
          if (res.R) {
            this.$success("操作成功");
            this.reloadTable();
          }
        });
      });

    },

    // 附件下载
    downFile(url) {
      window.open(this.URL.downloadEnclosure + "?url=" + url, "_blank");
    },


    // 提交按钮
    saveDialogBtn(form) {
      this.$refs[form].validate(valid => {
        if (valid) {
          var arr = [undefined, null, ""];
          let i = arr.indexOf(this.applyform.id);

          if (this.applyform.powerType === this.powerType.选择用户) {
            this.applyform.powerCodes = [];
            let selectUser = this.user.userTableDataRight;
            selectUser.forEach(e => {
              this.applyform.powerCodes.push(e.empCode)
            })
          }
          let empCodes = this.applyform.powerCodes;
          let params = {};
          for (let key in this.applyform) {
            params[key] = this.applyform[key];
            if (key === "powerCodes") {
              params[key] = empCodes.join(",");
            }
          }

          if (i > 0) {
            this.save(params);
          } else {
            this.update(params);
          }
        } else {
          this.$warning("参数必填");
          return false;
        }
      });
    },

    // 更新请求
    update(params) {
      this.$put(this.URL.update, params).then((res) => {
        if (res.R) {
          this.reqResult(res);
        }
      });
    },

    // 新增请求
    save(params) {
      this.$postJson(this.URL.save, params).then(res => {
        if (res.R) {
          this.reqResult(res);
        }
      });
    },
    // 浏览公告
    checkView(row) {
      this.drawer.visible = true
      this.drawer.content = row;
      this.drawer_getNoticeList();
    },

    drawer_getNoticeList() {
      this.$get(this.URL.noticeList, {}).then(res => {
        if (res.R) {
          this.drawer.noticeList = res.data.rows
        }
      })
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


    // 编辑数据回显
    setDataToForm(row) {
      if (row != null) {
        for (const key in row) {
          if (row.hasOwnProperty(key)) {
            let element = row[key];
            if (this.applyform.hasOwnProperty(key)) {
              if (key === 'powerCodes') {
                if (element != null || element != '') {
                  element = element.split(",");
                }
              }
              this.applyform[key] = element;
            }
          }
        }
        if (row.powerType === this.powerType.部门) {
          this.dept.checkedCodes = row.powerCodes.split(",");
          this.role.selectCodes = [];
        } else if (row.powerType === this.powerType.角色) {
          this.dept.checkedCodes = [];
          this.role.selectCodes = row.powerCodes.split(",");
        }
        this.powerTypeChange(row.powerType);
      } else {
        for (const key in this.applyform) {
          if (this.applyform.hasOwnProperty(key)) {
            this.applyform[key] = "";
          }
        }
        this.applyform['powerType'] = this.powerType.无;
        this.applyform['powerCodes'] = [];
      }

      this.applyform['enclosure'] = "";
    },
    // 刷新表格
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
    // 权限类型改变
    powerTypeChange(value, isClick) {
      this.applyform.powerType = value;
      if (isClick) {
        this.applyform.powerCodes = [];
      }
      if (value === this.powerType.角色) {
        this.getRoles(null);
        // 数据还原
        if (this.applyform.powerCodes.length === 0) {
          this.applyform.powerCodes = this.role.selectCodes
        }
      } else if (value === this.powerType.部门) {
        this.dept_InitDeptData();
        // 数据还原
        if (this.applyform.powerCodes.length === 0) {
          this.applyform.powerCodes = this.dept.checkedCodes
        }
      } else if (value === this.powerType.选择用户) {
        this.userPageList();
        this.userPageListRight();
      }
    },
    // 文件选择
    onChange(file, fileList) {
      let reader = new FileReader();
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
    },
    handleSizeChange: function (pageSize) {
      this.user.pageSize = pageSize;
      this.user.currentPage = 1;
      this.userPageList();
    },
    // 换页：更新列表数据
    handleCurrentChange: function (currentPage) {
      this.user.currentPage = currentPage;
      this.userPageList();
    }
  }
  ,
}

</script>
<style>
/* 导航条的 不让它扶起来 */
/* 导航条的 不让它扶起来 */
.main .v-note-wrapper .v-note-op.shadow,
.main .v-note-op,
.shadow {
  box-shadow: none !important;
}

.powerSelectUserDiv .el-table td, .powerSelectUserDiv .el-table th {
  padding: 0 !important;
}

.transfterTableDiv {
  display: flex;
}

.transfterTableDiv .item:first-child {
  margin-right: 10px;
}

.transfterTableDiv .item:last-child {
  margin-left: 10px;
}

.transfterTableDiv .item {
  width: 50%;
}

.arrow {
  display: flex;
  align-items: center;
}

.notice .el-drawer {
  overflow-y: scroll;
  padding-top: 20px;
}

.content .v-note-op {
  display: none !important;
}

.notice .content {
  margin: 0 auto;
  padding: 0 20px;
}

.notice .content h2 {
  font-size: 25px;
  margin: 0;
}

.notice .content .item ul li {
  list-style: initial;
  padding: 3px 0;
}

.notice .content .item ul li:hover {
  color: #f3c10b;
  cursor: pointer;
}

.notice .content .item ul li .el-tag {
  font-size: 12px;
}

.content .el-card__body {
  padding: 0px !important;
}

.notice .content .item ul li .el-tag--mini {
  height: 20px;
  padding: 0 2px;
  line-height: 19px;
  margin: 0 5px;
}

.noticeView {
  border: 1px solid #ebebeb;
  padding: 20px;
  border-radius: 10px;
}
</style>
