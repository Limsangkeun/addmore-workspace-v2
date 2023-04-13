<script setup>
import MemberList from "@/views/admin/member/MemberList.vue";
import MemberDetail from '@/views/admin/member/MemberDetail.vue';
import {ref} from "vue";

const list = ref(null);
const detail = ref(null);

const fnSelectUser = function (userId) {
  detail.value.load(userId);
}

const fnCreateUser = () => {
  detail.value.fnNewUser();
}

const fnSaveCompleted = () => {
  list.value.fnSearch();
}

</script>

<template>
  <div class="card flex-1">
    <div class="grid">
      <div class="col-6">
        <MemberList ref="list" @select-user="fnSelectUser" @create-user="fnCreateUser"></MemberList>
      </div>
      <div class="col-6">
        <MemberDetail ref="detail" @save-completed="fnSaveCompleted"></MemberDetail>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>