<script setup>

import PreviewCard from "@/componet/PreviewCard.vue";
import {reactive, ref} from "vue";
import {get} from "@/net";
const list=ref([])
const updateList=()=>{
  get('/api/monitor/list',data=>{
    list.value=data
  } )
}
const detail=reactive({
  show:false,
  id:-1
})
setInterval(updateList,5000)
updateList()
</script>

<template>
  <div class="manage-main">
    <div class="manage-tittle">
      <i class="fa-solid fa-server" ></i>
      <span style="margin: 0 10px;">管理主机</span>
    </div>
    <div style="color: gray;font-size: 13px;">
      在这里管理所有已经注册的主机实例，实时监控主机运行状态，快速进行管理和操作。
    </div>
    <el-divider style="margin: 10px 0;"></el-divider>
    <div class="card_list">
      <preview-card v-for="item in list" :data="item" :update="updateList" @click="detail.show=true"/>
    </div>
    <el-drawer size="520" :show-close="false" v-model="detail.show"
               :with-header="false" v-if="list.length" @close="detail.id = -1">
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