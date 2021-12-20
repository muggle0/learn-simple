@echo off　　　　　　　　　　　不显示后续命令行及当前命令行
　　　　dir c:\*.* >a.txt　　　　　　　将c盘文件列表写入a.txt 
　　　　call c:\ucdos\ucdos.bat　　　　调用ucdos 
　　　　echo 你好 　　　　　　　　　　 显示"你好" 
　　　　pause 　　　　　　　　　　　　 暂停,等待按键继续 
　　　　rem 准备运行wps 　　　　　　　 注释：准备运行wps 
　　　　cd ucdos　　　　　　　　　　　 进入ucdos目录 
　　　　wps 　　　　　　　　　　　　　 运行wps　　
————————————————
版权声明：本文为CSDN博主「adelyspace」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/adelyspace/article/details/119757089

%[1-9]表示参数，参数是指在运行批处理文件时在文件名后加的以空格（或者Tab）分隔的字符串。变量可以从%0到%9，%0表示批处理命令本身，其它参数字符串用%1到%9顺序表示。

if errorlevel 2 goto x2

if [not] "参数"

if "%1"=="a" format a:

if exist c:\config.sys type c:\config.sys 

https://www.cnblogs.com/yigui/p/10889135.html