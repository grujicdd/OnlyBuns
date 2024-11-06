import { createRouter, createWebHistory } from 'vue-router';

// Import the components for the routes
import HelloWorld from '../components/HelloWorld.vue';
import Test from '../components/Test.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HelloWorld, // Default page, will be shown at root '/'
  },
  {
    path: '/test',
    name: 'Test',
    component: Test, // Page for '/test'
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
