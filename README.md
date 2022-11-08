# АСУИ Сканер

Автоматизированная система учета имущества, в дальнейшем АСУИ.
Приложение предназачено для внутренного использования в группе компаний "Кортрос".
Используются с целью получения информации об имуществе по Штрихкоду. Информация берется из базы 1С АСУИ через веб сервис.

Настройки подключения к веб-сервису задаются в приложении, для хранения используется Shared Preferences.
В приложении ведется история сканирования вместе с информаций по имуещству.

Android API не ниже 23.

Используемые библиотеки и технологии:
- ZXing Android Embedded (https://github.com/journeyapps/zxing-android-embedded)
- Android Jetpack's Navigation
- Coroutines
- Retrofit + GSon
- Logging Interceptor
- Room
- Dagger 2

Система сборки проекта Gradle 7.4
