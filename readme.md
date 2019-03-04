# MyGenerator
JAVA риложение создает Exel (xlsx) файл, в котором содержатся данные рандомно выбранных людей.

- Имя, фамилия, отчество, возраст, дата рождения, ИНН, почтовый индекс, страна, область, город, улица, дом, квартира.
- Количество людей от 1 до 30.
- Возраст высчитывается в приложении автоматически, исходя из даты рождения;
- Приложение генерирует рандомный, но валидный ИНН для физического лица;
- Приложение создает PDF файл с теми же данными;

*Пререквизиты: Java 8, JDK,  Maven, Git*

### Установка и запуск
- С помощью git клонировать проект в локальную директорию;
- Внутри этой директории запустить терминал;
- Cобрать мавеном проект;
- Запустить исполняемый jar файл;

```sh
$ git clone git@github.com:Katerina100/mygenerator.git
$ mvn clean install
$ mvn package
$ java -jar target/mygenerator-1.0-SNAPSHOT-jar-with-dependencies.jar
```
> В корневой директории проекта будут сформированы 2 соответствующих файла

### Автор
Екатерина Абрамова