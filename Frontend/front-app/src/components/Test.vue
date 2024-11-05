<template>
    <div>
      <h1>List of Teachers</h1>
      <p v-if="loading">Loading...</p>
      <ul v-if="!loading && teachers.length">
        <li v-for="teacher in teachers" :key="teacher.id">
          {{ teacher.firstName }} {{ teacher.lastName }}
        </li>
      </ul>
      <p v-else-if="!loading && error">{{ error }}</p>
      <p v-else>No teachers found.</p>
    </div>
  </template>


<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';

const teachers = ref([]);       // Store the list of teachers
const loading = ref(false);      // Track loading state
const error = ref(null);         // Store any error messages

onMounted(() => {
    loadTeachers();
})

function loadTeachers() {
    loading.value = true; // Set loading to true at the start
    error.value = null;   // Reset any previous error
    axios.get('http://localhost:8080/api/teachers/all/')
    .then(response => {
      console.log(response.data);
      });
    
    // teachers.value = response.data; // Store the teacher data
    // } catch (err) {
    //     error.value = `Failed to load teachers: ${err.message}`; // Capture any error
    // } finally {
    //     loading.value = false; // End the loading state
    // }
  }
</script>