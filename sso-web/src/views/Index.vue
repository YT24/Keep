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
      <el-aside :width="isCollaspe ? '64px' : '200px'">
        <div class="toggle-button" @click="toggleCollaspe">|||</div>
        <el-menu
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#409EFF"
            router
            :collapse="isCollaspe"
            :collapse-transition="false"
            :default-active="activePath"
            :unique-opened="true">

          <el-submenu :index="menu.id.toString()" v-for="menu in menusData" :key="menu.id">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>{{ menu.name }}</span>
            </template>
            <el-menu-item-group>
              <el-menu-item v-for="subMenu in menu.children"
                            :key="subMenu.id"
                            :index="'/'+subMenu.url"
                            @click="saveNavState('/'+subMenu.url)">
                <i class="el-icon-menu"></i>
                <span slot="title">{{ subMenu.name }}</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>

        </el-menu>
      </el-aside>
      <el-main>
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
    this.getMenus();
    this.activePath = window.sessionStorage.getItem("activePath")
    this.handleSelectMenu(this.activePath)
  },
  data() {
    return {
      isCollaspe: false,
      activePath: '',
      menusData: []
    }
  },

  methods: {
    logout: function () {
      console.log("登出了～～～")
      window.sessionStorage.removeItem("token")
    },
    handleSelectMenu(item) {
      this.$router.push(item)
      this.activePath = item
    }
    ,
    getMenus() {
      return axios.get("/keep-sso/keepMenus").then(resp => {
        this.menusData = resp.data.data;
      }).catch(err => {
        console.log(err.message)
      });
    },
    saveNavState(activePath) {
      window.sessionStorage.setItem("activePath", activePath)
      this.activePath = activePath
      console.log("activePath:" + activePath)
    },
    toggleCollaspe() {
      this.isCollaspe = !this.isCollaspe
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
  background-color: #545c64;

  .el-menu {
    border-right: none;
  }
}

.el-main {
  background-color: white;
}

.el-breadcrumb {
  margin-bottom: 20px;
}

.toggle-button {
  background-color: gray;
  font-size: 10px;
  line-height: 24px;
  color: white;
  text-align: center;
  letter-spacing: 0.3em;
  cursor: pointer;
}
</style>