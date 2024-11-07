import { createRouter, createWebHistory } from 'vue-router';

// Import the components for the routes
import HomePage from '../components/HomePage.vue';
import Test from '../components/Test.vue';
import PostList from '../components/PostList.vue';
import Trends from '../components/Trends.vue';
import NearbyPosts from '../components/NearbyPosts.vue';
import Chat from '../components/Chat.vue';
import Profile from '../components/Profile.vue';


const routes = [
  { path: '/', name: 'Home', component: HomePage },
  { path: '/posts', name: 'PostList', component: PostList },
  { path: '/trends', name: 'Trends', component: Trends },
  { path: '/nearby-posts', name: 'NearbyPosts', component: NearbyPosts },
  { path: '/chat', name: 'Chat', component: Chat },
  { path: '/profile', name: 'Profile', component: Profile },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
