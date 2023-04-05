<script setup>

import {inject, reactive} from "vue";
import _ from "lodash";
import globalStore from "@/store";
import {useToast} from "primevue/usetoast";

const toast = useToast();
const UT = inject('$UT');

const data = reactive({
  authorities : [],
  selectedAuthorities: [],
  searchName: '',
  saveName: '',
  showDialog : false
});

const search = (e) => {
  if(!_.isEmpty(e) && e.keyCode !== 13) return;
  globalStore.commit('setLoading', true);
  UT.post('/api/authority/find', {name: data.searchName.trim()}, null)
      .then(response => {
        data.searchName = '';
        data.authorities = response.data.authorities;
      })
      .catch(err => {
        const msg = err.response.data.msg;
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      })
      .finally(()=> {
        globalStore.commit('setLoading', false);
      });
  ;
}

const open = () => {
  data.showDialog = true;
}

const close = () => {
  data.saveName = '';
  data.showDialog = false;
};

const createAuthority = () => {
  globalStore.commit('setLoading', true);
  UT.post('/api/authority/save', {name:data.saveName.trim()})
      .then(response => {
        data.saveName = '';
        search();
        close();
        toast.add({ severity: 'success', summary: '성공', detail: '권한이 생성되었습니다.', life: 3000 });
      })
      .catch(err => {
        const msg = err.response.data.msg;
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      })
      .finally(()=>{
        globalStore.commit('setLoading', false);
      });
};

</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <Toast />
        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center mb-3">
          <h5 class="m-0 font-bold">권한 목록</h5>
          <div class="my-2 flex flex-row">
            <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
              <i class="pi pi-search"/>
              <InputText v-model="data.searchName" placeholder="Search..." @keydown="search"/>
            </span>
            <Button class="p-button-success mr-2" icon="pi pi-plus" label="New" @click="open"/>
            <Button class="p-button-danger" icon="pi pi-trash" label="Delete"/>
          </div>
        </div>
        <DataTable
            ref="authorityGrid"
            :value="data.authorities"
            :selection="data.selectedAuthorities"
            class="p-datatable-sm"
            dataKey="id"
            scrollHeight="500px"
            scrollable
        >
          <Column selectionMode="multiple" headerStyle="flex:0 0 1rem" style="flex:0 0 1rem"></Column>
          <Column field="name" header="권한명" ></Column>
          <Column field="createdBy" header="생성자"></Column>
          <Column field="createdAt" header="생성일자"></Column>
        </DataTable>
      </div>
    </div>
  </div>
  <Dialog v-model:visible="data.showDialog" :breakpoints="{ '960px': '75vw' }" :modal="true" :style="{ width: '30vw' }"
          header="권한생성">
    <div class="p-fluid">
      <div class="field">
        <label for="name1">권한명을 입력하세요.</label>
        <InputText id="name1" v-model="data.saveName" type="text"/>
      </div>
    </div>
    <template #footer>
      <Button class="p-button-outlined" icon="pi pi-check" label="Ok" @click="createAuthority"/>
      <Button class="p-button-outlined" icon="pi pi-check" label="Cancel" @click="close"/>
    </template>
  </Dialog>
</template>

<style scoped>

</style>