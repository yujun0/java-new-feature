package com.practice.feature;

import com.practice.pk4.Line;
import com.practice.pk4.Rectangle;
import com.practice.pk4.Shape;
import org.junit.Test;

public class SwitchTest {

    @Test
    public void test1() {
        int week = 4;
        String memo;   // 表示計算結果
        switch (week) {
            case 1, 2, 3, 4, 5 -> memo = "工作日";
            case 6 -> memo = "星期六，休息";
            case 7 -> memo = "星期日，休息";
            default -> throw new RuntimeException("無效的日期");
        }
        System.out.println("memo: " + memo);
    }

    @Test
    public void test2() {
        int week = 5;
        String memo = switch (week) {
            case 1, 2, 3, 4, 5:
                yield "工作日";
            case 6:
                yield "星期六";
            case 7:
                yield "星期天";
            default:
                yield "無效的日期";
        };
        System.out.println("memo: " + memo);
    }

    @Test
    public void test3() {
        int week = 6;
        String memo = switch (week) {
            case 1, 2, 3, 4, 5 -> {
                System.out.println("工作日，執行了自定義代碼");
                yield "工作日";
            }
            case 6 -> {
                System.out.println("星期六，執行了自定義代碼");
                yield "星期六";
            }
            case 7 -> {
                System.out.println("星期日，執行了自定義代碼");
                yield "星期日";
            }
            default -> {
                System.out.println("執行了自定義代碼");
                yield "無效日期";
            }
        };
        System.out.println("memo: " + memo);
    }

    @Test
    public void test4() {
        Line line = new Line(10, 20);
        Rectangle rectangle = new Rectangle(20, 50);
        Shape shape = new Shape(50, 80);

        Object object = line;
        int result = switch (object) {
            case Line(int x, int y) -> {
                System.out.println("圖形是Line，x: " + x + ", y: " + y);
                yield x + y;
            }
            case Rectangle(int w, int h) -> 2 * (w + h);
            case Shape(int width, int height) -> {
                System.out.println("圖形是Shape，width: " + width + ", height: " + height);
                yield width * height;
            }
            default -> 0;
        };
        System.out.println("result: " + result);
    }

}
