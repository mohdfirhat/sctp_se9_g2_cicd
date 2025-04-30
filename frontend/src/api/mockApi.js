import axios from "axios";

const MOCK_API_BASE_URL = "https://677be8e220824100c07b3800.mockapi.io/";
//Testing using with cors-anywhere.[Min]
/* const MOCK_API_BASE_URL = "https://cors-anywhere.herokuapp.com/https://677be8e220824100c07b3800.mockapi.io/"; */
const mockApi = axios.create({
  baseURL: MOCK_API_BASE_URL,
});
export default mockApi;
