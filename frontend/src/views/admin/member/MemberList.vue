<script setup>
import {inject, onMounted, reactive, ref} from "vue";
import _ from "lodash";
import {useToast} from "primevue/usetoast";

  const UT = inject('$UT');
  const toast = useToast();

  const searchParam = reactive({
    name : '',
    page : 0,
    size : 50
  });

  const memberList = ref([]);

  onMounted(()=> {
    search();
  });

  const search = (e) => {
    if(!_.isEmpty(e) && e.keyCode !== 13) return;

    UT.post('/api/user/find', searchParam, null)
        .then(data => {
          memberList.value = data.user_list;
        })
        .catch(msg => {
          toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
        });
  }
</script>

<template>
      <div class="flex flex-row md:justify-content-between md:align-items-center mb-3">
        <h5 class="m-0 font-bold">직원현황</h5>
        <div class="my-2 flex flex-row">
              <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
                <i class="pi pi-search"/>
                <InputText placeholder="Search..." v-model="searchParam.name" @keydown="search"/>
              </span>
            <Button class="p-button-primary mr-2" icon="pi pi-plus" label="신규"/>
        </div>
        <Toast></Toast>
      </div>
      <DataTable
          dataKey="id"
          :paginator="true"
          :value="memberList"
          :rows="20"
          paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
          :rowsPerPageOptions="[5, 10, 25]"
          currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
          responsiveLayout="scroll"
          style="min-height: 500px"
      >
        <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
      </DataTable>
</template>

<style scoped>

</style>