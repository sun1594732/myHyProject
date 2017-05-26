package com.hyzs.onekeyhelp.contact.bean;

import java.util.List;

/**
 * Created by wubin on 2017/3/11.
 */

public class ContactGroupBean {

    /**
     * conactList : [{"uid":179,"targetUserId":189,"relationGroup":"邻居","trueName":"刘娟","userNick":"最最幸福的小黑妞","remarkName":"","urgentFlag":"否","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"13699214250"},{"uid":179,"targetUserId":193,"relationGroup":"邻居","trueName":"陈鑫宇","userNick":"云山","remarkName":"","urgentFlag":"否","avatar":"http://img.zglzxf.com/default/2016/0526/4641997953795445629.gif","phone":"188000808091"},{"uid":179,"targetUserId":192,"relationGroup":"家人","trueName":"牛萌萌","userNick":"小萌是长颈鹿","remarkName":"","urgentFlag":"否","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"15901095095"},{"uid":179,"targetUserId":183,"relationGroup":"朋友","trueName":"郭杉","userNick":"仙猫","remarkName":"","urgentFlag":"否","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"15600578619"},{"uid":179,"targetUserId":191,"relationGroup":"邻居","trueName":"黄春丽","userNick":"倾晨","remarkName":"","urgentFlag":"否","avatar":"http://img.zglzxf.com/default/2016/0509/5332633944418864301.jpg","phone":"13167505028"},{"uid":179,"targetUserId":184,"relationGroup":"家人","trueName":"Fairy","userNick":"魔小七","remarkName":"","urgentFlag":"是","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"18830756686"},{"uid":179,"targetUserId":186,"relationGroup":"家人","trueName":"罗建勋","userNick":"罗建勋","remarkName":"","urgentFlag":"是","avatar":"http://img.zglzxf.com/default/2016/0907/5492768518989939222.png","phone":"18611880257"},{"uid":179,"targetUserId":182,"relationGroup":"朋友","trueName":"程紧","userNick":"锦衣卫","remarkName":"","urgentFlag":"否","avatar":"http://img.zglzxf.com/default/2016/0913/5394853051054567065.jpg","phone":"18610174883"},{"uid":179,"targetUserId":185,"relationGroup":"家人","trueName":"李瑞冬","userNick":"Rose123","remarkName":"","urgentFlag":"是","avatar":"http://img.zglzxf.com/default/2016/0427/4931099640502363940.jpg","phone":"13693547604"},{"uid":179,"targetUserId":187,"relationGroup":"邻居","trueName":"周轶文","userNick":"雯雯","remarkName":"","urgentFlag":"否","avatar":"http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg","phone":"15110079056"},{"uid":179,"targetUserId":188,"relationGroup":"邻居","trueName":"朱月","userNick":"Immannuel","remarkName":"","urgentFlag":"否","avatar":"http://img.zglzxf.com/default/2016/0615/5617480022369682741.png","phone":"13220119911"},{"uid":179,"targetUserId":190,"relationGroup":"邻居","trueName":"虚构","userNick":"貔貅","remarkName":"","urgentFlag":"否","avatar":"http://img.zglzxf.com/default/2016/1009/5452874974477979821.jpg","phone":"15712925578"}]
     * totatl : 12
     * uid : 179
     * trueName : 宁夏
     * userNick : 娃哈哈
     * avatar : http://img.zglzxf.com/default/2016/0509/5633710139180658127.jpg
     * phone : 15910745659
     * message : 正常
     * code : 10000
     */

    private int totatl;
    private int uid;
    private String trueName;
    private String userNick;
    private String avatar;
    private String phone;
    private List<ContactListBean> contactList;

    public int getTotatl() {
        return totatl;
    }

    public void setTotatl(int totatl) {
        this.totatl = totatl;
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

    public List<ContactListBean> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactListBean> conactList) {
        this.contactList = conactList;
    }

    public static class ContactListBean {
        /**
         * uid : 179
         * targetUserId : 189
         * relationGroup : 邻居
         * trueName : 刘娟
         * userNick : 最最幸福的小黑妞
         * remarkName :
         * urgentFlag : 否
         * avatar : http://img.zglzxf.com/default/2016/0415/t013ce300916004b321.jpg
         * phone : 13699214250
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
                    '}';
        }

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
    }
}
