<script setup>

import axios from "axios";
import globalStore from "@/store";
import {reactive} from "vue";
import _ from "lodash";

const data = reactive({
  showDialog: false,
  deptName: '',
  searchName: '',
  deptList: [],
  searchModel: {
    name: '',
    page: 0,
    size: 50
  },
  pageModel: {
    totalCount: 0,
    totalPage: 0,
    currentPage: 0
  },
  selectedDeptList: []
});

const reset = () => {
  data.showDialog = false;
  data.deptName = '';
  data.searchName = '';
  data.deptList = [];
  data.searchModel = {
    name: '',
    page: 0,
    size: 50
  };
  data.pageModel = {
    totalCount: 0,
    totalPage: 0,
    currentPage: 0
  };
}

const open = () => {
  data.showDialog = true;
};

const close = () => {
  data.deptName = '';
  data.showDialog = false;
};

const checkDuplication = (e) => {
  console.log(e.currentTarget.value);
}

const createDept = () => {
  console.log(data.deptName);
  axios.post('/api/dept/create', {'name': data.deptName}, {
    headers: {
      "Authorization": "Bearer " + globalStore.state.userSession.token,
      "Content-Type": "application/json"
    }
  }).then(response => {
    console.log(response.data);
    close();
    search();
  }).catch(err => console.log(err));
}

const search = (e) => {
  if (!_.isEmpty(e) && e.keyCode != '13') return;
  axios.post('/api/dept/list', data.searchModel, {
    headers: {
      "Authorization": "Bearer " + globalStore.state.userSession.token,
      "Content-Type": "application/json"
    }
  }).then(response => {
    console.log(response.data);
    data.deptList = response.data.dept_list;
    data.pageModel.totalCount = response.data.total_count;
  }).catch(err => console.log(err));
};
``

</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center mb-3">
          <h5 class="m-0 font-bold">부서현황</h5>
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
            ref="deptGrid"
            :paginator="true"
            v-model:rows="data.searchModel.size"
            :rowsPerPageOptions="[5, 10, 25, 50]"
            v-model:totalRecords="data.pageModel.totalCount"
            v-model:value="data.deptList"
            class="p-datatable-sm"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
            dataKey="id"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            scrollHeight="500px"
            scrollable
        >
          <Column selectionMode="multiple" style="flex:0 0 1rem"></Column>
          <Column field="name" header="부서명" ></Column>
          <Column field="createdBy" header="생성자"></Column>
          <Column field="createdAt" header="생성일자"></Column>
        </DataTable>
      </div>
    </div>
  </div>
  <Dialog v-model:visible="data.showDialog" :breakpoints="{ '960px': '75vw' }" :modal="true" :style="{ width: '30vw' }"
          header="부서생성">
    <div class="p-fluid">
      <div class="field">
        <label for="name1">Name</label>
        <InputText id="name1" v-model="data.deptName" type="text" @change="checkDuplication"/>
      </div>
    </div>
    <template #footer>
      <Button class="p-button-outlined" icon="pi pi-check" label="Ok" @click="createDept"/>
      <Button class="p-button-outlined" icon="pi pi-check" label="Cancel" @click="close"/>
    </template>
  </Dialog>
</template>

<style scoped>

</style>