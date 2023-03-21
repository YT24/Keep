<template>
  <div class="main">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input class="search-input" v-model="keyword" placeholder="请输入内容"></el-input>
        </el-col>
        <el-col :span="16">
          <el-button type="primary" @click="getUsers(keyword)">搜索</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addRes(true)">新建用户</el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-table
        :data="usersData"
        style="width: 100%">
      <el-table-column
          label="ID"
          >
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="用户名"
          >
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.username }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
          label="姓名"
          >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.realName }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="手机号"
          >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.mobile }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="邮箱"
          >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.email }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page=currentPage
        :page-sizes="[10, 20, 30, 50]"
        :page-size=pageSize
        layout="total, sizes, prev, pager, next, jumper"
        :total=total>
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";


export default {

  components: {},
  created() {
    this.getUsers(this.keyword)
  },
  data() {
    return {
      usersData: [],
      keyword: '',
      showDialog: false,
      showAddDialog:false,
      userData: {
        id: '',
        username: '',
        realName: '',
        mobile: '',
        email: ''
      },
      pageSize: 10,
      total: 0,
      currentPage: 1,

    }
  },
  methods: {
    // 获取菜单列表
    async getUsers(keyword) {
      axios.get("/keep-sso/keepUsers?keyword=" + keyword).then(resp => {
        this.usersData = resp.data.data.list
        this.currentPage = resp.data.data.currentPage
        this.pageSize = resp.data.data.pageSize
        this.total = resp.data.data.total
      }).catch(err => {
        console.log(err.message)
      });
    },

    // 分页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    }
  }
}
</script>
<style>

</style>