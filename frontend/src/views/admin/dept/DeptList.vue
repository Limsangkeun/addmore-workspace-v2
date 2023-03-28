<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";
import store from "@/store";
const display = ref(false);
const deptNm = ref('');
const searchNm = ref('');
const searchModel = ref({
  name: '',
  page: 0,
  size: 50
});
const open = () => {
  deptNm.value = '';
  display.value = true;
};

const close = () => {
  deptNm.value = '';
  display.value = false;
};

const checkDuplication = (e) => {
  console.log(e.currentTarget.value);
}

const createDept = () => {
  console.log(deptNm.value);
  axios.post('/api/dept/create', {'name':deptNm.value}, {
    headers: {
      "Authorization" : "Bearer " + store.state.userSession.token,
      "Content-Type" : "application/json"
    }
  }).then(response => {
    console.log(response.data);
    if(response.data.flag) {
      alert("성공!");
      close();
    }
  }).catch(err => console.log(err));
}

const search = (e) => {
  if(e.keyCode != '13') return;
  axios.post('/api/dept/list', searchModel.value, {
    headers: {
      "Authorization" : "Bearer " + store.state.userSession.token,
      "Content-Type" : "application/json"
    }
  }).then(response => {
    console.log(response.data);
  }).catch(err => console.log(err));
};

</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <div class="p-card-content">
          <DataTable
              dataKey="id"
              :paginator="true"
              :rows="20"
              paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
              :rowsPerPageOptions="[5, 10, 25]"
              currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
              responsiveLayout="scroll"
          >
            <template #header>
              <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                <h5 class="m-0 font-bold">부서현황</h5>
                <div class="my-2 flex flex-row">
                <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
                  <i class="pi pi-search" />
                  <InputText placeholder="Search..." v-model="searchNm" @keydown="search"/>
                </span>
                  <Button label="New" icon="pi pi-plus" class="p-button-success mr-2" @click="open"/>
                  <Button label="Delete" icon="pi pi-trash" class="p-button-danger"/>
                </div>
              </div>
            </template>
            <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
          </DataTable>
        </div>
      </div>
    </div>
  </div>
  <Dialog header="부서생성" v-model:visible="display" :breakpoints="{ '960px': '75vw' }" :style="{ width: '30vw' }" :modal="true">
    <div class="p-fluid">
      <div class="field">
        <label for="name1">Name</label>
        <InputText id="name1" type="text" @change="checkDuplication" v-model="deptNm"/>
      </div>
    </div>
    <template #footer>
      <Button label="Ok" @click="createDept" icon="pi pi-check" class="p-button-outlined"/>
      <Button label="Cancel" @click="close" icon="pi pi-check" class="p-button-outlined" />
    </template>
  </Dialog>
</template>

<style scoped>

</style>