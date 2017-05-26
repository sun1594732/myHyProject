package com.hyzs.onekeyhelp.netRequest;


public class PortUtil {
    //    public static String baseUrl = "http://csnew.hyzsnt.com/";
//    public static String NewsUrl = "http://csold.hyzsnt.com/";
//    public static String H5Url = "http://csh5.hyzsnt.com/";
    public static String baseUrl = "http://app.hyzsnt.com/";
    public static String NewsUrl = "http://old.hyzsnt.com/";
    public static String H5Url = "http://h5.hyzsnt.com/";

    public static String loginbyphone = NewsUrl + "/user/loginbyphone";
    public static String EZZX = H5Url + "/web/rzzx.aspx";
    public static String Service_Handler = H5Url + "/H5Service/Service_Handler.ashx";
    public static String index = H5Url + "/web/index.aspx";
    public static String SuggestionFeedback_Handler = H5Url + "/H5Service/SuggestionFeedback_Handler.ashx";
    public static String HXGroupsCreate = baseUrl + "/UserService.asmx/HXGroupsCreate";
    public static String HXGroupsList = baseUrl + "/FamilyService.asmx/HXGroupsList";
    public static String FamilyMemberList = baseUrl + "FamilyService.asmx/FamilyMemberList";
    public static String FamilyDynamicList = baseUrl + "FamilyService.asmx/FamilyDynamicList";
    public static String FamilyMemberAdd = baseUrl + "FamilyService.asmx/FamilyMemberAdd";//添加成员
    public static String FamilySearchAddList = baseUrl + "FamilyService.asmx/FamilySearchAddList";//添加列表
    public static String FamilyMemberDel = baseUrl + "FamilyService.asmx/FamilyMemberDel";//删除成员
    public static String VehicleRescueTeamDetails = baseUrl + "VehicleRescueService.asmx/VehicleRescueTeamDetails";//删除成员
    public static String LifeServiceOrganizationDetails = baseUrl + "LifeService.asmx/LifeServiceOrganizationDetails";
    public static String LifeServicePersonalDetails = baseUrl + "LifeService.asmx/LifeServicePersonalDetails";
    public static String LifeServiceOrganization = baseUrl + "LifeService.asmx/LifeServiceOrganization";
    public static String LifeServicePersonal = baseUrl + "LifeService.asmx/LifeServicePersonal";
    public static String LifeServiceCommentList = baseUrl + "LifeService.asmx/LifeServiceCommentList";
    public static String LifeServiceSPAdd = baseUrl + "LifeService.asmx/LifeServiceSPAdd";
    public static String LifeServiceComplainAdd = baseUrl + "LifeService.asmx/LifeServiceComplainAdd";
    public static String LifeServiceCommentAdd = baseUrl + "LifeService.asmx/LifeServiceCommentAdd";

    public static String balance = NewsUrl + "/Appeal/balance";
    public static String ApplyJoinCommunity = baseUrl + "/CommunityService.asmx/ApplyJoinCommunity";
    public static String EventMemberList = baseUrl + "/ActivityService.asmx/EventMemberList";
    public static String UserContactListDelete = baseUrl + "/ContactService.asmx/UserContactListDelete";
    public static String EventRegistrationDetails = baseUrl + "/ActivityService.asmx/EventRegistrationDetails";
    public static String ActivityApply = baseUrl + "/ActivityService.asmx/ActivityApply";
    public static String SearchAroundList = baseUrl + "/ContactService.asmx/SearchAroundList";
    public static String MovieToGroup = baseUrl + "/ContactService.asmx/MovieToGroup";
    public static String ContactSearchAddList = baseUrl + "/ContactService.asmx/ContactSearchAddList";
    public static String PublishOrAppendVehicleRescue = baseUrl + "/VehicleRescueService.asmx/PublishOrAppendVehicleRescue";
    public static String DoUpLoad = baseUrl + "/Upload.asmx/DoUpLoad";
    public static String StreamUpLoad = baseUrl + "/Upload.asmx/StreamUpLoad";
    public static String CircleCommentUser = baseUrl + "/CircleService.asmx/CircleCommentUser";
    public static String CircleCommentList = baseUrl + "/CircleService.asmx/CircleCommentList";
    public static String UserContactListBatchQuery = baseUrl + "/ContactService.asmx/UserContactListBatchQuery";

