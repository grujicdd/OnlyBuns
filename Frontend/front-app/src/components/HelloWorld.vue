<script setup>
import { ref } from 'vue'

</script>

<template>
  <div>
    <h1>Welcome to the Home Page</h1>
    <RouterLink to="/test">
      <button>Go to Test Component</button>
    </RouterLink>
    <router-view></router-view>
  </div>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
