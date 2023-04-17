# go 语言速成系列（1）-go 基本特性

## 前言

这个系列文章计划是速通go语言，通过上手gin框架和go-zero 框架来掌握go语言，对于大家而言，已经有了语法基础，而且大部分人不会愿意去花大量的时间去学基础的if else 。以我的经验来说学完go的基础不去实践，很难把go学好。我的博客的计划是在学习框架同时学习语法，这样理论上来说节省时间而且比较能看到成果。

本节主要讲解做web项目必要掌握的基本语法、指令和知识点，下一节直接进入go的web项目开发

## go 的安装

Go官网下载地址：https://golang.org/dl/

Go官方镜像站：https://golang.google.cn/dl/

下载对应版本安装完成后需要配置`GOROOT`和`GOPATH`环境变量，其中GOROOT是我们安装go开发包的路径，GOPATH是执行`go get`指令拉取三方依赖的时候的存储路径，相当于本地maven仓库地址。
`GOPROXY`是拉取依赖的代理地址，由于国内网络的特点需要将这个代理地址替换成国内镜像：
```
go env -w GOPROXY=https://goproxy.cn,direct
```

## go 数据类型
![](2023-01-28-21-10-32.png)

先记住这张图，后续做项目中遇到相关知识点在学习

## go 指令

- go env 用于打印Go语言的环境信息

![](2023-01-28-21-40-46.png)
![](2023-01-28-21-41-12.png)

- go get

## go module


