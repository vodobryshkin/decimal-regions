# Библиотека для работы с логикой на основе BigDecimal.

---
## Что это?
Библиотека для работы с геометрией в контексте BigDecimal чисел.

## Минимальные требования
Java 11

## Для чего это?
Я написал весь этот код во время работы над первой ЛР по веб-программированию (https://github.com/vodobryshkin/web-lab1,
https://se.ifmo.ru/courses/web), изначально планируя его переиспользовать в других лабораторных для реализации логики.

Чем больше размерность чисел, тем больше точность вычислений. Мне не хватило double-точности, поэтому я решил использовать
класс BigDecimal.

Проблема возникла в том, что, в отличие от типа double, для работы с которым есть библиотека Abstract Window Toolkit, 
для BigDecimal такой библиотеки нет. Поэтому я решил написать свою.

## Возможности библиотеки
- Создание точки в 2D-пространстве.
- Создание геометрических областей в пространстве. На данный момент есть возможность создавать:
  - Области, описанные математическими выражениями.
  - Прямоугольные области. (Deprecated)
  - Треугольные области. (Deprecated)
  - Секторовидные области. (Deprecated)
- Проверка на вхождение точки внутрь области.
- Автоматическое создание областей из конфигурационного файла с помощью классов JsonAreasConfigParser AreaFactory.

Начиная с версии 3.0.0 прямоугольная, треугольная и секторовидная области считаются устаревшим API и не рекомендованы к использованию.
Каждую такую область можно заменить с помощью FormulaArea.

### Автоматическое создание областей.
Для автоматического создания областей нужны следующие компоненты:
1. Корректный конфигурационный файл в формате json.
2. Класс JsonAreasConfigParser.
3. Класс AreaFactory.

Для автоматического создания областей используется класс ```CheckoutManager```. Пример использования:
```java
String areasPath;
        try {
            areas4Path = Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("areas.json")).toURI()
            ).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CheckoutManager checkoutManager = new CheckoutManager(areas4Path);
        CheckoutRequest checkoutRequest = new CheckoutRequest(new Point(new BigDecimal("-1"), new BigDecimal("-1.05")),
                new BigDecimal("4"));

        assertFalse(checkoutManager.checkRequest(checkoutRequest));
```

#### Конфигурационный файл
Конфигурационный файл имеет следующую структуру:
```
{
  "areas": [
    {
      "type": "<тип области>",
      параметры...
    },
    другие поля...
  ]
}
```
Вот пример конкретного файла с пояснениями:

```json
{
  "areas": [
    {
      "type": "formula",
      "value": "x^2 + y^2 <= r^2 && sqrt(x^2 - 2*r) < 5"
    },
    {
      "type": "rectangle",
      "x": 0,
      "y": 0,
      "widthK": 0.5,
      "heightK": 1
    },
    {
      "type": "triangle",
      "xA": 0,
      "yA": 0,
      "xBK": 1,
      "yBK": 0,
      "xCK": 0,
      "yCK": -1
    },
    {
      "type": "circle",
      "xC": 0,
      "yC": 0,
      "radiusK": 0.5,
      "startAngleK": 1,
      "endAngleK": 1.5
    }
  ]
}
```

### Задание области с помощью формулы
Для того чтобы успешно задавать область с помощью математических функций, был разработан математический DSL. Для генерации парсеров используется инструмент ANTLR4.

Формальная грамматика языка:
```bnf
<formula> ::= <constraint> (<boolean_symbol> <constraint>)*
<constraint> ::= <expr> <par_symbol> <expr>
<par_symbol> ::= ">" | ">=" | "<" | "<=" | "=" | "!="
<boolean_symbol> ::= "&&" | "||" | "xor"
<expr>  ::= <sum>
<sum>   ::= <prod> (("+" | "-") <prod>)*
<prod>  ::= <pow>  (("*" | "/") <pow>)*
<pow>   ::= <unary> ("^" <unary>)*
<unary> ::= ("+" | "-")? <atom>
<atom> ::= "x"
         | "y"
         | "r"
         | <number>
         | "(" <expr> ")"
         | <func> "(" <expr> ")"
<func> ::= "sqrt" | "sin" | "cos" | "tan" | "ln" | "log" | "exp" | "abs"
<number> ::= <int> | <float>
<int>    ::= <digit>+
<float>  ::= <digit>+ "." <digit>+
```