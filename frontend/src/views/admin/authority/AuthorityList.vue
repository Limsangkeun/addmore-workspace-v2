<script setup>

import {inject, onMounted, reactive} from "vue";
import _ from "lodash";
import useCommonStore from "@/store/commonStore";
import {useToast} from "primevue/usetoast";

const toast = useToast();
const commonStore = useCommonStore();
const UT = inject('$UT');

const data = reactive({
  authorities : [],
  selectedAuthority: {},
  searchName: '',
  saveModel: {
    id : null,
    name : ''
  },
  showDialog : false
});

onMounted(() => {
  searchAuthorities();
});

const open = () => {
  data.showDialog = true;
}

const close = () => {
  resetSaveModel();
  data.showDialog = false;
};

const searchAuthorities = (e) => {
  if(!_.isEmpty(e) && e.keyCode !== 13) return;

  UT.get('/api/authority/find?name='+encodeURIComponent(data.searchName.trim()), null)
      .then(receiveData => {
        data.authorities = receiveData.authList;
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
  ;
};

const resetSaveModel = () => {
  data.saveModel = {
    id : null,
    name : ''
  };
}

const saveAuthority = () => {
  const saveModel = data.saveModel;
  const url = _.isEmpty(data.saveModel.id) ? 'create' : 'modify';
  const msg = _.isEmpty(data.saveModel.id) ? '생성' : '수정';
  saveModel.name = saveModel.name.trim();
  UT.post('/api/authority/'+url, saveModel, null)
      .then(() => {
        toast.add({ severity: 'success', summary: '성공', detail: '권한이 '+msg+'되었습니다.', life: 3000 });
        resetSaveModel();
        searchAuthorities();
        close();
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
};

const removeAuthority = () => {
  const selectedAuthority = data.selectedAuthority;
  if(_.isEmpty(selectedAuthority)) {
    toast.add({severity: 'warning', summary: '실패', detail: "삭제할 권한을 선택해주세요.", life: 3000});
    return;
  }
  UT.post('/api/authority/remove', selectedAuthority, null)
      .then(()=> {
        searchAuthorities();
        toast.add({ severity: 'success', summary: '성공', detail: '권한이 삭제되었습니다.', life: 3000 });
      })
      .catch( msg =>{
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
};

const modifyAuthority = () => {
  data.saveModel = _.cloneDeep(data.selectedAuthority);
  open();
}


</script>

<template>
  <div class="card flex-1 flex-column">
    <Toast />
    <div class="flex flex-row md:justify-content-between md:align-items-center mb-3">
      <h5 class="m-0 font-bold">권한현황</h5>
      <div class="my-2 flex flex-row">
            <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
              <i class="pi pi-search"/>
              <InputText v-model="data.searchName" placeholder="Search..." @keydown="searchAuthorities"/>
            </span>
        <Button class="p-button-primary mr-2" icon="pi pi-plus" label="신규" @click="open"/>
        <Button class="p-button-success mr-2" icon="pi pi-plus" label="수정" @click="modifyAuthority" :disabled="_.isEmpty(data.selectedAuthority)"/>
        <Button class="p-button-danger" icon="pi pi-trash" label="삭제" @click="removeAuthority" :disabled="_.isEmpty(data.selectedAuthority)"/>
      </div>
    </div>
    <DataTable
        ref="authorityGrid"
        :value="data.authorities"
        v-model:selection="data.selectedAuthority"
        class="p-datatable-sm flex-1"
        dataKey="id"
        scrollHeight="500px"
        scrollable
        :show-gridlines="true"
    >
      <Column selectionMode="single" style="flex:0 0 40px"></Column>
      <Column field="name" header="권한명" style="flex: 0 0 200px"></Column>
      <Column field="createdBy" header="생성자" style="flex: 0 0 100px;"></Column>
      <Column field="createdAt" header="생성일자" style="flex: 0 0 100px"></Column>
      <Column></Column>
    </DataTable>
  </div>
  <Dialog v-model:visible="data.showDialog" :breakpoints="{ '960px': '75vw' }" :modal="true" :style="{ width: '30vw' }"
          header="권한">
    <div class="p-fluid">
      <div class="field">
        <label for="name1">권한명을 입력하세요.</label>
        <InputText id="name1" v-model="data.saveModel.name" type="text"/>
      </div>
    </div>
    <template #footer>
      <Button class="p-button-outlined" icon="pi pi-check" label="Ok" @click="saveAuthority"/>
      <Button class="p-button-outlined" icon="pi pi-check" label="Cancel" @click="close"/>
    </template>
  </Dialog>
</template>

<style scoped>

</style>