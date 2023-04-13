<script setup>

import {inject, onMounted, reactive, ref} from "vue";
import _ from "lodash";
import useCommonStore from "@/store/commonStore";
import {useToast} from "primevue/usetoast";

const toast = useToast();
const commonStore = useCommonStore();
const UT = inject('$UT');

// 권한 목록
const authorities = ref([]);
// 선택한 권한 목록
const selectedAuthority = ref({});
// 팝업 오픈 여부
const showDialog = ref(false);

const saveModel = reactive({
  id : null,
  name : '',
  reset () {
    this.id = null;
    this.name = '';
  },
  setData (data) {
    this.id = data.id;
    this.name = data.name;
  }
});

const searchParam = reactive({
  searchName: '',
  reset () {
    this.searchName = '';
  }
});

onMounted(() => {
  fnSearchAuthorities();
});

const fnOpen = () => {
  showDialog.value = true;
}

const fnClose = () => {
  saveModel.reset();
  showDialog.value = false;
};

const fnSearchAuthorities = (e) => {
  if(!_.isEmpty(e) && e.keyCode !== 13) return;
  const name = searchParam.name || '';

  UT.get('/api/authority/find?name='+encodeURIComponent(name.trim()), null)
      .then(receiveData => {
        authorities.value = receiveData.authList;
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
  ;
};

const fnSaveAuthority = () => {
  const url = _.isEmpty(saveModel.id) ? 'create' : 'modify';
  const msg = _.isEmpty(saveModel.id) ? '생성' : '수정';
  saveModel.name = saveModel.name.trim();
  UT.post('/api/authority/'+url, saveModel, null)
      .then(() => {
        toast.add({ severity: 'success', summary: '성공', detail: '저장되었습니다.', life: 3000 });
        fnSearchAuthorities();
        fnClose();
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
};

const fnRemoveAuthority = () => {
  if(_.isEmpty(selectedAuthority.value)) {
    toast.add({severity: 'warning', summary: '실패', detail: "삭제할 권한을 선택해주세요.", life: 3000});
    return;
  }
  UT.post('/api/authority/remove', selectedAuthority.value, null)
      .then(()=> {
        fnSearchAuthorities();
        toast.add({ severity: 'success', summary: '성공', detail: '권한이 삭제되었습니다.', life: 3000 });
        selectedAuthority.value = {};
      })
      .catch( msg =>{
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
};

const fnModifyAuthority = () => {
  saveModel.setData(_.cloneDeep(selectedAuthority.value));
  fnOpen();
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
              <InputText v-model="searchParam.name" placeholder="Search..." @keydown="fnSearchAuthorities"/>
            </span>
        <Button class="p-button-primary mr-2" icon="pi pi-plus" label="신규" @click="fnOpen"/>
        <Button class="p-button-success mr-2" icon="pi pi-plus" label="수정" @click="fnModifyAuthority" :disabled="_.isEmpty(selectedAuthority)"/>
        <Button class="p-button-danger" icon="pi pi-trash" label="삭제" @click="fnRemoveAuthority" :disabled="_.isEmpty(selectedAuthority)"/>
      </div>
    </div>
    <DataTable
        ref="authorityGrid"
        :value="authorities"
        v-model:selection="selectedAuthority"
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
  <Dialog v-model:visible="showDialog" :breakpoints="{ '960px': '75vw' }" :modal="true" :style="{ width: '30vw' }"
          header="권한">
    <div class="p-fluid">
      <div class="field">
        <label for="name1">권한명을 입력하세요.</label>
        <InputText id="name1" v-model="saveModel.name" type="text"/>
      </div>
    </div>
    <template #footer>
      <Button class="p-button-outlined" icon="pi pi-check" label="Ok" @click="fnSaveAuthority"/>
      <Button class="p-button-outlined" icon="pi pi-check" label="Cancel" @click="fnClose"/>
    </template>
  </Dialog>
</template>

<style scoped>

</style>