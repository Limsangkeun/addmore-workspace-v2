<script setup>
  import ClassicEditor from '@ckeditor/ckeditor5-build-classic'
  import Ckeditor from '@ckeditor/ckeditor5-vue';
  import {onMounted, ref, useAttrs} from "vue";

  const props = defineProps({
    height: Number
  });

  const editor = ref(null);

  onMounted(()=> {
    ClassicEditor.create(editor.value, {

    }).then(editor=> {
      console.log(editor);
    });
  });
</script>

<template>
  <div>
    <div ref="editor">
      <Ckeditor></Ckeditor>
    </div>
  </div>
</template>

<style scoped>

</style>