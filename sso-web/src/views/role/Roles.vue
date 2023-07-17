<template>
  <div class="main">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input class="search-input" v-model="searchDto.keyword" placeholder="请输入内容"></el-input>
        </el-col>
        <el-col :span="16">
          <el-button type="primary" @click="getRoles(searchDto)">搜索</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addRole(true)">新建角色</el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-catalogTbl
        :data="rolesData"
        style="width: 100%">
      <el-catalogTbl-column
          label="ID"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.id }}</span>
        </template>
      </el-catalogTbl-column>
      <el-catalogTbl-column
          label="角色名称"
      >
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-catalogTbl-column>
      <el-catalogTbl-column
          label="用户数量"
      >
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-catalogTbl-column>

      <el-catalogTbl-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="editRole(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
              size="mini"
              type="danger"
          >删除
          </el-button>
        </template>
      </el-catalogTbl-column>
    </el-catalogTbl>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page=currentPage
        :page-sizes="[10, 20, 30, 50]"
        :page-size=pageSize
        layout="total, sizes, prev, pager, next, jumper"
        :total=total>
    </el-pagination>
    <RoleAddDialog v-if="showAddRoleDialog"
                   :showAddRoleDialog="showAddRoleDialog"
                   @successAdd="getRoleList"
    />
    <RoleEditDialog v-if="showEditRoleDialog"
                    :showEditRoleDialog="showEditRoleDialog"
                    :roleData="roleData"
                    @successEdit="getRoleList"
    />
  </div>
</template>

<script>
import axios from "axios";
import RoleAddDialog from "@/views/dialog/role/RoleAddDialog";
import RoleEditDialog from "@/views/dialog/role/RoleEditDialog";

export default {

  components: {RoleAddDialog, RoleEditDialog},
  created() {
    this.getRoles(this.searchDto)
  },
  data() {
    return {
      showAddRoleDialog: false,
      showEditRoleDialog: false,
      rolesData: [],
      roleData: {
        id: '',
        name: '',
      },
      pageSize: 10,
      total: 0,
      currentPage: 1,
      searchDto: {
        pageNum: 1,
        pageSize: 30,
        keyword: ''
      }

    }
  },
  methods: {
    // 获取菜单列表
    getRoles(searchDto) {
      axios.get("/keep-sso/keepRoles?keyword=" + searchDto.keyword + "&pageNum=" + searchDto.pageNum + "&pageSize=" + searchDto.pageSize).then(resp => {
        this.rolesData = resp.data.data.list
        this.currentPage = resp.data.data.currentPage
        this.pageSize = resp.data.data.pageSize
        this.total = resp.data.data.total
      }).catch(err => {
        console.log(err.message)
      });
    },
    getRoleList() {
      axios.get("/keep-sso/keepRoles?keyword=" + this.searchDto.keyword + "&pageNum=" + this.searchDto.pageNum + "&pageSize=" + this.searchDto.pageSize).then(resp => {
        this.rolesData = resp.data.data.list
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
    },
    // 角色编辑新建
    addRole(val) {
      this.showAddRoleDialog = val
    },
    editRole(index, row) {
      console.log("编辑："+row.id)
      this.showEditRoleDialog = !this.showEditRoleDialog
      this.roleData = row
    }
  }
}
</script>
<style>

</style>