import axios from "axios"

const PURCHASE_API_URL = 'http://localhost:9000/purchases'

class PurchasesService {

    getAllRecords() {
        return axios.get(PURCHASE_API_URL);
    }

    getRecord(id) {
        return axios.get(`${PURCHASE_API_URL}/${id}`);
    }

    deleteRecord(id) {
        return axios.delete(PURCHASE_API_URL + '/' + id);
    }

    updateRecord(id, record) {
        return axios.put(`${PURCHASE_API_URL}/${id}`, record);
    }

    createRecord(record) {
        return axios.post(`${PURCHASE_API_URL}`, record);
    }
}

export default new PurchasesService()