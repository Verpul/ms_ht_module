<template>
  <v-main>
    <v-container>
      <v-row justify="center">
        <v-col cols=4>
          <v-card width="400" class="pa-12">
           <v-sheet width="300" class="mx-auto mt-4 mb-4">
              <v-form ref="form">
                <v-text-field
                  v-model="email"
                  label="Email"
                  :rules="emailRules"
                  variant="underlined"
                  required
                ></v-text-field>

                <v-text-field
                  type="password"
                  v-model="password"
                  label="Пароль"
                  :rules="passwordRules"
                  variant="underlined"
                  required
                ></v-text-field>

                <v-checkbox
                  v-model="rememberMe"
                  label="Запомнить меня"
                ></v-checkbox>

                <div class="d-flex flex-column">
                  <v-btn
                    color="blue"
                    class="mt-2"
                    block
                    @click="login"
                  >
                    Войти
                  </v-btn>
                </div>
              </v-form>
            </v-sheet>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
</v-main>
</template>

<script>
export default {
  name: 'WeightTracker',
  data: () => ({
    email: '',
    emailRules: [
      v => !!v || 'Введите email',
      v => (v && /^[a-z.-]+@[a-z.-]+\.[a-z]+$/i.test(v)) || 'Введите корректный email'
    ],
    password: '',
    passwordRules: [
      v => !!v || 'Введите пароль',
    ],
    rememberMe: false,
  }),

  methods: {
    async login () {
      await this.$refs.form.validate()
    }
  }
}
</script>