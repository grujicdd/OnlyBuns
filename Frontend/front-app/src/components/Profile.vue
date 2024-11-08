<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

// Define refs to store users and post counts
const users = ref([]);
const postCounts = ref({});

// Define reactive variables for search filters
const searchFirstName = ref('');
const searchLastName = ref('');
const searchEmail = ref('');
const searchPostCount = ref('');

// Define reactive variable for sorting direction (ascending/descending)
const sortEmailAscending = ref(true);

// Fetch users from the API
const fetchUsers = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/users'); // Replace with your actual endpoint
    users.value = response.data; // Assign the fetched users to the ref
    console.log(response.data);
  } catch (error) {
    console.error('Error fetching users:', error);
  }
};

// Fetch post counts from the API
const fetchPostCounts = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/users/postCounts'); // Replace with your actual endpoint
    postCounts.value = response.data; // Assign post counts to the ref, mapping user IDs to post counts
  } catch (error) {
    console.error('Error fetching post counts:', error);
  }
};

// Computed property to filter and sort users based on search and sort inputs
const filteredUsers = computed(() => {
  const filtered = users.value.filter(user => {
    const postCount = postCounts.value[user.id] || 0;
    const firstNameMatches = user.firstName.toLowerCase().includes(searchFirstName.value.toLowerCase());
    const lastNameMatches = user.lastName.toLowerCase().includes(searchLastName.value.toLowerCase());
    const emailMatches = user.email.toLowerCase().includes(searchEmail.value.toLowerCase());
    const postCountMatches = postCount.toString().includes(searchPostCount.value);

    return firstNameMatches && lastNameMatches && emailMatches && postCountMatches;
  });

  // Sort by email (ascending or descending)
  return filtered.sort((a, b) => {
    const emailA = a.email.toLowerCase();
    const emailB = b.email.toLowerCase();

    if (emailA < emailB) return sortEmailAscending.value ? -1 : 1;
    if (emailA > emailB) return sortEmailAscending.value ? 1 : -1;
    return 0;
  });
});

// Fetch users and post counts when the component is mounted
onMounted(() => {
  fetchUsers();
  fetchPostCounts();
});
</script>

<template>
  <div>
    <h2>All Users</h2>

    <!-- Search Form -->
    <div>
      <input v-model="searchFirstName" type="text" placeholder="Search by First Name" />
      <input v-model="searchLastName" type="text" placeholder="Search by Last Name" />
      <input v-model="searchEmail" type="text" placeholder="Search by Email" />
      <input v-model="searchPostCount" type="text" placeholder="Search by Post Count" />
    </div>

    <!-- Display users in a table format -->
    <table>
      <thead>
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Name</th>
          <th>Email <span @click="sortEmailAscending = !sortEmailAscending" style="cursor: pointer;">
            ({{ sortEmailAscending ? '▲' : '▼' }})
          </span></th>
          <th>Number of Posts</th>
          <th>Number of Followers</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in filteredUsers" :key="user.id">
          <td>{{ user.firstName }}</td>
          <td>{{ user.lastName }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>                 
          <!-- Use the postCounts object to display the post count for each user, defaulting to 0 if not found -->
          <td>{{ postCounts[user.id] || 0 }}</td>
          <td>{{ user.followerCount }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
/* Optional: Style the table for better readability */
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 8px;
  border: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
  text-align: left;
}

input {
  margin: 5px;
  padding: 5px;
  border: 1px solid #ddd;
}

button {
  margin: 10px 0;
  padding: 5px 10px;
  border: 1px solid #ddd;
  background-color: #f4f4f4;
  cursor: pointer;
}

button:hover {
  background-color: #e0e0e0;
}
</style>
