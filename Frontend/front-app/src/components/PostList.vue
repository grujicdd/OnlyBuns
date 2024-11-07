<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const posts = ref([]);

const fetchPosts = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/posts'); // Endpoint za zapraćene objave
    posts.value = response.data;
  } catch (error) {
    console.error('Error fetching posts:', error);
  }
};

onMounted(() => {
  fetchPosts();
});
</script>

<template>
  <div>
    <h2>Followed Posts</h2>
    <ul v-if="posts.length > 0">
      <li v-for="post in posts" :key="post.id">
        <h3>{{ post.title }}</h3>
        <p>{{ post.content }}</p>
        <p><strong>Author:</strong> {{ post.authorUsername }}</p>
      </li>
    </ul>
    <p v-else>Loading posts...</p>
  </div>
</template>

<style scoped>
ul {
  list-style: none;
  padding: 0;
}

li {
  margin-bottom: 20px;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
}
</style>
