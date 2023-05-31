<template>
  <div>
    <v-card
        width="600px" class="mx-auto"
        outlined>
      <v-tabs
          v-model="tab"
          background-color="grey lighten-4"
          grow
      >
        <v-tab
            v-for="tab in tabs"
            :key="tab"
        >
          {{ tab }}
        </v-tab>
      </v-tabs>

      <v-tabs-items v-model="tab">
        <!--  Purchases tab start    -->
        <v-tab-item>
          <v-card flat>
            <v-simple-table class="ml-2 mr-2">
              <thead>
              <tr>
                <th>Наименование</th>
                <th>Количество</th>
                <th>Место покупки</th>
                <th class="text-right">
                  <v-btn density="compact" icon @click="dialog = true">
                    <v-icon color="success">mdi-plus-box-outline</v-icon>
                  </v-btn>
                </th>
              </tr>
              </thead>
              <tbody>
              <tr
                  v-for="(record) in recordsActive"
                  :key="record.id"
              >
                <td v-if="record.link == null">{{ record.title }}</td>
                <td v-else><a
                    :href="record.link">{{ record.title }}</a></td>
                <td>{{ record.amount }}</td>
                <td>{{ record.purchasePlace }}</td>
                <td class="text-right">
                  <div class="d-inline-block" style="white-space: nowrap">
                    <v-btn density="compact" @click="setEditMode(record)" icon>
                      <v-icon color="warning">mdi-square-edit-outline</v-icon>
                    </v-btn>
                    <v-btn density="compact" @click="deleteRecord(record.id)" class="ms-3"
                           icon>
                      <v-icon color="error">mdi-delete-outline</v-icon>
                    </v-btn>
                  </div>
                </td>
              </tr>
              </tbody>
            </v-simple-table>
          </v-card>
        </v-tab-item>
        <!--  Purchases tab end    -->
        <!--  Guarantees tab start    -->
        <v-tab-item>
          <v-card flat>
            <v-simple-table class="ml-2 mr-2">
              <thead>
              <tr>
                <th>Наименование</th>
                <th>Гарантия до</th>
                <th>Место покупки</th>
                <th class="text-right">
                  <v-btn density="compact" icon @click="dialog = true">
                    <v-icon color="success">mdi-plus-box-outline</v-icon>
                  </v-btn>
                </th>
              </tr>
              </thead>
              <tbody>
              <tr
                  v-for="(record) in recordsGuarantee"
                  :key="record.id"
              >
                <td v-if="record.link == null">{{ record.title }}</td>
                <td v-else><a
                    :href="record.link">{{ record.title }}</a></td>
                <td>{{ formatDate(record.guaranteeExpireDate) }}</td>
                <td>{{ record.purchasePlace }}</td>
                <td class="text-right">
                  <div class="d-inline-block" style="white-space: nowrap">
                    <v-btn density="compact" @click="setEditMode(record)" icon>
                      <v-icon color="warning">mdi-square-edit-outline</v-icon>
                    </v-btn>
                    <v-btn density="compact" @click="deleteRecord(record.id)" class="ms-3"
                           icon>
                      <v-icon color="error">mdi-delete-outline</v-icon>
                    </v-btn>
                  </div>
                </td>
              </tr>
              </tbody>
            </v-simple-table>
          </v-card>
        </v-tab-item>
        <!--  Guarantees tab end    -->
        <!--  Links and place tab start    -->
        <v-tab-item>
          <v-card flat>
            <v-simple-table class="ml-2 mr-2">
              <thead>
              <tr>
                <th>Наименование</th>
                <th>Место покупки</th>
                <th>Дата покупки</th>
                <th></th>
              </tr>
              </thead>
              <tbody>
              <tr
                  v-for="(record) in recordsLinksPlaces"
                  :key="record.id"
              >
                <td v-if="record.link == null">{{ record.title }}</td>
                <td v-else><a
                    :href="record.link">{{ record.title }}</a></td>
                <td>{{ record.purchasePlace }}</td>
                <td>{{ formatDate(record.purchaseDate) }}</td>
                <td class="text-right">
                  <div class="d-inline-block" style="white-space: nowrap">
                    <v-btn density="compact" @click="setEditMode(record)" icon>
                      <v-icon color="warning">mdi-square-edit-outline</v-icon>
                    </v-btn>
                    <v-btn density="compact" @click="deleteRecord(record.id)" class="ms-3"
                           icon>
                      <v-icon color="error">mdi-delete-outline</v-icon>
                    </v-btn>
                  </div>
                </td>
              </tr>
              </tbody>
            </v-simple-table>
          </v-card>
        </v-tab-item>
        <!--  Links and places tab end    -->
      </v-tabs-items>
    </v-card>

    <!--  Modal start  -->
    <v-row justify="center">
      <v-dialog
          v-model="dialog"
          persistent
          max-width="600px"
      >
        <v-card>
          <v-card-title>
            <span class="text-h5">{{ tabs[tab] }}</span>
          </v-card-title>
          <!--    Fields for every tab start     -->
          <v-card-text>
            <v-text-field
                label="Наименование*"
                required
            ></v-text-field>
            <v-row>
              <v-col cols="6">
                <v-text-field
                    label="Количество*"
                    type="number"
                    required
                ></v-text-field>
              </v-col>
              <v-col cols="6">
                <v-select
                    v-model="sourceOfPurchaseSelected"
                    :items="sourceOfPurchase"
                    label="Источник покупки"
                    multiple
                ></v-select>
              </v-col>
            </v-row>
            <v-expand-transition>
              <div v-if="checkSourceOfPurchaseSelected('Ссылка')">
                <v-text-field
                    label="Ссылка"
                ></v-text-field>
              </div>
            </v-expand-transition>
            <v-expand-transition>
              <div v-if="checkSourceOfPurchaseSelected('Место покупки')">
                <v-text-field
                    label="Место покупки"
                ></v-text-field>
              </div>
            </v-expand-transition>
            <!--    Fields for every tab end     -->

            <!--    Fields for Guarantee and Links&Places tabs start     -->
            <!--    Purchase date       -->
            <div v-if="tab !== 0">
              <v-menu
                  v-model="calendarMenu"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                      v-model="computedDateFormatted"
                      label="Дата покупки*"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                    v-model="purchaseDate"
                    @input="calendarMenu = false"
                    locale="ru-ru"
                ></v-date-picker>
              </v-menu>
            </div>
            <!--    Fields for Guarantee tab end     -->

            <!--    Fields for Guarantee tab start     -->
            <!--    Guarantee time        -->
            <div v-if="tab === 1">
              <v-row>
                <v-col cols="6">
                  <v-select
                      :items="guaranteeTimeInterval"
                      label="Гарантия (интервал)*"
                  ></v-select>
                </v-col>
                <v-col cols="6">
                  <v-text-field type="number"
                                label="Гарантия (количество)*"
                  ></v-text-field>
                </v-col>
              </v-row>
            </div>
            <!--    Fields for Guarantee tab end     -->
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                color="blue darken-1"
                text
                @click="dialog = false"
            >
              Закрыть
            </v-btn>
            <v-btn
                color="blue darken-1"
                text
                @click="dialog = false"
            >
              Сохранить
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
    <!--  Modal end  -->
  </div>