    // 求助-完成帮助用户列表（一键帮助，车辆救援，生活求助通用）
    public static String ComplateSeekHelpUserListCommon = baseUrl + "/VehicleRescueService.asmx/ComplateSeekHelpUserListCommon";
    // 搜索社区
    public static String SearchCommunity = baseUrl + "/CommunityService.asmx/SearchCommunity";
    // 我的社区列表
    public static String MyCommunity = baseUrl + "/CommunityService.asmx/Mycommunity";
    // 设置默认社区
    public static String SetDefaultCommunity = baseUrl + "/CommunityService.asmx/SetDefaultCommunity";
    //    通讯录列表页
    public static String ContactList = baseUrl + "/ContactService.asmx/UserContactList?currId=179&searchKeyWords=&pageIndex=1&pageSize=999";
    //    删除联系人
    public static String DeleteContact = baseUrl + "/Contact.asmx/UserContactListDelete";
    //   搜索添加
    public static String SearchContact = baseUrl + "/ContactService.asmx/ContactSearchAddList";
    //    添加好友验证列表
    public static String SearchCheckContact = baseUrl + "/ContactService.asmx/FriendAddCheck";
    //    圈子列表页
    public static String CircleList = baseUrl + "/CircleService.asmx/CircleList";
    //    圈子类型
    public static String CircleTypeList = baseUrl + "/CircleService.asmx/CircleTypeList";
    //    圈子详情
    public static String CircleDetail = baseUrl + "/CircleService.asmx/CircleCommentUser";
    //    圈子详情评论列表
    public static String CircleDetailComment = baseUrl + "/CircleService.asmx/CircleCommentList";
    //    加入圈子
    public static String CircleJoin = baseUrl + "/CircleService.asmx/CircleJoin";
    //    圈子发布
    public static String CirclePublish = baseUrl + "/CircleService.asmx/CircleAdd";
    //    圈子发布评论
    public static String CircleComment = baseUrl + "/CircleService.asmx/CircleComment";
    //    圈子点赞
    public static String CirclePraise = baseUrl + "/CircleService.asmx/CirclePraise";
    //    通讯录设置紧急联系人
    public static String SetUrgencyContact = baseUrl + "/ContactService.asmx/SetUrgencyContact";

