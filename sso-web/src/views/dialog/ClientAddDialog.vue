<template>
  <el-dialog title="应用添加" :visible.sync="show">
    <el-form :model="formData">
      <el-form-item label="应用名称" :label-width="formLabelWidth">
        <el-input v-model="formData.serviceName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="应用ID" :label-width="formLabelWidth">
        <el-input v-model="formData.clientId" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="应用密钥" :label-width="formLabelWidth">
        <el-input v-model="formData.clientSecret" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="应用回调地址" :label-width="formLabelWidth">
        <el-input v-model="formData.callbackUrl" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="认证协议" :label-width="formLabelWidth">
        <el-input v-model="formData.protocol" autocomplete="off"></el-input>
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
  name: "ClientAddDialog",
  props: {
    showAddDialog: false
  },
  data() {
    return {
      formData: {
        id: '',
        serviceName: '',
        clientId: '',
        clientSecret: '',
        callbackUrl: '',
        protocol: ''
      },
      show: false,
      formLabelWidth: '120px'
    }
  },
  created() {
    this.show = this.showAddDialog
  },

  methods: {
    cancle(value) {
      console.log("dialog 取消 or 确认: " + value)
      // this.showAddDialog = value
      this.show = value
    },
    confirm(value) {
      axios.post("/keep-sso/keepClients",this.formData).then(resp => {
        if (resp.data.code !== 200) {
          alert(resp.data.msg)
        }
        this.showAddDialog = value
      }).catch(err => {
        console.log(err)
      });
      this.cancle(false);
      this.$router.push({ path: item.url })
    }
  }
}
</script>

<style scoped>

</style>