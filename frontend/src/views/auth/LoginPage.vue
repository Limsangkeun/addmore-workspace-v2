<script setup>
import {useLayout} from '@/layout/composables/layout';
import {ref, computed, reactive} from 'vue';
import AppConfig from '@/layout/AppConfig.vue';
import axios from 'axios';
import _ from 'lodash';
import useSessionStore from "@/store/sessionStore";

const {layoutConfig, contextPath} = useLayout();
const sessionStore = useSessionStore();

const loginModel = reactive({
  username: '',
  password: '',
  reset() {
    this.username = '';
    this.password = '';
  }
});

const loginSuccess = function () {
  loginModel.reset();
  location.href = '/';
}

const fnDoLogin = (e) => {
  if (!_.isEmpty(e) && (e.keyCode === 13 || e.type === 'click')) {
    axios.post('/api/auth/authenticate', loginModel, {}).then(response => {
      if (!_.isEmpty(response.data.token)) {
        localStorage.setItem("atk", response.data.token);
        response.data.isValid = true;
        sessionStore.authenticated(response.data);
        loginSuccess();
      }
    });
  }
}
</script>

<template>
  <div class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
    <div class="flex flex-column align-items-center justify-content-center">
      <div
          style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 200%)">
        <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px">
          <div class="text-center mb-5">
            <div class="text-900 text-3xl font-medium mb-3">ADDMORE WORKSPACE</div>
            <span class="text-600 font-medium">Sign in to continue</span>
          </div>

          <div>
            <label for="username" class="block text-900 text-xl font-medium mb-2">ID</label>
            <InputText id="username" type="text" class="w-full md:w-30rem mb-5" style="padding: 1rem"
                       v-model="loginModel.username" maxlength="30"/>

            <label for="password1" class="block text-900 font-medium text-xl mb-2">Password</label>
            <Password id="password1" v-model="loginModel.password" class="w-full mb-3" inputClass="w-full"
                      inputStyle="padding:1rem" @keydown="fnDoLogin"></Password>

            <!--                        <div class="flex align-items-center justify-content-between mb-5 gap-5">
                                        <div class="flex align-items-center">
                                            <Checkbox v-model="checked" id="rememberme1" binary class="mr-2"></Checkbox>
                                            <label for="rememberme1">Remember me</label>
                                        </div>
                                        <a class="font-medium no-underline ml-2 text-right cursor-pointer" style="color: var(&#45;&#45;primary-color)">Forgot password?</a>
                                    </div>-->
            <Button label="Sign In" class="w-full p-3 text-xl" @click="fnDoLogin" @keydown="fnDoLogin"></Button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <AppConfig simple/>
</template>

<style scoped>
.pi-eye {
  transform: scale(1.6);
  margin-right: 1rem;
}

.pi-eye-slash {
  transform: scale(1.6);
  margin-right: 1rem;
}
</style>
