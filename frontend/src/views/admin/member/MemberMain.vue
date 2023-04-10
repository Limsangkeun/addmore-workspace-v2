<script setup>
import MemberList from "@/views/admin/member/MemberList.vue";
import MemberDetail from '@/views/admin/member/MemberDetail.vue';
import {ref} from "vue";

let showList = ref(true);

const changeDisplay = (display, mode) => {
  if(display === 'list') {
    showList.value = true;
  } else {
    showList.value = false;
  }
}

</script>

<template>
  <div class="card flex-1">
    <div class="grid">
      <div class="col-6">
        <MemberList></MemberList>
      </div>
      <div class="col-6">
        <MemberDetail></MemberDetail>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>