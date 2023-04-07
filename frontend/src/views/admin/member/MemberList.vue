<script setup>
import MemberDetail from "@/views/admin/member/MemberDetail.vue";
</script>

<template>
  <div class="card flex-1">
  <div class="grid">
    <div class="col-6">
      <div class="flex flex-row md:justify-content-between md:align-items-center mb-3">
      <h5 class="m-0 font-bold">직원현황</h5>
      <div class="my-2 flex flex-row">
            <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
              <i class="pi pi-search"/>
              <InputText placeholder="Search..."/>
            </span>
      </div>
      </div>
      <DataTable
          dataKey="id"
          :paginator="true"
          :rows="20"
          paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
          :rowsPerPageOptions="[5, 10, 25]"
          currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
          responsiveLayout="scroll"
          style="min-height: 500px"
      >
        <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
      </DataTable>
    </div>
    <div class="col-6">
      <TabView>
        <TabPanel header="상세정보">
          <MemberDetail></MemberDetail>
        </TabPanel>
        <TabPanel header="권한">

        </TabPanel>
      </TabView>
    </div>
  </div>
  </div>
</template>

<style scoped>

</style>