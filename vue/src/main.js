import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import Keycloak from "keycloak-js";
import axios from "axios";

Vue.config.productionTip = false

let initOptions = {
  url: 'http://127.0.0.1:8081/', realm: 'NewRealm', clientId: 'spring-gateway-client', onLoad: 'login-required'
}

let keycloak = new Keycloak(initOptions);

keycloak.init({onLoad: initOptions.onLoad}).then((auth) => {
  if (!auth) {
    window.location.reload();
  } else {
    console.log("Authenticated");
    setHeader(keycloak.token);

    new Vue({
      router,
      vuetify,
      render: h => h(App)
    }).$mount('#app')
  }

  //Token refresh
  setInterval(() => {
    keycloak.updateToken(1200).then((refreshed) => {
      if (refreshed) {
        console.log('Token refreshed' + refreshed);
        setHeader(keycloak.token);
      } else {
        console.log('Token not refreshed, valid for '
            + Math.round(keycloak.tokenParsed.exp + keycloak.timeSkew - new Date().getTime() / 1000) + ' seconds');
      }
    }).catch(() => {
      console.log('Failed to refresh token');
    });
  }, 60000)

}).catch(() => {
  console.log("Authenticated Failed");
});

function setHeader(token) {
  console.log(token)
  axios.defaults.headers.common['Authorization'] =`Bearer ` + token;
}

