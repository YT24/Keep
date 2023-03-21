<template>
  <div class="main">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input class="search-input" v-model="keyword" placeholder="请输入内容"></el-input>
        </el-col>
        <el-col :span="12">
          <el-button type="primary" @click="search(keyword)">搜索</el-button>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" @click="addRes(true)">新建菜单</el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-table
        :data="menusData"
        style="width: 100%">
      <el-table-column
          label="ID"
          width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="菜单名称"
          width="180">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
          label="菜单URL"
          width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.url }}</span>
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
  },
  data() {
    return {
      tableData: [],
      keyword: '',
      showDialog: false,
      showAddDialog:false,
      menusData: {
        id: '',
        name: '',
        url: ''
      },
      pageSize: 10,
      total: 0,
      currentPage: 1,

    }
  },
  methods: {
    // 获取菜单列表
    async getRoles() {
      axios.get("/keep-sso/keepRoles").then(resp => {
        this.tableData = resp.data.data.list
        this.currentPage = resp.data.data.currentPage
        this.total = resp.data.data.total
      }).catch(err => {
        console.log(err.message)
      });
    },

    // 编辑菜单
    operation(val) {
      this.showDialog = val;
    },
    changeIsShowDialog(val) {
      console.log("changeIsShowDialog:" + val);
      this.showDialog = val;  //监听变化时触发的函数修改父组件的是否显示状态
    },

    //搜索
    search(keyword) {
      axios.get("/keep-sso/keepMenus?keyword=" + keyword).then(resp => {
        this.tableData = resp.data.data.list
      }).catch(err => {
        console.log(err.message)
      });
    },
    // 展示编辑菜单对话框
    handleEdit(index, row) {
      this.showDialog = true
      this.clientDialogData = row
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    // 分页方法
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    addRes(val){
      this.showAddDialog = val
    }
  }
}
</script>
<style>

</style>