<script setup>
import {inject, onMounted, reactive, ref} from "vue";
import _ from "lodash";
import {useToast} from "primevue/usetoast";


const emit = defineEmits(['selectUser', 'createUser']);
const UT = inject('$UT');
const toast = useToast();

const searchParam = reactive({
  name: '',
  page: 0,
  size: 50
});

const memberList = ref([]);
const selectedMember = ref({});

onMounted(() => {
  fnSearch();
});

const fnSearch = (e) => {
  if (!_.isEmpty(e) && e.keyCode !== 13) return;

  UT.post('/api/user/find', searchParam, null)
      .then(data => {
        memberList.value = data.user_list;
      })
      .catch(msg => {
        toast.add({severity: 'error', summary: '실패', detail: msg, life: 3000});
      });
};

const fnCellClick = (event) => {
  if (!event.originalEvent.target.classList.contains('link')) return;
  emit("selectUser", event.data.id);
};

const fnCreateUser = () => {
  emit('createUser');
}

const fnRemoveUser = () => {
  console.log(selectedMember.value)
  UT.post('/api/user/remove', selectedMember.value, null)
      .then(data => {
        toast.add({severity: 'success', summary: '성공', detail: '삭제되었습니다.', life: 3000});
        fnSearch();
      })
      .catch(msg => {
        toast.add({severity: 'error', summary: '실패', detail: msg, life: 3000});
      });
}

defineExpose({
  fnSearch
})
</script>

<template>
  <div class="flex flex-row md:justify-content-between md:align-items-center mb-3">
    <h5 class="m-0 font-bold">직원현황</h5>
    <div class="my-2 flex flex-row">
              <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
                <i class="pi pi-search"/>
                <InputText placeholder="Search..." v-model="searchParam.name" @keydown="fnSearch"/>
              </span>
      <Button class="p-button-primary mr-2" icon="pi pi-plus" label="신규" @click="fnCreateUser"/>
      <Button class="p-button-danger mr-2" icon="pi pi-minus" label="삭제" @click="fnRemoveUser"/>
    </div>
    <Toast></Toast>
  </div>
  <DataTable
      dataKey="id"
      class="p-datatable-sm"
      :paginator="true"
      :value="memberList"
      v-model:selection="selectedMember"
      :rows="25"
      paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
      :rowsPerPageOptions="[5, 10, 25]"
      currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
      responsiveLayout="scroll"
      :show-gridlines="true"
      @row-click="fnCellClick"
  >
    <Column selectionMode="single" headerStyle="width: 3rem"></Column>
    <Column field="username" header="아이디" body-class="link"></Column>
    <Column field="name" header="이름"></Column>
    <Column field="joinDate" header="입사일"></Column>
  </DataTable>
</template>

<style scoped>

</style>