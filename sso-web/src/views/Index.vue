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
        <el-menu background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>用户管理</span>
            </template>
            <!--<el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>-->
          </el-submenu>

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
      <el-main>Main</el-main>
    </el-container>
  </el-container>
</template>

<script>
import axios from "axios";

export default {
  name: "Index",
  created() {
    this.getMenus()
  },

  methods: {
    logout: function () {
      console.log("登出了～～～")
    },
    async getMenus() {
      axios.get("/menus").then(resp =>{
        console.log(resp.data)
      }).catch(err =>{
        console.log(err.message)
      });

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
  background-color: #eeeeee;
}

.el-main {
  background-color: beige;
}

</style>