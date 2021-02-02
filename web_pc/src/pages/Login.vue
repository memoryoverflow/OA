<template>
  <div class="main-content" :style="bg">
    <div class="login-box">
      <el-card>
        <div class="title">
          <h3>后台管理登陆</h3>
        </div>
        <el-form>
          <el-input
            prefix-icon="el-icon-user-solid"
            id="name"
            v-model="loginName"
            placeholder="请输入帐号"
          />

          <el-input
            prefix-icon="el-icon-key"
            id="password"
            v-model="password"
            type="password"
            placeholder="请输入密码"
          />

          <!-- <div class="login-code">
            <el-input
              prefix-icon="el-icon-picture-outline"
              id="qrcode"
              v-model="code"
              placeholder="请输入验证码"
            />
            <img class="qrCode" @click="changImgCode" :src="codeUrl" />
          </div> -->
          <div>
            <el-button
              id="login"
              @click="LoginBtn"
              style="width: 100%"
              type="primary"
              >登录</el-button
            >
          </div>
        </el-form>
      </el-card>
    </div>
  </div>
</template>
<script type="text/javascript">
import { token } from "@/token";
export default {
  data() {
    return {
      bg: {
        backgroundImage: "url(" + require("@/assets/login-background.jpg") + ")",
      },

      loginName: "",
      code: "",
      password: "",

      codeUrl: "/getVerify",
      url: {
        login: "/login",
        loginRedirectPage: "/",
      },
    };
  },
  methods: {
    changImgCode() {
      this.codeUrl = this.$reqUserService + "/getVerify?=time" + new Date();
    },
    LoginBtn() {
      var _this = this;
      //获取值
      var loginName = this.loginName;
      var password = this.password;
      var code = this.code;
      if (loginName == "") {
        this.$error("账户不能为空");
      }

      if (password == "") {
        this.$error("密码不能留空");
        return;
      }

      // if (code == "") {
      //   this.$error("请输入验证码");
      //   return;
      // }

      // 测试下需要设置token 否则路由处会拦截掉
      // _this.$router.push({
      //   path: _this.url.loginRedirectPage
      // });

      this.login();
    },
    login() {
      var _this = this;
      _this
        .$postJson(this.url.login, {
          loginName: this.loginName,
          password: this.password,
        })
        .then((res) => {
          // _this.changImgCode()
          if (res.R) {
            this.$success("登陆成功");
            this.$saveToken(res.data.token);
            this.$saveUser(res.data);
            this.$cookies.remove("menus");
            _this.$router.push({
              path: _this.url.loginRedirectPage,
            });
          }
        });
    },
  },
};
</script>
<style>
.login-box .el-input {
  margin: 10px 0;
}
.title {
  text-align: center;
}
.title h3 {
  padding: 0;
  margin: 0;
  margin-bottom: 10px;
  color: #555e6f;
}
.main-content {
  height: 100vh;
  background-size: 100% 100%;
}
.login-box {
  width: 400px !important;
  height: 300px;
  margin: 0 auto;
  padding-top: 15%;
  padding-right: 25px;
  padding-left: 25px;
}
.login-code {
  display: flex;
  align-items: center;
}
#qrcode {
  width: 240px;
}
.qrCode {
  width: 100px;
  height: 40px;
}
#login {
  margin-top: 20px;
}
img {
  cursor: pointer;
}
</style>
