<script setup>

import axios from "axios";
import useCommonStore from "@/store/commonStore";
import {inject, onMounted, reactive, ref} from "vue";
import _ from "lodash";
import {useToast} from "primevue/usetoast";

const UT = inject('$UT');
const toast = useToast();
const showDialog = ref(false);
const searchParam = reactive({
  name: '',
  page: 0,
  size: 50,
  totalCount: 0,
  reset () {
    this.name = '';
    this.page = 0;
    this.size = 50;
    this.totalCount = 0;
  }
});
const saveModel = reactive({
  id : null,
  name : '',
  reset () {
    this.id = null;
    this.name = '';
  },
  setModel (data) {
    this.id = data.id;
    this.name = data.name;
  }
});
const deptList = ref([]);
const selectedDept = ref(null);

onMounted(()=> {
  searchDept();
})

const open = () => {
  showDialog.value = true;
};

const close = () => {
  saveModel.reset();
  showDialog.value = false;
};

const saveDept = () => {
  const url = _.isEmpty(saveModel.id) ? 'create' : 'modify';
  const msg = _.isEmpty(saveModel.id) ? '생성' : '수정';
  UT.post('/api/dept/' + url, saveModel.value, null)
      .then(()=> {
        toast.add({ severity: 'success', summary: '성공', detail: '정상적으로 '+msg+'되었습니다.', life: 3000 });
        close();
        searchDept();
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
}

const modifyDept = () => {
  saveModel.setModel(_.cloneDeep(selectedDept.value));
  open();
}

const removeDept = () => {
  UT.post('/api/dept/remove', selectedDept.value, null)
      .then(() => {
        toast.add({ severity: 'success', summary: '성공', detail: '정상적으로 '+msg+'되었습니다.', life: 3000 });
        close();
        searchDept();
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
}

const searchDept = (e) => {
  if (!_.isEmpty(e) && e.keyCode != '13') return;
  searchParam.name = searchParam.name.trim();

  UT.post('/api/dept/find', searchParam, null)
      .then(data => {
        deptList.value = data.dept_list;
        searchParam.totalCount = data.total_count;
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
};

</script>

<template>
  <div class="grid flex-1">
  <div class="card col-6 flex-column">
    <Toast/>
    <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center mb-3">
      <h5 class="m-0 font-bold">부서현황</h5>
      <div class="my-2 flex flex-row">
            <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
              <i class="pi pi-search"/>
              <InputText v-model="searchParam.name" placeholder="Search..." @keydown="searchDept"/>
            </span>
        <Button class="p-button-primary mr-2" icon="pi pi-plus" label="생성" @click="open"/>
        <Button class="p-button-success mr-2" icon="pi pi-plus" label="수정" @click="modifyDept" :disabled="_.isEmpty(selectedDept)"/>
        <Button class="p-button-danger" icon="pi pi-trash" label="삭제" @click="removeDept" :disabled="_.isEmpty(selectedDept)"/>
      </div>
    </div>
    <DataTable
        ref="deptGrid"
        class="p-datatable-sm"
        :value="deptList"
        dataKey="id"
        v-model:selection="selectedDept"
        v-model:rows="searchParam.size"
        :rowsPerPageOptions="[5, 10, 25, 50]"
        v-model:totalRecords="searchParam.totalCount"
        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
        scrollHeight="500px"
        scrollable
        :show-gridlines="true"
        :resizable-columns="true"
    >
      <Column selectionMode="single" style="flex:0 0 40px"></Column>
      <Column field="name" header="부서명" class="f-300-px"></Column>
      <Column field="createdBy" header="생성자" class="f-100-px"></Column>
      <Column field="createdAt" header="생성일자" class="f-100-px"></Column>
      <Column></Column>
    </DataTable>
  </div>
    <div class="col-1">
      <Divider layout="vertical"></Divider>
    </div>
  <div class="card col-5">
    <DataTable
        ref="deptGrid"
        class="p-datatable-sm"
        :value="deptList"
        dataKey="id"
        v-model:selection="selectedDept"
        v-model:rows="searchParam.size"
        :rowsPerPageOptions="[5, 10, 25, 50]"
        v-model:totalRecords="searchParam.totalCount"
        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
        scrollHeight="500px"
        scrollable
        :show-gridlines="true"
        :resizable-columns="true"
    >
      <Column selectionMode="single" style="flex:0 0 40px"></Column>
      <Column field="name" header="부서명" class="f-300-px"></Column>
      <Column field="createdBy" header="생성자" class="f-100-px"></Column>
      <Column field="createdAt" header="생성일자" class="f-100-px"></Column>
      <Column></Column>
    </DataTable>
  </div>
  </div>
  <Dialog v-model:visible="showDialog" :breakpoints="{ '960px': '75vw' }" :modal="true" :style="{ width: '30vw' }"
          header="부서정보">
    <div class="p-fluid">
      <div class="field">
        <label for="name1">Name</label>
        <InputText id="name1" v-model="saveModel.name" type="text" />
      </div>
    </div>
    <template #footer>
      <Button class="p-button-outlined" icon="pi pi-check" label="Ok" @click="saveDept"/>
      <Button class="p-button-outlined" icon="pi pi-check" label="Cancel" @click="close"/>
    </template>
  </Dialog>
</template>

<style scoped>

</style>