package com.hyva.bsfms.bs.bspojo;

import java.util.List;

public class BasePojo {

        private int pageNo;
        private  int pageSize;
        private boolean firstPage;
        private boolean lastPage;
        private boolean nextPage;
        private boolean prevPage;
        private Long fromItemId;
        private Long toItemId;
        private Long itemCategoryId;
        private String fromDates;
        private String toDate;
        private String name;
        private boolean status;
        private boolean filterApplied;
        private String empName;
        private Long id;
        private Long Location;
        private Long currency;
        private Long customer;
        private Long employee;
        private String searchText;
        private Long supplierId;
        private Long accountId;
        private List list;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public List getList() {
            return list;
        }

        public void setList(List list) {
            this.list = list;
        }

        public Long getAccountId() {
            return accountId;
        }

        public void setAccountId(Long accountId) {
            this.accountId = accountId;
        }

        public Long getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(Long supplierId) {
            this.supplierId = supplierId;
        }

        public Long getEmployee() {
            return employee;
        }

        public void setEmployee(Long employee) {
            this.employee = employee;
        }

        public Long getLocation() {
            return Location;
        }

        public void setLocation(Long location) {
            Location = location;
        }

        public Long getCurrency() {
            return currency;
        }

        public void setCurrency(Long currency) {
            this.currency = currency;
        }

        public Long getCustomer() {
            return customer;
        }

        public void setCustomer(Long customer) {
            this.customer = customer;
        }

        public String getSearchText() {
            return searchText;
        }

        public void setSearchText(String searchText) {
            this.searchText = searchText;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        private String selectedFilter="none";

        public String getSelectedFilter() {
            return selectedFilter;
        }

        public void setSelectedFilter(String selectedFilter) {
            this.selectedFilter = selectedFilter;
        }

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        public BasePojo(int pageNo, boolean firstPage, boolean lastPage, boolean nextPage, boolean prevPage) {
            this.pageNo = pageNo;
            this.firstPage = firstPage;
            this.lastPage = lastPage;
            this.nextPage = nextPage;
            this.prevPage = prevPage;
        }

        public Long getFromItemId() {
            return fromItemId;
        }

        public void setFromItemId(Long fromItemId) {
            this.fromItemId = fromItemId;
        }

        public Long getToItemId() {
            return toItemId;
        }

        public void setToItemId(Long toItemId) {
            this.toItemId = toItemId;
        }

        public Long getItemCategoryId() {
            return itemCategoryId;
        }

        public void setItemCategoryId(Long itemCategoryId) {
            this.itemCategoryId = itemCategoryId;
        }

        public String getFromDates() {
            return fromDates;
        }

        public void setFromDates(String fromDates) {
            this.fromDates = fromDates;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isFilterApplied() {
            return filterApplied;
        }

        public void setFilterApplied(boolean filterApplied) {
            this.filterApplied = filterApplied;
        }

        public BasePojo(){}

        /**
         * Sets new pageSize.
         *
         * @param pageSize New value of pageSize.
         */
        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        /**
         * Gets pageSize.
         *
         * @return Value of pageSize.
         */
        public int getPageSize() {
            return pageSize;
        }

        /**
         * Gets pageNo.
         *
         * @return Value of pageNo.
         */
        public int getPageNo() {
            return pageNo;
        }

        /**
         * Sets new pageNo.
         *
         * @param pageNo New value of pageNo.
         */
        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        /**
         * Sets new firstPage.
         *
         * @param firstPage New value of firstPage.
         */
        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        /**
         * Gets lastPage.
         *
         * @return Value of lastPage.
         */
        public boolean isLastPage() {
            return lastPage;
        }

        /**
         * Sets new lastPage.
         *
         * @param lastPage New value of lastPage.
         */
        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        /**
         * Gets firstPage.
         *
         * @return Value of firstPage.
         */
        public boolean isFirstPage() {
            return firstPage;
        }

        /**
         * Sets new prevPage.
         *
         * @param prevPage New value of prevPage.
         */
        public void setPrevPage(boolean prevPage) {
            this.prevPage = prevPage;
        }

        /**
         * Gets nextPage.
         *
         * @return Value of nextPage.
         */
        public boolean isNextPage() {
            return nextPage;
        }

        /**
         * Sets new nextPage.
         *
         * @param nextPage New value of nextPage.
         */
        public void setNextPage(boolean nextPage) {
            this.nextPage = nextPage;
        }

        /**
         * Gets prevPage.
         *
         * @return Value of prevPage.
         */
        public boolean isPrevPage() {
            return prevPage;
        }
    }


