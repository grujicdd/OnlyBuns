<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const posts = ref([]);

const fetchPosts = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/posts'); // Fetch posts with comments included
    posts.value = response.data;
    console.log(response.data)
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
        
        <!-- Display the image if it exists -->
        <div v-if="post.imagePath">
          <img :src="`src/assets/${post.imagePath}`" alt="Post image" class="post-image" width="250" height="250"/>
        </div>

        <p>{{ post.description }}</p>
        <p><strong>Author:</strong> {{ post.authorUsername }}</p>
        
        <!-- Display formatted creation date -->
        <p><strong>Created At:</strong> {{ new Date(post.createdAt).toLocaleString() }}</p>
        <p><strong>Likes:</strong> {{ post.likes }}</p>
        
        <!-- Display comments -->
        <div v-if="post.comments && post.comments.length > 0">
          <h4>Comments:</h4>
          <ul>
            <li v-for="comment in post.comments" :key="comment.id">
              <p><strong>{{ comment.author }}:</strong> {{ comment.content }}</p>
              <p><small>{{ new Date(comment.createdAt).toLocaleString() }}</small></p>
            </li>
          </ul>
        </div>
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

.post-image {
  max-width: 100%;
  height: auto;
  border-radius: 5px;
  margin-bottom: 10px;
}
</style>

