<script setup>

import PreviewCard from "@/componet/PreviewCard.vue";
import {reactive, ref} from "vue";
import {get} from "@/net";
import DetailCard from "@/componet/DetailCard.vue";
import {Connection, Plus} from "@element-plus/icons-vue";
import RegisterCard from "@/componet/RegisterCard.vue";
import TerminalCard from "@/componet/TerminalCard.vue";
const list=ref([])
const updateList=()=>{
  get('/api/monitor/list',data=>{
    list.value=data
  } )
}
const refreshToken = () => get('/api/monitor/register', token => register.token = token)
const detail=reactive({
  show:false,
  id:-1,
  ip:''
})
const register=reactive({
  show:false,
  token:''
})
const openTerminal=(id,ip)=> {
  terminal.show = true
  terminal.id = id
  terminal.ip=ip
  detail.show = false
}
const terminal = reactive({
  show: false,
  id: -1
})
setInterval(updateList,5000)
updateList()
const selectCard=(id)=>{
  detail.id=id
  detail.show=true
}
</script>

<template>
  <div class="manage-main">
    <div style="display: flex;justify-content: space-between;align-items: end">
      <div>
        <div class="manage-tittle">
          <i class="fa-solid fa-server" ></i><span style="margin: 0 10px;">管理主机</span>
        </div>
        <div style="color: gray;font-size: 13px;">
          在这里管理所有已经注册的主机实例，实时监控主机运行状态，快速进行管理和操作。
        </div>
      </div>
      <div style="display: flex;gap: 10px;">
        <div>
          <el-button :icon="Connection" type="primary" plain
                     @click="terminal.show=true;terminal.id=-10">SSH直连</el-button>
        </div>
        <div>
          <el-button :icon="Plus" type="primary" plain
                     @click="register.show = true">添加新主机</el-button>
        </div>
      </div>

    </div>

    <el-divider style="margin: 10px 0;"></el-divider>
    <div class="card_list">
      <preview-card v-for="item in list" :data="item" :update="updateList" @click="selectCard(item.id)"/>
    </div>
    <el-drawer size="520" :show-close="false" v-model="detail.show"
               :with-header="false" v-if="list.length" @close="detail.id = -1">
    <detail-card :id="detail.id" :update="updateList" @delete="updateList" @terminal="openTerminal"/>
    </el-drawer>
    <el-drawer v-model="register.show" direction="btt" :with-header="false"
               style="width: 600px;margin: 10px auto" size="320" @open="refreshToken">
      <register-card :token="register.token"/>
    </el-drawer>
    <el-drawer style="width: 900px;margin:170px auto;" :size="600" direction="btt"
               @close="terminal.id = -1"
               v-model="terminal.show" :close-on-click-modal="false">
      <template #header>
        <div>
          <div style="font-size: 18px;color: dodgerblue;font-weight: bold;">SSH远程连接</div>
          <div style="font-size: 14px">
            远程连接的建立将由服务端完成，因此在内网环境下也可以正常使用。
          </div>
        </div>
      </template>
      <terminal-card :id="terminal.id" :ip="terminal.ip"/>
    </el-drawer>
  </div>

</template>

<style scoped>
:deep(.el-drawer) {
  margin: 10px;
  height: calc(100% - 20px);
  border-radius: 10px;
}
.manage-main{
  margin: 0 50px;
}
.manage-tittle{
  font-size: 20px;
}
.card_list{
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}
</style>