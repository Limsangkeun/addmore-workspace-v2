<script setup>
  const props = defineProps({isLoading:Boolean});
</script>

<template>
  <div class="loading-container" v-if="props.isLoading">
    <div class="loading-bar"></div>
  </div>
</template>

<style>
.loading-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 9999;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.loading-bar {
  width: 80%;
  height: 10px;
  background-color: #007aff;
  animation: loading 2s infinite;
}

@keyframes loading {
  0% {
    width: 0%;
  }
  50% {
    width: 50%;
  }
  100% {
    width: 100%;
  }
}
</style>