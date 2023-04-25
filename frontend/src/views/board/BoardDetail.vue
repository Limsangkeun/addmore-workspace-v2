<script setup>
import {ref} from "vue";
import CustomEditor from "../common/ckeditor/CustomEditor.vue";

const tempValue = ref('');
const fileUploader = ref(null);

const fnCreateBoard = () => {
  console.log(tempValue);
}

const fnBeforeUpload = (xhr, formData) => {
console.log(xhr);
console.log(formData)
}

const fnUpload = (event) => {
  const files = event.files;
  console.log(files);
  console.log(fileUploader);
}
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
    <div class="flex flex-column">
      <InputText placeholder="제목을 입력하세요." class="mb-1"></InputText>
      <CustomEditor></CustomEditor>
    </div>
    <panel header="첨부파일">
      <FileUpload ref="fileUploader" name="files" :url="'/api/file/upload'" :multiple="true" @upload="fnUpload" :auto="false" @before-upload="fnBeforeUpload">
        <template #empty>
          <p>드래그하거나 파일을 첨부해주세요</p>
        </template>
      </FileUpload>
    </panel>
  </div>
</template>

<style scoped>

</style>