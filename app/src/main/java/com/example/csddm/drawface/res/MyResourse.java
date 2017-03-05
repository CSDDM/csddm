package com.example.csddm.drawface.res;

import com.example.csddm.R;

/**
 * Created by dell on 2017/2/24.
 */

public class MyResourse {

    //public static final String TAG_TYEP ="image_type";
    public static final String TAG_ISBOY ="isBoy";
    public static final String TAG_AGE = "age";

    //public static final int ID_HAIR =0;
    //public static final int ID_FACE =ID_HAIR+1;


    static int [] boyHair; //s1
    static int [] youngAgeGirlHair;//s2
    static int [] middleAgeGirlHair;
    static int [] oldAgeGirlHair;
    static int [] faceShape;//s5
    static int [] eyebrow;//s6
    static int [] eye;//s7
    static int [] youngAgeMouth;//s8
    static int [] middleAgeBoyMouth;//s9
    static int [] middleAgeGirlMouth;//s10
    static int [] oldAgeMouth;//s11
    static int [] youngBoyClothes;//s12
    static int [] middleBoyClothes;
    static int [] oldBoyClothes;
    static int [] youngGirlClothes;//s13
    static int [] middleGirlClothes;
    static int [] oldGirlClothes;
    static int [] background;//s14
    static int [] boy_default;
    static int [] girl_default;


    /*获取boy发型的图片地址数组
     * */
    public static int [] getBoyHair(){
        if(boyHair==null){
            boyHair = new int[]{
                    R.drawable.pic_s1_0,
                    R.drawable.pic_s1_1,
                    R.drawable.pic_s1_2,
                    R.drawable.pic_s1_3,
                    R.drawable.pic_s1_4,
                    R.drawable.pic_s1_5,
                    R.drawable.pic_s1_6,
                    R.drawable.pic_s1_7,
                    R.drawable.pic_s1_8,
                    R.drawable.pic_s1_9,
                    R.drawable.pic_s1_10,
                    R.drawable.pic_s1_11,
                    R.drawable.pic_s1_12,
                    R.drawable.pic_s1_13,
                    R.drawable.pic_s1_14,
                    R.drawable.pic_s1_15,
                    R.drawable.pic_s1_16
            };
        }
        return boyHair;
    }

    /*
     * 获取girl发型的图片地址数组
     */
    public static int [] getYoungAgeGirlHair(){
        if(youngAgeGirlHair==null){
            youngAgeGirlHair = new int[]{
                    R.drawable.pic_s2_0,
                    R.drawable.pic_s2_1,
                    R.drawable.pic_s2_2,
                    R.drawable.pic_s2_3
            };
        }
        return youngAgeGirlHair;
    }

    public static int [] getMiddleAgeGirlHair(){
        if(middleAgeGirlHair==null){
            middleAgeGirlHair = new int[]{
                    R.drawable.pic_s2_4,
                    R.drawable.pic_s2_5,
                    R.drawable.pic_s2_6,
                    R.drawable.pic_s2_7,
                    R.drawable.pic_s2_8,
                    R.drawable.pic_s2_9,
                    R.drawable.pic_s2_10,
                    R.drawable.pic_s2_11,
                    R.drawable.pic_s2_12,
                    R.drawable.pic_s2_13,
                    R.drawable.pic_s2_14,
                    R.drawable.pic_s2_15,
                    R.drawable.pic_s2_16,
                    R.drawable.pic_s2_17,
                    R.drawable.pic_s2_18
            };
        }
        return middleAgeGirlHair;
    }

    public static int [] getOldAgeGirlHair(){
        if(oldAgeGirlHair==null){
            oldAgeGirlHair = new int[]{
                    R.drawable.pic_s2_8,
                    R.drawable.pic_s2_9,
                    R.drawable.pic_s2_10,
                    R.drawable.pic_s2_11,
                    R.drawable.pic_s2_12,
                    R.drawable.pic_s2_13,
                    R.drawable.pic_s2_14,
                    R.drawable.pic_s2_15,
                    R.drawable.pic_s2_16,
                    R.drawable.pic_s2_17,
                    R.drawable.pic_s2_18
            };
        }
        return oldAgeGirlHair;
    }
    /*
     * 脸型图片数组
     */
    public static int [] getFaceShape(){
        if(faceShape == null){
            faceShape = new int[]{
                    R.drawable.pic_s5_0,
                    R.drawable.pic_s5_1,
                    R.drawable.pic_s5_2,
                    R.drawable.pic_s5_3,
                    R.drawable.pic_s5_4,
                    R.drawable.pic_s5_5,
                    R.drawable.pic_s5_6,
                    R.drawable.pic_s5_7,
                    R.drawable.pic_s5_8,
                    R.drawable.pic_s5_9,
                    R.drawable.pic_s5_10,
                    R.drawable.pic_s5_11,
                    R.drawable.pic_s5_12,
                    R.drawable.pic_s5_13,
                    R.drawable.pic_s5_14,
                    R.drawable.pic_s5_15,
                    R.drawable.pic_s5_16,
                    R.drawable.pic_s5_17,
                    R.drawable.pic_s5_18,
                    R.drawable.pic_s5_19
            };
        }
        return faceShape;
    }

