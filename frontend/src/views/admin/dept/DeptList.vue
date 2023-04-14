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
const memberList = ref([]);

onMounted(()=> {
  fnSearchDept();
})

const fnOpen = (isNew) => {
  if(isNew) saveModel.reset();
  showDialog.value = true;
};

const fnClose = () => {
  saveModel.reset();
  showDialog.value = false;
};

const saveDept = () => {
  const url = _.isEmpty(saveModel.id) ? 'create' : 'modify';
  const msg = _.isEmpty(saveModel.id) ? '생성' : '수정';
  UT.post('/api/dept/' + url, saveModel, null)
      .then(()=> {
        toast.add({ severity: 'success', summary: '성공', detail: '정상적으로 '+msg+'되었습니다.', life: 3000 });
        fnClose();
        fnSearchDept();
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
}

const modifyDept = () => {
  saveModel.setModel(_.cloneDeep(selectedDept.value));
  fnOpen(false);
}

const removeDept = () => {
  UT.post('/api/dept/remove', selectedDept.value, null)
      .then(() => {
        toast.add({ severity: 'success', summary: '성공', detail: '정상적으로 삭제되었습니다.', life: 3000 });
        fnClose();
        fnSearchDept();
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
}

const fnSearchDept = (e) => {
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

const findMemberList = (e) => {
  if(!e.originalEvent.target.classList.contains('link')) return;
  const deptId = e.data.id;

  UT.get(`/api/user/find/users-in-dept/${deptId}`, null)
      .then(data => {
        memberList.value = data.userList;
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
}

</script>

<template>
  <div class="card flex-1">
    <Toast/>
    <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center mb-5">
      <h5 class="m-0 font-bold">부서현황</h5>
      <div class="my-2 flex flex-row">
            <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
              <i class="pi pi-search"/>
              <InputText v-model="searchParam.name" placeholder="Search..." @keydown="fnSearchDept"/>
            </span>
        <Button class="p-button-primary mr-2" icon="pi pi-plus" label="생성" @click="fnOpen"/>
        <Button class="p-button-success mr-2" icon="pi pi-plus" label="수정" @click="modifyDept" :disabled="_.isEmpty(selectedDept)"/>
        <Button class="p-button-danger" icon="pi pi-trash" label="삭제" @click="removeDept" :disabled="_.isEmpty(selectedDept)"/>
      </div>
    </div>
    <div class="grid">
      <div class="col-6">
        <h5 class="m-0 font-bold">부서 목록</h5>
    <DataTable
        ref="deptGrid"
        class="p-datatable-sm mb-5"
        :value="deptList"
        dataKey="id"
        v-model:selection="selectedDept"
        v-model:rows="searchParam.size"
        v-model:totalRecords="searchParam.totalCount"
        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
        paginator="true"
        scrollHeight="500px"
        scrollable
        :show-gridlines="true"
        :resizable-columns="true"
        @row-click="findMemberList"
    >
      <Column selectionMode="single" style="flex:0 0 40px"></Column>
      <Column field="name" header="부서명" class="f-300-px" body-class="link"></Column>
      <Column field="createdBy" header="생성자" class="f-100-px justify-content-center"></Column>
      <Column field="createdAt" header="생성일자" class="f-100-px justify-content-center"></Column>
      <Column></Column>
    </DataTable>
      </div>
      <div class="col-6">
    <h5 class="m-0 font-bold">소속 직원 현황</h5>
    <DataTable
        ref="memberGrid"
        class="p-datatable-sm"
        :value="memberList"
        dataKey="id"
        scrollHeight="500px"
        scrollable
        :show-gridlines="true"
        :resizable-columns="true"
        table-style="min-height:500px"
    >
      <Column field="username" header="아이디" class="f-200-px"></Column>
      <Column field="name" header="이름" class="f-200-px"></Column>
      <Column></Column>
    </DataTable>
      </div>
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
      <Button class="p-button-outlined" icon="pi pi-check" label="Cancel" @click="fnClose"/>
    </template>
  </Dialog>
</template>

<style scoped>

</style>