    public static String UpLoadPicture = baseUrl + "/Upload.asmx/DoUpLoad";
    //    圈子详情评论点赞
    public static String CircleDetailCommentPraise = baseUrl + "/CircleService.asmx/CircleCommentPraise";
    //    论坛列表
    public static String ForumList = baseUrl + "/ForumService.asmx/ForumList";
    //    论坛详情
    public static String ForumDetail = baseUrl + "/ForumService.asmx/ForumDetails";
    //    论坛详情评论
    public static String ForumDetailComment = baseUrl + "/ForumService.asmx/ForumeCommentList";
    //    论坛详情评论点赞
    public static String ForumDetailCommentPraise = baseUrl + "/ForumService.asmx/ForumCommentPraise";
    //    论坛详情发表评论
    public static String ForumDetailAddComment = baseUrl + "/ForumService.asmx/ForumComment";
    //    论坛类型
    public static String ForumType = baseUrl + "/ForumService.asmx/ForumTypeList";
    //    发布论坛
    public static String ForumPublish = baseUrl + "/ForumService.asmx/ForumAdd";
    //    生活求助发布
    public static String PublishSeekHelp = baseUrl + "/LifeSeekHelpService.asmx/PublishSeekHelp";
    //    我的相册发布
    public static String PublishMineAlbum = baseUrl + "/PersonalAlbum.asmx/PersonalAlbumPublish";
    //    个人主页
    public static String UserHomePage = baseUrl + "/ContactService.asmx/UserHomePage";
    //    个人相册列表
    public static String UserAlbumPage = baseUrl + "/PersonalAlbum.asmx/PersonalAlbumDynamicList";
    //    微信支付
    public static String WXPayUrl = NewsUrl + "/welfare/addWx";
    public static String START_WEIXIN = "http://wxpay.zglzxf.com/Handler.ashx";
    //    主页BANNER
    public static String HomeBanner = NewsUrl + "/index/focus";
    //    主页活动类型
    public static String HomeActiveType = baseUrl + "/ActivityService.asmx/ActivityType";
    //    主页活动列表
    public static String HomeActiveList = baseUrl + "/ActivityService.asmx/EventRegistrationList";
    //    主页数据
    public static String HomePage = baseUrl + "/HomePageService.asmx/HomePage";
    //    主页社区动态
    public static String HomeCommunityDynamicList = baseUrl + "HomePageService.asmx/HomeCommunityDynamicList";
    //    公告点击记录
    public static String UserStatistics = baseUrl + "/StatisticsService.asmx/UCR";
    //    我的余额
    public static String MyMoney = NewsUrl + "/Appeal/balance";
    //    注册
    public static String RegisteUrl = NewsUrl + "/user/loginbyphone";
    //    登陆
    public static String LoginUrl = NewsUrl + "/user/login";
    //    新闻类型
    public static String NewsCountType = NewsUrl + "/News/getlistbypid";
    public static String HomeNewType = NewsUrl + "/News/getlistbypid";
    //    新闻列表
    public static String HomeNewList = baseUrl + "/NewsService.asmx/NewsListByClassify";
    public static String NewsList = NewsUrl + "/News/appnewslist";
    //    新闻详情
    public static String NewsDetails = NewsUrl + "/appweb/newscontent.aspx?id=";
    //    重置密码
    public static String ResetPwd = baseUrl + "/UserService.asmx/ResetPwd";
    //    警报提醒
    public static String Alert = baseUrl + "/VehicleRescueService.asmx/DistressAlarmList";
    //    个人信息获取
    public static String MineInfo = baseUrl + "/PersonalCenterService.asmx/PersonalUserInfo";
    //    账户-消费记录列表
    public static String ExpenseCalendar = baseUrl + "/AccountService.asmx/AccountConsumptionList";
    //    充值提现记录列表
    public static String ChargeCalendar = baseUrl + "/AccountService.asmx/AccountPayList";
    //    语音记事本获取语音列表及保存语音
    public static String AudioRecordList = H5Url + "/H5Service/VoiceAccounting_Handler.ashx";
    //    生活服务H5接口
    public static String ServiceH5 = H5Url + "/web/serve.aspx";
    //    社区医疗H5接口
    public static String HospitalH5 = H5Url + "/web/hospital.aspx";
    //家庭相册接口
    public static String FamilyAlbum = baseUrl + "/FamilyService.asmx/GroupAlbumDynamicList";
    //家庭相册发布接口
    public static String FamilyAlbumPublish = baseUrl + "/FamilyService.asmx/FamilyAlbumPublish";
    //版本更新接口
    public static String VersionUpdate = H5Url + "/H5Service/AppVersion_Handler.ashx";
    //救援大队列表
    public static String ResuceTroopList = baseUrl + "VehicleRescueService.asmx/VehicleRescueTeamList";
    //机构服务申请加入
    public static String LifeServiceOrganizationApplyJoin = baseUrl + "LifeService.asmx/LifeServiceOrganizationApplyJoin";
    //个人服务申请加入
    public static String LifeServicePersonalApplyJoin = baseUrl + "LifeService.asmx/LifeServicePersonalApplyJoin";
    //服务项目列表
    public static String LifeServiceSP = baseUrl + "LifeService.asmx/LifeServiceSP";
    //籍贯
    public static String PCCA = baseUrl + "CommonService.asmx/PCCA";
    //生活服务申请状态查询
    public static String LifeServiceApplyStatusQuery = baseUrl + "LifeService.asmx/LifeServiceApplyStatusQuery";
    //生活服务申请列表
    public static String LifeServiceSPList = baseUrl + "LifeService.asmx/LifeServiceSPList";
    //生活服务项目详情
    public static String LifeServiceSPDetails = baseUrl + "LifeService.asmx/LifeServiceSPDetails";
    //生活服务项目删除
    public static String LifeServiceSPDel = baseUrl + "LifeService.asmx/LifeServiceSPDel";
    //生活服务项目上下架状态
    public static String LifeServiceSPUseStatusSet = baseUrl + "LifeService.asmx/LifeServiceSPUseStatusSet";
    //生活服务订单预约H5
    public static String LifeServiceOrderApply = H5Url + "web/orderForm/bespeakForm.aspx";
    //生活服务订单列表H5
    public static String LifeServiceOrderList = H5Url + "web/orderForm/orderForm.aspx";
    //我的订单列表H5
    public static String MineLifeServiceOrderList = H5Url + "/web/orderForm/bespeakForm.aspx";
    //二手市场发布宝贝儿
    public static String ReleaseSecondGoods = baseUrl + "SecondGoodsService.asmx/ReleaseSecondGoods";
    //首页功能按钮消息状态
    public static String HomePageButtonNewInfoStatistics = baseUrl + "HomePageService.asmx/HomePageButtonNewInfoStatistics";
    //我的删除
    public static String MyDoDel = baseUrl + "PersonalCenterService.asmx/MyDoDel";
    //视频流上传
    public static String VideoStreamUpLoad = baseUrl + "Upload.asmx/VideoStreamUpLoad";
    //家庭相册导入个人相册
    public static String FamilyAlbumImportPDynamicList = baseUrl + "FamilyService.asmx/FamilyAlbumImportPDynamicList";
    //家庭相册导入个人相册
    public static String PersonalAlbumGetNoImportDynamicList = baseUrl + "FamilyService.asmx/PersonalAlbumGetNoImportDynamicList";
    //个人中心我参与的 int type 0活动，1论坛，2，圈子
    public static String PersonalCenterIParticipation = baseUrl + "PersonalCenterService.asmx/PersonalCenterIParticipation";
    //关注我的
    public static String WatchMylist = baseUrl + "PersonalCenterService.asmx/WatchMylist";
    //我关注的动态
    public static String MyFavoritesList = baseUrl + "PersonalCenterService.asmx/MyFavoritesList";
    //添加到我的关注
    public static String MyWatchlistAdd = baseUrl + "/PersonalCenterService.asmx/MyWatchlistAdd";
    //签到
    public static String MySign = H5Url + "/web/signIn/sign.aspx";

