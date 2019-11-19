package com.tattle.tantou.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 版本更新bean
 */
public class UpdateInfoBean implements Serializable {

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private VersionIosBean version_ios;
        private VersionAndroidBean version_android;
        private String down_url_ios;
        private String down_url_android;

        public VersionIosBean getVersion_ios() {
            return version_ios;
        }

        public void setVersion_ios(VersionIosBean version_ios) {
            this.version_ios = version_ios;
        }

        public VersionAndroidBean getVersion_android() {
            return version_android;
        }

        public void setVersion_android(VersionAndroidBean version_android) {
            this.version_android = version_android;
        }

        public String getDown_url_ios() {
            return down_url_ios;
        }

        public void setDown_url_ios(String down_url_ios) {
            this.down_url_ios = down_url_ios;
        }

        public String getDown_url_android() {
            return down_url_android;
        }

        public void setDown_url_android(String down_url_android) {
            this.down_url_android = down_url_android;
        }

        public static class VersionIosBean {
            /**
             * version : 1.0.1
             * is_force : 0
             * auto_update : false
             * content : ["1.优化图片加载","2.优化验证码问题","3.修改其他已知bug"]
             */

            private String version;
            private String is_force;
            private boolean auto_update;
            private List<String> content;

            public boolean isAuto_update() {
                return auto_update;
            }

            public void setAuto_update(boolean auto_update) {
                this.auto_update = auto_update;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getIs_force() {
                return is_force;
            }

            public void setIs_force(String is_force) {
                this.is_force = is_force;
            }

            public List<String> getContent() {
                return content;
            }

            public void setContent(List<String> content) {
                this.content = content;
            }
        }

        public static class VersionAndroidBean {
            /**
             * version : 1.0.1
             * is_force : 0
             * auto_update : true
             * content : ["1.优化图片加载","2.优化验证码问题","3.修改其他已知bug"]
             */

            private String version;
            private String is_force;
            private boolean auto_update;
            private List<String> content;

            public boolean isAuto_update() {
                return auto_update;
            }

            public void setAuto_update(boolean auto_update) {
                this.auto_update = auto_update;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getIs_force() {
                return is_force;
            }

            public void setIs_force(String is_force) {
                this.is_force = is_force;
            }

            public List<String> getContent() {
                return content;
            }

            public void setContent(List<String> content) {
                this.content = content;
            }
        }
    }
}
