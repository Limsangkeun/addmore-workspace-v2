<script setup>
  import Editor from "ckeditor5-custom-build/build/ckeditor";
  import {onMounted, ref} from "vue";
  import useSessionStore from "@/store/sessionStore";

  const sessionStore = useSessionStore();

  const props = defineProps({
    height: Number
  });

  const editor = ref(null);

  onMounted(()=> {
    Editor.create(editor.value, {
      plugins: ['SimpleUploadAdapter'],
      language: 'ko',
      placeholder: '내용을 입력하세요.',
      simpleUpload: {
        uploadUrl: '/api/file/image-upload',
        withCredentials: true,
        headers: {
          Authorization: 'Bearer ' + sessionStore.getToken
        }
      }
    }).then(editor=> {
      console.log(editor);
    }).catch(err => {
      console.log(err);
    })
  });


</script>

<template>
  <div class="mb-2">
    <<div ref="editor"></div>>
  </div>
</template>

<style scoped>
</style>