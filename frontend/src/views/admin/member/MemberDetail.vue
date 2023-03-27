<script setup>
const emits = defineEmits(['goList']);
const goList = () => {
  emits('goList', 'list');
}
</script>

<template>
  <div class="grid">
    <div class="col-12">
      <h6>유저 상세</h6>
      <Button @click="goList"></Button>
    </div>
  </div>
</template>

<style scoped></style>