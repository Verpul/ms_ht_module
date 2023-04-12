<template>
  <v-main>
    <v-container>
      <v-row justify="center">
        <v-card width="600px">
          <v-form ref="form" validate-on="submit">
            <v-table density="compact">
              <thead>
              <tr>
                <th class="text-left" style="width: 30%">
                  Дата
                </th>
                <th class="text-left" style="width: 30%">
                  Вес
                </th>
                <th class="text-left" style="width: 30%">
                  Изменение
                </th>
                <th class="text-left" style="width: 10%"></th>
              </tr>
              </thead>
              <tbody>
              <tr>

                <td>
                  <v-text-field type="date" density="compact" variant="underlined" v-model="weightDate"
                                :rules="weightDateRules"></v-text-field>
                </td>
                <td>
                  <v-text-field density="compact" variant="underlined" v-model="weight"
                                :rules="weightRules"></v-text-field>
                </td>
                <td></td>
                <td>
                  <div class="d-inline-block" style="white-space: nowrap">
                    <v-btn density="compact" icon @click="submitRecord" flat>
                      <v-icon color="success">{{ id ? 'mdi mdi-check-circle-outline' : 'mdi mdi-plus-box-outline' }}
                      </v-icon>
                    </v-btn>
                    <v-btn density="compact" icon="mdi mdi-close-circle-outline" @click="clearFields" flat v-if="id"
                           class="ms-3">
                      <v-icon color="primary"></v-icon>
                    </v-btn>
                  </div>
                </td>

              </tr>
              <tr
                  v-for="(record, index) in records"
                  :key="record.id"
              >
                <td>{{ formatDate(record.weightDate) }}</td>
                <td>{{ record.weight }}</td>
                <td :class="[countWeightChange(index) > 0 ? 'text-green': 'text-red']">{{
                    countWeightChange(index)
                  }}
                </td>
                <td>
                  <div class="d-inline-block" style="white-space: nowrap">
                    <v-btn density="compact" icon="mdi mdi-square-edit-outline" @click="setEditMode(record)" flat>
                      <v-icon color="warning"></v-icon>
                    </v-btn>
                    <v-btn density="compact" icon="mdi mdi-delete-outline" @click="deleteRecord(record.id)" class="ms-3"
                           flat>
                      <v-icon color="error"></v-icon>
                    </v-btn>
                  </div>
                </td>
              </tr>
              </tbody>
            </v-table>
          </v-form>
        </v-card>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import WeightTrackerService from "@/service/WeightTrackerService";

export default {
  name: 'WeightTracker',
  data() {
    return {
      records: [],
      weightDate: "",
      weight: "",
      id: "",
      weightRules: [
        v => !!v || 'Введите вес',
        v => (v && /^[5-9]\d(\.\d)?$/.test(v)) || "Введите корректный вес"
      ],
      weightDateRules: [
        v => !!v || 'Введите дату взвешивания',
        v => (v && /^20\d{2}-\d{2}-\d{2}$/.test(v)) || "Введите корректную дату"
      ],
    };
  },
  methods: {
    loadRecords() {
      WeightTrackerService.getAllRecords().then((res) => {
        this.records = res.data;
      });
    },
    async submitRecord() {
      const {valid} = await this.$refs.form.validate()

      if (valid) {
        if (this.id === "") {
          WeightTrackerService.createRecord({
            weightDate: this.weightDate,
            weight: this.weight,
          }).then(() => {
            this.clearFields();
            this.loadRecords();
          });
        } else {
          WeightTrackerService.updateRecord(this.id, {
            id: this.id,
            weightDate: this.weightDate,
            weight: this.weight,
          }).then(() => {
            this.clearFields();
            this.loadRecords();
          });
        }
      }
    },
    setEditMode(record) {
      this.weightDate = record.weightDate;
      this.weight = record.weight;
      this.id = record.id;
    },
    deleteRecord(id) {
      WeightTrackerService.deleteRecord(id).then(() => {
        this.loadRecords();
      });
    },
    countWeightChange(index) {
      if (index === this.records.length - 1) return "";
      return (this.records[index].weight - this.records[index + 1].weight).toFixed(1);
    },
    formatDate(date) {
      return date.split("-").reverse().join(".");
    },
    clearFields() {
      this.weightDate = "";
      this.weight = "";
      this.id = "";
    }
  },
  created() {
    this.loadRecords();
  },
}
</script>