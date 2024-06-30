# Cinema World
## Android приложение клон кинопоиска

#### _Поддержка офлайн-режима_
#### _Для сетевого взаимодействия используется открытое API  - <code>[kinopoisk](https://kinopoiskapiunofficial.tech/)</code>_

## Реализованные фичи:
### 1. Просмотр списка счетов и общей суммы по ним

<img src="https://user-images.githubusercontent.com/6205122/233399923-abfb103d-3ccb-43fb-8865-e27c72481a5c.png" alt="drawing" width="300"/>


### 2. Создание нового счета

<img src="https://user-images.githubusercontent.com/6205122/233399974-50c5e620-057e-4d83-aba9-9adcfa2c1afa.png" alt="drawing" width="300"/>


### 3. Выбор валюты счета

<img src="https://user-images.githubusercontent.com/6205122/233400046-512c241d-831b-42f3-bc34-90f2ca1761ff.png" alt="drawing" width="300"/>


### 4. Редактирование счета

<img src="https://user-images.githubusercontent.com/6205122/233400098-c0ae31f8-6ea4-4942-989c-fba55c0d8478.png" alt="drawing" width="300"/>


## Стек технологий:
- язык программирования Kotlin
- UI на Jetpack Compose 
- Single Activity Application паттерн
- MVVM в презентационном слое на AAC (ViewModel + StateFlow)
- навигация Jetpack Navigation
- Многомодульность
- Dagger Hilt в качестве DI для организации архитектуры приложения
- Kotlin Coroutines для асинхронных операций + Flow
- Retrofit + Kotlin Serialization для сетевого взаимодействия
- Room для БД
- 5 unit-тестов для фичи списка счетов
- 1 UI-тест сценария открытия нового счета

## Многомодульность (14 модулей)
<img width="1441" alt="Снимок экрана 2023-04-20 в 16 20 50" src="https://user-images.githubusercontent.com/6205122/233400448-744191d2-c99c-41f8-862b-023df89041ea.png">

## Схема БД
<img width="1266" alt="Снимок экрана 2023-04-20 в 17 20 28" src="https://user-images.githubusercontent.com/6205122/233400767-45e271e9-ceca-414e-bc3d-bcd3a7942a7e.png">

## Дальнейшее развитие
1) Реализация фичей
    - Фильтры к поиску
    - просмотр коллекций фильмов, сортировка и очистка коллекции
    - увеличение фото жестами. 
2) Dagger Free Di подход
3) Перевод Room на KSP
4) Все в Compose
5) Jetpack Compose Navigation
6) Модернизировать дизайн
