<script setup>
import MemberList from "@/views/admin/member/MemberList.vue";
import MemberDetail from '@/views/admin/member/MemberDetail.vue';
import {ref} from "vue";

const detail = ref(null);
let showList = ref(true);

defineExpose()

const changeDisplay = (display, mode) => {
  if(display === 'list') {
    showList.value = true;
  } else {
    showList.value = false;
  }
}

const selectUser = function (userId) {
  detail.value.load(userId);
}

const createUser = () => {
  detail.value.fnNewUser();
}

</script>

<template>
  <div class="card flex-1">
    <div class="grid">
      <div class="col-6">
        <MemberList @select-user="selectUser" @create-user="createUser"></MemberList>
      </div>
      <div class="col-6">
        <MemberDetail ref="detail"></MemberDetail>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>