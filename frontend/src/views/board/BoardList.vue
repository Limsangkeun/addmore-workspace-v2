<script setup>
  import {ref} from "vue";

  const emits = defineEmits(['createBoard']);

  const postList = ref([]);

  const fnCreateBoard = () => {
    emits('createBoard');
  };
</script>

<template>
  <div class="card flex-1">
    <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center mb-3">
      <h5 class="m-0 font-bold">게시판 목록</h5>
      <div class="my-2 flex flex-row">
        <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
                  <i class="pi pi-search"/>
              <InputText class="p-inputtext-sm" placeholder="제목 검색" @keydown=""/>
                </span>
        <Button class="p-button-sm" label="생성" @click="fnCreateBoard"></Button>
      </div>
    </div>
    <data-table
      id="boardGrid"
      class="p-datatable-sm"
      :value="postList"
      show-gridlines
      scrollable
    >
      <column header="제목" body-class="link"></column>
      <column header="작성자" class="f-120-px"></column>
      <column header="작성일" class="f-120-px"></column>
      <column header="조회수" class="f-100-px"></column>
      <column></column>
    </data-table>
  </div>
</template>

<style scoped>

</style>