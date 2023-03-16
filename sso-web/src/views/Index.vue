<template>
  <el-container class="home-container">
    <!--  头部区域  -->
    <el-header>
      <div>
        <span>KEEP管理系统</span>
      </div>
      <el-button type="info" @click="logout">退出</el-button>

    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            router
            :default-active="activePath"
        >
          <el-menu-item index="/clients" @click="saveNavState('/clients')">
            <i class="el-icon-menu"></i>
            <span slot="title">应用管理</span>
          </el-menu-item>
          <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">权限管理</span>
          </el-menu-item>
          <el-menu-item index="3">
            <i class="el-icon-document"></i>
            <span slot="title">菜单管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item >应用管理</el-breadcrumb-item>
        </el-breadcrumb>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import axios from "axios";

export default {
  name: "Index",
  created() {
    this.handleSelectMenu("/clients")
    this.activePath = window.sessionStorage.getItem("activePath")
  },
  data() {
    return {
      activePath:''
    }
  },

  methods: {
    logout: function () {
      console.log("登出了～～～")
    }
    ,
    handleSelectMenu(item) {
      console.log(item);
      this.$router.push(item);
    }
    ,
    async getMenus() {
      axios.get("/menus").then(resp => {
        console.log(resp.data)
      }).catch(err => {
        console.log(err.message)
      });
    },
    saveNavState(activePath){
      window.sessionStorage.setItem("activePath",activePath)
      this.activePath = activePath
    }
  }
}
</script>

<style lang="less" scoped>

//.el-aside {
//  height: 100%;
//}
.home-container {
  height: 100%;
}

.el-header {
  background-color: #505458;
  display: flex;
  justify-content: space-between;
  padding-left: 5px;
  align-items: center;
  color: white;
  font-size: 20px;

  > div {
    display: flex;
    align-items: center;

    span {
      margin-left: 25px;
    }
  }
}

.el-aside {
  background-color: #545c64
}

.el-main {
  background-color: white;
}
.el-breadcrumb{
  margin-bottom: 20px;
}

</style>