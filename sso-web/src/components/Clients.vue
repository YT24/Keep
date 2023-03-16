<template>
  <div class="main">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="">
        <el-input v-model="keyword" placeholder="应用名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search(keyword)">搜索</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="tableData"
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
          label="应用名称"
          width="180">
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
          width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.clientId }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="应用密钥"
          width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.clientSecret }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="认证协议"
          width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.protocol }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="回调地址"
          width="180">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.serviceName }}</el-tag>
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
        :current-page="currentPage"
        :page-sizes="[100, 200, 300, 400]"
        :page-size="100"
        layout="total, sizes, prev, pager, next, jumper"
        :total="400">
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";

export default {
  created() {
    this.getClients()
  },
  data() {
    return {
      tableData: [],
      currentPage: 1,
      keyword:''
    }
  },
  methods: {
    //搜索
    search(keyword) {
      console.log(keyword);
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    async getClients() {
      axios.get("/keep-sso/keepClients").then(resp => {
        console.log(resp.data)
        this.tableData = resp.data.data
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
    }
  }
}
</script>
<style>
.el-pagination {
  margin-top: 10px;
}
</style>