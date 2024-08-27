<template>
  <div class="main">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input class="search-input" v-model="keyword" placeholder="请输入内容"></el-input>
        </el-col>
        <el-col :span="16">
          <el-button type="primary" @click="search(keyword)">搜索</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addRes(true)">新建应用</el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-table
        :data="tableData"
        style="width: 100%">
      <el-table-column
          label="ID">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="应用名称"
      >
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.serviceName }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
          label="应用ID"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.clientId }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="应用密钥"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.clientSecret }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="认证协议"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.protocol }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="回调地址"
      >
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.callbackUrl }}</el-tag>
            </div>
          </el-popover>
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
    <ClientEditDialog v-if="showDialog"
                      :showDialog="showDialog"
                      :clientDialogData="clientDialogData"
                      @on-result-change="changeIsShowDialog"
                      @dialog-operation="operation"/>
    <ClientAddDialog v-if="showAddDialog" :showAddDialog="showAddDialog" @success="getClients"/>
  </div>
</template>

<script>
import axios from "axios";
import ClientEditDialog from "../dialog/client/ClientEditDialog.vue"
import ClientAddDialog from "@/views/dialog/client/ClientAddDialog";

export default {

  components: {ClientEditDialog, ClientAddDialog},
  data() {
    return {
      tableData: [],
      keyword: '',
      showDialog: false,
      showAddDialog: false,
      clientDialogData: {
        id: '',
        serviceName: '',
        clientId: '',
        clientSecret: '',
        callbackUrl: '',
        protocol: ''
      },
      pageSize: 10,
      total: 0,
      currentPage: 1,

    }
  },
  created() {
    this.getClients()
  },
  methods: {
    // 获取应用列表
    async getClients() {
      axios.get("/keep-sso/keepClients").then(resp => {
        this.tableData = resp.data.data.list
        this.currentPage = resp.data.data.currentPage
        this.total = resp.data.data.total
      }).catch(err => {
        console.log(err.message)
      });
    },

    // 编辑应用
    operation(val) {
      this.showDialog = val;
    },
    changeIsShowDialog(val) {
      this.showDialog = val;  //监听变化时触发的函数修改父组件的是否显示状态
    },
    //搜索
    search(keyword) {
      axios.get("/keep-sso/keepClients?keyword=" + keyword).then(resp => {
        this.tableData = resp.data.data.list
      }).catch(err => {
        console.log(err.message)
      });
    },
    // 展示编辑应用对话框
    handleEdit(index, row) {
      this.showDialog = true
      this.clientDialogData = row
    },
    handleDelete(index, row) {
      console.log("删除："+row.id)
      axios.delete("/keep-sso/keepClients/"+row.id).then(resp => {
        if (resp.data.code !== 200) {
          alert(resp.data.msg)
        } else {
          this.search('')
        }
      }).catch(err => {
        console.log(err.message)
      });
    },
    // 分页方法
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    addRes(val) {
      this.showAddDialog = val
    }
  }
}
</script>
<style lang="less" scoped>
.el-pagination {
  margin-top: 400px;
  text-align: center;
}
</style>