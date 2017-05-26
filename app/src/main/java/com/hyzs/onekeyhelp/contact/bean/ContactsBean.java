package com.hyzs.onekeyhelp.contact.bean;

import java.util.List;


public class ContactsBean {


    /**
     * contactList : [{"uid":179,"targetUserId":188,"relationGroup":"閭诲眳","trueName":"鏈辨湀","userNick":"Immannuel","remarkName":"","urgentFlag":"鍚�","avatar":"http://img.zglzxf.com/default/2016/0615/5617480022369682741.png","phone":"13220119911","sortFirstChar":"I "},{"uid":179,"targetUserId":182,"relationGroup":"鏈嬪弸","trueName":"绋嬬揣","userNick":"閿﹁。鍗�","remarkName":"","urgentFlag":"鍚�","avatar":"http://img.zglzxf.com/default/2016/0913/5394853051054567065.jpg","phone":"18610174883","sortFirstChar":"J "},{"uid":179,"targetUserId":186,"relationGroup":"瀹朵汉","trueName":"缃楀缓鍕�","userNick":"缃楀缓鍕�","remarkName":"","urgentFlag":"鏄�","avatar":"http://img.zglzxf.com/default/2016/0907/5492768518989939222.png","phone":"18611880257","sortFirstChar":"L "},{"uid":179,"targetUserId":184,"relationGroup":"瀹朵汉","trueName":"Fairy","userNick":"榄斿皬涓�","remarkName":"","urgentFlag":"鏄�","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"18830756686","sortFirstChar":"M "},{"uid":179,"targetUserId":190,"relationGroup":"閭诲眳","trueName":"铏氭瀯","userNick":"璨旇矃","remarkName":"","urgentFlag":"鍚�","avatar":"http://img.zglzxf.com/default/2016/1009/5452874974477979821.jpg","phone":"15712925578","sortFirstChar":"P "},{"uid":179,"targetUserId":191,"relationGroup":"閭诲眳","trueName":"榛勬槬涓�","userNick":"鍊炬櫒","remarkName":"","urgentFlag":"鍚�","avatar":"http://img.zglzxf.com/default/2016/0509/5332633944418864301.jpg","phone":"13167505028","sortFirstChar":"Q "},{"uid":179,"targetUserId":185,"relationGroup":"瀹朵汉","trueName":"鏉庣憺鍐�","userNick":"Rose123","remarkName":"","urgentFlag":"鏄�","avatar":"http://img.zglzxf.com/default/2016/0427/4931099640502363940.jpg","phone":"13693547604","sortFirstChar":"R "},{"uid":179,"targetUserId":187,"relationGroup":"閭诲眳","trueName":"鍛ㄨ蕉鏂�","userNick":"闆洴","remarkName":"","urgentFlag":"鍚�","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"15110079056","sortFirstChar":"W "},{"uid":179,"targetUserId":192,"relationGroup":"瀹朵汉","trueName":"鐗涜悓钀�","userNick":"灏忚悓鏄暱棰堥箍","remarkName":"","urgentFlag":"鍚�","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"15901095095","sortFirstChar":"X "},{"uid":179,"targetUserId":183,"relationGroup":"鏈嬪弸","trueName":"閮潐","userNick":"浠欑尗","remarkName":"","urgentFlag":"鍚�","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"15600578619","sortFirstChar":"X "},{"uid":179,"targetUserId":193,"relationGroup":"閭诲眳","trueName":"闄堥懌瀹�","userNick":"浜戝北","remarkName":"","urgentFlag":"鍚�","avatar":"http://img.zglzxf.com/default/2016/0526/4641997953795445629.gif","phone":"188000808091","sortFirstChar":"Y "},{"uid":179,"targetUserId":189,"relationGroup":"閭诲眳","trueName":"鍒樺","userNick":"鏈\u20ac鏈\u20ac骞哥鐨勫皬榛戝","remarkName":"","urgentFlag":"鍚�","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"13699214250","sortFirstChar":"Z "}]
     * total : 12
     * uid : 179
     * trueName : 瀹佸
     * userNick : 濞冨搱鍝�
     * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
     * phone : 15910745659
     * message : 姝ｅ父
     * code : 10000
     */

    private int total;
    private int uid;
    private String trueName;
    private String userNick;
    private String avatar;
    private String phone;
    private String message;
    private String code;
    private List<ContactListBean> contactList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ContactListBean> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactListBean> contactList) {
        this.contactList = contactList;
    }

    public static class ContactListBean {
        /**
         * uid : 179
         * targetUserId : 188
         * relationGroup : 閭诲眳
         * trueName : 鏈辨湀
         * userNick : Immannuel
         * remarkName :
         * urgentFlag : 鍚�
         * avatar : http://img.zglzxf.com/default/2016/0615/5617480022369682741.png
         * phone : 13220119911
         * sortFirstChar : I
         */

        private int uid;
        private int targetUserId;
        private String relationGroup;
        private String trueName;
        private String userNick;
        private String remarkName;
        private String urgentFlag;
        private String avatar;
        private String phone;
        private String sortFirstChar;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getTargetUserId() {
            return targetUserId;
        }

        public void setTargetUserId(int targetUserId) {
            this.targetUserId = targetUserId;
        }

        public String getRelationGroup() {
            return relationGroup;
        }

        public void setRelationGroup(String relationGroup) {
            this.relationGroup = relationGroup;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getUserNick() {
            return userNick;
        }

        public void setUserNick(String userNick) {
            this.userNick = userNick;
        }

        public String getRemarkName() {
            return remarkName;
        }

        public void setRemarkName(String remarkName) {
            this.remarkName = remarkName;
        }

        public String getUrgentFlag() {
            return urgentFlag;
        }

        public void setUrgentFlag(String urgentFlag) {
            this.urgentFlag = urgentFlag;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSortFirstChar() {
            return sortFirstChar;
        }

        public void setSortFirstChar(String sortFirstChar) {
            this.sortFirstChar = sortFirstChar;
        }

        @Override
        public String toString() {
            return "ContactListBean{" +
                    "uid=" + uid +
                    ", targetUserId=" + targetUserId +
                    ", relationGroup='" + relationGroup + '\'' +
                    ", trueName='" + trueName + '\'' +
                    ", userNick='" + userNick + '\'' +
                    ", remarkName='" + remarkName + '\'' +
                    ", urgentFlag='" + urgentFlag + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", phone='" + phone + '\'' +
                    ", sortFirstChar='" + sortFirstChar + '\'' +
                    '}';
        }
    }
}
