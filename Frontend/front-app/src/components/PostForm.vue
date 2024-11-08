<template>
    <div>
      <h1>Create a Post</h1>
      <form @submit.prevent="createPost">
        <input type="text" v-model="title" placeholder="Enter title" required />
        <input type="text" v-model="description" placeholder="Enter description" required />
        <input type="file" @change="handleFileUpload" required /> <!-- Image file input -->
        <input type="text" v-model="location" placeholder="Enter location" />
        <button type="submit" :disabled="isSubmitting">
          {{ isSubmitting ? "Creating Post..." : "Submit" }}
        </button>
      </form>
      <div v-if="imagePreview" class="preview">
        <h3>Preview:</h3>
        <img :src="imagePreview" alt="Image Preview" />
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    data() {
      return {
        title: "",
        description: "",
        image: null,
        location: "",
        imagePreview: null, // Preview for the uploaded image
        isSubmitting: false, // Disable the button while submitting
      };
    },
    methods: {
      handleFileUpload(event) {
        const file = event.target.files[0];
        this.image = file;
        // Generate an image preview
        if (file) {
          const reader = new FileReader();
          reader.onload = (e) => {
            this.imagePreview = e.target.result;
          };
          reader.readAsDataURL(file);
        }
      },
      async createPost() {
        this.isSubmitting = true; // Disable the button during submission
        const formData = new FormData();
        formData.append("title", this.title);
        formData.append("description", this.description);
        formData.append("image", this.image); // Send the file as "image"
        formData.append("location", this.location);
        formData.append("authorUsername", "test_user"); // Replace with the actual username
  
        try {
          const response = await axios.post("http://localhost:8080/api/posts", formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          });
          alert("Post created successfully!");
          console.log("Created Post:", response.data);
          this.resetForm();
        } catch (error) {
          console.error("Error creating post:", error.response || error.message);
          alert("Failed to create post. Please try again.");
        } finally {
          this.isSubmitting = false; // Re-enable the button
        }
      },
      resetForm() {
        this.title = "";
        this.description = "";
        this.image = null;
        this.imagePreview = null;
        this.location = "";
      },
    },
  };
  </script>
  
  <style scoped>
  h1 {
    text-align: center;
    color: #333;
  }
  
  form {
    display: flex;
    flex-direction: column;
    max-width: 400px;
    margin: 0 auto;
  }
  
  input[type="text"],
  input[type="file"] {
    margin-bottom: 10px;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  
  button {
    padding: 10px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button:disabled {
    background-color: #6c757d;
    cursor: not-allowed;
  }
  
  button:hover:enabled {
    background-color: #0056b3;
  }
  
  .preview {
    text-align: center;
    margin-top: 20px;
  }
  
  .preview img {
    max-width: 100%;
    max-height: 300px;
    border: 1px solid #ddd;
    border-radius: 4px;
    padding: 10px;
  }
  </style>
  
  