</template>

<script>
import PurchasesService from "@/service/PurchasesService";

export default {
  name: "PurchasesComponent",
  data() {
    return {
      tab: null,
      tabs: [
        'Покупки', 'Гарантии', 'Ссылки и места купленных вещей'
      ],
      recordsActive: [],
      recordsGuarantee: [],
      recordsLinksPlaces: [],
      dialog: false,
      sourceOfPurchase: [
        'Ссылка', 'Место покупки'
      ],
      sourceOfPurchaseSelected: [],
      guaranteeTimeInterval: [
        'День', 'Месяц', 'Год'
      ],
      purchaseDate: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      calendarMenu: false
    }
  },
  methods: {
    loadRecords() {
      PurchasesService.getAllRecords().then((res) => {
        this.distributeRecords(res.data);
      });
    },
    distributeRecords(records) {
      this.recordsActive = records.filter(record => record.active);
      this.recordsGuarantee = records.filter(record => !record.active && record.guaranteeExpireDate != null);
      this.recordsLinksPlaces = records.filter(record => !record.active && record.guaranteeExpireDate == null);
    },
    formatDate(date) {
      if (date == null) return "";
      return date.split("-").reverse().join(".");
    },
    checkSourceOfPurchaseSelected(source) {
      return this.sourceOfPurchaseSelected.includes(source);
    },
  },
  created() {
    this.loadRecords();
  },
  computed: {
    computedDateFormatted () {
      return this.formatDate(this.purchaseDate)
    },
  },
}
</script>
