<script setup>
  import {onMounted, ref} from "vue";
  import useSessionStore from "@/store/sessionStore";
  import Editor from 'ckeditor5-custom-build/src/ckeditor'
  import SimpleUploadAdapter from "@ckeditor/ckeditor5-upload/src/adapters/simpleuploadadapter.js";

  const sessionStore = useSessionStore();

  const editor = ref(null);
  const content = ref('');
  const props = defineProps({
    height: Number
  });


  onMounted(()=> {
    Editor.create(editor.value, {
      language: 'ko',
      placeholder: '내용을 입력하세요.'
    }).then(editor=> {
      console.log(editor);
    }).catch(err => {
      console.log(err);
    })
  });

</script>

<template>
  <div class="mb-2">
    <div ref="editor"></div>
  </div>
</template>

<style scoped>
</style>