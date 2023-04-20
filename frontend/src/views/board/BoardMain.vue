<script setup>
import {ref} from "vue";
import BoardList from "@/views/board/BoardList.vue";
import BoardDetail from "@/views/board/BoardDetail.vue";

const list = ref(null);
const detail = ref(null);
const showDetail = ref(false);

const fnCreateBoard = () => {
  showDetail.value = true;
}

</script>

<template>
  <BoardList ref="list" @create-board="fnCreateBoard" v-if="!showDetail"></BoardList>
  <BoardDetail ref="detail" v-if="showDetail"></BoardDetail>
</template>

<style scoped>

</style>