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
              :router=true
              :default-active="activePath">
            <el-submenu :index="'/'+menu.url" v-for="menu in menusData" :key="menu.id">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>{{ menu.name }}</span>
              </template>
              <el-menu-item v-for="subMenu in menu.children" :key="subMenu.id" :index="'/'+subMenu.url">
                <i class="el-icon-menu"></i>
                <span slot="title">{{subMenu.name}}</span>
              </el-menu-item>
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
    this.handleSelectMenu("clients")
    this.activePath = window.sessionStorage.getItem("activePath")
  },
  data() {
    return {
      activePath:'',
      menusData:[]
    }
  },

  methods: {
    logout: function () {
      console.log("登出了～～～")
    }
    ,
    handleSelectMenu(item) {
      console.log("item:"+item);
      console.log("this.$route.path:"+this.$route.path);
      console.log(this.$route.path !== item.url)
      this.$router.push(item)
      this.activePath=item
    }
    ,
     getMenus() {
     return axios.get("/keep-sso/keepMenus").then(resp => {
        this.menusData = resp.data.data;
      }).catch(err => {
        console.log(err.message)
      });
    },
  async  saveNavState(activePath){
      await getMenus()
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