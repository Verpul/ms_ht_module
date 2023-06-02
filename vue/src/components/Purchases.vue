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
                <td v-if="record.link == null || record.link === ''">{{ record.title }}</td>
                <td v-else><a
                    :href="record.link">{{ record.title }}</a></td>
                <td>{{ record.amount }}</td>
                <td>{{ record.purchasePlace }}</td>
                <td class="text-right">
                  <div class="d-inline-block" style="white-space: nowrap">
                    <v-btn density="compact" @click="editRecord(record)" icon>
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
                    <v-btn density="compact" @click="editRecord(record)" icon>
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
                    <v-btn density="compact" @click="editRecord(record)" icon>
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
        <v-form ref="form">
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ tabs[tab] }}</span>
            </v-card-title>
            <!--    Fields for every tab start     -->
            <v-card-text>
              <v-text-field
                  label="Наименование*"
                  v-model="purchaseData.title"
                  :rules="purchaseDataRules.titleRules"
                  required
              ></v-text-field>
              <v-row>
                <v-col cols="6">
                  <v-text-field
                      label="Количество*"
                      type="number"
                      v-model="purchaseData.amount"
                      :rules="purchaseDataRules.amountRules"
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
                      v-model="purchaseData.link"
                      :rules="purchaseDataRules.linkRules"
                  ></v-text-field>
                </div>
              </v-expand-transition>
              <v-expand-transition>
                <div v-if="checkSourceOfPurchaseSelected('Место покупки')">
                  <v-text-field
                      label="Место покупки"
                      v-model="purchaseData.purchasePlace"
                      :rules="purchaseDataRules.purchasePlaceRules"
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
                      v-model="purchaseData.purchaseDate"
                      @input="calendarMenu = false"
                      :rules="purchaseDataRules.purchaseDateRules"
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
                        v-model="purchaseData.guaranteeInterval"
                        :rules="purchaseDataRules.guaranteeTimeIntervalRules"
                        label="Гарантия (интервал)*"
                    ></v-select>
                  </v-col>
                  <v-col cols="6">
                    <v-text-field
                        v-model="purchaseData.guaranteeDuration"
                        :rules="purchaseDataRules.guaranteeDurationRules"
                        type="number"
                                  label="Гарантия (длительность)*"
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
                  @click="clearFields()"
              >
                Закрыть
              </v-btn>
              <v-btn
                  color="blue darken-1"
                  text
                  @click="saveButtonModalWindows()"
              >
                Сохранить
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-form>
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
      guaranteeTimeInterval: [
        'День', 'Месяц', 'Год'
      ],
      sourceOfPurchaseSelected: [],
      calendarMenu: false,
      purchaseData: {
        id: null,
        title: null,
        amount: 1,
        link: null,
        purchasePlace: null,
        guaranteeInterval: null,
        guaranteeDuration: null,
        purchaseDate: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      },
      purchaseDataRules: {
        titleRules: [],
        amountRules: [],
        purchasePlaceRules: [],
        linkRules: [],
        purchaseDateRules: [],
        guaranteeTimeIntervalRules: [],
        guaranteeDurationRules: []
      }
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
    saveButtonModalWindows() {
      //Validate for every tab
      this.purchaseDataRules.titleRules = [
        v => !!v || "Введите наименование",
        v => (v && v.length <= 250) || "Наименование не может превышать 250 символов"
      ];

      this.purchaseDataRules.amountRules = [
        v => (!!v && (v >= 1 || v <= 999)) || "Количество должно быть от 1 до 999"
      ]

      this.purchaseDataRules.linkRules = [
        v => (v === null || v === "" || (v && this.urlRegex().test(v))) || "Введите корректную ссылку"
      ]

      this.purchaseDataRules.purchasePlaceRules = [
        v => (v === null || v === "" || (v && v.length <= 100 )) || "Место покупки не может превышать 100 символов"
      ]

      //Guarantees and places
      if (this.tab === 1 || this.tab === 2) {
        this.purchaseDataRules.purchaseDateRules = [
          v => !!v || "Выберите дату покупки",
        ];
      }

      //Guarantee tab
      if (this.tab === 1) {
        this.purchaseDataRules.guaranteeTimeIntervalRules = [
          v => !!v || "Выберите временной интервал гарантии",
        ];

        this.purchaseDataRules.guaranteeDurationRules = [
          v => !!v || "Выберите длительность гарантии",
        ];
      }

      this.$nextTick(() => {
        const valid = this.$refs.form.validate();

        if (valid) {
          const record = {
            id: this.purchaseData.id,
            title: this.purchaseData.title,
            amount: this.purchaseData.amount,
            link: this.purchaseData.link,
            purchasePlace: this.purchaseData.purchasePlace,
          }

          //Guarantee & place
          if (this.tab === 1 || this.tab === 2) {
            record.purchaseDate = this.purchaseData.purchaseDate;
          }

          //Guarantee tab
          if (this.tab === 1) {
            record.active = false;
            record.guaranteeInterval = this.purchaseData.guaranteeInterval;
            record.guaranteeDuration = this.purchaseData.guaranteeDuration;
            record.guaranteeExpireDate = this.getGuaranteeExpireDate(this.purchaseData.guaranteeInterval, this.purchaseData.guaranteeDuration);
          }

          if (this.purchaseData.id == null) {
            PurchasesService.createRecord(record).then(() => {
              this.clearFields();
              this.loadRecords();
            });
          } else {
            PurchasesService.updateRecord(this.purchaseData.id, record).then(() => {
              this.clearFields();
              this.loadRecords();
            });
          }
        }
      })
    },
    editRecord(record) {
      this.purchaseData.id = record.id;
      this.purchaseData.title = record.title;
      this.purchaseData.amount = record.amount;

      this.purchaseData.link = record.link;
      if (record.link != null && record.link !== "") this.sourceOfPurchaseSelected.push("Ссылка");

      this.purchaseData.purchasePlace = record.purchasePlace;
      if (record.purchasePlace != null && record.purchasePlace !== "") this.sourceOfPurchaseSelected.push("Место покупки");

      //Guarantee & places
      if (this.tab === 1 || this.tab === 2) {
        this.purchaseData.purchaseDate = record.purchaseDate;
      }

      //Guarantee tab
      if (this.tab === 1) {
        this.purchaseData.guaranteeInterval = record.guaranteeInterval;
        this.purchaseData.guaranteeDuration = record.guaranteeDuration;
      }

      this.dialog = true;
    },
    deleteRecord(id) {
      PurchasesService.deleteRecord(id).then(() => {
        this.loadRecords();
      })
    },
    clearFields() {
      this.id = null;
      this.purchaseData.title = null;
      this.purchaseData.amount = 1;
      this.purchaseData.link = null;
      this.purchaseData.purchasePlace = null;
      this.sourceOfPurchaseSelected = [];
      this.purchaseData.guaranteeInterval = null;
      this.purchaseData.guaranteeDuration = null;
      this.$refs.form.resetValidation();
      this.dialog = false;
    },
    urlRegex() {
      const pattern = new RegExp(
          '^(https?:\\/\\/)?' +
          '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' +
          '((\\d{1,3}\\.){3}\\d{1,3}))' +
          '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' +
          '(\\?[;&a-z\\d%_.~+=-]*)?' +
          '(\\#[-a-z\\d_]*)?$',
          'i'
      );
      return pattern;
    },
    getGuaranteeExpireDate(interval, duration) {
      const date = new Date(this.purchaseData.purchaseDate);

      if (interval === "День") {
        date.setDate(date.getDate() + parseInt(duration));
      } else if (interval === "Месяц") {
        date.setMonth(date.getMonth() + parseInt(duration));
      } else if (interval === "Год") {
        date.setFullYear(date.getFullYear() + parseInt(duration));
      }

      return date;
    }
  },
  created() {
    this.loadRecords();
  },
  computed: {
    computedDateFormatted() {
      return this.formatDate(this.purchaseData.purchaseDate)
    },
  },
}
</script>
