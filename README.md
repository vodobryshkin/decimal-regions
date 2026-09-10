# Decimal Regions

[![Tests](https://github.com/vodobryshkin/decimal-regions/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/vodobryshkin/decimal-regions/actions/workflows/test.yml)
[![Checkstyle](https://github.com/vodobryshkin/decimal-regions/actions/workflows/lint.yml/badge.svg?branch=master)](https://github.com/vodobryshkin/decimal-regions/actions/workflows/lint.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.vodobryshkin/decimal-regions)](https://central.sonatype.com/artifact/io.github.vodobryshkin/decimal-regions)
[![License](https://img.shields.io/github/license/vodobryshkin/decimal-regions)](LICENSE)

Java-библиотека для описания двумерных геометрических областей и проверки попадания точки внутрь них.

Координаты и параметры областей представлены через `BigDecimal`, а сами области могут задаваться математическими выражениями с помощью небольшого DSL, построенного на ANTLR4.

## Минимальные требования

- Java 11+

## Подключение

Библиотека опубликована в Maven Central.

### Maven

```xml
<dependency>
    <groupId>io.github.vodobryshkin</groupId>
    <artifactId>decimal-regions</artifactId>
    <version>4.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.vodobryshkin:decimal-regions:4.0.0'
```

## Для чего это?

Изначально этот проект появился во время моей [первой лабораторной работы по веб-программированию](https://github.com/vodobryshkin/web-lab1) в ИТМО.

В задании нужно было проверять попадание точки в заданную геометрическую область. Я хотел вынести эту логику из конкретной лабораторной работы в отдельную библиотеку, чтобы затем переиспользовать её в следующих работах.

Для координат я решил использовать `BigDecimal`, чтобы не зависеть от ошибок представления десятичных значений, характерных для `double`, и иметь более предсказуемое поведение при вычислениях на бэкенде.

Изначально для разных фигур существовали отдельные Java-классы: прямоугольник, треугольник и сектор. Позже я пришёл к более универсальному варианту — описанию области обычным математическим выражением.

Так появился `FormulaArea` и небольшой математический DSL.

Начиная с версии `3.0.0` специализированные классы областей считаются устаревшим API. В новом коде рекомендуется использовать формулы.

## Возможности

- Представление точек на плоскости через `BigDecimal`.
- Проверка попадания точки в область.
- Описание областей математическими выражениями.
- Переменные `x`, `y` и `r`.
- Арифметические операции.
- Сравнения.
- Логические операции `&&`, `||` и `xor`.
- Математические функции.
- Загрузка нескольких областей из JSON.
- Автоматическое создание областей из конфигурации.
- Изменение конфигурации во время работы приложения.
- Парсер выражений на ANTLR4.
- Работа без привязки к Spring или другому фреймворку.

## Быстрый старт

Допустим, в `src/main/resources/areas.json` лежит:

```json
{
  "areas": [
    {
      "type": "formula",
      "value": "x^2 + y^2 <= r^2"
    }
  ]
}
```

Загрузить конфигурацию и проверить точку можно через `CheckoutManager`:

```java
import io.github.vodobryshkin.decimalregions.checker.CheckoutManager;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;
import io.github.vodobryshkin.decimalregions.request.implementations.messages.CheckoutRequest;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Objects;

public class Example {

    public static void main(String[] args) throws Exception {
        try (InputStream config = Objects.requireNonNull(
                Example.class.getClassLoader().getResourceAsStream("areas.json"),
                "areas.json not found"
        )) {
            CheckoutManager manager = new CheckoutManager(config);

            CheckoutRequest request = new CheckoutRequest(
                    new Point(
                            new BigDecimal("3"),
                            new BigDecimal("4")
                    ),
                    new BigDecimal("5")
            );

            boolean inside = manager.checkRequest(request);

            System.out.println(inside); // true
        }
    }
}
```

В данном случае формула:

```text
x^2 + y^2 <= r^2
```

задаёт окружность с центром в `(0, 0)` и радиусом `r`.

Точка `(3, 4)` при `r = 5` лежит внутри этой области.

## Работа напрямую с FormulaArea

JSON и `CheckoutManager` использовать необязательно.

Область можно создать напрямую:

```java
import io.github.vodobryshkin.decimalregions.geometry.areas.implementations.FormulaArea;
import io.github.vodobryshkin.decimalregions.geometry.model.Point;

import java.math.BigDecimal;

FormulaArea circle = new FormulaArea(
        "x^2 + y^2 <= r^2",
        new BigDecimal("5")
);

boolean inside = circle.checkPoint(
        new Point(
                new BigDecimal("3"),
                new BigDecimal("4")
        )
);

System.out.println(inside); // true
```

У `Point` также есть конструктор для обычных Java-типов, наследующих `Number`:

```java
Point point = new Point(3, 4);
```

## Несколько областей

Конфигурация может содержать несколько областей:

```json
{
  "areas": [
    {
      "type": "formula",
      "value": "x^2 + y^2 <= r^2"
    },
    {
      "type": "formula",
      "value": "x >= 0 && y >= 0 && x + y <= r"
    },
    {
      "type": "formula",
      "value": "abs(x) + abs(y) <= r"
    }
  ]
}
```

`CheckoutManager` последовательно проверяет все области.

Если точка попала хотя бы в одну из них, метод:

```java
manager.checkRequest(request);
```

вернёт:

```java
true
```

То есть список областей в конфигурации фактически работает как объединение областей.

## Примеры формул

### Круг

```text
x^2 + y^2 <= r^2
```

### Правая половина круга

```text
x^2 + y^2 <= r^2 && x >= 0
```

### Первая четверть круга

```text
x >= 0 && y >= 0 && x^2 + y^2 <= r^2
```

### Кольцо

```text
x^2 + y^2 >= (r / 2)^2 && x^2 + y^2 <= r^2
```

### Квадрат с центром в начале координат

```text
abs(x) <= r && abs(y) <= r
```

### Ромб

```text
abs(x) + abs(y) <= r
```

### Прямоугольная область

```text
x >= -r && x <= r && y >= -r / 2 && y <= r / 2
```

### Прямоугольный треугольник

```text
x >= 0 && y >= 0 && x + y <= r
```

### Область между двумя горизонтальными прямыми

```text
y >= -r && y <= r
```

### Область справа от параболы

```text
x >= y^2
```

### Область с использованием `sqrt`

```text
sqrt(x^2 + y^2) <= r && x >= 0
```

### Область между синусом и косинусом

```text
y >= sin(x) && y <= cos(x)
```

## Переменные DSL

В формуле доступны три переменные:

| Переменная | Значение |
|---|---|
| `x` | координата точки по оси X |
| `y` | координата точки по оси Y |
| `r` | параметр области, переданный во время проверки |

Например:

```java
CheckoutRequest request = new CheckoutRequest(
        new Point(1, 2),
        new BigDecimal("5")
);
```

Для формулы:

```text
x^2 + y^2 <= r^2
```

значения будут:

```text
x = 1
y = 2
r = 5
```

## Операторы

### Сравнение

Поддерживаются:

```text
>
>=
<
<=
=
!=
```

Для проверки равенства используется:

```text
=
```

а не:

```text
==
```

Например:

```text
x = 0
```

### Арифметические операции

```text
+
-
*
/
^
```

Примеры:

```text
x + y <= r
```

```text
x^2 + y^2 <= r^2
```

```text
x / 2 >= y
```

### Логические операции

```text
&&
||
xor
```

Пример с `&&`:

```text
x >= 0 && y >= 0
```

Пример с `||`:

```text
x >= r || x <= -r
```

Пример с `xor`:

```text
x >= 0 xor y >= 0
```

## Математические функции

Поддерживаются:

| Функция | Описание |
|---|---|
| `sqrt(x)` | квадратный корень |
| `sin(x)` | синус |
| `cos(x)` | косинус |
| `tan(x)` | тангенс |
| `ln(x)` | натуральный логарифм |
| `log(x)` | десятичный логарифм |
| `exp(x)` | экспонента |
| `abs(x)` | абсолютное значение |

Примеры:

```text
sqrt(x^2 + y^2) <= r
```

```text
abs(x) <= r
```

```text
y <= sin(x)
```

```text
ln(x) >= 0
```

Вычисления выражений выполняются через `BigDecimal` с `MathContext` точностью 50 цифр.

## Особенности DSL

Логические операции вычисляются слева направо.

Например:

```text
a || b && c
```

в текущей грамматике не имеет стандартного приоритета `&&` над `||`.

Поэтому сложные выражения лучше записывать как несколько заранее понятных ограничений и не полагаться на привычный приоритет логических операторов.

То же относится к последовательному возведению в степень:

```text
2^3^2
```

вычисляется слева направо.

На практике для описания геометрических областей обычно достаточно простых ограничений вида:

```text
x >= 0 && y >= 0 && x^2 + y^2 <= r^2
```

## JSON-конфигурация

Базовый формат:

```json
{
  "areas": [
    {
      "type": "formula",
      "value": "<формула>"
    }
  ]
}
```

Например:

```json
{
  "areas": [
    {
      "type": "formula",
      "value": "x^2 + y^2 <= r^2"
    },
    {
      "type": "formula",
      "value": "x >= 0 && y >= 0 && x + y <= r"
    }
  ]
}
```

Загрузить файл можно двумя способами.

### Через InputStream

```java
InputStream inputStream =
        getClass().getClassLoader().getResourceAsStream("areas.json");

CheckoutManager manager = new CheckoutManager(inputStream);
```

### Через путь к файлу

```java
CheckoutManager manager =
        new CheckoutManager("/path/to/areas.json");
```

Для приложений, где конфигурация находится в classpath, предпочтительнее использовать `InputStream`: такой код не зависит от абсолютного пути на конкретном компьютере.

## Обновление конфигурации во время работы

Конфигурацию `CheckoutManager` можно заменить без создания нового объекта:

```java
try (InputStream newConfig = Objects.requireNonNull(
        Example.class.getClassLoader().getResourceAsStream("areas-v2.json")
)) {
    manager.updateAreasData(newConfig);
}
```

Следующие вызовы:

```java
manager.checkRequest(request);
```

будут использовать уже новую конфигурацию.

## Как это работает

Основная цепочка выглядит так:

```text
JSON
  ↓
JsonAreasConfigParser
  ↓
AreasRequest
  ↓
AreaFactory
  ↓
Area
  ↓
CheckoutManager
  ↓
checkPoint(...)
```

Для областей типа `formula`:

```text
Formula
  ↓
ANTLR4 Lexer
  ↓
ANTLR4 Parser
  ↓
ParseTree
  ↓
EvalExprVisitor / EvalFormulaVisitor
  ↓
boolean
```

То есть математическое выражение сначала разбирается ANTLR-парсером, после чего дерево вычисляется с подстановкой текущих значений `x`, `y` и `r`.

## Формальная грамматика

Актуальная грамматика находится в:

[`src/main/antlr4/Constraints.g4`](src/main/antlr4/Constraints.g4)

В упрощённом виде:

```bnf
<formula> ::= <constraint> (<boolean_symbol> <constraint>)*

<constraint> ::= <expr> <comparison> <expr>

<comparison> ::= ">"
               | ">="
               | "<"
               | "<="
               | "="
               | "!="

<boolean_symbol> ::= "&&"
                   | "||"
                   | "xor"

<expr> ::= <sum>

<sum> ::= <prod> (("+" | "-") <prod>)*

<prod> ::= <pow> (("*" | "/") <pow>)*

<pow> ::= <unary> ("^" <unary>)*

<unary> ::= ("+" | "-")? <atom>

<atom> ::= "x"
         | "y"
         | "r"
         | <number>
         | "(" <expr> ")"
         | <func> "(" <expr> ")"

<func> ::= "sqrt"
         | "sin"
         | "cos"
         | "tan"
         | "ln"
         | "log"
         | "exp"
         | "abs"

<number> ::= <int> | <float>

<int> ::= <digit>+

<float> ::= <digit>+ "." <digit>+
```

## Устаревшие области

До появления DSL библиотека содержала отдельные реализации геометрических фигур:

- `RectangleArea`
- `TriangleArea`
- `SectorArea`

Начиная с версии `3.0.0` эти классы помечены как `Deprecated`.

Они оставлены для обратной совместимости, но для нового кода рекомендуется использовать `FormulaArea`.

Например, вместо отдельного прямоугольника:

```text
abs(x) <= r && abs(y) <= r / 2
```

вместо треугольника:

```text
x >= 0 && y >= 0 && x + y <= r
```

вместо сектора или части круга:

```text
x^2 + y^2 <= r^2 && x >= 0 && y >= 0
```

Такой подход позволяет описывать новые области без добавления нового Java-класса для каждой фигуры.

## Сборка из исходников

```shell
git clone https://github.com/vodobryshkin/decimal-regions.git
cd decimal-regions
mvn clean verify
```

Во время сборки Maven:

- генерирует parser и visitor-классы из ANTLR-грамматики;
- компилирует проект под Java 11;
- запускает тесты;
- собирает основной JAR;
- собирает sources JAR;
- собирает Javadoc JAR.

## Стиль кода

Для проверки стиля используется [Checkstyle](https://checkstyle.org/) с правилами
[Google Java Style](https://google.github.io/styleguide/javaguide.html).

Проверить код локально:

```shell
mvn checkstyle:check
```

Полная проверка проекта:

```shell
mvn clean verify
```

Сборка завершается с ошибкой при наличии нарушений Checkstyle.
Generated-код ANTLR из проверки исключён.

## License

Проект распространяется под лицензией [Apache License 2.0](LICENSE).