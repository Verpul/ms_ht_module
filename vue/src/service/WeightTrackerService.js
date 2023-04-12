import axios from "axios"

const WEIGHT_API_URL = 'http://localhost:8080/weight'

class WeightTrackerService {

    getAllRecords() {
        return axios.get(WEIGHT_API_URL);
    }

    getRecord(id) {
        return axios.get(`${WEIGHT_API_URL}/${id}`);
    }

    deleteRecord(id) {
        return axios.delete(WEIGHT_API_URL + '/' + id);
    }

    updateRecord(id, record) {
        return axios.put(`${WEIGHT_API_URL}/${id}`, record);
    }

    createRecord(record) {
        return axios.post(`${WEIGHT_API_URL}`, record);
    }
  }
export default new WeightTrackerService()