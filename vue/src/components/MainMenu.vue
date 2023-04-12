<template>
  <v-navigation-drawer
      theme="dark"
      expand-on-hover
      rail
      permanent
      app>
    <v-list>
      <v-list-item
          title="Homethings"
      >
        <template v-slot:prepend>
          <img width="24" height="24" :src="require('@/assets/house.png')" class="me-8">
        </template>
      </v-list-item>
    </v-list>

    <v-divider></v-divider>

    <v-list density="compact" nav>
      <v-list-item prepend-icon="mdi-weather-sunny" title="Погода" value="weather" href="/"></v-list-item>
      <v-list-item prepend-icon="mdi-weight-kilogram" title="Учет собственного веса" value="weight" href="/weight"></v-list-item>
    </v-list>

    <template v-slot:append>
      <v-divider></v-divider>
      <v-list density="compact" nav>
        <v-list-item prepend-icon="mdi-location-exit" title="Выйти" value="logout" href="/login"></v-list-item>
      </v-list>
    </template>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: 'MainMenu'
}
</script>

