# Домашнее задание для YLab university

### Что нужно сделать?
Реализуйте приложение командной строки, которое будет выводить все полные пути для данного ввода.

```bash
java -jar assignment.jar -f <xml_file> -s <input>
```

где:
xml_file - это путь к предоставленному XML-файлу; input - строка поиска для фильтрации путей.

Пример файла:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<node is-file="false">
   <name>/</name>
   <children>
      <child is-file="true">
         <name>file-776194140.xml</name>
      </child>
      <child is-file="false">
         <name>dir-880176375</name>
         <children>
            <child is-file="true">
               <name>file-1073842118.java</name>
            </child>
            <child is-file="false">
               <name>dir-2145307015</name>
               <children>
                  <child is-file="true">
                     <name>file-1498940214.xhtml</name>
                  </child>
               </children>
            </child>
         </children>
      </child>
   </children>
</node>
```

### Примеры выполнения:

**Без поиска:**

java -jar application.jar -f Data.xml

Результат:

```bash
/file-776194140.xml
/dir-880176375/file-1073842118.java
/dir-880176375/file-1498940214.xhtml
```

**Поиск по полному имени:**

java -jar application.jar -f Data.xml -s file-1498940214.xhtml

Результат:

```bash
/dir-880176375/file-1498940214.xhtml
```

**Поиск по маске:**

java -jar application.jar -f Data.xml -s *.java

Результат:

```bash
/dir-880176375/file-1073842118.java
```

**Поиск по регулярному выражению:**

java -jar application.jar -f Data.xml -S *?[a-z]{4}-\d+\.[a-z]+

Результат:

```bash
/file-776194140.xml
/dir-880176375/file-1073842118.java
/dir-880176375/file-1498940214.xhtml
```



### Как сделано

* Что бы запусить приложение нужно клонировать репозиторий на локальный компьютер собрать проект (например командой maven install...)
* Для тестирования классов использован JUnit + Mockito
* В проекте используется библиотека "commons-cli", из за этого что бы избежать лишней настройки classpath при запуске приложения maven собрал сборку с включенными зависимостями sax-xml-parser-0.0.1-SNAPSHOT-jar-with-dependencies.jar

Дальше можно запустить 

```bash
java -jar sax-xml-parser-0.0.1-SNAPSHOT-jar-with-dependencies.jar -f <xml_file> -s/-S <input> 
```

где:

**-f xml_file** - путь к файлу xml, обязательный параметр. Если будет не задан то будет вызвано исключение. Можно указать в качестве аргумента -f DataFile.xml, этот файл можно взять из resources.

**-s/-S input** - фильтр имен файлов, не обязательный параметр. Реализовано 2 варианта задания фильтра: 
1. По маске -s. Пример: `-s file-1*.xml`
2. По регулярному выражению -S. Пример: `-S .*?[a-z]{4}-\d+\.[a-z]+`



