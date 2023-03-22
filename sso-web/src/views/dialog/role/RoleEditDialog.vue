<template>
  <div>
    <el-dialog title="角色编辑" :visible.sync="show">
      <el-container>
        <el-aside width="200px">Aside</el-aside>
        <el-main>Main</el-main>
      </el-container>
      <div slot="footer" class="dialog-footer">
        <el-button @click="operation(false)">取 消</el-button>
        <el-button type="primary" @click="confirm(false)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ClientDialog",
  props: {
    roleData: {},
    showEditRoleDialog: false
  },
  data() {
    return {
      formData: {},
      show: false,
      formLabelWidth: '120px',

    }
  },
  created() {
    this.formData = this.roleData
    this.show = this.showEditRoleDialog
  },

  methods: {
    operation(value) {
      console.log("dialog 取消 or 确认: " + value)
      this.show = !this.show
      this.$emit('dialog-operation',  !this.show)
    },
    confirm(value) {
      axios.put("/keep-sso/keepRoles/" + this.formData.id, this.formData).then(resp => {
        if (resp.data.code !== 200) {
          alert(resp.data.msg)
        } else {
          this.cancle(value)
          this.$emit("successEdit")
        }
      }).catch(err => {
        console.log(err)
      });
    }
  }
}
</script>

<style scoped>

</style>