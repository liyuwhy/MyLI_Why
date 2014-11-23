/**
 * 
 */
package com.jju.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author С��
 *
 */

public class HanZiToPinYin {

    /**

     * ����һ���ֵ�ƴ��

     * @param hanzi

     * @return

     */

    public static String toPinYin(char hanzi){

        HanyuPinyinOutputFormat hanyuPinyin = new HanyuPinyinOutputFormat();

        hanyuPinyin.setCaseType(HanyuPinyinCaseType.LOWERCASE);

        hanyuPinyin.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);

        hanyuPinyin.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

        String[] pinyinArray=null;

        try {

            //�Ƿ��ں��ַ�Χ��

            if(hanzi>=0x4e00 && hanzi<=0x9fa5){

                pinyinArray = PinyinHelper.toHanyuPinyinStringArray(hanzi, hanyuPinyin);

            }

        } catch (BadHanyuPinyinOutputFormatCombination e) {

            e.printStackTrace();

        }

        //����ȡ����ƴ������

        return pinyinArray[0];

    }

}