    public static String MyWatchlistCancel = baseUrl + "/PersonalCenterService.asmx/MyWatchlistCancel";

    public static String InsurInfo = baseUrl + "/VehicleRescueService.asmx/MyVehicleInsuranceList";

    public static String InsurInfo2 = baseUrl + "/VehicleRescueService.asmx/VehicleInsuranceAdd";

    public static String bangzhufragment = baseUrl + "/VehicleRescueService.asmx/VehicleRescueMyHelpList";

    public static String qiuzhufragment = baseUrl + "/VehicleRescueService.asmx/MyVehicleRescueList";

    public static String carInfoFragment = baseUrl + "/VehicleRescueService.asmx/MyVehicleList";

    public static String AddcCarInfo = baseUrl + "/VehicleRescueService.asmx/VehicleInfoAdd";

    public static String ResuceDetail = baseUrl + "/VehicleRescueService.asmx/MyVehicleRescueDetails";

    public static String ResuceDetailState = baseUrl + "/VehicleRescueService.asmx/SetVehicleRescueState";

    public static String MyVehicleRescueDetails = baseUrl + "/VehicleRescueService.asmx/MyVehicleRescueDetails";

    public static String SetHelpRescueStatus = baseUrl + "/VehicleRescueService.asmx/SetHelpRescueStatus";

    public static String FriendAddCheck = baseUrl + "/ContactService.asmx/FriendAddCheck";

    public static String UserContactList = baseUrl + "/ContactService.asmx/UserContactList";

    public static String UserGPSInfoAdd = baseUrl + "/ContactService.asmx/UserGPSInfoAdd";

    public static String EventRegistrationList = baseUrl + "/activityService.asmx/EventRegistrationList";

    public static String MySeekHelpList = baseUrl + "/LifeSeekHelpService.asmx/MySeekHelpList";

    public static String PersonalSignIn = baseUrl + "/PersonalCenterService.asmx/PersonalSignIn";

    public static String PersonalCenterMyActivity = baseUrl + "/PersonalCenterService.asmx/PersonalCenterMyActivity";

