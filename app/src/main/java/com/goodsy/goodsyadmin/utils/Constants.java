package com.goodsy.goodsyadmin.utils;

public class Constants {

    public static final float END_SCALE = 0.7f;
    public static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //constants
    public static final int REQUEST_CODE = 9001;
    public static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final int GPS_REQUEST_CODE = 903;
    public static final int RC_SIGN_IN = 1;
    public static final int PICK_IMAGES = 2;
    public static final int REQUEST_CALL = 9004;
    public static final int GALLERY_PICK = 990;
    public static final int TOTAL_ITEMS_TO_LOAD = 10;
    public static final String CHANNEL_1_ID = "Delivery alerts";
    public static final String CHANNEL_2_ID = "Offers and Promotions";
    public static final String CHANNEL_3_ID = "Social updates";
    //All image links
    public static final String defaultProfilePic = "https://firebasestorage.googleapis.com/v0/b/goodsy-da8cc.appspot.com/o/DefaultImages%2Fmale_avatar.png?alt=media&token=1b31ccb4-d3bf-48c3-91e9-b5495dd25150";
    public static final String linkSubu = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/About_us_image%2FSubu.jpg?alt=media&token=e3047138-22ff-457a-8391-8dff16a8ef60";
    public static final String linkMission = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/About_us_image%2Fmission.png?alt=media&token=16859a8d-cda2-4dfc-b0eb-494c949522ee";
    public static final String linkVision = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/About_us_image%2Fvision_new.png?alt=media&token=e661e531-c382-4ad4-9705-8f99fdfbdaeb";
    public static final String linkValues = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/About_us_image%2Fvalues.png?alt=media&token=3986417e-a159-4099-96ac-3e2208f417a7";
    public static final String linkTapanImage = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/About_us_image%2Ftapan.jpg?alt=media&token=3576fb31-c016-4977-b20c-896098af736d";
    public static final String linkChotuImage = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/About_us_image%2Fchotu.jpg?alt=media&token=cddcb64b-26a4-48ad-86cc-a646d130b4a8";
    public static final String linkGabbyImage = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/About_us_image%2Futkarsh.jpg?alt=media&token=39f5de46-dec5-4753-8968-382fe4b10294";
    public static final String linkUtkarshImage = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/About_us_image%2Fraghu.png?alt=media&token=f7849038-0da6-47ea-b902-490939b1867a";
    public static final String linkGradientMain = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/About_us_image%2Fgradient_main.jpg?alt=media&token=2d8d1ae8-43d7-4c02-b348-c0298aa92609";
    public static final String linkGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/About_us_image%2Fgradient.png?alt=media&token=8b41115b-532b-49b9-809b-f05235e6e54d";
    public static final String linkGradientSunrise = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/gradient_new%2FSunrise.jpg?alt=media&token=53ef45c2-8f41-4b8f-bba0-9e7787f6be22";
    public static final String googleTAG = "googleLOG";
    public static final String locationTag = "MapDragActivity";
    //location
    public static final String PACKAGE_NAME = "com.goodsy.users";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    public static final String CITY_DATA = "city";
    public static final int SUCCESS_RESULT = 1;
    public static final int FAILURE_RESULT = 0;
    public static final String CITY_STATUS = "Not found";
    public static final String notiURL = "https://fcm.googleapis.com/fcm/send";
    public static String mainUsersCollection = "Users";
    public static int mCurrentPage = 1;
    //Tags
    public static String otpTAG = "OtpActivity";

    //new_gradients

    public static final String linkAnamNisarGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FAnamnisar.jpg?alt=media&token=c67f20a1-4a04-486c-8517-b02d7cd01f3b";
    public static final String linkAzureLaneGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FAzur%20Lane.jpg?alt=media&token=ba6f4c1e-eb42-4a5a-a331-6fe518162768";
    public static final String linkInboxGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FInbox.jpg?alt=media&token=c2c98c85-4e66-4125-bd6c-ba988362dfdc";
    public static final String linkMaldivesGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FMaldives.jpg?alt=media&token=b62f0f42-5eb5-401a-846d-a8d281b0245b";
    public static final String linkSubuGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FSubu.jpg?alt=media&token=7dbf6f47-bf79-4c2a-bf4b-2a8ff4c0c247";
    public static final String linkTealLoveGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FTeal%20Love.jpg?alt=media&token=4657ec10-ebb9-440e-a2a5-44d10d800e26";
    public static final String linkVeniceGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FVenice%20(1).jpg?alt=media&token=c52b7503-b722-4833-b6b4-ffe38d84d308";
    public static final String linkFlareGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FFlare.jpg?alt=media&token=51f6cf3c-8245-4c54-a3a0-dec8522e7d64";
    public static final String linkMoonPurpleGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FMoon%20Purple.jpg?alt=media&token=b39570f4-42fa-41fb-8fb2-4d792e8ba5e3";
    public static final String linkNeuromancerGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FNeuromancer.jpg?alt=media&token=4e6ec934-1e48-4ca5-8099-833a76d9b881";
    public static final String linkReefGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FReef.jpg?alt=media&token=4820928b-3f1c-4121-994a-f5b1a402046c";
    public static final String linkViceGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FVice%20City.jpg?alt=media&token=f38fb144-85ea-4f32-b732-63bce9b2b7bd";
    public static final String linkSocialLiveGradient = "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/Gradient_Images%2FSocialive.jpg?alt=media&token=1ced6af2-085a-4147-8c7a-e7ef21dd09fe";
}