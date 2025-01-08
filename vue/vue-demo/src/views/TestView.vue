<template>
  <div>
    <h3>Test view.</h3>
    <div class="button-container">
      <button @click="fetchData">Call Hello API</button>
      <button @click="clearResponse">Clear response</button>
    </div>
    <p v-if="result">API Response: {{ result }}</p>
    <p v-if="error" style="color: red;">Error: {{ error }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      result: null,
      error: null,
    };
  },
  methods: {
    async fetchData() {
      try {
        this.error = null; // Reset error state
        const response = await axios.get("/api/v1/devices/home"); // Replace with your actual endpoint
        this.result = response.data; // Assuming the response is plain text or JSON
      } catch (err) {
        console.error("Error fetching data:", err);
        this.error = err.response?.data || "Unable to fetch data";
      }
    },
    async clearResponse() {
      try {
        this.error = null; // Reset error state
        this.result = null; // Reset response
      } catch (err) {
        console.error("Error fetching data:", err);
        this.error = err.response?.data || "Unable to fetch data";
      }
    }
  },
};
</script>

<style scoped>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}

.button-container {
  display: flex;
  gap: 10px; /* Add space between buttons */
}

button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #358a62;
}
</style>