    /*
     * 眉毛图片数组
     */
    public static int [] getEyeBrow(){
        if(eyebrow == null){
            eyebrow = new int[]{
                    R.drawable.pic_s6_0,
                    R.drawable.pic_s6_1,
                    R.drawable.pic_s6_2,
                    R.drawable.pic_s6_3,
                    R.drawable.pic_s6_4,
                    R.drawable.pic_s6_5,
                    R.drawable.pic_s6_6,
                    R.drawable.pic_s6_7,
                    R.drawable.pic_s6_8,
                    R.drawable.pic_s6_9,
                    R.drawable.pic_s6_10,
                    R.drawable.pic_s6_11,
                    R.drawable.pic_s6_12,
                    R.drawable.pic_s6_13,
                    R.drawable.pic_s6_14,
                    R.drawable.pic_s6_15,
                    R.drawable.pic_s6_16,
                    R.drawable.pic_s6_17,
                    R.drawable.pic_s6_18,
                    R.drawable.pic_s6_19,
            };
        }
        return eyebrow;
    }

    /*
     * 眼睛图片数组
     * */
    public static int [] getEye(){
        if(eye == null){
            eye = new int[]{
                    R.drawable.pic_s7_0,
                    R.drawable.pic_s7_1,
                    R.drawable.pic_s7_2,
                    R.drawable.pic_s7_3,
                    R.drawable.pic_s7_4,
                    R.drawable.pic_s7_5,
                    R.drawable.pic_s7_6,
                    R.drawable.pic_s7_7,
                    R.drawable.pic_s7_8,
                    R.drawable.pic_s7_9,
                    R.drawable.pic_s7_10,
                    R.drawable.pic_s7_11,
                    R.drawable.pic_s7_12,
                    R.drawable.pic_s7_13,
                    R.drawable.pic_s7_14,
                    R.drawable.pic_s7_15,
                    R.drawable.pic_s7_16,
                    R.drawable.pic_s7_17,
                    R.drawable.pic_s7_18,
                    R.drawable.pic_s7_19
            };
        }
        return eye;
    }

	/*
	 * 嘴巴图片数组
	 */

    public static int[] getYoungAgeMouth(){
        if(youngAgeMouth == null){
            youngAgeMouth = new int[]{
                    R.drawable.pic_s8_0,
                    R.drawable.pic_s8_1,
                    R.drawable.pic_s8_2,
                    R.drawable.pic_s8_3,
                    R.drawable.pic_s8_4,
                    R.drawable.pic_s8_5,
                    R.drawable.pic_s8_6,
                    R.drawable.pic_s8_7,
                    R.drawable.pic_s8_8,
                    R.drawable.pic_s8_9,
                    R.drawable.pic_s8_10,
                    R.drawable.pic_s8_11,
                    R.drawable.pic_s8_12,
                    R.drawable.pic_s8_13,
                    R.drawable.pic_s8_14,
                    R.drawable.pic_s8_15,
                    R.drawable.pic_s8_16,
                    R.drawable.pic_s8_17
            };
        }
        return youngAgeMouth;
    }

    public static int[] getMiddleAgeBoyMouth(){
        if(middleAgeBoyMouth == null){
            middleAgeBoyMouth = new int[]{
                    R.drawable.pic_s9_0,
                    R.drawable.pic_s9_1,
                    R.drawable.pic_s9_2,
                    R.drawable.pic_s9_3,
                    R.drawable.pic_s9_4,
                    R.drawable.pic_s9_5,
                    R.drawable.pic_s9_6,
                    R.drawable.pic_s9_7,
                    R.drawable.pic_s9_8,
                    R.drawable.pic_s9_9
            };
        }
        return middleAgeBoyMouth;
    }

