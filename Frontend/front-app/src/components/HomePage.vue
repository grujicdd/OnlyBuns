<script setup>
</script>

<template>
  <div>
    <h1>Welcome to Your Home Page</h1>
    <nav>
      <ul>
        <li><router-link to="/posts">Followed Posts</router-link></li>
        <li><router-link to="/trends">Trends</router-link></li>
        <li><router-link to="/nearby-posts">Nearby Posts</router-link></li>
        <li><router-link to="/chat">Chat</router-link></li>
        <li><router-link to="/profile">My Profile</router-link></li>
      </ul>
    </nav>
    <router-view></router-view>
  </div>
</template>

<style scoped>
h1 {
  text-align: center;
  color: #333;
}

nav ul {
  list-style: none;
  padding: 0;
  text-align: center;
}

nav ul li {
  display: inline-block;
  margin: 0 10px;
}

nav ul li a {
  text-decoration: none;
  color: #007bff;
  font-weight: bold;
}

nav ul li a:hover {
  color: #0056b3;
}
</style>
