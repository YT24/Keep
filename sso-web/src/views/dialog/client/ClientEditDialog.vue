<template>
  <el-dialog title="应用编辑" :visible.sync="show">
    <el-form :model="formData">
      <el-form-item label="记录ID" :label-width="formLabelWidth">
        <el-input v-model="formData.id" autocomplete="off"></el-input>
      </el-form-item>
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
      <el-button @click="operation(false)">取 消</el-button>
      <el-button type="primary" @click="confirm(false)">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from "axios";
export default {
  name: "ClientDialog",
  props: {
    clientDialogData: {},
    showDialog: false
  },
  data() {
    return {
      formData: {},
      show: false,
      formLabelWidth: '120px'
    }
  },
  created() {
    console.log("dialog 打印")
    this.formData = this.clientDialogData
    this.show = this.showDialog
  },

  methods: {
    operation(value) {
      console.log("dialog 取消 or 确认: " + value)
      this.show = value
      this.$emit('dialog-operation', value)
    },
    confirm(value) {
      axios.put("/keep-sso/keepClients/"+this.formData.id,this.formData).then(resp =>{
        if(resp.data.code !== 200){
          alert(resp.data.msg)
        }
        this.show = value
      }).catch(err =>{console.log(err)});
    }
  }
}
</script>

<style scoped>

</style>