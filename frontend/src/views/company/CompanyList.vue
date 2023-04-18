<script setup>
import {inject, reactive, ref} from "vue";
import _ from "lodash";
import {useToast} from "primevue/usetoast";

const UT = inject('$UT');
const toast = useToast();
const companyList = ref([]);
const searchParam = reactive({
  name: '',
  totalCount: 0,
  totalPage: 0,
  reset() {
    this.name = '';
    this.totalCount = 0;
    this.totalPage = 0;
  }
});

const fnKeyDown = (event) => {
  if(event && event.keyCode === 13) fnSearch();
}

const fnSearch = (event) => {
  if(_.isEmpty(event)) {
    event = {
      page : 0,
      rows : 50
    }
  }
  UT.get('/api/company'+`?page=${event.page? event.page : 0}&size=${event.rows? event.rows : 50}`, null)
      .then(data => {
        companyList.value = data.company_list;
        searchParam.totalCount = data.total_count;
        searchParam.totalPage = data.total_page;
      })
      .catch(msg => {
        console.log(msg);
      });
};

</script>

<template>
  <div class="card flex-1">
    <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center mb-3">
      <h5 class="m-0 font-bold">업체정보</h5>
      <div class="my-2 flex flex-row">
            <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
                  <i class="pi pi-search"/>
                  <InputText class="p-inputtext-sm" placeholder="업체명 검색" @keydown="fnKeyDown"/>
                </span>
      </div>
    </div>
    <DataTable
        id="companyGrid"
        class="p-datatable-sm"
        :value="companyList"
        lazy
        dataKey="id"
        @page="fnSearch($event)"
        :paginator="true"
        :rows="25"
        v-model:total-records="searchParam.totalCount"
        :last="searchParam.totalPage"
        :rowsPerPageOptions="[5, 10, 25, 50]"
        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
        show-gridlines
        scrollable
        scrollHeight="flex"
    >
      <Column class="f-40-px" selectionMode="multiple"></Column>
      <Column field="name" header="업체명"></Column>
      <Column field="category" header="업종"></Column>
      <Column field="addr" header="주소"></Column>
      <Column field="tel" header="연락처"></Column>
      <Column field="is114" header="114등록여부"></Column>
      <Column field="regDate" header="업체등록일"></Column>
      <Column field="createdAt" header="생성일" data-type="date"></Column>
      <Column field="test1" header="관련페이지" class="f-200-px justify-content-center">
        <template #body="props">
          <span class="p-buttonset">
            <Button label="홈" class="p-button-sm p-button-" style="width:80px;"></Button>
            <Button label="상세" class="p-button-sm p-button-secondary" style="width:80px;"></Button>
          </span>
        </template>
      </Column>
      <Column field="test1" header="관련페이지"></Column>
    </DataTable>
  </div>
  <Toast/>
</template>

<style scoped>

</style>