# service-desk-project

Spring-MVC приложение, которое служит для организации работы с заявками сотрудников организации (банка)

- авторизация и регистрация новых пользователей
- разграничение доступов в зависимости от роли пользователей (ADMIN, MANAGER, USER)
- работа с данными пользователей (CRUD операции)
- работа с заявками (обращениями) пользователей (CRUD операции)

Функциональность:

USER:
- создает заявку на обслуживание 
(5 категорий: 1. Закупка и выдача ТМЦ, транспорт; 2. Обслуживание помещений,
3. Обслуживание техники, 4. Установка/настройка программ, 5. Ошибки/доработки банковского ПО).

  От типа заявки зависит, в какое подразделение заявка попадает на исполнение
- может отредактировать, отменить свою заявку, видит ее статус, у кого из менеджеров находится в работе

MANAGER:
- создает заявку на обслуживание
- может отредактировать, отменить свою заявку, видит ее статус, у кого из менеджеров находится в работе
- берет в работу доступные для его подразделения заявки пользователей (в зависимости от типа заявки)

ADMIN:
- создает заявку на обслуживание
- может отредактировать, отменить свою заявку, видит ее статус, у кого из менеджеров находится в работе
- берет в работу доступные для его подразделения заявки пользователей (в зависимости от типа заявки)
- видит заявки всех пользователей, может изменить исполнителя по конкретной заявке
- видит список всех пользователей, может удалять/редактировать/добавлять новых пользователей

Стек основных технологий, использовавшихся в приложении:
•	Java 18
•	MySQL
•	Spring Boot
•	Spring MVC
•	Spring Security
•	Hibernate
•	Lombook
•	HTML, CSS + Thymeleaf
•	Maven