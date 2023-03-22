<template>

  <el-dialog title="角色添加" :visible.sync="show">
    <el-form :model="formData">
      <el-form-item label="角色名称" :label-width="formLabelWidth">
        <el-input v-model="formData.name" autocomplete="off"></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancle(false)">取 消</el-button>
      <el-button type="primary" @click="confirm(false)">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from "axios";

export default {
  name: "RoleAddDialog",
  props: {
    showAddRoleDialog: false
  },
  data() {
    return {
      formData: {
        name: ''
      },
      show: false,
      formLabelWidth: '120px'
    }
  },
  created() {
    this.show = this.showAddRoleDialog
  },

  methods: {
    cancle(value) {
      this.show = value
    },
    confirm(value) {
      axios.post("/keep-sso/keepRoles", this.formData).then(resp => {
        if (resp.data.code !== 200) {
          alert(resp.data.msg)
        } else {
          this.cancle(value)
          this.$emit("successAdd")
        }
      }).catch(err => {
        console.log(err)
      });
    }
  }
}
</script>

<style lang="less" scoped>

</style>