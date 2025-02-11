<template>
  <div>
    <textarea v-model="userInput" rows="5" cols="70"></textarea>

    <div class="button-container">
      <button @click="fetchAiReponse" :disabled="loading">
        <span v-if="loading">⏳ Asking...</span>
        <span v-else>Ask</span>
      </button>
      <button @click="clearResponse" :disabled="loading">Clear response</button>
    </div>

    <!-- Show busy indicator while loading -->
    <div v-if="loading" class="loading">
      <p>Loading... ⏳ Please wait</p>
    </div>

    <!-- Show AI Response with Formatting -->
    <div v-if="result" class="ai-response-container">
      <div class="ai-response" v-html="formatText(result)"></div>
    </div>

    <!-- Show Error -->
    <div v-if="error" class="error">
      <h3>Error:</h3>
      <p>{{ error }}</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      userInput: "", // Holds the input from textarea
      result: null, // Stores AI response
      error: null, // Stores error messages
      loading: false, // Tracks whether the request is in progress
    };
  },
  methods: {
    async fetchAiReponse() {
      try {
        this.loading = true; // Show busy indicator
        this.error = null; // Reset error state
        this.result = null; // Clear previous result

        const response = await axios.post("/api/v1/chat/ask", {
          question: this.userInput,
        });

        // Extract the generated text from the response
        if (Array.isArray(response.data) && response.data.length > 0) {
          this.result = response.data[0].generated_text;
        } else {
          this.result = "No valid response received.";
        }
      } catch (err) {
        console.error("Error fetching data:", err);
        this.error = err.response?.data || "Unable to fetch data";
      } finally {
        this.loading = false; // Hide busy indicator after request completes
      }
    },
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
      this.error = null; // Reset error state
      this.result = null; // Reset response
    },
    formatText(text) {
      if (!text) return "";

      // Convert newlines into proper HTML line breaks
      let formattedText = text
        .replace(/\n\n/g, "<br><br>") // Double newline -> Paragraph break
        .replace(/\n/g, "<br>") // Single newline -> Line break
        .replace(/- \*\*(.*?)\*\*/g, "<li><strong>$1</strong></li>") // Convert bold bullet points
        .replace(/\*\*(.*?)\*\*/g, "<strong>$1</strong>") // Convert **bold** to <strong>
        .replace(/.*?<\/think>/, "")

      return `<p>${formattedText}</p>`;
    },
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
.ai-response-container {
  margin-top: 10px;
}
textarea {
  border: 1px solid #ddd;
}

.ai-response {
  background-color: #f8f9fa;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
  font-family: Arial, sans-serif;
  line-height: 1.5;
  max-height: 500px; /* Set a maximum height */
  overflow-y: auto; /* Enable vertical scrolling */
  white-space: pre-wrap; /* Preserve new lines */
}

.loading {
  color: blue;
  font-weight: bold;
  margin-top: 10px;
}

.error {
  color: red;
  font-weight: bold;
  margin-top: 10px;
}
</style>
