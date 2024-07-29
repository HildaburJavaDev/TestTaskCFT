# TestTaskCFT

## Утилита фильтрации содержимого файлов

### Содержание

1 [Общие сведения](#общие-сведения)  
2 [Сборка и запуск](#сборка-и-запуск)  
2.1 [Опции работы утилиты](#опции-работы-утилиты)  
2.2 [Инструменты разработки](#инструменты-использованные-для-разработки-утилиты)  
3 [Архитектура и структура проекта](#архитектура-и-структура-проекта)  
4 [Расширение функциональных возможностей приложения](#гайдлайн-как-расширить-функциональные-возможности-приложения)  
4.1 [Гайдлайн по приведению утилиты к многопоточности](#гайдлайн-по-приведению-утилиты-к-многопоточности-)  
4.2 [Гайдлайн по добавлению нового типа данных](#гайдлайн-по-добавлению-нового-типа-данных)

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

Данный проект имеет слудющую структуру пакетов:  
<strong> models </strong> - определены классы для ведения статистики.  
`Stats` - базовый класс, реализующий общую логику для всех типов данных.  
`StatsInterface` - интерфейс, в котором объявлены методы для обновления статистики и ее получения.  
`FloatStats, IntegerStats, StringStats` - классы, реализующие ведение статистики для каждого типа данных и наследующиеся от базового класса `Stats`.  
<strong> services </strong> - пакет сервисов, определяющих бизнес-логику всего приложения - парсинг аргументов, работу с директориями и работу с файлами.  
`ArgumentProvider` - класс, реализующий функционал парсинга аргументов командной строки утилиты.  
`DirectoryManager` - класс, реализующий функционал создания директории.  
`DirectoryProvider` - класс, реализующий функционал проверки существования директорий.  
`FileService` - класс, реализующий функционал чтения и записи файлов, а также управляет объектами статистики.  
<strong> storage </strong> - пакет, включающий в себя функционал по сохранению аргументов командной строки.  
`ArgumentStorage` - класс, определяющий функционал и поля класса для сохранения аргументов командной строки, таких как опции, пути к файлам и префиксы имен файлов.  
<strong> utils </strong> - пакет, включающий в себя классы-утилиты и перечисления.  
`DataType` - перечисление, включающее в себя названия типов данных и названия выходных файлов по умолчанию.  
`DataTypeDeterminer` - класс, реализующий функционал для определения типа данных считанной строки.  
`Notificator` - класс, задачей которого является предупреждения пользователей о возможных исключительных ситуациях.

### Гайдлайн, как расширить функциональные возможности приложения

#### Гайдлайн по приведению утилиты к многопоточности  
> <strong> Важно! </strong> При приведении данной утилиты к многопоточному программированию порядок чтения файлов и записи результатов в 
> соответствующие выходные файлы может отличаться от порядка передачи путей исходных файлов в качестве аргументов.  

Для этого необходимо в методе `start` класса `Main` инициализировать потоконебезопасную `ConcurencyHashMap`,
которую мы будем использовать в качестве структуры данных для сохранения объектов статистики. Сделать это можно следующим образом:
```java
ConcurrentHashMap<DataType, Stats> statsMap = new ConcurrentHashMap<>();
```
В качестве ключа для данной структуры используется уже определенное перечисление `DataType`,
а в качестве значения - базовый класс для ведения статистики `Stats`.
В дальнейшем будем использовать рефлексию для сохраненения экземпляров нужных классов.
В дальнейшем создадим объект `executorService` для создания пулла потоков.  
```java
ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
```
В качестве параметра количества потоков будем использовать доступное в текущий момент количество "логических" процессоров, так как основная задача - чтение и запись файлов.
Далее, в цикле `foreach` пройдемся по аргументам командной строки (переданным исходящим файлам) и запустим новую задачу, перед этим создав эту задачу в классе `FileService` , реализующий интерфейс `Runnable`.
```java
for (String fileName: argumentStorage.getFiles()) {
    executorService.execute(new FileService(argumentStorage, fileName, statsMap));
}
```
В классе `FileService` обернем блоки статистики и метод, отвечающий за запись данных в файл в `syncronized` для предотвращения `race condition` потоков.  
Перед вызовом метода печати статистики добавим следующий код, обеспечивающий гарантию завершения всех потоков (для того, чтобы быть уверенными, что прочитаны все файлы).
```java
executorService.shutdown();
try {
    while (!executorService.isTerminated()) {
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }
} catch (InterruptedException e) {
    executorService.shutdownNow();
}
```
Как итог, обновленный класс `Main` будет выглядеть следующим образом (не включая импорты):
```java
public class Main {
    public static void main(String[] args) {
        start(args);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void start(String[] args) {
        ArgumentStorage argumentStorage = ArgumentProvider.getArguments(args);
        DirectoryManager directoryManager = new DirectoryManager(new DirectoryProvider(argumentStorage.getFilepath()));
        if (!directoryManager.createDirectoryIfNeed()) {
            Notificator.printWarning("Invalid path. Result will be in default location");
            argumentStorage.setFilepath("");
        } else {
            ConcurrentHashMap<DataType, Stats> statsMap = new ConcurrentHashMap<>();
            initializeStatsMap(statsMap);

            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (String fileName: argumentStorage.getFiles()) {
                executorService.execute(new FileService(argumentStorage, fileName, statsMap));
            }
            executorService.shutdown();
            try {
                while (!executorService.isTerminated()) {
                    executorService.awaitTermination(1, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
            printStats(statsMap, argumentStorage.isOptionF());
        }
    }

    private static void initializeStatsMap(ConcurrentHashMap<DataType, Stats> statsMap) {
        statsMap.put(DataType.FLOAT, new FloatStats());
        statsMap.put(DataType.INTEGER, new IntegerStats());
        statsMap.put(DataType.STRING, new StringsStats());
    }
    private static void printStats(ConcurrentHashMap<DataType, Stats> statsMap, boolean isOutFullStats) {
        statsMap.forEach(
                (key, stats) -> System.out.println(isOutFullStats
                    ? stats.getFullStats()
                    : stats.getShortStats(key.getTypeName().toLowerCase()))
        );
    }
}
```
Обновленный класс FileService (не включая импорты):
```java
public class FileService implements Runnable {
    private final ArgumentStorage argumentStorage;
    private final String fileName;
    private final ConcurrentHashMap<DataType, Stats> statsMap;

    public FileService(ArgumentStorage argumentStorage, String fileName, ConcurrentHashMap<DataType, Stats> statsMap) {
        this.argumentStorage = argumentStorage;
        this.fileName = fileName;
        this.statsMap = statsMap;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                updateDataTypeStats(DataTypeDeterminer.determineDataType(line), line);
            }
        } catch (FileNotFoundException exception) {
            Notificator.printWarning("Error: The file '" + fileName + "' was not found. Please check the file path and try again.");
        } catch (IOException exception) {
            Notificator.printWarning("Error: An unexpected error occurred while processing the file '" + fileName + "'.");
        }
    }



    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    private void updateDataTypeStats(DataType dataType, String line) {
        Stats stats = statsMap.get(dataType);
        if (stats != null) {
            synchronized (stats) {
                switch (dataType) {
                    case INTEGER -> stats.updateStats(new BigInteger(line));
                    case FLOAT -> stats.updateStats(new BigDecimal(line));
                    case STRING -> stats.updateStats(line);
                    default -> {
                        return;
                    }
                }
                writeToFile(dataType, line);
            }
        }
    }

    private synchronized void writeToFile(DataType dataType, String line) {
        String filepath = argumentStorage.getFilepath() + "/" + argumentStorage.getFileNamePrefix() + dataType.getFileName();
        File file = new File(filepath);

        try {
            if (statsMap.get(dataType).getCount() == 1) {
                if (file.exists() && !argumentStorage.isOptionA()) {
                    // Перезаписываем файл при первой записи данного типа данных и isOptionA равно false
                    writeLineToFile(file, line + "\n", false);
                } else if (!file.exists()) {
                    if (!file.createNewFile()) {
                        throw new IOException("Error: Could not create file");
                    } else {
                        writeLineToFile(file, line + "\n", true);
                    }
                } else {
                    writeLineToFile(file, line + "\n", true);
                }
            } else {
                writeLineToFile(file, line + "\n", true);
            }
        } catch (IOException exception) {
            Notificator.printWarning("Error: Could not create or write to file '" + filepath + "'. " + exception.getMessage());
        }
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    private void writeLineToFile(File file, String line, boolean isAppend) {
        synchronized (file) {
            try (FileWriter writer = new FileWriter(file, isAppend)) {
                writer.write(line);
            } catch (IOException e) {
                Notificator.printWarning("Error: Could not write to file '" + file.getPath() + "'.");
            }
        }
    }
}
```
#### Гайдлайн по добавлению нового типа данных

