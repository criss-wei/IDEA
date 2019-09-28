package com.wei.idea;

/**
 * @author wei
 * @date 2019/7/6-13:46
 */
public class Wei {
    public   String name;
    public int age;

    public static void main(String[] args) {
        int a=8;

        System.out.println(a>>>3);
        System.out.println(("8".hashCode()&0x7FFFFFFF)%10);
        System.out.println(("9".hashCode()&0x7FFFFFFF)%10);
        System.out.println(("7".hashCode()&0x7FFFFFFF)%10);
        String wei="wei";

    }
}
 class  Wei1 extends  Wei{
    private String message;
    public  String wei(){
        this.name="2   ";
        return this.name;


    }
}
