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
  <MemberList v-if="showList" @go-detail="changeDisplay"></MemberList>
  <MemberDetail v-else @go-list="changeDisplay"></MemberDetail>
</template>

<style scoped>
</style>