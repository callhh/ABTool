package com.callhh.nn.bean;

import java.util.List;

/**
 * Created by callhh on 2020/3/9
 */
public class DemoBean {

        private List<TypeBean> type;

        public List<TypeBean> getType() {
            return type;
        }

        public void setType(List<TypeBean> type) {
            this.type = type;
        }

        public static class TypeBean {
            /**
             * cid : 10043
             * type_name : 新手必看
             * sort : 0
             */

            private String cid;
            private String type_name;
            private String sort;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }
        }
}
