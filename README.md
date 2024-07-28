# TestTaskCFT

## Утилита фильтрации содержимого файлов

### Содержание

1 [Общие сведения](#общие-сведения)  
2 [Сборка и запуск](#сборка-и-запуск)  
2.1 [Опции работы утилиты](#опции-работы-утилиты)  
2.2 [Инструменты разработки](#инструменты-использованные-для-разработки-утилиты)  

### Общие сведения
Данная утилита предназначена для фильтрации содержимого файлов. При запуске утилиты в командной строке подается несколько файлов, содержащих в
перемешку целые числа, строки и вещественные числа. В качестве разделителя
используется перевод строки. Строки из файлов читаются по очереди в соответствии с их
перечислением в командной строке. Утилита записывает разные типы данных в разные файлы.
По умолчанию содержимое результирующих файлов перезаписывается. Но с помощью аргументов командной строки можно задавать различные опции работы утилиты.

#### Опции работы утилиты
| Опция         | Функция                                                                                                                                                                                                                                                |
|---------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `-p` или `-P` | Задает префикс выходных файлов                                                                                                                                                                                                                         |
| `-o` или `-O` | Задает путь для результатов                                                                                                                                                                                                                            |
| `-a` или `-A` | Задает режим добавления в существующие файлы                                                                                                                                                                                                           |
| `-s` или `-S  | Задает краткий тип ведения статистики.Записываются только количество элементов, записанных в исходящие файлы.                                                                                                                                          |
| `-f` или `-F` | Задает полный тип статистики. Полная статистика для чисел дополнительно содержит минимальное и максимальное значения, сумма и среднее. Полная статистика для строк, помимо их количества, содержит также размер самой короткой строки и самой длинной. |

<strong> Статистику по каждому типу данных утилита выводит в консоль </strong>

#### Инструменты, использованные для разработки утилиты
| Инструмент    | Версия                                                  |
|---------------|---------------------------------------------------------|
| Gradle        | 8.0                                                     |
| Kotlin        | 1.8.10                                                  |
| Groovy        | 3.0.13                                                  |
| Ant:          | Apache Ant(TM) version 1.10.11 compiled on July 10 2021 |
| JVM:          | 17.0.10 (Oracle Corporation 17.0.10+11-LTS-240)         |
| OS            | Windows 11 10.0 amd64                                   |
| lombok        | 1.18.26                                                 |
| ------------- | ---------                                               |

#### Пример работы утилиты
Пример входного файла `test.txt`:  
hello wordl  
123.31  
1E6  
dfs;afkls  
asd  
123  

Пример входного файла `test2.txt`:  
74  
1e-6  
12.321  
123.1  
1.15  
0.88  
1e-20  
Lorem ipsum dolor sit amet  

Результат работы программы:
```shell
java -jar .\build\libs\content-filter.jar -s -p new_ -o ./test_res/ test.txt test2.txt
```
Вывод статистики:  
Short stats for float:  
count: 8  
Short stats for string:  
count: 4  
Short stats for integer:  
count: 2

Пример выходного файла `./test_res/new_floats.txt`:  
123.31  
1e-6  
1E6  
12.321  
123.1  
1.15  
0.88  
1e-20  

Пример выходного файла `./test_res/new_integers.txt`:  
74  
123  

Пример выходного файла `./test_res/new_strings.txt`:  
hello wordl  
dfs;afkls  
asd  
Lorem ipsum dolor sit amet

### Сборка и запуск
* Для сборки приложения в корне проекта выполнить `./gradlew clean build`

* Для запуска приложения выполняем `java -jar [filepath (default: .\build\libs\)] content-filter.jar [options] [input_files] `
### Архитектура и структура проекта
### Детали реализации методов классов
### Гайдлайн, как расширить функциональные возможности приложения
#### Как сделать утилиту многопоточной
#### Как добавить новый тип данных