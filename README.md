# JDK8~19 有用的新特性
JDK8~19 新增了不少新特性，例如：
-   Java Record
-   Switch 開關表達式
-   Text Block 文本塊
-   var 聲明局部變量
-   sealed 密封類
## Record
Java 14 中預覽的新特性叫做Recoed，在 Java 中，Record是一種特殊類型的 Java 類，可用來創建不可變class，語法簡短。參考 [JEP 395: Records (openjdk.org)](https://openjdk.org/jeps/395)，Jackson 2.12 支持Record類。

任何時間創建Java class，都會創建大量的樣板代碼，可能做的操作如下：

-   每個字段的set、get方法
-   public的構造方法
-   Override hashcode()、toString()、equals()

Java Record 避免了上述的樣板代碼，有以下特點：

-   帶有全部參數的構造方法
-   public 訪問器
-   hashcode()、toString()、equals()
-   無 set、get 方法。沒有遵循 Bean 的命名規範
-   final class，不能繼承 Record，Record為隱式的 final class，除此之外與普通class一樣
-   不可變class，透過構造創建 Record
-   final 屬性，不可修改
-   不能聲明實例屬性，能聲明 static 成員

### **如何使用**

```java=
public record Student(Integer id, String name, String email, Integer age) {
    // 實例方法，concat連接字符串
    public String concat() {
        return String.format("姓名是%s, 年齡是%d", this.name, this.age);
    }

    // 靜態方法，把email轉為大寫
    public static String emailToUpperCase(String email) {
        return Optional.ofNullable(email).orElse("no email").toUpperCase();
    }

}
```
```java=
// 創建Record object
Student lisa = new Student(1, "lisa", "lisa@gmail.com", 25);
System.out.println("lisa = " + lisa); // lisa = Student[id=1, name=lisa, [email=lisa@gmail.com](<mailto:email=lisa@gmail.com>), age=25]
// public方法器，獲取屬性值，readonly，沒有get、set方法
Integer id = lisa.id();
String name = lisa.name();
System.out.println("id = " + id);      // id = 1
System.out.println("name = " + name);  // name = lisa
Student susan = new Student(2, "Susan", "susan@gmail.com", 22);
System.out.println("equals: " + lisa.equals(susan));  // equals: false
```
```java=
Student lisa = new Student(1, "lisa", "lisa@gmail.com", 25);
String str = lisa.concat();
System.out.println("str = " + str);

// 使用類.靜態方法
String email = Student.emailToUpperCase("lisa@gmail.com");
System.out.println("email = " + email);
```

### Record 的構造方法
可以在 Record 中添加構造方法，有三種類型的構造方法分別是：緊湊的、規範的和訂製構造方法

-   緊湊型構造方法沒有任何參數，甚至沒有括號
-   規範構造方法是所有成員作為參數
-   訂製構造方法是自定義參數個數

```java=
// 緊湊
 public Student {
     System.out.println("id = " + id);
     if (id < 1) {
         throw new RuntimeException("id < 1");
     }
 }
 // 定製構造方法
 public Student(Integer id, String name) {
     this(id, name, null, null);
 }
```
```java=
@Test
public void test04() {
    Student student = new Student(2, "lisa");
    System.out.println("student = " + student);  // student = Student[id=2, name=lisa, email=null, age=null]
}
```

- 編譯後的將兩個構造方法都合併了

![](https://i.imgur.com/9Pg3fQM.png)

### Record 與 Lombok
Java Record 是創建不可變類且減少樣板代碼的好方法。Lombok 是一種減少樣板代碼的工具。兩者有表面上的重疊部分，可能有人會說 Java Record 會代替 Lombok，兩者是有不同用途的工具。

Lombok 提供語法的便利性，通常預裝一些代碼模板，根據加入到類中的註解自動執行代碼模板。這樣的庫純粹是為了方便實現POJO類，透過預編譯代碼，將代碼的模板加入到 class 中。

Java Record 是語言級別的，一種語意特性，為了建模而用，數據集合。簡單說就是提供了通用的數據類，充當**數據載體**，用於在類和應用程序之間進行數據傳輸。

### Record 實現介面
Java Record 可以與普通類一樣implement interface，override implement的方法。

-   自定義一個interface

```java
public interface PrintInterface {
    // 輸出自定義信息
    void print();
}
```
-   定義一個record實現類

```java
public record ProductRecord(Integer id, String name, Integer qty) implements PrintInterface {
    @Override
    public void print() {
        StringJoiner joiner = new StringJoiner("-");
        String s = joiner.add(id.toString()).add(name).add(qty.toString()).toString();
        System.out.println("商品信息 = " + s);
    }
}
```

-   測試呼叫
```java=
ProductRecord productRecord = new ProductRecord(1001, "手機", 200);
productRecord.print();
```

### Local Record
Record 可以做為局部object使用，在代碼塊中定義並使用Record，下面定義一個 SaleRecord

```java=
// 定義 Local Record
record SaleRecord(String saleId, String productName, Integer money) {
}
// 創建object
SaleRecord saleRecord = new SaleRecord("S001", "顯示器", 5000);

System.out.println("saleRecord = " + saleRecord);
```

### 嵌套 Record

多個 Record 可以組合定義，一個 Record 能夠包含其他的 Record。

例如：定義 Record 為 Customer，儲存客戶信息，包含了 Address 和 PhoneNumber 兩個 Record

-   Address record類

```java
public record Address(String city, String address, String zipCode) {}
```

-   PhoneNumber record類

```java
public record PhoneNumber(String areaCode, String number) {}
```

-   Customer嵌套 Record類

```java
public record Customer(String id, String name, PhoneNumber phoneNumber, Address address) {}
```

-   測試呼叫

```java=
Address address = new Address("台北", "忠孝東路四段547號", "110");
PhoneNumber phoneNumber = new PhoneNumber("110", "2233-4321");
Customer customer = new Customer("C1001", "Lisa", phoneNumber, address);
System.out.println("customer = " + customer);
String number = customer.phoneNumber().number();
System.out.println("number = " + number);
String adr = customer.address().address();
System.out.println("address = " + adr);

```

### instanceof 判斷 Record 類型

instanceof 能夠與 Java Record 一起使用。編譯器知道紀錄組件的確切數量和類型。

-   定義一個Person Record類

```java
public record Person(String name, Integer age) {}
```

-   定義一個service方法

```java
public class SomeService {
    // 定義業務方法，判斷年齡是否18
    public boolean isEligible(Object obj) {
        if (obj instanceof Person(String name, Integer age)) {
            return age >= 18;
        }
        return false;
    }
}
```

-   測試呼叫

```java
Person person = new Person("Lisa", 20);
SomeService someService = new SomeService();
boolean flag = someService.isEligible(person);
System.out.println("flag = " + flag);
```

### 總結

-   abstract 類 java.lang.Record 是所有Record的父類。
-   有對於 equals()、hashcode()、toString() 的定義說明
-   Record類能夠實現 java.io.Serializable 序列化或反序列化
-   Record 支持泛型，例如 record Gif<T>(T t) { }
-   `java.lang.Class`類與Record類有關的兩個方法：

```java
boolean isRecord()：判斷一個類是否是Record類型
```

```java
RecordComponent[] getRecordComponents()：Record的數組，表示此紀錄類的所有紀錄組件
```

```java=
Address address = new Address("台北", "忠孝東路四段547號", "110");
PhoneNumber phoneNumber = new PhoneNumber("110", "2233-4321");
Customer customer = new Customer("C1001", "Lisa", phoneNumber, address);
RecordComponent[] recordComponents = customer.getClass().getRecordComponents();
for (RecordComponent recordComponent : recordComponents) {
    System.out.println("recordComponent = " + recordComponent);
}
boolean record = customer.getClass().isRecord();
System.out.println("record = " + record);
```

## Switch

switch 的三個方面，參考：[JEP 361: Switch Expressions (openjdk.org)](https://openjdk.org/jeps/361)

-   支持箭頭表達示
-   支持 yield 返回值
-   支持 Jaca Record

### 箭頭表達式，新的 case 標籤

Switch 新的語法，case label → 表達式| throw 語句 | block

![](https://i.imgur.com/EidWW59.png)

例子：
```java
int week = 4;
String memo;   // 表示計算結果
switch (week) {
    case 1, 2, 3, 4, 5 -> memo = "工作日";
    case 6 -> memo = "星期六，休息";
    case 7 -> memo = "星期日，休息";
    default -> throw new RuntimeException("無效的日期");
}
System.out.println("memo: " + memo);
```

### yeild 返回值

yeild 讓 switch 作為表達式，能夠返回值

語法：

```java
變量 = swich (value) {
	case v1: yeild 結果值；
	case v2: yeild 結果值；
	case v3,v4,v5..: yeild 結果值；
}
```
例子：
```java
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

```

```java
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

```

> 注意： `case →`與 `case:` 不能混用。一個 switch 語句塊中使用一種語法格式。 switch 作為表達式，賦值給變量，需要 `yield` 或者 `case`→ 表達式。 → 右側表達式為 case 返回值

### switch 中使用 record

switch 表達式中使用 record，結合 case 標籤 → 表達式， yield 實現複雜的計算

例如：準備三個 Reocrd

```java
public record Line(int x, int y) {}
public record Rectangle(int w, int h) {}
public record Shape(int width, int height) {}
```

```java
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
```

## Text Block

Text Block 處理多行文本十分方便，省時省力，無須連接 “+” , 單引號，換行符號等。Java 15，參考[JEP 378: Text Blocks (openjdk.org)](https://openjdk.org/jeps/378)

語法：使用三個雙引號字符括起來的字符串

```java
"""
內容
"""
```

例如：
![](https://i.imgur.com/5VFYQAj.png)

Text Block 要求：

-   Text Block 以三個雙引號字符開始，後跟一個行結束符
-   不能將Text Block 放在單行上
-   Text Block 的內容也不能在沒有中間行結束符的情況下跟隨三個開頭雙引號

三個雙引號字符 `”””` 與兩個雙引號 `””` 的字符串處理是一樣的，與普通字符串一樣使用。例如 `equals()`、`==`、`+`，作為方法的參數等。

	
	
### 空白

-   JEP 378 中包含空格處理的詳細算法說明
-   Text Block 中的縮進會自動去除，左側和右側的
-   要保留左側的縮進、空格
    -   將Text Block 的內容向左移動(tab 鍵) 
    
    ![](https://i.imgur.com/XEv48I1.gif)
    -   使用indent()
    
    ```java
    String color = """
                    red
                    blue
                    green
                    """;
    color = color.indent(15);
    ```

**TextBlock方法**

Text Block 的格式方法 `formatted()`

例子：
```java
String info = """
                Name: %s,
                Phone: %s,
                Age: %d
                """.formatted("Lisa", "0911234221", 25);

```

![](https://i.imgur.com/wQ1hwtU.png)

-   String stripIndent()：刪除每行開頭和結尾的空白
-   String translateEscapes()：轉義序列轉換為字符串字面量

### 轉義字符

新的轉義字符`\\`， 表示隱式換行符，這個轉義字符被 Text Block 轉義為空格。通常用於拆分非常長的字符串文本，串聯多個較小字符串，包裝為多行生成字符串。

![](https://i.imgur.com/kyUSrms.png)

新的轉義字符，組合非常長的字符串。

### 總結

-   多行字符串，應該使用Text Block
-   當 Text Block 可以提高代碼的清晰度時，推薦使用。比如代碼中嵌入 SQL 語句
-   避免不必要的縮進，開頭和結尾部分
-   使用空格或僅使用制表符 Text Block 的縮進。混和空白將導致不規則的縮進
-   對於大多數多行字符串，分隔符位於上一行的右端，並將結束分隔符位於文本塊單獨行上。

## var
	
在 JDK10 及更高版本中，可以使用 `var` 聲明具有非空初始化的局部變量，這可以協助編寫出簡潔的代碼，消除冗餘信息使代碼更具可讀性

### var 聲明局部變量

-   var 是一個保留字，不是關鍵字 (可以聲明 var 為變量名)
-   方法內聲明的局部變量，必須有初始值
-   每次聲明一個變量，不可複合聲明多個變量
    -   如：`var s1 = “Hello”, age = 20;` ⇒ Error
-   var 動態類型是編譯器根據變量所賦的值來推斷類型
-   var 代替顯示類型，代碼簡潔，減少不必要的排版，混亂
-   代碼簡潔和整齊，但降低了程序的可讀性(無強類型聲明)

例子：

```java
// before
try (Stream<Customer> result = conn.executeQuery(sql)) { ... }
// recommand
try (var customers = conn.executeQuery(sql)) { ... }

```

### 使用時機

-   簡單的臨時變量
-   複雜，多步驟邏輯，嵌套的表達式等，簡短的變量有助於理解代碼
-   能夠確定變量初始值
-   變量類型比較長

例子：

```java
var s1 = "Lisa";
var age = 25;
for (var i = 0; i < 10; i++) {
    System.out.println("i = " + i);
}
List<String> strings = List.of("a", "b", "c");
for (var str : strings) {
    System.out.println("str = " + str);
}

```

## sealed

sealed 翻譯為密封，密封類(Sealed Classes)的首次提出是在 Java 15 的 [JEP 360](https://openjdk.org/jeps/360) 中，並在 Java 16 的

[JEP 397](https://openjdk.org/jeps/397) 再次 preview，而在 Java 17 的 [JEP 409](https://openjdk.org/jeps/409) 成為正式的功能

_**Sealed Classes 主要特點是限制繼承**_

Sealed Classes 主要特點是限制繼承，Java 中透過繼承增強，擴展了類的能力，復用某些功能。當這種能力不受控時，與原有類的設計相違背，導致不預見的異常邏輯。

_**Sealed Classes 限制無限的擴張**_

_**Java 中已有 sealed 的設計**_

-   final 關鍵字，修飾類不能被繼承
-   private 限制私有類

sealed 作為關鍵字，可在 class 和 interface 上使用，結合 permits 關鍵字。定義限制繼承的Sealed Classes

### Sealed Classes
語法：

```java
sealed class 類名 permits 子類 1, 子類 N 列表 {}
```
permits 表示允許的子類，一個或多個

### 聲明子類別

子類別，需要使用以下聲明，有三種：
![](https://i.imgur.com/QJbrbSz.png)
-   final 終結，依然是密封的
-   sealed 子類是密封類，需要子類實現
-   non-sealed 非密封類，擴展使用，不受限

例子：

```java
public sealed class Shape {
    private Integer width;
    private Integer height;

    public void draw() {
        System.out.println("畫一個圖形Shape");
    }
}
```

```java
public final class Circle extends Shape{}
public sealed class Square extends Shape permits RoundSquare {}
public final class RoundSquare extends Square {
    @Override
    public void draw() {
        System.out.println("=====RoundSquare=====");
    }
}
public non-sealed class Rectangle extends Shape {
    @Override
    public void draw() {
        System.out.println("=====Rectangle=====");
    }
}
public class Line extends Rectangle {
    @Override
    public void draw() {
        System.out.println("======Line======");
    }
}
```

### Sealed Interface

```java
public sealed interface SomeService permits SomeServiceImpl {
    void doThing();
}
```

```java
public final class SomeServiceImpl implements SomeService {
    @Override
    public void doThing() {
        System.out.println("doThing()....");
    }
}
```

```java
SomeService service = new SomeServiceImpl();
service.doThing();
```