    public static int[] getMiddleAgeGirlMouth(){
        if(middleAgeGirlMouth == null){
            middleAgeGirlMouth = new int[]{
                    R.drawable.pic_s10_0,
                    R.drawable.pic_s10_1,
                    R.drawable.pic_s10_2,
                    R.drawable.pic_s10_3,
                    R.drawable.pic_s10_4,
                    R.drawable.pic_s10_5,
                    R.drawable.pic_s10_6,
                    R.drawable.pic_s10_7,
                    R.drawable.pic_s10_8,
                    R.drawable.pic_s10_9
            };
        }
        return middleAgeGirlMouth;
    }
    public static int[] getOldAgeMouth(){
        if(oldAgeMouth == null){
            oldAgeMouth = new int[]{
                    R.drawable.pic_s11_0,
                    R.drawable.pic_s11_1,
                    R.drawable.pic_s11_2,
                    R.drawable.pic_s11_3,
                    R.drawable.pic_s11_4,
                    R.drawable.pic_s11_5,
                    R.drawable.pic_s11_6,
                    R.drawable.pic_s11_7,
                    R.drawable.pic_s11_8,
                    R.drawable.pic_s11_9,
                    R.drawable.pic_s11_10,
                    R.drawable.pic_s11_11,
                    R.drawable.pic_s11_12,
                    R.drawable.pic_s11_13,
                    R.drawable.pic_s11_14
            };
        }
        return oldAgeMouth;
    }
    /*
     * 获取boy衣服图片数组
     */
    public static int[] getYoungBoyClothes(){
        if(youngBoyClothes == null){
            youngBoyClothes = new int[]{
                    R.drawable.pic_s12_0,
                    R.drawable.pic_s12_1,
                    R.drawable.pic_s12_2,
                    R.drawable.pic_s12_3,
                    R.drawable.pic_s12_4,
                    R.drawable.pic_s12_5,
                    R.drawable.pic_s12_6
            };
        }
        return youngBoyClothes;
    }
    public static int[] getMiddleBoyClothes(){
        if(middleBoyClothes == null){
            middleBoyClothes = new int[]{
                    R.drawable.pic_s12_7,
                    R.drawable.pic_s12_8,
                    R.drawable.pic_s12_9,
                    R.drawable.pic_s12_10,
                    R.drawable.pic_s12_11,
                    R.drawable.pic_s12_12
            };
        }
        return middleBoyClothes;
    }
    public static int[] getOldBoyClothes(){
        if(oldBoyClothes == null){
            oldBoyClothes = new int[]{
                    R.drawable.pic_s12_13,
                    R.drawable.pic_s12_14,
                    R.drawable.pic_s12_15,
                    R.drawable.pic_s12_16
            };
        }
        return oldBoyClothes;
    }

    /**
     * 获取girl衣服图片数组
     */

    public static int[] getYoungGirlClothes(){
        if(youngGirlClothes == null){
            youngGirlClothes = new int[]{
                    R.drawable.pic_s13_0,
                    R.drawable.pic_s13_1,
                    R.drawable.pic_s13_2,
                    R.drawable.pic_s13_3,
                    R.drawable.pic_s13_4,
                    R.drawable.pic_s13_5
            };
        }
        return youngGirlClothes;
    }
    public static int[] getMiddleGirlClothes(){
        if(middleGirlClothes == null){
            middleGirlClothes = new int[]{
                    R.drawable.pic_s13_6,
                    R.drawable.pic_s13_7,
                    R.drawable.pic_s13_8,
                    R.drawable.pic_s13_9,
                    R.drawable.pic_s13_10
            };
        }
        return middleGirlClothes;
    }
    public static int[] getOldGirlClothes(){
        if(oldGirlClothes == null){
            oldGirlClothes = new int[]{
                    R.drawable.pic_s13_11,
                    R.drawable.pic_s13_12,
                    R.drawable.pic_s13_13
            };
        }
        return oldGirlClothes;
    }

    /**
     * 获取背景图片数组
     */
    public static int[] getBackGround(){
        if(background == null){
            background = new int[]{
                    R.drawable.pic_s14_0,
                    R.drawable.pic_s14_1,
                    R.drawable.pic_s14_2,
                    R.drawable.pic_s14_3,
                    R.drawable.pic_s14_4,
                    R.drawable.pic_s14_5
            };
        }
        return background;
    }

    /**
     * 获取默认boy图片数组
     */
    public static int [] getBoyDefault(){
        if(boy_default == null){
            boy_default = new int[]{
                    R.drawable.pic_s1_0, 	//默認髮型
                    R.drawable.pic_s5_0,//默認臉型
                    R.drawable.pic_s6_0,   //默認眉毛
                    R.drawable.pic_s7_0,	//默認眼睛
                    R.drawable.pic_s8_1,	//默認嘴巴
                    R.drawable.pic_s12_0,	//默認衣服
                    R.drawable.pic_s14_0,	//默認背景----藍色
            };
        }
        return boy_default;
    }
    /**
     * 获取默认girl图片数组
     */
    public static int[] getGirlDefault(){
        if(girl_default == null){
            girl_default = new int[]{
                    R.drawable.pic_s2_1, 	//默認髮型
                    R.drawable.pic_s5_0,//默認臉型
                    R.drawable.pic_s6_0,   //默認眉毛
                    R.drawable.pic_s7_0,	//默認眼睛
                    R.drawable.pic_s10_1,	//默認嘴巴
                    R.drawable.pic_s13_0,	//默認衣服
                    R.drawable.pic_s14_1,	//默認背景----红色
            };

        }
        return girl_default;
    }
}

