<script setup>
import {ref} from "vue";

const tempValue = ref('');
</script>

<template>
  <div class="card flex-1">
    <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center mb-3">
      <h5 class="mt-2 font-bold">게시판 상세</h5>
      <div class="my-2 flex flex-row">
        <Button class="p-button-sm w-110-px mr-2" icon="pi pi-save" label="작성완료" @click="fnCreateBoard"></Button>
        <Button class="p-button-sm w-110-px mr-2" icon="pi pi-refresh" label="취소" @click="fnCreateBoard"></Button>
        <Button class="p-button-sm p-button-danger w-110-px" icon="pi pi-trash" label="삭제" @click="fnCreateBoard"></Button>
      </div>
    </div>
    <div>
      <Editor v-model="tempValue" editor-style="height:320px"></Editor>
    </div>
    <FileUpload :multiple="true"></FileUpload>
  </div>
</template>

<style scoped>

</style>