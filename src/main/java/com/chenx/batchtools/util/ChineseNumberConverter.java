package com.chenx.batchtools.util;

import java.util.Stack;

/**
 * <p>把阿拉伯数字转换成中文数字
 * <p>只支持到亿吧,后面单位我也不清楚  兆在大陆是百万,在台湾是万亿
 * <p>暂时只支持整数把,小数用不到
 *
 * 通过分析中文数字的读法可以发现如下规则:
 * 0.从左到右看作是从高位到低位
 * 1.中文数字以万分组,即十亿表示为10,0000,0000 对应 英文中用千分组,即十亿表示为 1,000,000,000
 * 按照英文的逻辑,问题就问分解为研究没4位数字的读法
 * 2.在个十百千四位中,只要两位中间有0,不管几个就需要读作'零',如1030:一千零三十,1003:一千零三,都是一个零.
 * 如果有比千大的单位,会出现1,0001:一万零一这样的情况
 * 3.如果最高位是十位,读作一十,如果不是最高位,读作十
 * 4.小数后面一个一个读,如:'1.000321'读作,一点零零零三二一
 *
 */
public class ChineseNumberConverter {

  public static void main(String[] args) {
    //three 0
    convert(1);
    convert(10);
    convert(100);
    convert(1000);

    //two 0
    convert(11);
    convert(101);
    convert(1001);
    convert(1010);
    convert(1100);

    //one 0
    convert(1110);
    convert(1101);
    convert(1011);
    convert(111);

    // zero 0
    convert(1111);


    //random test
    System.out.println(convert(10101010));
    System.out.println(convert(10000001000L));
    System.out.println(convert(10350501000L));
  }

  public static String convert(long number) {
    long tempNumber = number;
    //一万亿
    long maxNumber = 1000000000000L;

    if (tempNumber >= maxNumber) {
      throw new IllegalArgumentException("参数大于等于一万亿了");
    }

    //一万
    long group = 10000;
    char chineseNumber[] = new char[]{'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
    char term1[] = new char[]{'千', '百', '十', ' '};
    char term2[] = new char[]{' ', '万', '亿'};

    StringBuilder finalSb = new StringBuilder();
    int groupIndex = 0;
    while (tempNumber > 0) {
      long groupNumber = tempNumber % group;
      tempNumber /= group;

      if (groupNumber == 0) {
        if (groupIndex > 0){
          finalSb.insert(0,chineseNumber[0]);
        }
        groupIndex++;
        continue;
      }

      //parse groupNumber
      Stack<Long> stack = new Stack<>();
      for (int i = 0; i < 4; i++) {
        long singleNumber = 0;
        if (groupNumber != 0) {
          singleNumber = groupNumber % 10;
          groupNumber /= 10;
        }
        stack.push(singleNumber);
      }

      //process group number
      StringBuilder sb = new StringBuilder();
      boolean preZero = false;
      for (int i = 0; i < 4; i++) {
        int singleNumber = stack.pop().intValue();
        if (singleNumber == 0) {
          if (!preZero) {
            preZero = true;
          }
        } else {
          if (preZero) {
            sb.append(chineseNumber[0]);
            preZero = false;
          }
          sb.append(chineseNumber[singleNumber]);
          if (i < 3) {
            sb.append(term1[i]);
          }
        }
      }

      //special process
      if (tempNumber == 0 && sb.charAt(0) == chineseNumber[0]) {
        sb.deleteCharAt(0);
      }

      sb.append(term2[groupIndex]);
      finalSb.insert(0, sb);
      groupIndex++;
    }

    return finalSb.toString().substring(0, finalSb.toString().length()-1);
  }
}