import axios from "axios";

const BACKEND_BASE_URL = process.env.BACKEND_BASE_URL ? process.env.BACKEND_BASE_URL : "http://localhost:8080/api";

export const backendApi = axios.create({
  baseURL: BACKEND_BASE_URL,
});

export const jwtBackendApi = axios.create({
  baseURL: BACKEND_BASE_URL,
});

//adding jwtToken from localStorage on every request
jwtBackendApi.interceptors.request.use((config) => {
  const jwtToken = localStorage.getItem("minimartJwtToken");

  if (jwtToken) {
    config.headers["Authorization"] = `Bearer ${jwtToken}`;
  }

  return config;
});
