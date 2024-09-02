authorization-uri：你的认证服务地址
token-uri：获取token地址
user-info-uri：获取用户信息地址
user-name-attribute：用户名，在获取用户信息接口返回的json中的key
redirect-uri：跳转地址，这个地址必须与服务认证那里配置的跳转地址一致。{baseUrl}系统会自动替换你当前服务的地址及端口，{registrationId} 会被替换成auth2
————————————————
版权声明：本文为CSDN博主「asoklove」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/asoklove/article/details/116978605

client  作用是获取token 存起来
resource 作用是 验证token
server 的作用是 提供验证地址

