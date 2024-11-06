<template>
  <div>
    <h1>List of Teachers</h1>

    <!-- Add Teacher Button and Form -->
    <button v-if="!isAddingTeacher" @click="addTeacherForm">Add Teacher</button>

    <div v-if="isAddingTeacher">
      <input v-model="newTeacher.firstName" placeholder="First Name" />
      <input v-model="newTeacher.lastName" placeholder="Last Name" />
      <button @click="saveTeacher">Save</button>
      <button @click="cancelAddTeacher">Cancel</button>
    </div>

    <!-- Teacher Table with Edit and Delete Buttons -->
    <table id="teacher-table">
      <thead>
        <tr>
          <th>Name</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="teacher in teachers" :key="teacher.id">
          <td>{{ teacher.firstName }} {{ teacher.lastName }}</td>
          <td>
            <!-- Edit button -->
            <button @click="editTeacher(teacher)">Edit</button>
            <!-- Delete button -->
            <button @click="deleteTeacher(teacher.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Edit Teacher Form (Visible when editing) -->
    <div v-if="isEditingTeacher">
      <input v-model="editTeacherData.firstName" placeholder="First Name" />
      <input v-model="editTeacherData.lastName" placeholder="Last Name" />
      <button @click="updateTeacher">Save Changes</button>
      <button @click="cancelEditTeacher">Cancel</button>
    </div>
  </div>
</template>



<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const teachers = ref([]);
const newTeacher = ref({ firstName: '', lastName: '' });
const isAddingTeacher = ref(false);
const isEditingTeacher = ref(false);
const editTeacherData = ref({ id: null, firstName: '', lastName: '' });

// Load teachers when component is mounted
onMounted(() => {
  loadTeachers();
});

// Load teachers from API
function loadTeachers() {
  axios.get('http://localhost:8080/api/teachers/all')
    .then(response => {
      teachers.value = response.data;
    })
    .catch(error => {
      console.error('Error fetching teachers:', error);
    });
}

// Function to delete teacher (logically)
function deleteTeacher(teacherId) {
  axios.delete(`http://localhost:8080/api/teachers/${teacherId}`)
    .then(response => {
      teachers.value = teachers.value.filter(teacher => teacher.id !== teacherId); // Remove teacher from list
    })
    .catch(error => {
      console.error('Error deleting teacher:', error);
    });
}

// Function to show add teacher form
function addTeacherForm() {
  isAddingTeacher.value = true;
}

// Save new teacher
function saveTeacher() {
  const teacherData = {
    firstName: newTeacher.value.firstName,
    lastName: newTeacher.value.lastName
  };

  axios.post('http://localhost:8080/api/teachers', teacherData)
    .then(response => {
      teachers.value.push(response.data); // Add teacher to local list
      newTeacher.value.firstName = '';
      newTeacher.value.lastName = '';
      isAddingTeacher.value = false; // Hide form after saving
    })
    .catch(error => {
      console.error('Error adding teacher:', error);
    });
}

// Cancel adding new teacher
function cancelAddTeacher() {
  newTeacher.value.firstName = '';
  newTeacher.value.lastName = '';
  isAddingTeacher.value = false;
}

// Function to show edit form for a teacher
function editTeacher(teacher) {
  editTeacherData.value = { ...teacher }; // Copy teacher data into edit form
  isEditingTeacher.value = true; // Show the edit form
}

// Update teacher (matching TeacherDTO)
function updateTeacher() {
  // Prepare the teacherDTO object to send in the PUT request
  const updatedTeacher = editTeacherData.value;

  axios.put('http://localhost:8080/api/teachers', updatedTeacher)
    .then(response => {
      console.log(response.data);
      
      // Find the index of the teacher in the list based on the ID
      const index = teachers.value.findIndex(teacher => teacher.id === response.data.id);
      if (index !== -1) {
        teachers.value[index] = response.data;
      }

      // Hide the form after saving changes
      isEditingTeacher.value = false; 

      editTeacherData.value = { id: null, firstName: '', lastName: '' }; 
    })
    .catch(error => {
      console.error('Error updating teacher:', error);
    });
}


// Cancel editing teacher
function cancelEditTeacher() {
  isEditingTeacher.value = false;
  editTeacherData.value = { id: null, firstName: '', lastName: '' };
}
</script>



<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 10px;
  text-align: left;
  border: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
}

button {
  margin-right: 10px;
  padding: 5px 10px;
  cursor: pointer;
}

button:hover {
  background-color: #f0f0f0;
}

input {
  margin-right: 10px;
  padding: 5px;
  margin-bottom: 10px;
}

.error {
  color: red;
}
</style>
