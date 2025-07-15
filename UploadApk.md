Для загрузки APK приложения в BrowserStack - отправить запрос
- через curl:
```
curl -u "bsuser_0ICnjh:WyBWBXh51LtBL12SNRrq" -F "file=@D:/Java Projects/qaguru/java basic/qaguru_basic_lesson21_mobile_automation_browserstack/WikipediaSample.apk" https://api-cloud.browserstack.com/app-automate/upload
```
- через Postman:
```
Метод - POST
URL - https://api-cloud.browserstack.com/app-automate/upload
Authorization: Basic Auth
Username / Password - те, которые генерирует BrowserStack в примере https://app-automate.browserstack.com/dashboard/qig/get-started
Body: form-data
   Key: "file"
   Value:  WikipediaSample.apk (указать путь к файлу)
```

В ответе вернётся app_url
```
{
    "app_url": "bs://8fc213a3d67e849a1aab033b6fc9c1d41a8ad835"
}
```