    public static String PubliActivity = baseUrl + "/ActivityService.asmx/PubliActivity";
    public static String HelpDetailsUrl = baseUrl + "VehicleRescueService.asmx/MyHelpVehicleRescueDetails";
    public static String CarListDetails = baseUrl + "/VehicleRescueService.asmx/MyVehicleList";
    public static String CarHelpDetailsUrl = baseUrl + "/VehicleRescueService.asmx/MyHelpVehicleRescueDetails";
    public static String CarMResuceDetailsHelpUrl = baseUrl + "/VehicleRescueService.asmx/MyVehicleRescueDetails";
    public static String CarSelectIsHelpUrlUrl = baseUrl + "/VehicleRescueService.asmx/SetVehicleRescueState";
    public static String CarWEBUrlSeekHelpMe = baseUrl + "/VehicleRescueService.asmx/MyVehicleRescueMoneyAllotList";
    public static String CarWEBUrlPayMoney = baseUrl + "/VehicleRescueService.asmx/MyVehicleRescuePayReward";
    public static String AllForumUrl = baseUrl + "/PersonalCenterService.asmx/PersonalCenterMyPost";
    public static String LifeHelpOther = baseUrl + "/LifeSeekHelpService.asmx/MyHelpList";
    public static String LifeHelpMe = baseUrl + "/LifeSeekHelpService.asmx/MySeekHelpList";
    public static String LifeHelpList = baseUrl + "/LifeSeekHelpService.asmx/MyHelpList";
    public static String LifeSeekHelpDetails = baseUrl + "/LifeSeekHelpService.asmx/LifeSeekHelpDetails";
    public static String LifeSeekMyHelpDetails = baseUrl + "/LifeSeekHelpService.asmx/LifeSeekMyHelpDetails";
    public static String LifeSeekHelpCommentList = baseUrl + "/LifeSeekHelpService.asmx/LifeSeekHelpCommentList";
    public static String LifeSeekHelpComment = baseUrl + "/LifeSeekHelpService.asmx/LifeSeekHelpComment";
    public static String MySeekHelpMoneyAllotList = baseUrl + "/LifeSeekHelpService.asmx/MySeekHelpMoneyAllotList";
    public static String MySeekHelpPayReward = baseUrl + "/LifeSeekHelpService.asmx/MySeekHelpPayReward";
    public static String AllFamilyUrl = baseUrl + "/PersonalCenterService.asmx/PersonalCenterFamilyMember";
    public static String AllCircleUrl = baseUrl + "/PersonalCenterService.asmx/PersonalCenterMyCircle";
    public static String ActivityDynamicList = baseUrl + "/ActivityService.asmx/ActivityDynamicList";
    public static String PublishActivityDynamic = baseUrl + "/ActivityService.asmx/PublishActivityDynamic";
    public static String SendMessage = baseUrl + "/SMSService.asmx/SendSMS";
    public static String MyVehicleRescueList = baseUrl + "/VehicleRescueService.asmx/MyVehicleRescueList";
    public static String SetSeekHelpStatus = baseUrl + "/LifeSeekHelpService.asmx/SetSeekHelpStatus";
    public static String SetSeekStatus = baseUrl + "/LifeSeekHelpService.asmx/SetSeekStatus";
    public static String GetPersonMessage = NewsUrl + "/user/get";
    public static String CommunityNoticeList = baseUrl + "/CommunityNoticeService.asmx/CommunityNoticeList";
    public static String CommunityNoticeType = baseUrl + "/CommunityNoticeService.asmx/CommunityNotice2ClassifyList";
    public static String CommunityNoticeH5 = H5Url + "CommunityNotice/CommunityNoticeContent.aspx?id=";

    public static String AvatarUpLoad = baseUrl + "/Upload.asmx/AvatarUpLoad";

    public static String GetCommunityList = baseUrl + "/HomePageService.asmx/HomePageGetChangeCommunityList";
    public static String ChangeNick = NewsUrl + "/user/updatepart";
    public static String ChangePWD = baseUrl + "/PersonalCenterService.asmx/UpdatePwd";
    //我的关注
    public static String MyWatchlist = baseUrl + "/PersonalCenterService.asmx/MyWatchlist";
    //手机导入检测
    public static String TelImport = baseUrl + "/ContactService.asmx/ContactPhoneImportCheck";
    //注册接口
    public static String Register = baseUrl + "/UserService.asmx/registerV2";
    public static String denglu = baseUrl + "/LoginService.asmx/UserLogin";

}
