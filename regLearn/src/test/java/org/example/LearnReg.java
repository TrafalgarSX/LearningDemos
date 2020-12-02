package org.example;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname LearnReg
 * @Description TODO
 * @Date 2020/12/2 23:03
 * @Created by guo
 * @ProjectName LearnDemos
 */
public class LearnReg {
    @Test
    public void testReg(){
        String line = "This order was placed for guoyawen! nice";
        String pattern = "(guoyawen)";
        //Create a Pattern object
        Pattern pattern1 = Pattern.compile(pattern);
        //Create a Matcher object
        Matcher matcher = pattern1.matcher(line);
        if (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.start());//26
            System.out.println(matcher.end());//34
        }else{
            System.out.println("no match");
        }
    }

    @Test
    public void testAppend() {
        String REGEX = "a*b";
        String INPUT = "aabfooaabfooabfoobkkk";
        String REPLACE = "-";
        Pattern p = Pattern.compile(REGEX);
        // 获取 matcher 对象
        Matcher m = p.matcher(INPUT);
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            m.appendReplacement(sb,REPLACE);
        }
        System.out.println(sb.toString());
        m.appendTail(sb);
        System.out.println(sb.toString());
    }

    @Test
    public void test() {
        // 生成 Pattern 对象并且编译一个简单的正则表达式"cat"
        Pattern p = Pattern.compile("cat");
        // 用 Pattern 类的 matcher() 方法生成一个 Matcher 对象
        Matcher m = p.matcher("one cat two cats in the yard");
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            //此时sb为fatdogfatdog，cat被替换为dog,并且将最后匹配到之前的子串都添加到sb对象中
            m.appendReplacement(sb,"dog");
        }
        System.out.println(sb.toString());
        //此时sb为fatdogfatdogfat，将最后匹配到后面的子串添加到sb对象中
        m.appendTail(sb);
        //输出内容为fatdogfatdogfat
        System.out.println("sb:"+sb);
    }
}
