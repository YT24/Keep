<template>
  <div class="login-container">
    <div class="login-box">
      <div class="avatar-box">
        <img src="../assets/logo.png">
      </div>
      <el-form ref="form" :model="form" label-width="0px" class="login-form">
        <el-form-item>
          <el-input prefix-icon="el-icon-search" v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item >
          <el-input prefix-icon="el-icon-search" v-model="form.passWord" type="password"></el-input>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" @click="doLogin">登录</el-button>
          <el-button type="info">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>

</template>

<script>
import axios from 'axios'

export default {
  name: 'LoginWeb',
  data: function () {
    return {
      form:{
        userName: 'yangt1',
        passWord: '1qaz@WSX'
      }
    }
  },
  methods: {
    doLogin: function () {
      let username = this.form.userName;
      let password = this.form.passWord;
      let url = "/keep-sso/api/v1/keepUser/login";
      //发起ajax请求-GET(注意参数必须保存到params属性中)
      axios.post(url, {
        userName:username,
        passWord:password
      }).then(resp => {
        console.log(resp.data);
        this.$message({
          type: resp.data.code == 200 ? 'success' : 'error',
          message: resp.data.msg
        });
        console.log(resp.data);
        window.sessionStorage.setItem("token", resp.data.data.accessToken)
        this.$router.push('/index')
      }).catch(err => {
        console.log(err);
      })
    },
    doRegister: function () {
      this.$router.push('/Register')
    }
  }
}
</script>

<style>

.login-form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  justify-content: flex-end;
}

.avatar-box {
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%,-50%);
  background-color: white;
}

img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #eeeeee;

}


.login-box {
  background-color: #fff;
  width: 450px;
  height: 300px;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

}

.login-container {
  background-color: #2b4b6b;
  height: 100%;
}


</style>